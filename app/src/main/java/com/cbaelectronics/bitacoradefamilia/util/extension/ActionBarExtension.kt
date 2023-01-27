/**
 *  Created by CbaElectronics by Eduardo Sanchez on 27/1/23 10:51.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.util.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.util.UIConstants
import com.cbaelectronics.bitacoradefamilia.util.Util

fun ActionBar.transparent(context: Context){
    val drawable = ContextCompat.getDrawable(context, R.drawable.keyboard_arrow_left)
    drawable?.setColorFilter(ContextCompat.getColor(context, R.color.light), PorterDuff.Mode.SRC_ATOP);
    setDisplayHomeAsUpEnabled(true)
    setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(context, R.color.primary)))

    this.setHomeAsUpIndicator(drawable)
    this.elevation = 0F

    this.title = ""
}