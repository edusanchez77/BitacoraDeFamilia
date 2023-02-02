/**
 *  Created by CbaElectronics by Eduardo Sanchez on 2/1/23, 8:05 AM.
 *  www.cbaelectronics.com.ar
 */

/**
 *  Created by CbaElectronics by Eduardo Sanchez on 30/1/23 16:18.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.notebook.ui.growth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cbaelectronics.bitacoradefamilia.R

class GrowthViewModel : ViewModel() {

    // Localization

    val title = R.string.growth_title
    val button = R.string.growth_button
    val headerDate = R.string.growth_table_header_date
    val headerWeight = R.string.growth_table_header_weight
    val headerHeight = R.string.growth_table_header_height
    val headerPC = R.string.growth_table_header_PC

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
}