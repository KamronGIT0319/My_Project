package com.example.my_project

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    private val emailInputLayout: TextInputLayout by lazy { findViewById<TextInputLayout>(R.id.emailInputLayout) }
    private val passwordInputLayout: TextInputLayout by lazy { findViewById<TextInputLayout>(R.id.passwordInputLayout) }
    private val emailEditText: TextInputEditText by lazy { findViewById<TextInputEditText>(R.id.editEmail) }
    private val passwordEditText: TextInputEditText by lazy { findViewById<TextInputEditText>(R.id.editPassword) }
    private val nextButton: MaterialButton by lazy { findViewById<MaterialButton>(R.id.next_button) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        nextButton.setOnClickListener {
            validateCredentials()
        }
    }

    private fun validateCredentials() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        val isEmailValid = validateEmail(email)
        val isPasswordValid = validatePassword(password)

        if (isEmailValid && isPasswordValid && email == "test@te.st" && password == "1234") {
            // Move to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun validateEmail(email: String): Boolean {
        return if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInputLayout.error = "Invalid email"
            false
        } else {
            emailInputLayout.error = null
            true
        }
    }

    private fun validatePassword(password: String): Boolean {
        return if (password.isEmpty()) {
            passwordInputLayout.error = "Password cannot be empty"
            false
        } else {
            passwordInputLayout.error = null
            true
        }
    }
}

