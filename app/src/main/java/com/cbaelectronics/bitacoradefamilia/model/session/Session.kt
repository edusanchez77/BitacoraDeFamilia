/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/7/23, 2:49 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.model.session

import android.content.Context
import com.cbaelectronics.bitacoradefamilia.model.domain.Children
import com.cbaelectronics.bitacoradefamilia.model.domain.User
import com.cbaelectronics.bitacoradefamilia.provider.preferences.PreferencesKey
import com.cbaelectronics.bitacoradefamilia.provider.preferences.PreferencesProvider

class Session {

    // Initializacion

    companion object {
        val instance = Session()
    }

    // Properties

    var user: User? = null
        private set
    var children: Children? = null
        private set

    // Life Cycle

    fun configure(context: Context) {

        PreferencesProvider.string(context, PreferencesKey.AUTH_USER)?.let {
            user = User.fromJson(it)
            user?.settings = User.fromJson(it)?.settings
        }

    }

    fun childrenInstance(pChildren: Children){
        children = pChildren
    }

    // Private

    fun save(context: Context, user: User) {

        PreferencesProvider.set(context, PreferencesKey.AUTH_USER, User.toJson(user))
        PreferencesProvider.set(context, PreferencesKey.ONBOARDING, true)

    }

}