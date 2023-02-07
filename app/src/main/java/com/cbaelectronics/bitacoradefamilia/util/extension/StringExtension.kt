/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/7/23, 2:16 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.util.extension

fun String.removeFirebaseInvalidCharacters(): String {
    return replace(".", "")
        .replace("#", "")
        .replace("$", "")
        .replace("[", "")
        .replace("]", "")
}