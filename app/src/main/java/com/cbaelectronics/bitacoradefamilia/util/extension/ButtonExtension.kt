/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/8/23, 3:35 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.util.extension

import android.widget.Button
import com.cbaelectronics.bitacoradefamilia.util.UIConstants

fun Button.enable(enable: Boolean, opacity: Boolean = false) {
    if (opacity) {
        alpha = if (enable) 1f else UIConstants.SHADOW_OPACITY
    }
    isEnabled = enable
}