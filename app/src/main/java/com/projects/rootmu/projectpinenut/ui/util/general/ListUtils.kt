package com.projects.rootmu.projectpinenut.ui.util.general

fun <T> List<T>.indexOfFirstOrNull(predicate: (T) -> Boolean): Int? {
    val index = indexOfFirst(predicate)
    return if (index >= 0) index else null
}