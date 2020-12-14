package com.projects.rootmu.projectpinenut.view.base

import androidx.appcompat.app.AppCompatActivity
import com.projects.rootmu.projectpinenut.listeners.Navigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseActivity: AppCompatActivity(), Navigator {

}