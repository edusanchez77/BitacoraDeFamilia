/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/17/23, 9:45 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.common.tabs

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cbaelectronics.bitacoradefamilia.usecases.pregnant.ui.info.tabs.InformationFragment
import com.cbaelectronics.bitacoradefamilia.usecases.pregnant.ui.info.tabs.NamesFragment
import com.cbaelectronics.bitacoradefamilia.usecases.pregnant.ui.info.tabs.WeightFragment

class tabInfoAdapter(fa: Fragment): FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        var mFragment: Fragment = InformationFragment()
        when(position){
            0 -> {
                mFragment = InformationFragment()
            }
            1 -> {
                mFragment = WeightFragment()
            }
            2 -> {
                mFragment = NamesFragment()
            }
        }

        return mFragment
    }
}