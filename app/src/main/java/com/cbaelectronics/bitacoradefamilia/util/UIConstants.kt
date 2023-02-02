/**
 *  Created by CbaElectronics by Eduardo Sanchez on 27/1/23 10:52.
 *  www.cbaelectronics.com.ar
 */

package com.cbaelectronics.bitacoradefamilia.util

object UIConstants {

    const val VIEW_OPACITY = 0.5f
    const val SHADOW_OPACITY = 0.2f
    const val LOGO_HEIGHT = 24

}

enum class FontType(val path: String) {
    GALADA("font/galada.ttf"),
    LIGHT("font/farro_light.ttf"),
    REGULAR("font/farro.ttf"),
    BOLD("font/farro_bold.ttf")
}

enum class FontSize(val size: Int) {
    TITLE_APP(50),
    TITLE(24),
    SUBTITLE(22),
    HEAD(18),
    SUBHEAD(16),
    BODY(15),
    BUTTON(14),
    CAPTION(12)
}

enum class Size(val size: Int) {
    NONE(0),
    EXTRA_SMALL(2),
    VERY_SMALL(4),
    SMALL(8),
    SMALL_MEDIUM(10),
    MEDIUM(16),
    MEDIUM_BIG(24),
    BIG(32),
    VERY_BIG(40),
    GIGANT(80)
}

enum class Maps(val key: String){
    ADDRESS("address")
}