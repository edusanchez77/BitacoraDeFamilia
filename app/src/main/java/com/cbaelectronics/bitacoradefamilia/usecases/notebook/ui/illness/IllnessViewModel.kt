package com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.illness

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.model.domain.Illness
import com.cbaelectronics.bitacoradefamilia.model.domain.User
import com.cbaelectronics.bitacoradefamilia.model.domain.UserSettings
import com.cbaelectronics.bitacoradefamilia.model.session.Session
import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.FirebaseDBService

class IllnessViewModel : ViewModel() {

    // Properties

    var user = Session.instance.user ?: User()
    var settings = Session.instance.user?.settings ?: UserSettings()
    var children = Session.instance.children

    // Localization

    val title = R.string.illness_title
    val button = R.string.illness_button

    // Public

    fun load(): LiveData<MutableList<Illness>>{
        val mutableList = MutableLiveData<MutableList<Illness>>()

        FirebaseDBService.loadIllness(children?.id!!).observeForever {
            mutableList.value = it
        }

        return mutableList
    }

}