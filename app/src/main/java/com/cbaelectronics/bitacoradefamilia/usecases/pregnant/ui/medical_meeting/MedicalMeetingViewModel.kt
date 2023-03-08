/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/1/23, 9:41 AM.
 *  www.cbaelectronics.com.ar
 */


package com.cbaelectronics.bitacoradefamilia.usecases.pregnant.ui.medical_meeting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.model.domain.MedicalMeeting
import com.cbaelectronics.bitacoradefamilia.model.domain.User
import com.cbaelectronics.bitacoradefamilia.model.domain.UserSettings
import com.cbaelectronics.bitacoradefamilia.model.session.Session
import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.FirebaseDBService

class MedicalMeetingViewModel : ViewModel() {

    // Properties

    var user = Session.instance.user ?: User()
    var settings = Session.instance.user?.settings ?: UserSettings()
    var children = Session.instance.children
    var permission = Session.instance.permission

    // Localization

    val title = R.string.medmeeting_title
    val button = R.string.medmeeting_button

    // Public

    fun load(): LiveData<MutableList<MedicalMeeting>>{
        val mutableList = MutableLiveData<MutableList<MedicalMeeting>>()

        FirebaseDBService.loadMedicalMeeting(children?.id!!).observeForever {
            mutableList.value = it
        }

        return mutableList
    }
}