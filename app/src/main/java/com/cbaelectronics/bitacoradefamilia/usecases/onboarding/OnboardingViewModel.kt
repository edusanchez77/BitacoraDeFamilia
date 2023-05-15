/**
 *  Created by CbaElectronics by Eduardo Sanchez on 3/1/23, 10:26 AM.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.usecases.onboarding

import androidx.lifecycle.ViewModel
import com.cbaelectronics.bitacoradefamilia.R
import com.cbaelectronics.bitacoradefamilia.model.domain.Onboarding

class OnboardingViewModel: ViewModel() {

    // Properties

    val data = arrayListOf(
        Onboarding(0, R.drawable.logo, R.string.onboarding_page0_title, R.string.onboarding_page0_body),
        Onboarding(1, R.drawable.pregnancy1, R.string.onboarding_page1_title, R.string.onboarding_page1_body),
        Onboarding(2, R.drawable.ecografia, R.string.onboarding_page2_title, R.string.onboarding_page2_body),
        Onboarding(3, R.drawable.bebe_ciguena, R.string.onboarding_page3_title, R.string.onboarding_page3_body),
        Onboarding(4, R.drawable.crecimiento_bebe, R.string.onboarding_page4_title, R.string.onboarding_page4_body),
        Onboarding(5, R.drawable.share, R.string.onboarding_page5_title, R.string.onboarding_page5_body),
    )

    val pages = data.size

    // Localization

    val understoodText = R.string.understood
    val previousText = R.string.previous
    val nextText = R.string.next

}