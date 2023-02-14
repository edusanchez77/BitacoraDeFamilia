/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/14/23, 3:33 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.model.domain

import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.DatabaseField
import com.cbaelectronics.bitacoradefamilia.util.Constants
import com.google.gson.GsonBuilder
import java.sql.Timestamp
import java.util.*

data class PediatricControl(
    val childrenId: String? = null,
    val date: Date? = null,
    val doctor: String? = null,
    val specialty: String? = null,
    val weight: String? = null,
    val height: Int? = null,
    val observation: String? = null,
    val nextControl: Date? = null,
    val notes: String? = null,
    val registeredBy: User,
    val registeredDate: Date? = Timestamp(Date().time)
){
    fun toJSON(): Map<String, Any> {

        val JSON: MutableMap<String, Any> = mutableMapOf(
            DatabaseField.CHILDREN_ID.key to (childrenId ?: ""),
            DatabaseField.DATE.key to (date ?: ""),
            DatabaseField.DOCTOR.key to (doctor ?: ""),
            DatabaseField.SPECIALTY.key to (specialty ?: ""),
            DatabaseField.WEIGHT.key to (weight ?: ""),
            DatabaseField.HEIGHT.key to (height ?: ""),
            DatabaseField.OBSERVATIONS.key to (observation ?: ""),
            DatabaseField.NEXT_CONTROL.key to (nextControl ?: ""),
            DatabaseField.NOTES.key to (notes ?: "-"),
            DatabaseField.REGISTERED_BY.key to (registeredBy ?: ""),
            DatabaseField.REGISTERED_DATE.key to (registeredDate ?: "")
        )

        return JSON
    }

    companion object {

        fun toJson(control: PediatricControl): String {
            return GsonBuilder().setDateFormat(Constants.JSON_DATE_FORMAT).create().toJson(control)
        }

        fun fromJson(json: String): PediatricControl? {
            return GsonBuilder().setDateFormat(Constants.JSON_DATE_FORMAT).create().fromJson(json, PediatricControl::class.java)
        }

    }
}
