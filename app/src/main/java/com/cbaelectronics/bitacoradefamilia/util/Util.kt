/**
 *  Created by CbaElectronics by Eduardo Sanchez on 26/1/23 15:12.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.DisplayMetrics
import com.cbaelectronics.bitacoradefamilia.BuildConfig

object Util {

    fun dpToPixel(context: Context, dp: Int): Float {
        return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun version(): String {
        return "${BuildConfig.VERSION_NAME}"
    }

    fun openBrowser(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

}