/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/3/23, 2:56 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.add.illness

import androidx.lifecycle.ViewModel
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.model.domain.Illness
import com.cbaelectronics.bitacoradefamilia.model.domain.User
import com.cbaelectronics.bitacoradefamilia.model.domain.UserSettings
import com.cbaelectronics.bitacoradefamilia.model.session.Session
import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.FirebaseDBService

class AddIllnessViewModel: ViewModel() {

    // Properties

    var user = Session.instance.user ?: User()
    var settings = Session.instance.user?.settings ?: UserSettings()
    var children = Session.instance.children

    // Localization

    val title = R.string.add_illness_title
    val save = R.string.add_button_save
    val cancel = R.string.add_button_cancel
    val back = R.string.add_button_back
    val editTextDate = R.string.add_illness_edittext_date
    val editTextIllnessName = R.string.add_illness_edittext_name
    val editTextDuration = R.string.add_illness_edittext_duration
    val editTextSymptom = R.string.add_illness_edittext_symptom
    val editTextMedication = R.string.add_illness_edittext_medication
    val editTextObservation = R.string.add_illness_edittext_observation
    val errorIncomplete = R.string.add_alert_error_incomplete
    val errorUnknown = R.string.add_alert_error_unknown
    val ok = R.string.add_alert_ok

    // Public

    fun save(illness: Illness){
        FirebaseDBService.save(illness)
    }

}