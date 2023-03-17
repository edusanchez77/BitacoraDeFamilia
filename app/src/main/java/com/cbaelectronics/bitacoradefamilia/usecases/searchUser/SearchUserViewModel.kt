/**
 *  Created by CbaElectronics by Eduardo Sanchez on 3/16/23, 11:17 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.searchUser

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.model.domain.User
import com.cbaelectronics.bitacoradefamilia.model.domain.UserSettings
import com.cbaelectronics.bitacoradefamilia.model.session.Session
import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.FirebaseDBService

class SearchUserViewModel: ViewModel() {

    // Properties

    var user = Session.instance.user ?: User()
    var settings = Session.instance.user?.settings ?: UserSettings()
    var children = Session.instance.children

    // Localization

    val title = R.string.search_title
    val search = R.string.search_search
    val alert = R.string.search_alert_message_1
    val positive = R.string.search_alert_button_positive
    val negative = R.string.search_alert_button_negative
    val ok = R.string.search_alert_button_ok

    // Public

    fun load(): LiveData<MutableList<User>>{
        val mutableList = MutableLiveData<MutableList<User>>()

        FirebaseDBService.load().observeForever {
            mutableList.value = it
        }

        return mutableList
    }

    fun messageQuestion(context: Context, name: String): String{
        return context.getString(alert, name)
    }

}