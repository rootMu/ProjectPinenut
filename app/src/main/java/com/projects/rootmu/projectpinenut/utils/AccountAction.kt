package com.projects.rootmu.projectpinenut.utils

data class AccountAction<out T>(val status: Status, val data: T? = null, val message: String? = null) {

    enum class Status{
        SUCCESS,
        ERROR,
        ATTEMPT,
        SIGNUP,
        LOGIN
    }

    companion object {
        fun <T> success(data: T): AccountAction<T> {
            return AccountAction(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): AccountAction<T> {
            return AccountAction(Status.ERROR, data, message)
        }

        fun <T> attempt(data: T? = null): AccountAction<T> {
            return AccountAction(Status.ATTEMPT, data, null)
        }

        fun <T> signup(): AccountAction<T> {
            return AccountAction(Status.SIGNUP)
        }

        fun <T> login(): AccountAction<T> {
            return AccountAction(Status.LOGIN)
        }
    }
}