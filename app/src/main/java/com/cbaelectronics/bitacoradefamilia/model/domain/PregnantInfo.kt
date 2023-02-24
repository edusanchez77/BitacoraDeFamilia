/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/23/23, 3:31 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.model.domain

import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.DatabaseField
import com.cbaelectronics.bitacoradefamilia.util.Constants
import com.google.gson.GsonBuilder
import java.sql.Timestamp
import java.util.*

data class PregnantInfo(
    val childrenId: String? = null,
    val mWhen: String? = null,
    val how: String? = null,
    val reactions: String? = null,
    val registeredBy: User,
    val registeredDate: Date? = Timestamp(Date().time)
){
    fun toJSON(): Map<String, Any> {

        val JSON: MutableMap<String, Any> = mutableMapOf(
            DatabaseField.CHILDREN_ID.key to (childrenId ?: ""),
            DatabaseField.WHEN.key to (mWhen ?: ""),
            DatabaseField.HOW.key to (how ?: ""),
            DatabaseField.REACTIONS.key to (reactions ?: ""),
            DatabaseField.REGISTERED_BY.key to (registeredBy ?: ""),
            DatabaseField.REGISTERED_DATE.key to (registeredDate ?: "")
        )

        return JSON
    }

    companion object {

        fun toJson(pregnantInfo: PregnantInfo): String {
            return GsonBuilder().setDateFormat(Constants.JSON_DATE_FORMAT).create().toJson(pregnantInfo)
        }

        fun fromJson(json: String): PregnantInfo? {
            return GsonBuilder().setDateFormat(Constants.JSON_DATE_FORMAT).create().fromJson(json, PregnantInfo::class.java)
        }

    }
}
