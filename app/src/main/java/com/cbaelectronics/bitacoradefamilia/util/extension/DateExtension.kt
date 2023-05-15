/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/14/23, 9:01 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.util.extension

import android.content.Context
import com.cbaelectronics.bitacoradefamilia.util.Constants
import java.sql.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun Date.toJSON(): String {
    val formatter = SimpleDateFormat("EEEE, dd 'de' MMMM, HH:mm", Locale.getDefault())
    return formatter.format(this)
}

fun Date.calendarDate() : String{
    val formatter = SimpleDateFormat(Constants.DATE, Locale.getDefault())
    return formatter.format(this)
}

fun Date.calendarHour() : String{
    val formatter = SimpleDateFormat(Constants.HOUR, Locale.getDefault())
    return formatter.format(this)
}

fun Date.longFormat(): String {
    return DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.SHORT, Locale.getDefault()).format(this).uppercaseFirst()
}

fun Date.mediumFormat(): String {
    return DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale.getDefault()).format(this).uppercaseFirst()
}

fun Date.shortFormat(): String {
    return DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault()).format(this).format(this).uppercaseFirst()
}

fun Date.customShortFormat(): String {

    val formatter = SimpleDateFormat(Constants.DATE_COMPLETE, Locale.getDefault())
    return formatter.format(this)
}

fun Date.dayOfWeek(): String{
    val formatter = SimpleDateFormat("EEEE", Locale.getDefault())
    return formatter.format(this)
}

fun Date.hourFixedTurnFormat(): String {

    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    return formatter.format(this)

}

fun Date.hourFixedTurnFormatSchedule(): String {

    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    return "${formatter.format(this)}hs."

}

fun Date.addDays(fecha: Date?, dias: Int): Date? {
    if (dias == 0) return fecha
    val calendar = Calendar.getInstance()
    calendar.time = fecha
    calendar.add(Calendar.DAY_OF_YEAR, dias)
    return calendar.time
}


fun Date.addOrRemoveDays(count: Int, flagStart: Boolean): Date?{
    return if(flagStart)
        Timestamp(Date().time + (1000 * 60 * 60 * (1 ?: 0) * (24 * (-(count-1))))).calendarDate().toDate()
    else
        Timestamp(Date().time + (1000 * 60 * 60 * (1 ?: 0) * (24 * (8 - count)))).calendarDate().toDate()
}