/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/8/23, 3:35 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.util.extension

import android.graphics.Typeface
import android.text.TextUtils
import android.util.TypedValue
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType
import com.cbaelectronics.bitacoradefamilia.util.UIConstants

fun Button.enable(enable: Boolean, opacity: Boolean = false) {
    if (opacity) {
        alpha = if (enable) 1f else UIConstants.SHADOW_OPACITY
    }
    isEnabled = enable
}

fun Button.navigation(listener: View.OnClickListener) {

    setTextSize(TypedValue.COMPLEX_UNIT_SP, FontSize.BODY.size.toFloat())
    setTextColor(ContextCompat.getColor(context, R.color.text))
    setTypeface(Typeface.createFromAsset(context.assets, FontType.LIGHT.path), Typeface.NORMAL)
    setOnClickListener(listener)

}

fun AppCompatButton.secondary(listener: View.OnClickListener) {

    maxLines = 1
    ellipsize = TextUtils.TruncateAt.END
    setTextSize(TypedValue.COMPLEX_UNIT_SP, FontSize.BUTTON.size.toFloat())
    setTextColor(ContextCompat.getColor(context, R.color.text))
    setTypeface(Typeface.createFromAsset(context.assets, FontType.BOLD.path), Typeface.NORMAL)
    background = ContextCompat.getDrawable(context, R.drawable.secondary_button_round)
    setOnClickListener(listener)

}