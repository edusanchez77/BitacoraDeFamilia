/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/2/23, 11:36 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.menu

import android.content.Context
import androidx.lifecycle.ViewModel
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.model.domain.Children
import com.cbaelectronics.bitacoradefamilia.model.domain.Permission
import com.cbaelectronics.bitacoradefamilia.model.domain.User
import com.cbaelectronics.bitacoradefamilia.model.domain.UserSettings
import com.cbaelectronics.bitacoradefamilia.model.session.Session
import com.cbaelectronics.bitacoradefamilia.provider.preferences.PreferencesProvider
import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.DatabaseField
import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.FirebaseDBService
import com.cbaelectronics.bitacoradefamilia.usecases.login.LoginRouter
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class MenuViewModel: ViewModel() {

    // Properties

    var user = Session.instance.user ?: User()
    var settings = Session.instance.user?.settings ?: UserSettings()
    var children = Session.instance.children
    var permission = Session.instance.permission
    var childrenShared: Children? = null

    // Localization

    val pregnancyDiary = R.string.menu_card_pregnant
    val pediatricNotebook = R.string.menu_card_notebook
    val alertShared = R.string.menu_alert_notShared
    val alertErrorChildren = R.string.menu_alert_children_error
    val alertLogout = R.string.session_alert_logout
    val alertButtonOk = R.string.session_alert_button_ok
    val alertButtonCancel = R.string.session_alert_button_cancel

    // Public

    suspend fun load(childrenId: String, permission: Int) = runBlocking {
        async {
            val documentSnapshot = FirebaseDBService.loadChildren(childrenId)
            if (documentSnapshot != null) {
                if (documentSnapshot.exists()) {
                    val user = User(
                        displayName = documentSnapshot.getString("${DatabaseField.REGISTERED_BY.key}.${DatabaseField.DISPLAY_NAME.key}")
                            .toString(),
                        email = documentSnapshot.getString("${DatabaseField.REGISTERED_BY.key}.${DatabaseField.EMAIL.key}")
                            .toString(),
                        photoProfile = documentSnapshot.getString("${DatabaseField.REGISTERED_BY.key}.${DatabaseField.PROFILE_IMAGE_URL.key}")
                            .toString(),
                        token = documentSnapshot.getString("${DatabaseField.REGISTERED_BY.key}.${DatabaseField.TOKEN.key}")
                            .toString(),
                        type = documentSnapshot.getLong("${DatabaseField.REGISTERED_BY.key}.${DatabaseField.TYPE.key}")
                            ?.toInt(),
                        registerDate = documentSnapshot.getDate("${DatabaseField.REGISTERED_BY.key}.${DatabaseField.REGISTERED_DATE.key}"),
                    )

                    val children = Children(
                        id = childrenId,
                        name = documentSnapshot.getString(DatabaseField.NAME.key),
                        genre = documentSnapshot.getString(DatabaseField.GENRE.key),
                        date = documentSnapshot.getString(DatabaseField.DATE_OF_BIRTH.key),
                        hour = documentSnapshot.getString(DatabaseField.HOUR_OF_BIRTH.key),
                        weight = documentSnapshot.getString(DatabaseField.WEIGHT.key),
                        height = documentSnapshot.getString(DatabaseField.HEIGHT.key),
                        avatar = documentSnapshot.getString(DatabaseField.AVATAR.key),
                        registeredBy = user,
                        registeredDate = documentSnapshot.getDate(DatabaseField.REGISTERED_DATE.key),
                        permission = permission
                    )

                    childrenShared = children
                }
            }
        }.await()
    }

    fun childrenInstance(children: Children, permission: Int){

        Session.instance.childrenInstance(children, permission)

    }

    fun close(context: Context) {
        PreferencesProvider.clear(context)
        LoginRouter().launch(context)
    }

}