package com.example.myloginapplication.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myloginapplication.R
import com.example.myloginapplication.viewmodel.UserViewModel
import com.google.android.material.button.MaterialButton

class LoginFragment : Fragment() {
    private val TAG: String = javaClass.simpleName
    private val userViewModel: UserViewModel? by activityViewModels()

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initObservers()
        // Inizializza le SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("login_preferences", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        // Inflate the layout for this fragment
        val email: TextView = view.findViewById(R.id.email)
        val password: TextView = view.findViewById(R.id.password)
        val signUpLink: TextView = view.findViewById(R.id.signuplink)
        val buttonLogin: MaterialButton = view.findViewById(R.id.loginbtn)

        buttonLogin.setOnClickListener {
            val userEmail = email.text.toString()
            val userPassword = password.text.toString()
            userViewModel?.login(userEmail,userPassword)
        }

        signUpLink.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        return view
    }



        /**
     * it initalises the view model and the methods used to retrieve the live data for the interface
     */
    private fun initObservers() {
        Log.i(TAG, "Registering Observers: ViewModel? $userViewModel")
        userViewModel?.loginResult?.observe(this) { loginResult ->
            if(loginResult) {
                Toast.makeText(activity, "Login successful", Toast.LENGTH_SHORT).show()
                Log.i(TAG, "Login Successful")

                val editor = sharedPreferences.edit()
                editor.putBoolean("first_login", false)
                editor.apply()

                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            } else {
                Toast.makeText(activity, "Wrong username or password", Toast.LENGTH_SHORT).show()
                Log.i(TAG, "Login Failed")
            }
        }
    }
}