/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/7/23, 3:02 PM.
 *  www.cbaelectronics.com.ar
 */

/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/7/23, 11:53 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.provider.services.firebase

import android.provider.ContactsContract.Data
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cbaelectronics.bitacoradefamilia.model.domain.Children
import com.cbaelectronics.bitacoradefamilia.model.domain.Growth
import com.cbaelectronics.bitacoradefamilia.model.domain.User
import com.cbaelectronics.bitacoradefamilia.util.extension.removeFirebaseInvalidCharacters
import com.google.firebase.firestore.FirebaseFirestore

enum class DatabaseField(val key: String) {

    // Schemes
    USERS("users"),
    CHILDREN("children"),
    GROWTH("growth"),

    // Generic Field
    SETTINGS("settings"),
    REGISTERED_DATE("registerDate"),
    REGISTERED_BY("registeredBy"),
    SHARED_WITH("sharedBy"),

    // User
    DISPLAY_NAME("displayName"),
    EMAIL("email"),
    PROFILE_IMAGE_URL("photoProfile"),
    TOKEN("tokenDevice"),
    TYPE("type"),
    NOTIFICATIONS("notifications"),

    // Children
    CHILDREN_ID("childrenId"),
    NAME("name"),
    GENRE("genre"),
    DATE_OF_BIRTH("dateOfBirth"),
    HOUR_OF_BIRTH("hourOfBirth"),
    WEIGHT("weight"),
    HEIGHT("height"),
    AVATAR("childrenAvatar"),

    // Growth
    DATE("date"),
    PC("PC")

}

object FirebaseDBService {

    // Properties
    private val usersRef = FirebaseFirestore.getInstance().collection(DatabaseField.USERS.key)
    private val childreRef = FirebaseFirestore.getInstance().collection(DatabaseField.CHILDREN.key)
    private val growthRef = FirebaseFirestore.getInstance().collection(DatabaseField.GROWTH.key)


    // Public

    fun save(user: User) {

        user.email?.let { login ->
            usersRef.document(login).set(user.toJSON())
        }
    }

    fun save(children: Children) {

        children.name?.let {
            childreRef.document().set(children.toJSON())
        }

    }

    fun load(user: User): LiveData<MutableList<Children>> {
        val mutableData = MutableLiveData<MutableList<Children>>()

        childreRef
            .whereEqualTo(
                "${DatabaseField.REGISTERED_BY.key}.${DatabaseField.EMAIL.key}",
                user?.email
            )
            .addSnapshotListener { value, error ->
                val listData = mutableListOf<Children>()

                for (document in value!!) {

                    val registeredByData =
                        document.data[DatabaseField.REGISTERED_BY.key] as Map<String, Any>
                    val id = document.id
                    val name = document.get(DatabaseField.NAME.key).toString()
                    val genre = document.get(DatabaseField.GENRE.key).toString()
                    val date = document.get(DatabaseField.DATE_OF_BIRTH.key).toString()
                    val hour = document.get(DatabaseField.HOUR_OF_BIRTH.key).toString()
                    val weight = document.get(DatabaseField.WEIGHT.key).toString()
                    val height = document.get(DatabaseField.HEIGHT.key).toString()
                    val registeredDate = document.getDate(DatabaseField.REGISTERED_DATE.key)
                    val usrEmail = registeredByData.get(DatabaseField.EMAIL.key).toString()
                    val usrName = registeredByData.get(DatabaseField.DISPLAY_NAME.key).toString()
                    val usrPhoto =
                        registeredByData.get(DatabaseField.PROFILE_IMAGE_URL.key).toString()
                    val usrRegisteredDate =
                        document.getDate("${DatabaseField.REGISTERED_BY.key}.${DatabaseField.REGISTERED_DATE.key}")
                    val usrToken = registeredByData.get(DatabaseField.TOKEN.key).toString()
                    val usrType = registeredByData.get(DatabaseField.TYPE.key).toString().toInt()

                    val user =
                        User(usrName, usrEmail, usrPhoto, usrToken, usrType, usrRegisteredDate)
                    val children = Children(
                        id = id,
                        name = name,
                        genre = genre,
                        date = date,
                        hour = hour,
                        weight = weight,
                        height = height,
                        registeredDate = registeredDate,
                        registeredBy = user
                    )

                    listData.add(children!!)
                }
                mutableData.value = listData
            }

        return mutableData
    }

    fun save(growth: Growth){

        growth.childrenId.let {
            growthRef.document().set(growth.toJSON())
        }

    }

}