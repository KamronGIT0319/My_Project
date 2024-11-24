// Put your package name here. Check your activity for reference.
package com.example.my_project

class CredentialsManager {
    private val emailPattern=("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}"+
            "\\@"+
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}"+
            "("+
            "\\."+
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}"+
            ")+")

    private val regex=Regex(emailPattern)

    fun isEmailValid(email: String):Boolean {
        return regex.matches(email)
    }

    fun isPasswordValid(password: String): Boolean {
        // Criteria:
        // - At least 8 characters
        // - At least one uppercase letter
        // - At least one lowercase letter
        // - At least one digit
        // - At least one special character
        if (password.length < 8) return false
        if (!password.any { it.isUpperCase() }) return false
        if (!password.any { it.isLowerCase() }) return false
        if (!password.any { it.isDigit() }) return false
        if (!password.any { "!@#$%^&*()-_=+[{]}|;:',<.>/?".contains(it) }) return false

        return true
    }
}