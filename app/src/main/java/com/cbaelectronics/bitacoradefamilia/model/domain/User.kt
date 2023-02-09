/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/7/23, 2:26 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.model.domain

import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.DatabaseField
import com.cbaelectronics.bitacoradefamilia.util.Constants
import com.google.gson.GsonBuilder
import java.sql.Timestamp
import java.util.*

data class User(
    var displayName: String? = null,
    var email: String? = null,
    var photoProfile: String? = null,
    var token: String? = null,
    var type: Int? = null,
    val registerDate: Date? = Timestamp(Date().time)
){

    var settings: UserSettings? = null

    fun toJSON(): Map<String, Any> {

        val JSON: MutableMap<String, Any> = mutableMapOf(
            DatabaseField.EMAIL.key to (email ?: ""),
            DatabaseField.DISPLAY_NAME.key to (displayName ?: ""),
            DatabaseField.PROFILE_IMAGE_URL.key to (photoProfile ?: ""),
            DatabaseField.TOKEN.key to (token ?: ""),
            DatabaseField.TYPE.key to (type ?: ""),
            DatabaseField.REGISTERED_DATE.key to (registerDate ?: "")
        )

        JSON[DatabaseField.SETTINGS.key] = settingsToJSON()

        return JSON
    }

    private fun settingsToJSON(): MutableMap<String, Any> {
        return settings?.toJSON() ?: mutableMapOf()
    }

    companion object {

        fun toJson(user: User): String {
            return GsonBuilder().setDateFormat(Constants.JSON_DATE_FORMAT).create().toJson(user)
        }

        fun fromJson(json: String): User? {
            return GsonBuilder().setDateFormat(Constants.JSON_DATE_FORMAT).create().fromJson(json, User::class.java)
        }

    }
}

data class UserSettings(
    var notifications: Boolean = Constants.NOTIFICATIONS
){
    fun toJSON() : MutableMap<String, Any>{

        return mutableMapOf(
            DatabaseField.NOTIFICATIONS.key to notifications
        )

    }

}
