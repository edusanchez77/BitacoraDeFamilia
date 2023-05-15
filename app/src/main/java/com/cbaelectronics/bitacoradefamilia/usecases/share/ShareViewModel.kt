/**
 *  Created by CbaElectronics by Eduardo Sanchez on 3/1/23, 10:28 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.share

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.model.domain.SharedChildren
import com.cbaelectronics.bitacoradefamilia.model.domain.User
import com.cbaelectronics.bitacoradefamilia.model.domain.UserSettings
import com.cbaelectronics.bitacoradefamilia.model.session.Session
import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.FirebaseDBService
import com.cbaelectronics.bitacoradefamilia.util.UIUtil.showAlert

class ShareViewModel: ViewModel() {

    // Properties

    var user = Session.instance.user ?: User()
    var settings = Session.instance.user?.settings ?: UserSettings()
    var children = Session.instance.children

    // Localization

    val title = R.string.share_title
    val with = R.string.share_with_title
    val save = R.string.add_button_save
    val cancel = R.string.add_button_cancel
    val back = R.string.add_button_back
    val errorIncomplete = R.string.add_alert_error_incomplete
    val errorUnknown = R.string.add_alert_error_unknown
    val ok = R.string.add_alert_ok
    val question = R.string.share_alert_message_1
    val done = R.string.share_alert_message_2
    val positive = R.string.share_alert_button_positive
    val negative = R.string.share_alert_button_negative


    // Public

    fun save(sharedChildren: SharedChildren){
        FirebaseDBService.save(sharedChildren)
    }

    fun load(): LiveData<MutableList<User>> {
        val mutableList = MutableLiveData<MutableList<User>>()
        FirebaseDBService.loadSharedChildren(children?.id!!, user).observeForever{
            mutableList.value = it
        }

        return mutableList
    }

    fun delete(id: String){
        FirebaseDBService.deleteSharedChildren(id)
    }

    fun messageQuestion(context: Context, name: String): String{
        return context.getString(question, name)
    }

    fun messageDone(context: Context, name: String): String{
        return context.getString(done, name)
    }

}