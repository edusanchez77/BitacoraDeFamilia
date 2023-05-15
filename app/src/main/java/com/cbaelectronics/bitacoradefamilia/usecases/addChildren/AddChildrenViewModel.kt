/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/7/23, 4:03 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.addChildren

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.model.domain.Children
import com.cbaelectronics.bitacoradefamilia.model.domain.User
import com.cbaelectronics.bitacoradefamilia.model.domain.UserSettings
import com.cbaelectronics.bitacoradefamilia.model.session.Session
import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.FirebaseDBService

class AddChildrenViewModel: ViewModel() {

    // Properties

    var user = Session.instance.user ?: User()
    var settings = Session.instance.user?.settings ?: UserSettings()

    // Localization

    val title = R.string.add_children_title
    val save = R.string.add_button_save
    val cancel = R.string.add_button_cancel
    val back = R.string.add_button_back
    val edit = R.string.add_button_edit
    val editTextName = R.string.add_children_edittext_name
    val editTextGenre = R.string.add_children_edittext_genre
    val editTextDate = R.string.add_children_edittext_date
    val editTextHour = R.string.add_children_edittext_hour
    val editTextWeight = R.string.add_children_edittext_weight
    val editTextHeight = R.string.add_children_edittext_height
    val errorIncomplete = R.string.add_alert_error_incomplete
    val errorUnknown = R.string.add_alert_error_unknown
    val ok = R.string.add_alert_ok
    val optionTitle = R.string.image_option_title
    val errorCamera = R.string.image_error_camera
    val errorImages = R.string.image_error_images
    val errorLoad = R.string.image_error_load
    val load = R.string.image_load

    // Public

    fun save(children: Children){
        FirebaseDBService.save(children)
    }

    fun update(children: Children){
        FirebaseDBService.update(children)
    }


}