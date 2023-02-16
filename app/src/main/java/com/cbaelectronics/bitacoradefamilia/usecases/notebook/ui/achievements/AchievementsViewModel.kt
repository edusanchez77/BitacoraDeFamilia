/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/1/23, 8:06 AM.
 *  www.cbaelectronics.com.ar
 */

/**
 *  Created by CbaElectronics by Eduardo Sanchez on 30/1/23 16:18.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.achievements

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.model.domain.Achievements
import com.cbaelectronics.bitacoradefamilia.model.domain.User
import com.cbaelectronics.bitacoradefamilia.model.domain.UserSettings
import com.cbaelectronics.bitacoradefamilia.model.session.Session
import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.FirebaseDBService

class AchievementsViewModel : ViewModel() {

    // Properties

    var user = Session.instance.user ?: User()
    var settings = Session.instance.user?.settings ?: UserSettings()
    var children = Session.instance.children

    // Localization

    val title = R.string.achievements_title
    val button = R.string.achievements_button

    // Public

    fun load(): LiveData<MutableList<Achievements>>{
        val mutableList = MutableLiveData<MutableList<Achievements>>()

        FirebaseDBService.loadAchievement(children?.id!!).observeForever {
            mutableList.value = it
        }

        return mutableList
    }
}