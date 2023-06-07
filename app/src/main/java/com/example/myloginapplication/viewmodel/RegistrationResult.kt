package com.example.myloginapplication.viewmodel


sealed class RegistrationResult {
    object Success : RegistrationResult()
    object Error : RegistrationResult()
    object AlreadyRegistered : RegistrationResult()
}