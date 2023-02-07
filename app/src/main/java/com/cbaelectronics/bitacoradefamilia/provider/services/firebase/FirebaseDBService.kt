/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/7/23, 3:02 PM.
 *  www.cbaelectronics.com.ar
 */

/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/7/23, 11:53 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.provider.services.firebase

import com.cbaelectronics.bitacoradefamilia.model.domain.User
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
    NOTIFICATIONS("notifications")

}

object FirebaseDBService{

    // Properties
    private val usersRef = FirebaseFirestore.getInstance().collection(DatabaseField.USERS.key)

    // Public

    fun save(user: User) {

        user.email?.let { login ->
            usersRef.document(login).set(user)
        }
    }

}