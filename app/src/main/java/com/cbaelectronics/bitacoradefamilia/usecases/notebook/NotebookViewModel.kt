/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/13/23, 2:50 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.notebook

import android.content.Context
import androidx.lifecycle.ViewModel
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.model.domain.Children
import com.cbaelectronics.bitacoradefamilia.model.domain.User
import com.cbaelectronics.bitacoradefamilia.model.domain.UserSettings
import com.cbaelectronics.bitacoradefamilia.model.session.Session
import com.cbaelectronics.bitacoradefamilia.provider.preferences.PreferencesProvider
import com.cbaelectronics.bitacoradefamilia.usecases.login.LoginRouter

class NotebookViewModel: ViewModel() {

    // Properties

    var user = Session.instance.user ?: User()
    var settings = Session.instance.user?.settings ?: UserSettings()
    var children = Session.instance.children
    var permission = Session.instance.permission
    var childrenShared: Children? = null

    // Localization

    val alertShared = R.string.menu_alert_notShared
    val alertLogout = R.string.session_alert_logout
    val alertButtonOk = R.string.session_alert_button_ok
    val alertButtonCancel = R.string.session_alert_button_cancel

    // Public

    fun close(context: Context) {
        PreferencesProvider.clear(context)
        LoginRouter().launch(context)
    }

}