package com.projects.rootmu.projectpinenut.ui.components.tabbedFragmentControl

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.projects.rootmu.projectpinenut.ui.components.listeners.BottomNavigationCountListener
import com.projects.rootmu.projectpinenut.ui.components.listeners.BottomNavigationListener
import com.projects.rootmu.projectpinenut.ui.components.views.FragmentContainer

class TabbedFragmentContainer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): FragmentContainer(context, attrs, defStyleAttr) {

    companion object {
        const val SUPER_STATE_KEY = "super_state"
        const val CURRENT_INDEX_KEY = "current_index"
    }

    interface Adapter {
        fun getFragmentManager(): FragmentManager
        fun getItem(index: Int): Fragment
        fun getItems(): List<Fragment>
        fun getItemTag(index: Int): String
        fun getCount(): Int
        fun getTabTitle(index: Int): CharSequence?
        fun getTabIconResId(index: Int): Int?
    }

    interface Listener {
        fun onTabChanged(
            oldIndex: Int?,
            newIndex: Int,
            oldFragment: Fragment?,
            newFragment: Fragment
        )
    }

    class DefaultOnMeowBottomNavigationItemSelectedListener(
        private val bottomNavigation: MeowBottomNavigation,
        private val tabbedFragmentContainer: TabbedFragmentContainer
    ): BottomNavigationListener {
        override fun onNavigationItemSelected(model: MeowBottomNavigation.Model) {
            val newIndex = bottomNavigation.models.let {
                var index: Int? = null
                for (i in 0 until it.count()) {
                    if (it[i].id == model.id) {
                        index = i
                        break
                    }
                }
                index
            }

            if (newIndex != null) {
                tabbedFragmentContainer.currentTabIndex = newIndex
            }
        }

        override fun onNavigationItemReSelected(model: MeowBottomNavigation.Model) {
            //TODO handle re selection if wanted
        }
    }

    var adapter: Adapter? = null

    var listener: Listener? = null

    var currentTabIndex: Int?
        get() = currentTabIndexInternal
        set(value) {
            if (currentTabIndexInternal != value) {
                val oldIndex = currentTabIndexInternal
                currentTabIndexInternal = value
                currentTabIndexUpdated(oldIndex, value)
            }
        }

    private var currentTabIndexInternal: Int? = null

    private val tabCount: Int get() = adapter?.getCount() ?: 0

    fun setupWithMeowBottomNavigation(bottomNavigationView: MeowBottomNavigation) {

        for (i in 0 until tabCount) {
            val icon = adapter?.getTabIconResId(i)?:0
            bottomNavigationView.add(MeowBottomNavigation.Model(i,icon))
        }
        val defaultOnMeowBottomNavigationItemSelectedListener = DefaultOnMeowBottomNavigationItemSelectedListener(
            bottomNavigationView,
            this
        )
        bottomNavigationView.setOnClickMenuListener(
            defaultOnMeowBottomNavigationItemSelectedListener::onNavigationItemSelected
        )

        bottomNavigationView.setOnReselectListener(
            defaultOnMeowBottomNavigationItemSelectedListener::onNavigationItemReSelected
        )

        bottomNavigationView.setOnShowListener(
            defaultOnMeowBottomNavigationItemSelectedListener::onNavigationItemSelected
        )
    }

    private fun currentTabIndexUpdated(oldIndex: Int?, newIndex: Int?) {
        val adapter = this.adapter ?: return

        if (newIndex == null || newIndex < 0 || newIndex >= tabCount) {
            return
        }

        val fragmentManager = adapter.getFragmentManager()

        val newFragmentTag = adapter.getItemTag(newIndex)
        val alreadyExists = fragmentManager.findFragmentByTag(newFragmentTag) != null
        val fragmentToShow = adapter.getItem(newIndex)

        val transaction = fragmentManager.beginTransaction()

        fragmentManager.fragments.filter { adapter.getItems().contains(it) }.forEach {
            transaction.detach(it)
        }

        if (alreadyExists) {
            transaction.attach(fragmentToShow)
        } else {
            transaction.add(id, fragmentToShow, newFragmentTag)
        }

        transaction.commit()

        val oldFragment =
            oldIndex?.let { fragmentManager.findFragmentByTag(adapter.getItemTag(oldIndex)) }
        (oldFragment as? TabFragment)?.hide()
        (fragmentToShow as? TabFragment)?.show()
        listener?.onTabChanged(oldIndex, newIndex, oldFragment, fragmentToShow)
    }

    override fun onSaveInstanceState(): Parcelable {
        return Bundle().apply {
            putParcelable(SUPER_STATE_KEY, super.onSaveInstanceState())
            currentTabIndexInternal?.let { putInt(CURRENT_INDEX_KEY, it) }
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var newState = state
        if (newState is Bundle) {
            (newState.get(CURRENT_INDEX_KEY) as? Int)?.let {
                currentTabIndexInternal = it
            }
            newState = newState.getParcelable(SUPER_STATE_KEY)
        }
        super.onRestoreInstanceState(newState)
    }
}