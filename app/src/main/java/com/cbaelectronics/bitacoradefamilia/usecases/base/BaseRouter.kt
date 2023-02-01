/**
 *  Created by CbaElectronics by Eduardo Sanchez on 27/1/23 14:39.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.base

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

interface BaseActivityRouter {

    // Activity

    fun intent(activity: Context) : Intent

    fun launch(activity: Context) = activity.startActivity(intent(activity))

}


interface BaseFragmentRouter {

    // Fragment

    fun fragment() : Fragment

    fun replace(manager: FragmentManager, containerId: Int) = manager.beginTransaction().replace(containerId, fragment()).commit()

    fun show(manager: FragmentManager) = manager.beginTransaction().show(fragment()).commitAllowingStateLoss()

    fun hide(manager: FragmentManager) = manager.beginTransaction().hide(fragment()).commitAllowingStateLoss()

    fun remove(manager: FragmentManager) = manager.beginTransaction().remove(fragment()).commitAllowingStateLoss()

}