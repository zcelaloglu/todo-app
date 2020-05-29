package com.celaloglu.zafer.validator

import com.celaloglu.zafer.todos.util.validator.InputValidator
import com.celaloglu.zafer.todos.util.validator.ValidationResult
import com.google.common.truth.Truth
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class EmailValidatorTest {

    @Test
    fun emailValidator_CorrectEmailSimple_ReturnsTrue() {
        Truth.assertThat(InputValidator.isValid("title")).isEqualTo(ValidationResult.VALID)
    }

    @Test
    fun emailValidator_EmptyString_ReturnsFalse() {
        Truth.assertThat(InputValidator.isValid("")).isEqualTo(ValidationResult.NOT_VALID)
    }

    @Test
    fun emailValidator_NullEmail_ReturnsFalse() {
        Truth.assertThat(InputValidator.isValid(null)).isEqualTo(ValidationResult.NOT_VALID)
    }
}
