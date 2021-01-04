package com.projects.rootmu.projectpinenut.ui.components.tabbedFragmentControl

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import java.lang.ref.WeakReference

class TabbedFragmentCachedAdapter(private val adapter: TabbedFragmentContainer.Adapter) :
    TabbedFragmentContainer.Adapter {

    private val fragments: Array<WeakReference<Fragment>?> =
        arrayOfNulls<WeakReference<Fragment>?>(getCount()).also { fragments ->
            val fragmentManager = getFragmentManager()
            repeat(getCount()) { index ->
                fragmentManager.findFragmentByTag(getItemTag(index))?.let {
                    fragments[index] = WeakReference(it)
                }
            }
        }

    override fun getItemTag(index: Int) = adapter.getItemTag(index)
    override fun getItem(index: Int): Fragment =
        fragments[index]?.get() ?: adapter.getItem(index).also {
            fragments[index] = WeakReference(it)
        }

    override fun getItems() = (0 until getCount()).map { getItem(it) }
    override fun getCount(): Int = adapter.getCount()
    override fun getTabTitle(index: Int) = adapter.getTabTitle(index)
    override fun getTabIconResId(index: Int) = adapter.getTabIconResId(index)

    override fun getFragmentManager(): FragmentManager = adapter.getFragmentManager()
}