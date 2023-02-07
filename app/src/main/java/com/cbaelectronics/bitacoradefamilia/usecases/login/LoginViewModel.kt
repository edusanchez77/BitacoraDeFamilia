/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/6/23, 3:42 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.login

import androidx.lifecycle.ViewModel
import com.cbaelectronics.bitacoradefamilia.R

class LoginViewModel: ViewModel() {

    // Localization
    val title = R.string.login_title
    val body = R.string.login_body
    val button = R.string.login_button
    val wait = R.string.login_alert_wait
    val error = R.string.login_alert_error_user

}