/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/7/23, 11:53 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.provider.firebase

import com.cbaelectronics.bitacoradefamilia.util.extension.removeFirebaseInvalidCharacters
import com.google.firebase.firestore.FirebaseFirestore

enum class DatabaseField(val key: String) {

    // Schemes
    USERS("users"),

    // Generic Field
    SETTINGS("settings"),

    // User
    DISPLAY_NAME("displayName"),
    EMAIL("email"),
    PROFILE_IMAGE_URL("photoProfile"),
    REGISTER_DATE("registerDate"),
    TOKEN("tokenDevice"),
    TYPE("type"),
    PROFILE_CATEGORY("profileCategory"),
    PROFILE_POSITION("profilePosition"),
    NOTIFICATION_TURN("notificationTurn"),
    NOTIFICATION_POST("notificationPost")

}

object FirebaseDBService{

    // Properties
    private val usersRef = FirebaseFirestore.getInstance().collection(DatabaseField.USERS.key)

    // Public

    fun save(email: String, name: String) {

        val user = hashMapOf(
            DatabaseField.EMAIL.key to email,
            DatabaseField.DISPLAY_NAME.key to name
        )

        email.let { login ->
            usersRef.document(login).set(user)
        }
    }

}