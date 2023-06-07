package com.example.myloginapplication.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myloginapplication.database.Repository
import com.example.myloginapplication.database.user.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private var TAG = this::class.simpleName
    private val repository: Repository = Repository(application)

    private var currentUser: MutableLiveData<UserData?> = MutableLiveData<UserData?>()
    val loginResult: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val registrationResult: MutableLiveData<RegistrationResult> = MutableLiveData<RegistrationResult>()

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if(repository.login(email,password)){
                currentUser.postValue(repository.getUserByEmail(email))
                loginResult.postValue(true)
                Log.i(TAG,"Current user ${currentUser.value.toString()}")
            } else {
                loginResult.postValue(false)
            }
        }
    }

    fun register(name: String, surname: String, email: String, password1: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if(repository.existsUser(email)) {
                registrationResult.postValue(RegistrationResult.AlreadyRegistered)
            } else {
                val user = UserData(name, surname, email, password1)
                if(repository.insertUser(user)) {
                    registrationResult.postValue(RegistrationResult.Success)
                } else {
                    registrationResult.postValue(RegistrationResult.Error)
                }
            }
        }
    }
}
