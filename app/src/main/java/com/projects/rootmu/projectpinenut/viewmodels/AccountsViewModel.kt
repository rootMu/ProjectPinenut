package com.projects.rootmu.projectpinenut.viewmodels

import android.content.Context
import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.firebase.ui.auth.AuthUI
import com.projects.rootmu.projectpinenut.R
import com.projects.rootmu.projectpinenut.utils.FirebaseUserLiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

class AccountsViewModel @ViewModelInject constructor(@ApplicationContext private val context: Context) :
    ViewModel() {

    companion object {
        const val USER = "user"
        const val SIGN_IN_REQUEST_CODE = 1000
    }

    val providers: MutableLiveData<List<AuthUI.IdpConfig>?> = MutableLiveData()
    val restartObserving: MutableLiveData<Boolean> = MutableLiveData()
    val loginVisibility: MutableLiveData<Int> = MutableLiveData(View.INVISIBLE)

    val authenticationState = FirebaseUserLiveData().map { user ->
        when {
            user != null -> {
                AuthenticationState.AUTHENTICATED
            }
            else -> {
                AuthenticationState.UNAUTHENTICATED
            }
        }
    }

    private val _profileVisibile = MutableLiveData(false)
    val profileVisible: LiveData<Boolean> get () = _profileVisibile
    fun setProfileVisibility(visible: Boolean = false){
        _profileVisibile.postValue(visible)
    }

    init {
        attemptLogin()
    }

    fun attemptLogin(){
        viewModelScope.launch(Dispatchers.IO) {
            providers.postValue(
                arrayListOf(
                    AuthUI.IdpConfig.AnonymousBuilder().build(),
                    AuthUI.IdpConfig.EmailBuilder().build(),
                    AuthUI.IdpConfig.GoogleBuilder().build(),
                    AuthUI.IdpConfig.PhoneBuilder().build()
                    //  AuthUI.IdpConfig.FacebookBuilder().build()


                    //TODO fix facebook login
                    //TODO add twitter login

                    // This is where you can provide more ways for users to register and
                    // sign in.
                )
            )
        }
    }

    fun restartObserving(){
        restartObserving.postValue(true)
    }

    fun showLogin(){
        loginVisibility.postValue(View.VISIBLE)
    }

    fun logout() {
        showLogin()
        AuthUI.getInstance().signOut(context)
    }

    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }

    fun getRandomFace() = liveData {
        val images = context.resources.obtainTypedArray(R.array.portraits)
        val num = Random.nextInt(0,images.length() - 1)
        emit(images.getResourceId(num, -1))
        images.recycle()
    }

    fun generateRandomName():String{
        val colour: String = context.resources.getStringArray(R.array.colour).random()
        val adjective: String = context.resources.getStringArray(R.array.adjective).random()
        val noun: String = context.resources.getStringArray(R.array.noun).random()
        return "${if(Random.nextBoolean())colour else adjective}$noun"
    }

}