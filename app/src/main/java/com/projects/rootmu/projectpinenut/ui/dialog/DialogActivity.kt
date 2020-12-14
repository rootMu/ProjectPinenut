package com.projects.rootmu.projectpinenut.ui.dialog

import androidx.fragment.app.Fragment
import com.projects.rootmu.projectpinenut.ui.base.BaseActivity
import com.projects.rootmu.projectpinenut.ui.models.DialogData

class DialogActivity: BaseActivity() {

    override fun changeToScreen(fragment: Fragment, backStackName: String?, animated: Boolean) {
        TODO("Not yet implemented")
    }

    override fun back(pastName: String?) {
        TODO("Not yet implemented")
    }

    fun DialogData.createDialog(id: Long) = when (this) {
        is DialogData.Input -> TODO("Not yet implemented")
        is DialogData.Basic -> TODO("Not yet implemented")
        is DialogData.Banner -> TODO("Not yet implemented")
    }

}