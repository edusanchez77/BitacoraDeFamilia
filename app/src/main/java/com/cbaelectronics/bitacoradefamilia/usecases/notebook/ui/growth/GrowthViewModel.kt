/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/1/23, 8:05 AM.
 *  www.cbaelectronics.com.ar
 */

/**
 *  Created by CbaElectronics by Eduardo Sanchez on 30/1/23 16:18.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.growth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.model.domain.Growth
import com.cbaelectronics.bitacoradefamilia.model.domain.User
import com.cbaelectronics.bitacoradefamilia.model.domain.UserSettings
import com.cbaelectronics.bitacoradefamilia.model.session.Session
import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.FirebaseDBService

class GrowthViewModel : ViewModel() {

    // Properties

    var user = Session.instance.user ?: User()
    var settings = Session.instance.user?.settings ?: UserSettings()
    var children = Session.instance.children

    // Localization

    val title = R.string.growth_title
    val button = R.string.growth_button
    val headerDate = R.string.growth_table_header_date
    val headerWeight = R.string.growth_table_header_weight
    val headerHeight = R.string.growth_table_header_height
    val headerPC = R.string.growth_table_header_PC

    // Public

    fun load(): LiveData<MutableList<Growth>>{
        val mutableData = MutableLiveData<MutableList<Growth>>()

        FirebaseDBService.load(children?.id!!).observeForever {
            mutableData.value = it
        }

        return mutableData
    }
}