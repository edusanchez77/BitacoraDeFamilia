/**
 *  Created by CbaElectronics by Eduardo Sanchez on 3/29/23, 11:22 PM.
 *  www.cbaelectronics.com.ar
 */

/**
 *  Created by CbaElectronics by Eduardo Sanchez on 3/29/23, 11:22 PM.
 *  www.cbaelectronics.com.ar
 */

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
    var date: Date? = null,
    var weight: String? = null,
    var height: String? = null,
    var avatar: String? = Constants.AVATAR_DEFAULT,
    val registeredBy: User,
    val registeredDate: Date? = Timestamp(Date().time),
    val permission: Int? = Permission.ADMIN.value
){

    fun toJSON(): Map<String, Any> {

        return mutableMapOf(
            DatabaseField.CHILDREN_ID.key to (id ?: ""),
            DatabaseField.NAME.key to (name ?: ""),
            DatabaseField.GENRE.key to (genre ?: ""),
            DatabaseField.DATE_OF_BIRTH.key to (date ?: ""),
            DatabaseField.WEIGHT.key to (weight ?: ""),
            DatabaseField.HEIGHT.key to (height ?: ""),
            DatabaseField.AVATAR.key to (avatar ?: Constants.AVATAR_DEFAULT),
            DatabaseField.REGISTERED_BY.key to (registeredBy ?: ""),
            DatabaseField.REGISTERED_DATE.key to (registeredDate ?: ""),
            DatabaseField.PERMISSION.key to (permission ?: "")
        )
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
