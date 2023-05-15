/**
 *  Created by CbaElectronics by Eduardo Sanchez on 3/1/23, 10:23 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.about

import android.content.Context
import androidx.lifecycle.ViewModel
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.model.domain.User
import com.cbaelectronics.bitacoradefamilia.model.domain.UserSettings
import com.cbaelectronics.bitacoradefamilia.model.session.Session
import com.cbaelectronics.bitacoradefamilia.util.Constants
import com.cbaelectronics.bitacoradefamilia.util.Util

enum class Network {

    TWITTER, INSTAGRAM, FACEBOOK;

    val uri: String
        get() {
            return when (this) {
                TWITTER -> Constants.TWITTER_CBAELECTRONICS_URI
                INSTAGRAM -> Constants.INSTAGRAM_CBAELECTRONICS_URI
                FACEBOOK -> Constants.FACEBOOK_CBAELECTRONICS_URI
            }
        }

}

class AboutViewModel: ViewModel() {

// Properties

    var user = Session.instance.user ?: User()
    var settings = Session.instance.user?.settings ?: UserSettings()

    // Localization

    val byText = R.string.about_by
    val infoText = R.string.about_info
    val siteText = R.string.about_site
    val onboardingText = R.string.about_onboarding

    // Public

    fun versionText(context: Context): String {
        return context.getString(R.string.about_version, Util.version())
    }

    fun open(context: Context, network: Network) {
        Util.openBrowser(context, network.uri)
    }

    fun openSite(context: Context) {
        Util.openBrowser(context, Constants.BITACORA_URI)
    }

}