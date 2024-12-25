package com.example.my_project

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class CreateAccountFragment : Fragment() {

    private val credentialsManager = CredentialsManager

    private lateinit var emailField: TextInputEditText
    private lateinit var emailLayout: TextInputLayout
    private lateinit var passwordField: TextInputEditText
    private lateinit var passwordLayout: TextInputLayout
    private lateinit var nextButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login_page, container, false)

        emailField = view.findViewById(R.id.etEmail)
        emailLayout = view.findViewById(R.id.textInputEmail)
        passwordField = view.findViewById(R.id.etPassword)
        passwordLayout = view.findViewById(R.id.textInputPassword)
        nextButton = view.findViewById(R.id.btnNext)

        nextButton.setOnClickListener { onNextButtonClick() }

        view.findViewById<TextView>(R.id.tvRegisterNoww).setOnClickListener {
            navigateToSignUpDetailsFragment()
        }

        return view
    }

    private fun onNextButtonClick() {
        val email = emailField.text.toString().trim()
        val password = passwordField.text.toString().trim()

        clearAllErrors()

        when {
            email.isEmpty() -> setError(emailLayout, R.string.error_email_required)
            !credentialsManager.isEmailValid(email) -> setError(
                emailLayout,
                R.string.error_invalid_email
            )

            password.isEmpty() -> setError(passwordLayout, R.string.error_password_required)
            !credentialsManager.isValidPassword(password) -> setError(
                passwordLayout,
                R.string.error_password_invalid
            )

            else -> {
                if (CredentialsManager.validateCredentials(email, password)) {
                    navigateToMainActivity(email, password)
                } else {
                    showToast(R.string.error_invalid_credentials)
                }
            }
        }
    }

    private fun setError(layout: TextInputLayout, errorResId: Int?) {
        layout.error = errorResId?.let { getString(it) }
    }

    private fun clearAllErrors() {
        setError(emailLayout, null)
        setError(passwordLayout, null)
    }

    private fun showToast(messageResId: Int) {
        Toast.makeText(requireContext(), getString(messageResId), Toast.LENGTH_SHORT).show()
    }

    private fun navigateToMainActivity(email: String, password: String) {
        credentialsManager.setLoggedIn(requireContext(), true)
        credentialsManager.saveUserCredentials(requireContext(), email, password)

        val intent = Intent(requireContext(), MainActivity::class.java).apply {
            putExtra("email", email)
            putExtra("password", password)
        }
        startActivity(intent)
        requireActivity().finish()
    }

    private fun navigateToSignUpDetailsFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, SignUpDetailsFragment())
            .addToBackStack(null)
            .commit()
    }
}