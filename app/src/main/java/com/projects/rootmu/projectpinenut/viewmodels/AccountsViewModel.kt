package com.projects.rootmu.projectpinenut.viewmodels

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.firebase.ui.auth.AuthUI
import com.projects.rootmu.projectpinenut.utils.FirebaseUserLiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountsViewModel @ViewModelInject constructor(@ApplicationContext private val context: Context) :
    ViewModel() {

    companion object {
        const val USER = "user"
        const val SIGN_IN_REQUEST_CODE = 1000
    }

    val providers: MutableLiveData<List<AuthUI.IdpConfig>> = MutableLiveData()

    val authenticationState = FirebaseUserLiveData().map { user ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }

    fun attemptLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            providers.postValue(
                arrayListOf(
                    AuthUI.IdpConfig.EmailBuilder().build(),
                    AuthUI.IdpConfig.GoogleBuilder().build()

                    // This is where you can provide more ways for users to register and
                    // sign in.
                )
            )
        }
    }

    fun guestLogin(){

    }

    fun logout() {
        AuthUI.getInstance().signOut(context)
    }

    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }

}