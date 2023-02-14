/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/7/23, 2:16 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.util.extension

import com.cbaelectronics.bitacoradefamilia.util.Constants
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.math.BigDecimal
import java.math.RoundingMode

fun String.removeFirebaseInvalidCharacters(): String {
    return replace(".", "")
        .replace("#", "")
        .replace("$", "")
        .replace("[", "")
        .replace("]", "")
}

fun String.toDate(): Date? {
    var formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    var date = formatter.parse(this)
    if (date == null) {
        formatter = SimpleDateFormat("dd/MM/yyyy", Constants.DEFAULT_LOCALE)
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        date = formatter.parse(this)
    }
    return date
}

fun String.parseFirebase(): Date {
    val sdf3 = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
    return sdf3.parse(this)
}

fun String.toDouble(): Double {
    //val df = DecimalFormat("##.##")
    return BigDecimal(this.toDouble()).setScale(3, RoundingMode.FLOOR).toDouble()

}