/**
 *  Created by CbaElectronics by Eduardo Sanchez on 27/1/23 10:51.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.util.extension

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.util.UIConstants
import com.cbaelectronics.bitacoradefamilia.util.Util

fun ActionBar.transparent(context: Context){
    val drawable = ContextCompat.getDrawable(context, R.drawable.keyboard_arrow_left)
    drawable?.setColorFilter(ContextCompat.getColor(context, R.color.light), PorterDuff.Mode.SRC_ATOP);
    setDisplayHomeAsUpEnabled(false)
    setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(context, R.color.primary)))

    this.elevation = 0F

    this.title = ""
}

fun ActionBar.titleLogo(context: Context) {

    setDisplayShowHomeEnabled(true)
    setDisplayHomeAsUpEnabled(true)
    setHomeButtonEnabled(false)
    setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(context, R.color.primary)))

    val drawable = ContextCompat.getDrawable(context, R.drawable.logo_nombre)
    val bitmap = (drawable as BitmapDrawable).bitmap
    val height = Util.dpToPixel(context, UIConstants.LOGO_HEIGHT).toInt()
    val width = (height * bitmap.width) / bitmap.height
    val resizedDrawable = BitmapDrawable(context.resources, Bitmap.createScaledBitmap(bitmap, width, height, true))

    val closeIcon = (ContextCompat.getDrawable(context, R.drawable.keyboard_arrow_left) as BitmapDrawable).bitmap
    val resizedCloseIcon: Drawable = BitmapDrawable(context.resources, Bitmap.createScaledBitmap(closeIcon, 48, 48, false))
    resizedCloseIcon.setTint(ContextCompat.getColor(context, R.color.light))
    setHomeAsUpIndicator(resizedCloseIcon)

    setIcon(resizedDrawable)
    title = ""
    elevation = 0F
}