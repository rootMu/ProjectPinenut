package com.projects.rootmu.projectpinenut.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.rootmu.projectpinenut.utils.AccountAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class AccountsViewModel @ViewModelInject constructor() : ViewModel() {

    val accountsAction: MutableLiveData<AccountAction<String>> = MutableLiveData()

    fun attemptLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            accountsAction.postValue(AccountAction.attempt("user"))
            delay(2000)
            accountsAction.postValue(
                if (Random.nextBoolean()) {
                    AccountAction.success("user")
                } else {
                    AccountAction.error("Failed to Login", "userId")
                }
            )
        }
    }

    fun attemptSignUp() {
        viewModelScope.launch(Dispatchers.IO) {
            accountsAction.postValue(AccountAction.attempt("user"))
            delay(2000)
            accountsAction.postValue(
                if (Random.nextBoolean()) {
                    AccountAction.success("user")
                } else {
                    AccountAction.error("Failed to Create Account", "user")
                }
            )
        }
    }


    fun goToSignUp() {
        viewModelScope.launch(Dispatchers.IO) {
            accountsAction.postValue(AccountAction.signup())
        }
    }

    fun goToLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            accountsAction.postValue(AccountAction.login())
        }
    }

}