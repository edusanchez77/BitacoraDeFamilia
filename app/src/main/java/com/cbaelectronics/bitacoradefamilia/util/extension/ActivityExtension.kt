/**
 *  Created by CbaElectronics by Eduardo Sanchez on 27/1/23 15:06.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.util.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.cbaelectronics.bitacoradefamilia.R

fun AppCompatActivity.addClose(context: Context) {

    supportActionBar?.title = getString(R.string.app_name)
    supportActionBar?.elevation = 0f
    supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(context, R.color.primary)))

    val closeIcon = (ContextCompat.getDrawable(this, R.drawable.keyboard_arrow_left) as BitmapDrawable).bitmap
    val resizedCloseIcon: Drawable = BitmapDrawable(resources, Bitmap.createScaledBitmap(closeIcon, 48, 48, false))
    resizedCloseIcon.setTint(ContextCompat.getColor(this, R.color.light))
    supportActionBar?.setHomeAsUpIndicator(resizedCloseIcon)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
}

fun AppCompatActivity.addCloseWithoutTitle(context: Context) {

    supportActionBar?.title = ""
    supportActionBar?.elevation = 0f
    supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(context, R.color.primary)))

    val closeIcon = (ContextCompat.getDrawable(this, R.drawable.keyboard_arrow_left) as BitmapDrawable).bitmap
    val resizedCloseIcon: Drawable = BitmapDrawable(resources, Bitmap.createScaledBitmap(closeIcon, 48, 48, false))
    resizedCloseIcon.setTint(ContextCompat.getColor(this, R.color.light))
    supportActionBar?.setHomeAsUpIndicator(resizedCloseIcon)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
}

fun AppCompatActivity.addCloseWithoutArrow(context: Context) {

    supportActionBar?.title = context.getString(R.string.app_name)
    supportActionBar?.elevation = 0f
    supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(context, R.color.primary)))
    supportActionBar?.setDisplayHomeAsUpEnabled(false)
}

fun AppCompatActivity.hideSoftInput() {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
}