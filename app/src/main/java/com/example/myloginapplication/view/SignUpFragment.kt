package com.example.myloginapplication.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myloginapplication.R
import com.example.myloginapplication.viewmodel.RegistrationResult
import com.example.myloginapplication.viewmodel.UserViewModel
import com.google.android.material.button.MaterialButton

class SignUpFragment : Fragment() {
    private val TAG: String = javaClass.simpleName
    private val userViewModel: UserViewModel? by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        val buttonSignup: MaterialButton = view.findViewById(R.id.signupbtn)
        val insertName : EditText = view.findViewById(R.id.namesignup)
        val insertSurname : EditText = view.findViewById(R.id.surnamesignup)
        val insertEmail : EditText = view.findViewById(R.id.emailsignup)
        val insertPassword1 : EditText = view.findViewById(R.id.password1)
        val insertPassword2 : EditText = view.findViewById(R.id.password2)

        initObservers()

        buttonSignup.setOnClickListener {
            val name = insertName.text.toString()
            val surname = insertSurname.text.toString()
            val email = insertEmail.text.toString()
            val password1 = insertPassword1.text.toString()
            val password2 = insertPassword2.text.toString()
            if( name.isEmpty() || surname.isEmpty() || email.isEmpty() ||
                password1.isEmpty() || password2.isEmpty()) {

                Toast.makeText(activity,"Inserire i campi mancanti",Toast.LENGTH_SHORT).show()

            } else if(password1 != password2) {
                Toast.makeText(activity,"Le password non corrispondono",Toast.LENGTH_SHORT).show()
            } else {
                userViewModel?.register(name,surname,email,password1)
            }
        }

        // Inflate the layout for this fragment
        return view
    }

    private fun initObservers() {
        Log.i(TAG, "Registering Observers: ViewModel? $userViewModel")
        userViewModel?.registrationResult?.observe(viewLifecycleOwner) { registrationResult ->
            when (registrationResult) {
                is RegistrationResult.Success -> {
                    Toast.makeText(activity, "Sign Up Success", Toast.LENGTH_SHORT).show()
                    Log.i(TAG, "Sign Up Success")
                    findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
                }
                is RegistrationResult.Error -> {
                    Toast.makeText(activity, "Registration Failed", Toast.LENGTH_SHORT).show()
                    Log.i(TAG, "Registration Failed")
                }
                RegistrationResult.AlreadyRegistered -> {
                    Toast.makeText(activity, "User already exists", Toast.LENGTH_SHORT).show()
                    Log.i(TAG, "User already exists")
                }
            }
        }
    }

}