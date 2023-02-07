/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/6/23, 3:42 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.login

import android.content.Context
import androidx.lifecycle.ViewModel
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.model.domain.User
import com.cbaelectronics.bitacoradefamilia.model.domain.UserSettings
import com.cbaelectronics.bitacoradefamilia.model.session.Session
import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.FirebaseDBService

class LoginViewModel: ViewModel() {

    // Properties

    var user = Session.instance.user ?: User()
    var settings = Session.instance.user?.settings ?: UserSettings()

    // Localization
    val title = R.string.login_title
    val body = R.string.login_body
    val button = R.string.login_button
    val wait = R.string.login_alert_wait
    val error = R.string.login_alert_error_user
    val errorToken = R.string.login_alert_error_token

    // Public

    fun save(user: User){
        FirebaseDBService.save(user)
    }

    fun configure(context: Context) {

        Session.instance.configure(context)

    }

    fun save(context: Context) {

        Session.instance.save(context, user)

    }

}