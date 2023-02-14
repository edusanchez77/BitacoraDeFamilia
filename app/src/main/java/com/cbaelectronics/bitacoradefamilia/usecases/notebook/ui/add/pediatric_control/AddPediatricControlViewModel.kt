/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/6/23, 8:47 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.add.pediatric_control

import androidx.lifecycle.ViewModel
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.model.domain.PediatricControl
import com.cbaelectronics.bitacoradefamilia.model.domain.User
import com.cbaelectronics.bitacoradefamilia.model.domain.UserSettings
import com.cbaelectronics.bitacoradefamilia.model.session.Session
import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.FirebaseDBService

class AddPediatricControlViewModel: ViewModel() {

    // Properties

    var user = Session.instance.user ?: User()
    var settings = Session.instance.user?.settings ?: UserSettings()
    var children = Session.instance.children

    // Localization

    val title = R.string.add_pediatriccontrol_title
    val save = R.string.add_button_save
    val cancel = R.string.add_button_cancel
    val editTextDate = R.string.add_pediatriccontrol_edittext_date
    val editTextDoctor = R.string.add_pediatriccontrol_edittext_doctor
    val editTextSpeciality = R.string.add_pediatriccontrol_edittext_speciality
    val editTextWeight = R.string.add_pediatriccontrol_edittext_weight
    val editTextHeight = R.string.add_pediatriccontrol_edittext_height
    val editTextObservation = R.string.add_pediatriccontrol_edittext_observation
    val editTextNext = R.string.add_pediatriccontrol_edittext_next
    val editTextNotes = R.string.add_pediatriccontrol_edittext_notes
    val errorIncomplete = R.string.add_alert_error_incomplete
    val errorUnknown = R.string.add_alert_error_unknown
    val ok = R.string.add_alert_ok

    // Public

    fun save(control: PediatricControl){
        FirebaseDBService.save(control)
    }

}