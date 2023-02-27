/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/3/23, 10:14 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.add.notes

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.provider.services.firebase.DatabaseField
import com.cbaelectronics.bitacoradefamilia.usecases.base.BaseActivityRouter

class AddNotesRouter: BaseActivityRouter {

    override fun intent(activity: Context): Intent = Intent(activity, AddNotesActivity::class.java)

    override fun launch(activity: Context) {
        activity.startActivity(intent(activity))
        (activity as Activity).overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
    }

    fun launch(activity: Context, type: String){
        activity.startActivity(intent(activity).apply {
            putExtra(DatabaseField.NOTE_TYPE.key, type)
        })
        (activity as Activity).overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
    }

}