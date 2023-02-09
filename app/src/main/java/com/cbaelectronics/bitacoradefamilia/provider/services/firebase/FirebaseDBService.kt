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
import com.cbaelectronics.bitacoradefamilia.model.domain.Children
import com.cbaelectronics.bitacoradefamilia.model.domain.User
import com.cbaelectronics.bitacoradefamilia.util.extension.removeFirebaseInvalidCharacters
import com.google.firebase.firestore.FirebaseFirestore

enum class DatabaseField(val key: String) {

    // Schemes
    USERS("users"),
    CHILDREN("children"),

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
    NAME("childrenName"),
    GENRE("childrenGenre"),
    DATE("childrenDate"),
    HOUR("childrenHour"),
    WEIGHT("childrenWeight"),
    HEIGHT("childrenHeight"),
    AVATAR("childrenAvatar")

}

object FirebaseDBService{

    // Properties
    private val usersRef = FirebaseFirestore.getInstance().collection(DatabaseField.USERS.key)
    private val childreRef = FirebaseFirestore.getInstance().collection(DatabaseField.CHILDREN.key)

    // Public

    fun save(user: User) {

        user.email?.let { login ->
            usersRef.document(login).set(user.toJSON())
        }
    }

    fun save(children: Children){

        children.name?.let {
            childreRef.document().set(children.toJSON())
        }

    }

}