/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/17/23, 12:56 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.pregnant.ui.add.names

import androidx.lifecycle.ViewModel
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.model.domain.PosibleNames
import com.cbaelectronics.bitacoradefamilia.model.domain.User
import com.cbaelectronics.bitacoradefamilia.model.domain.UserSettings
import com.cbaelectronics.bitacoradefamilia.model.session.Session
import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.FirebaseDBService

class AddNamesViewModel: ViewModel() {

    // Properties

    var user = Session.instance.user ?: User()
    var settings = Session.instance.user?.settings ?: UserSettings()
    var children = Session.instance.children

    // Localization

    val title = R.string.add_illness_title
    val save = R.string.add_button_save
    val cancel = R.string.add_button_cancel
    val back = R.string.add_button_back
    val editTextName = R.string.add_names_edittext_name
    val editTextGenre = R.string.add_names_edittext_genre
    val errorIncomplete = R.string.add_alert_error_incomplete
    val errorUnknown = R.string.add_alert_error_unknown
    val ok = R.string.add_alert_ok

    // Public

    fun save(name: PosibleNames){
        FirebaseDBService.save(name)
    }

}