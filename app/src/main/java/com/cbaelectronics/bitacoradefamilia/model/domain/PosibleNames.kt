/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/17/23, 12:22 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.model.domain

import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.DatabaseField
import com.cbaelectronics.bitacoradefamilia.util.Constants
import com.google.gson.GsonBuilder
import java.sql.Timestamp
import java.util.*

data class PosibleNames(
    val childrenId: String? = null,
    val name: String? = null,
    val genre: String? = null,
    val registeredBy: User,
    val registeredDate: Date? = Timestamp(Date().time)
){
    fun toJSON(): Map<String, Any> {

        val JSON: MutableMap<String, Any> = mutableMapOf(
            DatabaseField.CHILDREN_ID.key to (childrenId ?: ""),
            DatabaseField.NAME.key to (name ?: ""),
            DatabaseField.GENRE.key to (genre ?: ""),
            DatabaseField.REGISTERED_BY.key to (registeredBy ?: ""),
            DatabaseField.REGISTERED_DATE.key to (registeredDate ?: "")
        )

        return JSON
    }

    companion object {

        fun toJson(names: PosibleNames): String {
            return GsonBuilder().setDateFormat(Constants.JSON_DATE_FORMAT).create().toJson(names)
        }

        fun fromJson(json: String): PosibleNames? {
            return GsonBuilder().setDateFormat(Constants.JSON_DATE_FORMAT).create().fromJson(json, PosibleNames::class.java)
        }

    }
}
