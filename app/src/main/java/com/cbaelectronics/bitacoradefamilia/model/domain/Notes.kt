/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/15/23, 9:41 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.model.domain

import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.DatabaseField
import com.cbaelectronics.bitacoradefamilia.util.Constants
import com.google.gson.GsonBuilder
import java.sql.Timestamp
import java.util.*

data class Notes(
    val childrenId: String? = null,
    val date: Date? = null,
    val notes: String? = null,
    val registeredBy: User,
    val registeredDate: Date? = Timestamp(Date().time)
){
    fun toJSON(): Map<String, Any> {

        val JSON: MutableMap<String, Any> = mutableMapOf(
            DatabaseField.CHILDREN_ID.key to (childrenId ?: ""),
            DatabaseField.DATE.key to (date ?: ""),
            DatabaseField.FIELD_NOTES.key to (notes ?: "-"),
            DatabaseField.REGISTERED_BY.key to (registeredBy ?: ""),
            DatabaseField.REGISTERED_DATE.key to (registeredDate ?: "")
        )

        return JSON
    }

    companion object {

        fun toJson(notes: Notes): String {
            return GsonBuilder().setDateFormat(Constants.JSON_DATE_FORMAT).create().toJson(notes)
        }

        fun fromJson(json: String): Notes? {
            return GsonBuilder().setDateFormat(Constants.JSON_DATE_FORMAT).create().fromJson(json, Notes::class.java)
        }

    }
}
