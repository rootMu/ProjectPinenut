package com.projects.rootmu.projectpinenut.repository.account

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.firebase.ui.auth.data.model.User
import com.projects.rootmu.projectpinenut.models.account.Account
import com.projects.rootmu.projectpinenut.models.account.ProfilePicture
import com.projects.rootmu.projectpinenut.models.account.UserDetails
import com.projects.rootmu.projectpinenut.models.account.UserToken
import com.projects.rootmu.projectpinenut.models.token.Token
import com.projects.rootmu.projectpinenut.util.general.getRandomString
import com.projects.rootmu.projectpinenut.util.general.md5
import java.time.ZonedDateTime
import kotlin.random.Random

class AccountRepository {

    private val fullLoginState = MutableLiveData<AccountLogin?>()

    val loginState get() = fullLoginState

    val isLoggedIn = fullLoginState.map { it?.userToken != null }

    val user = fullLoginState.map {
        it?.userToken?.user
    }

    fun login() {
        //TODO do this properly (backend stuffs?)
        fullLoginState.value = AccountLogin(
            generateUserToken(),
            AuthenticationState.values().random()
        )
    }

    fun logout() {
        fullLoginState.value = null
    }

    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }

    data class AccountLogin(
        val userToken: UserToken?,
        val authenticationState: AuthenticationState
    )

    private fun generateUserToken(): UserToken {
        val token = generateToken()
        val passwordUpdateRequired = Random.nextBoolean()
        val account = generateAccount()

        return UserToken(token, passwordUpdateRequired, account)
    }

    private fun generateToken(): Token {
        val accessToken = getRandomString().md5()
        val expirationTime = ZonedDateTime.now().plusSeconds(Random.nextLong(1, 59))

        return Token(accessToken, expirationTime)
    }

    private fun generateAccount(): Account {
        val id = getRandomString(12)
        val userDetails = generateUserDetails()
        val profilePicture = generateProfilePicture(id)

        return Account(id, userDetails, profilePicture)

    }

    private fun generateUserDetails(): UserDetails {
        val userName = getRandomString(8)
        val firstName = Random.nextInt(0, 100).takeIf { it > 20 }?.let {
            getRandomString(8)
        }
        val lastName = Random.nextInt(0, 100).takeIf { firstName != null && it > 50 }.let {
            getRandomString(8)
        }
        val phoneNumber = null
        val emailAddress = null

        return UserDetails(userName, firstName, lastName, phoneNumber, emailAddress)
    }

    private fun generateProfilePicture(id: String): ProfilePicture {
        return ProfilePicture(id, null)
    }


}