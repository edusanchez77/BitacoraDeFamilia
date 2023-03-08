/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/8/23, 11:55 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.model.domain

import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.DatabaseField
import com.cbaelectronics.bitacoradefamilia.util.Constants
import com.google.gson.GsonBuilder
import java.sql.Timestamp
import java.util.*

enum class Genre(val type: String){
    BOY("Masculino"),
    WOMAN("Femenino"),
    INDETERMINATE("No Definido")
}

data class Children(
    val id: String? = null,
    var name: String? = null,
    var genre: String? = null,
    var date: String? = null,
    var hour: String? = null,
    var weight: String? = null,
    var height: String? = null,
    var avatar: String? = null,
    val registeredBy: User,
    val registeredDate: Date? = Timestamp(Date().time),
    val sharedWith: String? = null
){

    fun toJSON(): Map<String, Any> {

        val JSON: MutableMap<String, Any> = mutableMapOf(
            DatabaseField.CHILDREN_ID.key to (id ?: ""),
            DatabaseField.NAME.key to (name ?: ""),
            DatabaseField.GENRE.key to (genre ?: ""),
            DatabaseField.DATE_OF_BIRTH.key to (date ?: "-"),
            DatabaseField.HOUR_OF_BIRTH.key to (hour ?: "-"),
            DatabaseField.WEIGHT.key to (weight ?: "-"),
            DatabaseField.HEIGHT.key to (height ?: "-"),
            DatabaseField.AVATAR.key to (avatar ?: ""),
            DatabaseField.REGISTERED_BY.key to (registeredBy ?: ""),
            DatabaseField.REGISTERED_DATE.key to (registeredDate ?: ""),
            DatabaseField.SHARED_WITH.key to (sharedWith ?: "")
        )

        return JSON
    }

    companion object {

        fun toJson(children: Children): String {
            return GsonBuilder().setDateFormat(Constants.JSON_DATE_FORMAT).create().toJson(children)
        }

        fun fromJson(json: String): Children? {
            return GsonBuilder().setDateFormat(Constants.JSON_DATE_FORMAT).create().fromJson(json, Children::class.java)
        }

    }

}

data class ChildrenPermission(
    var permission: Int = Permission.READ.value
){
    fun toJSON() : MutableMap<String, Any>{

        return mutableMapOf(
            DatabaseField.PERMISSION.key to permission
        )

    }

}
