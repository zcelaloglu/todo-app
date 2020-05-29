package com.celaloglu.zafer.todos.util.validator

import android.text.TextUtils

object InputValidator {

    fun isValid(input: String?): ValidationResult {

        if (TextUtils.isEmpty(input)) {
            return ValidationResult.NOT_VALID
        }
        return ValidationResult.VALID
    }
}