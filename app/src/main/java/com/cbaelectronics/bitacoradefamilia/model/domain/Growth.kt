/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/13/23, 3:06 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.model.domain

import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.DatabaseField
import com.cbaelectronics.bitacoradefamilia.util.Constants
import com.google.gson.GsonBuilder
import java.sql.Timestamp
import java.util.Date

data class Growth(
    val childrenId: String? = null,
    val date: Date? = null,
    val weight: Double? = null,
    val height: Int? = null,
    val pc: Int? = null,
    val registeredBy: User,
    val registeredDate: Date? = Timestamp(Date().time)
){
    fun toJSON(): Map<String, Any> {

        val JSON: MutableMap<String, Any> = mutableMapOf(
            DatabaseField.CHILDREN_ID.key to (childrenId ?: ""),
            DatabaseField.DATE.key to (date ?: ""),
            DatabaseField.WEIGHT.key to (weight ?: ""),
            DatabaseField.HEIGHT.key to (height ?: ""),
            DatabaseField.PC.key to (pc ?: "-"),
            DatabaseField.REGISTERED_BY.key to (registeredBy ?: ""),
            DatabaseField.REGISTERED_DATE.key to (registeredDate ?: "")
        )

        return JSON
    }

    companion object {

        fun toJson(growth: Growth): String {
            return GsonBuilder().setDateFormat(Constants.JSON_DATE_FORMAT).create().toJson(growth)
        }

        fun fromJson(json: String): Growth? {
            return GsonBuilder().setDateFormat(Constants.JSON_DATE_FORMAT).create().fromJson(json, Growth::class.java)
        }

    }
}
