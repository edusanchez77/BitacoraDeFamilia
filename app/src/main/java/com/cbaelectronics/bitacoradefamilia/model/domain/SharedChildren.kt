/**
 *  Created by CbaElectronics by Eduardo Sanchez on 3/7/23, 3:48 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.model.domain

import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.DatabaseField
import com.cbaelectronics.bitacoradefamilia.util.Constants
import com.google.gson.GsonBuilder
import java.sql.Timestamp
import java.util.*

enum class Permission(val type: String, val value: Int){
    READ("Solo lectura", 0),
    WRITE("Editar información", 1),
    ADMIN("Administrador", 2)
}

data class SharedChildren(
    val id: String? = null,
    var name: String? = null,
    var genre: String? = null,
    var avatar: String? = null,
    val registeredBy: User,
    val registeredDate: Date? = Timestamp(Date().time),
    val email: String,
    val permission: Int
) {

    fun toJSON(): Map<String, Any> {

        val JSON: MutableMap<String, Any> = mutableMapOf(
            DatabaseField.CHILDREN_ID.key to (id ?: ""),
            DatabaseField.NAME.key to (name ?: ""),
            DatabaseField.GENRE.key to (genre ?: ""),
            DatabaseField.AVATAR.key to (avatar ?: ""),
            DatabaseField.REGISTERED_BY.key to (registeredBy ?: ""),
            DatabaseField.REGISTERED_DATE.key to (registeredDate ?: ""),
            DatabaseField.EMAIL.key to (email ?: ""),
            DatabaseField.PERMISSION.key to (permission ?: 0)
        )

        return JSON
    }

    companion object {

        fun toJson(sharedChildren: SharedChildren): String {
            return GsonBuilder().setDateFormat(Constants.JSON_DATE_FORMAT).create().toJson(sharedChildren)
        }

        fun fromJson(json: String): SharedChildren? {
            return GsonBuilder().setDateFormat(Constants.JSON_DATE_FORMAT).create().fromJson(json, SharedChildren::class.java)
        }

    }

}