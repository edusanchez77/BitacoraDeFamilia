/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/1/23, 7:49 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.pediatric_control

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.model.domain.PediatricControl
import com.cbaelectronics.bitacoradefamilia.model.domain.User
import com.cbaelectronics.bitacoradefamilia.model.domain.UserSettings
import com.cbaelectronics.bitacoradefamilia.model.session.Session
import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.FirebaseDBService

class PediatricControlViewModel : ViewModel() {

    // Properties

    var user = Session.instance.user ?: User()
    var settings = Session.instance.user?.settings ?: UserSettings()
    var children = Session.instance.children

    // Localization

    val title = R.string.control_title
    val button = R.string.control_button

    // Public

    fun load(): LiveData<MutableList<PediatricControl>> {
        val mutableList = MutableLiveData<MutableList<PediatricControl>>()

        FirebaseDBService.loadControl(children?.id!!).observeForever {
            mutableList.value = it
        }

        return mutableList
    }

}