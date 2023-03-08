/**
 *  Created by CbaElectronics by Eduardo Sanchez on 27/1/23 14:48.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.menu

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.model.domain.Children
import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.DatabaseField
import com.cbaelectronics.bitacoradefamilia.usecases.base.BaseActivityRouter

class MenuRouter: BaseActivityRouter {

    override fun intent(activity: Context): Intent = Intent(activity, MenuActivity::class.java)

    override fun launch(activity: Context) {
        activity.startActivity(intent(activity))
        (activity as Activity).overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
    }

    fun launch(activity: Context, children: Children){
        activity.startActivity(intent(activity).apply {
            putExtra(DatabaseField.CHILDREN.key, Children.toJson(children))
        })
    }

    fun launch(activity: Context, childrenId: String, permission: Int){
        activity.startActivity(intent(activity).apply {
            putExtra(DatabaseField.CHILDREN_ID.key, childrenId)
            putExtra(DatabaseField.PERMISSION.key, permission)
        })
    }


}