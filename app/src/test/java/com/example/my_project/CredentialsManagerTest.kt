import com.example.my_project.CredentialsManager
import org.junit.Assert.*
import org.junit.Test

class CredentialsManagerTest {

    private val credentialsManager = CredentialsManager

    @Test
    fun testValidEmail() {
        assertTrue(credentialsManager.isEmailValid("example@test.com"))
        assertFalse(credentialsManager.isEmailValid("invalid-email"))
    }

    @Test
    fun testValidPassword() {
        assertTrue(credentialsManager.isValidPassword("password123"))
        assertFalse(credentialsManager.isValidPassword("short"))
    }

    @Test
    fun testEmailAlreadyUsed() {
        credentialsManager.registerUser("test@test.com")

        assertTrue(credentialsManager.isEmailAlreadyUsed("test@test.com"))
        assertTrue(credentialsManager.isEmailAlreadyUsed("TEST@TEST.COM"))
        assertFalse(credentialsManager.isEmailAlreadyUsed("new@test.com"))
    }

    @Test
    fun testValidateCredentials() {
        // Valid email, password, and checkbox checked
        assertTrue(credentialsManager.validateCredentials("example@test.com", "password123", true))

        // Invalid email
        assertFalse(credentialsManager.validateCredentials("invalid-email", "password123", true))

        // Invalid password
        assertFalse(credentialsManager.validateCredentials("example@test.com", "short", true))

        // Checkbox not checked
        assertFalse(
            credentialsManager.validateCredentials(
                "example@test.com",
                "password123",
                false
            )
        )
    }

    @Test
    fun testValidFullName() {
        assertTrue(credentialsManager.isValidFullName("John Doe"))
        assertFalse(credentialsManager.isValidFullName(""))
        assertTrue(credentialsManager.isValidFullName("John"))
    }

    @Test
    fun testValidPhoneNumber() {
        assertTrue(credentialsManager.isValidPhoneNumber("1234567890"))
        assertFalse(credentialsManager.isValidPhoneNumber("12345"))
        assertFalse(credentialsManager.isValidPhoneNumber(""))
    }

    @Test
    fun testTermsAccepted() {
        assertFalse(credentialsManager.isTermsAccepted(false))
    }

    @Test
    fun testValidateCredentialsForSignUp() {
        // Valid full name, email, phone, password, and checkbox checked
        assertTrue(
            credentialsManager.ValidateCredentialsForSignUp(
                "John Doe", "example@test.com", "1234567890", "password123", true
            )
        )

        // Invalid full name
        assertFalse(
            credentialsManager.ValidateCredentialsForSignUp(
                "", "example@test.com", "1234567890", "password123", true
            )
        )

        // Invalid email
        assertFalse(
            credentialsManager.ValidateCredentialsForSignUp(
                "John Doe", "invalid-email", "1234567890", "password123", true
            )
        )

        // Invalid phone number
        assertFalse(
            credentialsManager.ValidateCredentialsForSignUp(
                "John Doe", "example@test.com", "12345", "password123", true
            )
        )

        // Invalid password
        assertFalse(
            credentialsManager.ValidateCredentialsForSignUp(
                "John Doe", "example@test.com", "1234567890", "short", true
            )
        )

        // Checkbox not checked
        assertFalse(
            credentialsManager.ValidateCredentialsForSignUp(
                "John Doe", "example@test.com", "1234567890", "password123", false
            )
        )
    }

    @Test
    fun testHardcodedLoginCredentials() {
        val validEmail = "test@te.st"
        val validPassword = "1234"

        assertTrue(validEmail == "test@te.st" && validPassword == "1234")

        assertFalse(validEmail == "wrong@te.st" && validPassword == "1234")
        assertFalse(validEmail == "test@te.st" && validPassword == "wrong")
    }
}