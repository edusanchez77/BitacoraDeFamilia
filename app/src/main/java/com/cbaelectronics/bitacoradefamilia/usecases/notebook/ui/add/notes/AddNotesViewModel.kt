/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/3/23, 10:13 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.add.notes

import androidx.lifecycle.ViewModel
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.model.domain.Notes
import com.cbaelectronics.bitacoradefamilia.model.domain.User
import com.cbaelectronics.bitacoradefamilia.model.domain.UserSettings
import com.cbaelectronics.bitacoradefamilia.model.session.Session
import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.FirebaseDBService

class AddNotesViewModel: ViewModel() {

    // Properties

    var user = Session.instance.user ?: User()
    var settings = Session.instance.user?.settings ?: UserSettings()
    var children = Session.instance.children

    // Localization

    val title = R.string.add_notes_title
    val save = R.string.add_button_save
    val cancel = R.string.add_button_cancel
    val back = R.string.add_button_back
    val editTextNote = R.string.add_notes_edittext_note
    val errorIncomplete = R.string.add_alert_error_incomplete
    val errorUnknown = R.string.add_alert_error_unknown
    val ok = R.string.add_alert_ok

    // Publi

    fun save(notes: Notes){
        FirebaseDBService.save(notes)
    }

}