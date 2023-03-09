/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/9/23, 12:50 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.util

import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope

object UIUtil {

    fun showAlert(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}