/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/7/23, 4:03 PM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.addChildren

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.usecases.base.BaseActivityRouter

class AddChildrenRouter: BaseActivityRouter {

    override fun intent(activity: Context): Intent = Intent(activity, AddChildrenActivity::class.java)

    override fun launch(activity: Context) {
        activity.startActivity(intent(activity))
        (activity as Activity).overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
    }

}