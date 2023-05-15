/**
 *  Created by CbaElectronics by Eduardo Sanchez on 3/29/23, 11:22 PM.
 *  www.cbaelectronics.com.ar
 */

/**
 *  Created by CbaElectronics by Eduardo Sanchez on 3/29/23, 11:22 PM.
 *  www.cbaelectronics.com.ar
 */

/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/26/23, 4:57 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.model.domain

import android.text.style.TtsSpan.DateBuilder
import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.DatabaseField
import com.cbaelectronics.bitacoradefamilia.util.Constants
import com.google.gson.GsonBuilder
import java.sql.Timestamp
import java.util.*

data class Echography(
    val id: String? = null,
    val childrenId: String? = null,
    val date: Date? = null,
    val week: Int? = null,
    val type: String? = null,
    val notes: String? = null,
    val image: String? = null,
    val registeredBy: User,
    val registeredDate: Date? = Timestamp(Date().time)
){
    fun toJSON(): Map<String, Any> {

        val JSON: MutableMap<String, Any> = mutableMapOf(
            DatabaseField.ID.key to (id ?: ""),
            DatabaseField.CHILDREN_ID.key to (childrenId ?: ""),
            DatabaseField.DATE.key to (date ?: ""),
            DatabaseField.WEEK.key to (week ?: ""),
            DatabaseField.ECHOGRAPHY_TYPE.key to (type ?: ""),
            DatabaseField.FIELD_NOTES.key to (notes ?: "-"),
            DatabaseField.IMAGE.key to (image ?: ""),
            DatabaseField.REGISTERED_BY.key to (registeredBy ?: ""),
            DatabaseField.REGISTERED_DATE.key to (registeredDate ?: "")
        )

        return JSON
    }

    companion object {

        fun toJson(echography: Echography): String {
            return GsonBuilder().setDateFormat(Constants.JSON_DATE_FORMAT).create().toJson(echography)
        }

        fun fromJson(json: String): Echography? {
            return GsonBuilder().setDateFormat(Constants.JSON_DATE_FORMAT).create().fromJson(json, Echography::class.java)
        }

    }
}
