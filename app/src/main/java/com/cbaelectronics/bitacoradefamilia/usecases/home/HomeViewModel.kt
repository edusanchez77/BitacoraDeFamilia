/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/9/23, 3:33 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.model.domain.Children
import com.cbaelectronics.bitacoradefamilia.model.domain.User
import com.cbaelectronics.bitacoradefamilia.model.domain.UserSettings
import com.cbaelectronics.bitacoradefamilia.model.session.Session
import com.cbaelectronics.bitacoradefamilia.provider.preferences.PreferencesProvider
import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.FirebaseDBService
import com.cbaelectronics.bitacoradefamilia.usecases.login.LoginRouter

class HomeViewModel: ViewModel() {

    // Properties

    var user = Session.instance.user ?: User()
    var settings = Session.instance.user?.settings ?: UserSettings()

    // Localization

    val title = R.string.app_name
    val alertLogout = R.string.session_alert_logout
    val alertButtonOk = R.string.session_alert_button_ok
    val alertButtonCancel = R.string.session_alert_button_cancel

    // Public

    fun load(): LiveData<MutableList<Children>> {
        val mutableData = MutableLiveData<MutableList<Children>>()

        FirebaseDBService.load(user).observeForever{
            mutableData.value = it
        }

        return mutableData
    }

    fun close(context: Context) {
        PreferencesProvider.clear(context)
        LoginRouter().launch(context)
    }
}