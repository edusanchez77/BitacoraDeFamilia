/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/6/23, 12:45 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.provider.firebase

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

enum class DatabaseField(val key: String) {

    // Schemes
    USERS("user"),
    FAMILY("family"),
    CHILDREN("children"),

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

object FirebaseDBService {

    // Properties
    private val userRef = FirebaseFirestore.getInstance().collection(DatabaseField.USERS.key)
    private val familyRef = FirebaseFirestore.getInstance().collection(DatabaseField.FAMILY.key)
    private val childrenRef = FirebaseFirestore.getInstance().collection(DatabaseField.CHILDREN.key)

    // Properties RealDataBase
    private val userRDBRef = FirebaseDatabase.getInstance().getReference(DatabaseField.USERS.key)
}