package com.projects.rootmu.projectpinenut.ui.util.general.colorHelper

internal object ColorHelper {

    fun mixTwoColors(color1: Int, color2: Int, amount: Float): Int {
        val alphaChannel = 24
        val redChannel = 16
        val greenChannel = 8

        val inverseAmount = 1.0f - amount

        val a =
            ((color1 shr alphaChannel and 0xff).toFloat() * amount + (color2 shr alphaChannel and 0xff).toFloat() * inverseAmount).toInt() and 0xff
        val r =
            ((color1 shr redChannel and 0xff).toFloat() * amount + (color2 shr redChannel and 0xff).toFloat() * inverseAmount).toInt() and 0xff
        val g =
            ((color1 shr greenChannel and 0xff).toFloat() * amount + (color2 shr greenChannel and 0xff).toFloat() * inverseAmount).toInt() and 0xff
        val b =
            ((color1 and 0xff).toFloat() * amount + (color2 and 0xff).toFloat() * inverseAmount).toInt() and 0xff

        return a shl alphaChannel or (r shl redChannel) or (g shl greenChannel) or b
    }
}