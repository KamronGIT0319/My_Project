package com.example.my_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.my_project.CredentialsManager
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SignUpDetailsFragment : Fragment() {

    private val credentialsManager = CredentialsManager

    private lateinit var fullNameField: TextInputEditText
    private lateinit var fullNameLayout: TextInputLayout
    private lateinit var emailField: TextInputEditText
    private lateinit var emailLayout: TextInputLayout
    private lateinit var phoneField: TextInputEditText
    private lateinit var phoneLayout: TextInputLayout
    private lateinit var passwordField: TextInputEditText
    private lateinit var passwordLayout: TextInputLayout
    private lateinit var termsCheckBox: CheckBox
    private lateinit var nextButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register_page, container, false)

        fullNameField = view.findViewById(R.id.fullName)
        fullNameLayout = view.findViewById(R.id.textInputFullName)
        emailField = view.findViewById(R.id.validEmail)
        emailLayout = view.findViewById(R.id.textInputValidEmail)
        phoneField = view.findViewById(R.id.phoneNumber)
        phoneLayout = view.findViewById(R.id.textInputPhoneNumber)
        passwordField = view.findViewById(R.id.strongPassword)
        passwordLayout = view.findViewById(R.id.textInputStrongPassword)
        termsCheckBox = view.findViewById(R.id.agreementCheck)
        nextButton = view.findViewById(R.id.btnNext)

        nextButton.setOnClickListener { onNextButtonClick() }

        view.findViewById<TextView>(R.id.tvRegisterNow).setOnClickListener {
            navigateToCreateAccountFragment()
        }

        return view
    }

    private fun onNextButtonClick() {
        val fullName = fullNameField.text.toString().trim()
        val email = emailField.text.toString().trim()
        val phone = phoneField.text.toString().trim()
        val password = passwordField.text.toString().trim()
        val isTermsAccepted = termsCheckBox.isChecked

        clearAllErrors()

        when {
            !credentialsManager.isValidFullName(fullName) -> setError(
                fullNameLayout,
                R.string.error_name_required
            )

            !credentialsManager.isEmailValid(email) -> setError(
                emailLayout,
                R.string.error_invalid_email
            )

            !credentialsManager.isValidPhoneNumber(phone) -> setError(
                phoneLayout,
                R.string.error_phone_number_required
            )

            !credentialsManager.isValidPassword(password) -> setError(
                passwordLayout,
                R.string.error_password_invalid
            )

            !isTermsAccepted -> showToast(R.string.error_terms_and_conditions_required)
            credentialsManager.isEmailAlreadyUsed(email) -> setError(
                emailLayout,
                R.string.error_email_already_used
            )

            else -> registerUserAndProceed(email, password)
        }
    }

    private fun registerUserAndProceed(email: String, password: String) {
        credentialsManager.registerUser(email, password)
        showToast(R.string.success_registration)
        parentFragmentManager.popBackStack()
    }

    private fun setError(layout: TextInputLayout, errorResId: Int?) {
        layout.error = errorResId?.let { getString(it) }
    }

    private fun clearAllErrors() {
        setError(fullNameLayout, null)
        setError(emailLayout, null)
        setError(phoneLayout, null)
        setError(passwordLayout, null)
    }

    private fun showToast(messageResId: Int) {
        Toast.makeText(requireContext(), getString(messageResId), Toast.LENGTH_SHORT).show()
    }

    private fun navigateToCreateAccountFragment() {
        parentFragmentManager.popBackStack()
    }
}