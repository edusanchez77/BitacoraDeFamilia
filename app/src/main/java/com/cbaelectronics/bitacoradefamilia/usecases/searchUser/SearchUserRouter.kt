/**
 *  Created by CbaElectronics by Eduardo Sanchez on 3/16/23, 11:15 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.searchUser

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.usecases.base.BaseActivityRouter
import com.cbaelectronics.bitacoradefamilia.usecases.login.LoginActivity

class SearchUserRouter: BaseActivityRouter {

    override fun intent(activity: Context): Intent = Intent(activity, SearchUserActivity::class.java)

    override fun launch(activity: Context) {
        activity.startActivity(intent(activity))
        (activity as Activity).overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
    }

}