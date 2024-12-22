package com.example.my_project

import android.content.Context

object CredentialsManager {

    private const val PREFS_NAME = "user_prefs"
    private const val KEY_IS_LOGGED_IN = "is_logged_in"
    private const val KEY_EMAIL = "email"
    private const val KEY_PASSWORD = "password"

    private val emailPattern = ("[a-zA-Z0-9\\+\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+").toRegex()

    private val registeredUsers = mutableMapOf<String, String>()

    fun isEmailValid(email: String): Boolean = email.matches(emailPattern)

    fun isEmailAlreadyUsed(email: String): Boolean = registeredUsers.containsKey(email.lowercase())

    fun isValidPassword(password: String): Boolean = password.length >= 8

    fun isValidFullName(fullName: String): Boolean = fullName.isNotEmpty()

    fun isValidPhoneNumber(phoneNumber: String): Boolean {
        val phonePattern = "^[0-9]{9,}$".toRegex()
        return phoneNumber.matches(phonePattern)
    }

    fun isHardcodedCredentials(email: String, password: String): Boolean {
        val hardcodedEmail = "test@te.st"
        val hardcodedPassword = "1234"
        return email == hardcodedEmail && password == hardcodedPassword
    }

    fun registerUser(email: String, password: String): Boolean {
        if (isEmailAlreadyUsed(email)) return false
        registeredUsers[email.lowercase()] = password
        return true
    }

    fun validateLogin(email: String, password: String): Boolean {
        return registeredUsers[email.lowercase()] == password
    }

    fun validateCredentials(email: String, password: String): Boolean {
        return isEmailValid(email) && isValidPassword(password)
    }

    private fun getSharedPreferences(context: Context) =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun isLoggedIn(context: Context): Boolean {
        return getSharedPreferences(context).getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun setLoggedIn(context: Context, isLoggedIn: Boolean) {
        getSharedPreferences(context).edit()
            .putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
            .apply()
    }

    fun saveUserCredentials(context: Context, email: String, password: String) {
        getSharedPreferences(context).edit()
            .putString(KEY_EMAIL, email)
            .putString(KEY_PASSWORD, password)
            .apply()
    }

    fun getUserCredentials(context: Context): Pair<String?, String?> {
        val sharedPreferences = getSharedPreferences(context)
        val email = sharedPreferences.getString(KEY_EMAIL, null)
        val password = sharedPreferences.getString(KEY_PASSWORD, null)
        return Pair(email, password)
    }

    fun validateCredentialsForSignUp(
        fullName: String,
        email: String,
        phoneNumber: String,
        password: String,
        isTermsAccepted: Boolean
    ): Boolean {
        return isValidFullName(fullName) &&
                isEmailValid(email) &&
                isValidPhoneNumber(phoneNumber) &&
                isValidPassword(password) &&
                isTermsAccepted
    }
}