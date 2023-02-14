/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/2/23, 11:36 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.menu

import androidx.lifecycle.ViewModel
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.model.domain.Children
import com.cbaelectronics.bitacoradefamilia.model.domain.User
import com.cbaelectronics.bitacoradefamilia.model.domain.UserSettings
import com.cbaelectronics.bitacoradefamilia.model.session.Session

class MenuViewModel: ViewModel() {

    // Properties

    var user = Session.instance.user ?: User()
    var settings = Session.instance.user?.settings ?: UserSettings()
    var children = Session.instance.children

    // Localization

    val pregnancyDiary = R.string.menu_card_pregnant
    val pediatricNotebook = R.string.menu_card_notebook

    // Public

    fun childrenInstance(children: Children){

        Session.instance.childrenInstance(children)

    }

}