/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/1/23, 7:52 AM.
 *  www.cbaelectronics.com.ar
 */

/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/1/23, 7:52 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.pregnant.ui.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.model.domain.*
import com.cbaelectronics.bitacoradefamilia.model.session.Session
import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.FirebaseDBService

class InfoViewModel : ViewModel() {

    // Properties

    var user = Session.instance.user ?: User()
    var settings = Session.instance.user?.settings ?: UserSettings()
    var children = Session.instance.children

    // Localization

    val title = R.string.info_title
    val tabInfo = R.string.info_tabs_info
    val tabWeight = R.string.info_tabs_weight
    val tabNames = R.string.info_tabs_names
    val buttonAddInfo = R.string.info_button_info
    val buttonAddWeight = R.string.info_button_weight
    val buttonAddNames = R.string.info_button_names
    val boy = R.string.info_tabs_names_boy
    val woman = R.string.info_tabs_names_woman
    val headerWeek = R.string.info_tabs_weight_header_week
    val headerWeight = R.string.info_tabs_weight_header_weight
    var mWhen = R.string.add_info_edittext_when
    var how = R.string.add_info_edittext_how
    var reactions = R.string.add_info_edittext_reactions

    // Public
    fun load(genre: String): LiveData<MutableList<PosibleNames>>{
        val mutableList = MutableLiveData<MutableList<PosibleNames>>()

        FirebaseDBService.loadNames(children?.id!!, genre).observeForever {
            mutableList.value = it
        }

        return mutableList
    }

    fun load(): LiveData<MutableList<ControlWeight>>{
        val mutableList = MutableLiveData<MutableList<ControlWeight>>()

        FirebaseDBService.loadWeight(children?.id!!).observeForever {
            mutableList.value = it
        }

        return mutableList
    }

    fun loadInfo(): LiveData<MutableList<PregnantInfo>>{
        val mutableList = MutableLiveData<MutableList<PregnantInfo>>()
        FirebaseDBService.loadPregnantInfo(children?.id!!).observeForever {
            mutableList.value = it
        }
        return mutableList
    }
}