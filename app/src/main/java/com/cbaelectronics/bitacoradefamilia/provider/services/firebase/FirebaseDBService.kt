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
import android.text.style.TtsSpan.DateBuilder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cbaelectronics.bitacoradefamilia.model.domain.*
import com.cbaelectronics.bitacoradefamilia.util.extension.removeFirebaseInvalidCharacters
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

enum class DatabaseField(val key: String) {

    // Schemes
    USERS("users"),
    CHILDREN("children"),
    GROWTH("growth"),
    ILLNESS("illness"),
    PEDIATRIC_CONTROL("pediatric_control"),

    // Generic Field
    SETTINGS("settings"),
    REGISTERED_DATE("registerDate"),
    REGISTERED_BY("registeredBy"),
    SHARED_WITH("sharedBy"),
    NOTES("notes"),

    // User
    DISPLAY_NAME("displayName"),
    EMAIL("email"),
    PROFILE_IMAGE_URL("photoProfile"),
    TOKEN("tokenDevice"), TYPE("type"),
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
    PC("PC"),

    // Illness
    ILLNESS_NAME("illness"),
    SYMPTOM("symptom"),
    DURATION("duration"),
    MEDICATION("medication"),
    OBSERVATIONS("observations"),

    // Pediatric Control
    DOCTOR("doctor"),
    SPECIALTY("specialty"),
    NEXT_CONTROL("nextControl")

}

object FirebaseDBService {

    // Properties
    private val usersRef = FirebaseFirestore.getInstance().collection(DatabaseField.USERS.key)
    private val childreRef = FirebaseFirestore.getInstance().collection(DatabaseField.CHILDREN.key)
    private val growthRef = FirebaseFirestore.getInstance().collection(DatabaseField.GROWTH.key)
    private val illnessRef = FirebaseFirestore.getInstance().collection(DatabaseField.ILLNESS.key)
    private val controlRef =
        FirebaseFirestore.getInstance().collection(DatabaseField.PEDIATRIC_CONTROL.key)

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

        childreRef.whereEqualTo(
            "${DatabaseField.REGISTERED_BY.key}.${DatabaseField.EMAIL.key}", user?.email
        ).addSnapshotListener { value, error ->
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

    fun save(growth: Growth) {

        growth.childrenId.let {
            growthRef.document().set(growth.toJSON())
        }

    }

    fun load(childrenId: String): LiveData<MutableList<Growth>> {

        val mutableData = MutableLiveData<MutableList<Growth>>()

        growthRef
            .whereEqualTo(DatabaseField.CHILDREN_ID.key, childrenId)
            .orderBy(DatabaseField.DATE.key, Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                val listData = mutableListOf<Growth>()

                for (document in value!!) {
                    val registeredByData =
                        document.data[DatabaseField.REGISTERED_BY.key] as Map<String, Any>
                    val date = document.getDate(DatabaseField.DATE.key)
                    val weight = document.get(DatabaseField.WEIGHT.key).toString()
                    val height = document.getLong(DatabaseField.HEIGHT.key)?.toInt()
                    val pc = document.get(DatabaseField.PC.key).toString().toInt()
                    val registeredDate = document.getDate(DatabaseField.REGISTERED_DATE.key)

                    val usrEmail = registeredByData[DatabaseField.EMAIL.key].toString()
                    val usrName = registeredByData[DatabaseField.DISPLAY_NAME.key].toString()
                    val usrPhoto =
                        registeredByData[DatabaseField.PROFILE_IMAGE_URL.key].toString()
                    val usrRegisteredDate =
                        document.getDate("${DatabaseField.REGISTERED_BY.key}.${DatabaseField.REGISTERED_DATE.key}")
                    val usrToken = registeredByData[DatabaseField.TOKEN.key].toString()
                    val usrType = registeredByData[DatabaseField.TYPE.key].toString().toInt()

                    val user =
                        User(usrName, usrEmail, usrPhoto, usrToken, usrType, usrRegisteredDate)
                    val growth = Growth(childrenId, date, weight, height, pc, user, registeredDate)

                    listData.add(growth)
                }
                mutableData.value = listData
            }

        return mutableData
    }

    fun save(illness: Illness) {

        illness.childrenId.let {
            illnessRef.document().set(illness.toJSON())
        }

    }

    fun loadIllness(childrenId: String): LiveData<MutableList<Illness>> {

        val mutableList = MutableLiveData<MutableList<Illness>>()

        illnessRef.orderBy(DatabaseField.DATE.key)
            .addSnapshotListener { value, error ->

                val listData = mutableListOf<Illness>()

                for (document in value!!) {
                    val registeredByData =
                        document.data[DatabaseField.REGISTERED_BY.key] as Map<String, Any>

                    val date = document.getDate(DatabaseField.DATE.key)
                    val illnessName = document.get(DatabaseField.ILLNESS_NAME.key)
                    val symptom = document.get(DatabaseField.SYMPTOM.key)
                    val duration = document.getLong(DatabaseField.DURATION.key)?.toInt()
                    val medication = document.get(DatabaseField.MEDICATION.key)
                    val observation = document.get(DatabaseField.OBSERVATIONS.key)
                    val registeredDate = document.getDate(DatabaseField.REGISTERED_DATE.key)

                    val usrEmail = registeredByData[DatabaseField.EMAIL.key].toString()
                    val usrName = registeredByData[DatabaseField.DISPLAY_NAME.key].toString()
                    val usrPhoto =
                        registeredByData[DatabaseField.PROFILE_IMAGE_URL.key].toString()
                    val usrRegisteredDate =
                        document.getDate("${DatabaseField.REGISTERED_BY.key}.${DatabaseField.REGISTERED_DATE.key}")
                    val usrToken = registeredByData[DatabaseField.TOKEN.key].toString()
                    val usrType = registeredByData[DatabaseField.TYPE.key].toString().toInt()

                    val user =
                        User(usrName, usrEmail, usrPhoto, usrToken, usrType, usrRegisteredDate)
                    val illness = Illness(
                        childrenId,
                        date,
                        illnessName.toString(),
                        symptom.toString(),
                        duration,
                        medication.toString(),
                        observation.toString(),
                        user,
                        registeredDate
                    )

                    listData.add(illness)
                }

                mutableList.value = listData

            }

        return mutableList

    }

    fun save(control: PediatricControl) {
        control.registeredBy.email.let {
            controlRef.document().set(control.toJSON())
        }
    }

    fun loadControl(childrenId: String): LiveData<MutableList<PediatricControl>> {

        val mutableList = MutableLiveData<MutableList<PediatricControl>>()

        controlRef.whereEqualTo(DatabaseField.CHILDREN_ID.key, childrenId)
            .addSnapshotListener { value, error ->

                val listData = mutableListOf<PediatricControl>()

                for (document in value!!) {
                    val registeredByData =
                        document.data[DatabaseField.REGISTERED_BY.key] as Map<String, Any>

                    val date = document.getDate(DatabaseField.DATE.key)
                    val doctor = document.get(DatabaseField.DOCTOR.key)
                    val specialty = document.get(DatabaseField.SPECIALTY.key)
                    val weight = document.get(DatabaseField.WEIGHT.key)
                    val height = document.getLong(DatabaseField.HEIGHT.key)
                    val observation = document.get(DatabaseField.OBSERVATIONS.key)
                    val next = document.getDate(DatabaseField.NEXT_CONTROL.key)
                    val notes = document.get(DatabaseField.NOTES.key)
                    val registeredDate = document.getDate(DatabaseField.REGISTERED_DATE.key)

                    val usrEmail = registeredByData[DatabaseField.EMAIL.key].toString()
                    val usrName = registeredByData[DatabaseField.DISPLAY_NAME.key].toString()
                    val usrPhoto =
                        registeredByData[DatabaseField.PROFILE_IMAGE_URL.key].toString()
                    val usrRegisteredDate =
                        document.getDate("${DatabaseField.REGISTERED_BY.key}.${DatabaseField.REGISTERED_DATE.key}")
                    val usrToken = registeredByData[DatabaseField.TOKEN.key].toString()
                    val usrType = registeredByData[DatabaseField.TYPE.key].toString().toInt()

                    val user =
                        User(usrName, usrEmail, usrPhoto, usrToken, usrType, usrRegisteredDate)
                    val control = PediatricControl(
                        childrenId,
                        date,
                        doctor.toString(),
                        specialty.toString(),
                        weight.toString(),
                        height?.toInt(),
                        observation.toString(),
                        next,
                        notes.toString(),
                        user,
                        registeredDate
                    )

                    listData.add(control)
                }

                mutableList.value = listData

            }

        return mutableList
    }

}