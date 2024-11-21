// Put your package name here. Check your activity for reference.
package com.example.my_project

import org.junit.Assert.*
import org.junit.Test

class CredentialsManagerTest {

    // Test empty email
    @Test
    fun givenEmptyEmail_thenReturnFalse() {
        val credentialsManager = CredentialsManager()
        val email=""
        val isEmailValid = credentialsManager.isEmailValid(email)

        assertEquals(false, isEmailValid)
    }

    // Test proper email
    @Test
    fun givenCorrectEmail_thenReturnTrue(){
        val credentialsManager=CredentialsManager()
        val email="myname@gmail.com"

        assertEquals(true, credentialsManager.isEmailValid(email))
    }

    // Test wrong email format
    @Test
    fun givenWrongEmailFormat_thenReturnFalse(){
        val credentialsManager=CredentialsManager()
        val email="absolutelyanemail"

        assertEquals(false, credentialsManager.isEmailValid(email))
    }

    // Test empty password

    @Test
    fun givenEmptyPassword_thenReturnFalse() {
        val credentialsManager = CredentialsManager()
        val password = ""
        val isPasswordValid = credentialsManager.isPasswordValid(password)

        assertEquals(false, isPasswordValid)
    }

    // Test filled password

    @Test
    fun givenStrongPassword_thenReturnTrue() {
        val credentialsManager = CredentialsManager()
        val password = "StrongPassword123!"
        val isPasswordValid = credentialsManager.isPasswordValid(password)

        assertEquals(true, isPasswordValid)
    }

    // Test weak password (e.g., too short or missing criteria)

    @Test
    fun givenWeakPassword_thenReturnFalse() {
        val credentialsManager = CredentialsManager()
        val password = "weak"
        val isPasswordValid = credentialsManager.isPasswordValid(password)

        assertEquals(false, isPasswordValid)
    }
}
