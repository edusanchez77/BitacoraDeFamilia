/**
 *  Created by CbaElectronics by Eduardo Sanchez on 31/1/23 09:56.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.util.extension

import android.graphics.Typeface
import android.util.TypedValue
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.util.FontSize
import com.cbaelectronics.bitacoradefamilia.util.FontType

fun TextView.font(size: FontSize, type: FontType? = null, color: Int = ContextCompat.getColor(context, R.color.text)){

    var fontType = type

    if (type == null) {
        fontType = when (size) {
            FontSize.TITLE, FontSize.HEAD, FontSize.BUTTON, FontSize.CAPTION -> FontType.BOLD
            FontSize.SUBTITLE, FontSize.SUBHEAD -> FontType.LIGHT
            FontSize.BODY -> FontType.REGULAR
        }
    }

    setTextSize(TypedValue.COMPLEX_UNIT_SP, size.size.toFloat())
    //typeface = Typeface.createFromAsset(context.assets,fontType!!.path);
    //typeface = ResourcesCompat.getFont(context, fontType!!.path);
    //setTypeface(Typeface.createFromAsset(context.assets, fontType!!.path), Typeface.NORMAL)
    setTextColor(color)
    includeFontPadding = false

}