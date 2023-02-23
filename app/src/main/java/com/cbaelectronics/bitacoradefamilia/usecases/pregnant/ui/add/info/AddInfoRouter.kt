/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/23/23, 11:53 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.pregnant.ui.add.info

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.usecases.base.BaseActivityRouter

class AddInfoRouter: BaseActivityRouter {

    override fun intent(activity: Context): Intent = Intent(activity, AddInfoActivity::class.java)

    override fun launch(activity: Context) {
        activity.startActivity(intent(activity))
        (activity as Activity).overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
    }
}