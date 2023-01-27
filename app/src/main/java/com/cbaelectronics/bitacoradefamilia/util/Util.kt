/**
 *  Created by CbaElectronics by Eduardo Sanchez on 26/1/23 15:12.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.util

import android.content.Context
import android.util.DisplayMetrics

object Util {

    fun dpToPixel(context: Context, dp: Int): Float {
        return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

}