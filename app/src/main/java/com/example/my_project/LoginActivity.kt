package com.example.my_project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    private val credentialsManager = CredentialsManager

    private val emailField: TextInputEditText
        get() = findViewById(R.id.etEmail)

    private val emailLayout: TextInputLayout
        get() = findViewById(R.id.textInputEmail)

    private val passwordField: TextInputEditText
        get() = findViewById(R.id.etPassword)

    private val passwordLayout: TextInputLayout
        get() = findViewById(R.id.textInputPassword)

    private val nextButton: Button
        get() = findViewById(R.id.btnNext)

    private val rememberMeCheckbox: CheckBox
        get() = findViewById(R.id.cbRememberMe)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        nextButton.setOnClickListener { handleNextButtonClick() }
        val loginButton = findViewById<TextView>(R.id.tvRegisterNoww)
        loginButton.setOnClickListener {
            val goToReg = Intent(this, RegisterActivity::class.java)
            startActivity(goToReg)
        }
    }

    private fun handleNextButtonClick() {
        val email = emailField.text.toString().trim()
        val password = passwordField.text.toString().trim()
        val isRememberMeChecked = rememberMeCheckbox.isChecked
        if (credentialsManager.isHardcodedCredentials(email, password)) {
            navigateToMainActivity()
            return
        }
        when {
            email.isEmpty() -> setError(emailLayout, getString(R.string.error_email_required))
            !credentialsManager.isEmailValid(email) -> setError(
                emailLayout,
                getString(R.string.error_invalid_email)
            )

            password.isEmpty() -> setError(
                passwordLayout,
                getString(R.string.error_password_required)
            )

            !credentialsManager.isValidPassword(password) -> setError(
                passwordLayout,
                getString(R.string.error_password_invalid)
            )

            !isRememberMeChecked -> showToast(getString(R.string.error_remember_me_required))

            credentialsManager.validateCredentials(email, password, isRememberMeChecked) -> {
                showToast(getString(R.string.success_signed_in))
            }

            else -> showToast(getString(R.string.error_invalid_credentials))
        }
    }

    private fun setError(layout: TextInputLayout, message: String) {
        layout.error = message
    }

    private fun clearError(layout: TextInputLayout) {
        layout.error = null
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToMainActivity() {
        clearError(emailLayout)
        clearError(passwordLayout)
        showToast(getString(R.string.success_signed_in))
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}