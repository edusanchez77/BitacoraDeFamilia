/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/23/23, 8:00 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.model.domain

import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.DatabaseField
import com.cbaelectronics.bitacoradefamilia.util.Constants
import com.google.gson.GsonBuilder
import java.sql.Timestamp
import java.util.*

data class ControlWeight(
    val childrenId: String? = null,
    val week: Int? = null,
    val weight: String? = null,
    val registeredBy: User,
    val registeredDate: Date? = Timestamp(Date().time)
){
    fun toJSON(): Map<String, Any> {

        val JSON: MutableMap<String, Any> = mutableMapOf(
            DatabaseField.CHILDREN_ID.key to (childrenId ?: ""),
            DatabaseField.WEEK.key to (week ?: 0),
            DatabaseField.WEIGHT.key to (weight ?: ""),
            DatabaseField.REGISTERED_BY.key to (registeredBy ?: ""),
            DatabaseField.REGISTERED_DATE.key to (registeredDate ?: "")
        )

        return JSON
    }

    companion object {

        fun toJson(weight: ControlWeight): String {
            return GsonBuilder().setDateFormat(Constants.JSON_DATE_FORMAT).create().toJson(weight)
        }

        fun fromJson(json: String): ControlWeight? {
            return GsonBuilder().setDateFormat(Constants.JSON_DATE_FORMAT).create().fromJson(json, ControlWeight::class.java)
        }

    }
}
