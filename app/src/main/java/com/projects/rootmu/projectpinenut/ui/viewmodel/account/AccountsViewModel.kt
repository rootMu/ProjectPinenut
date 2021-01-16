package com.projects.rootmu.projectpinenut.ui.viewmodel.account

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.firebase.ui.auth.AuthUI
import com.projects.rootmu.projectpinenut.repository.account.AccountRepository
import com.projects.rootmu.projectpinenut.ui.util.specific.FirebaseUserLiveData
import com.projects.rootmu.projectpinenut.util.general.map2
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountsViewModel @ViewModelInject constructor(@ApplicationContext private val context: Context, private val accountRepository: AccountRepository) :
    ViewModel() {

    companion object {
        const val USER = "user"
        const val SIGN_IN_REQUEST_CODE = 1000
    }

    val isLoggedIn = accountRepository.isLoggedIn

    val user = accountRepository.user

    val loginState = accountRepository.loginState

    fun attemptLogin() {
        accountRepository.login()
    }

    fun guestLogin() {
        //TODO anonymous login
    }

    fun goToCreateAccount() {
        //TODO listener
    }

    fun logout() {
        accountRepository.logout()
    }

    fun isLoggedIn() = isLoggedIn.value?:false


}