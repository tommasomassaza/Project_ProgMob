package com.example.myloginapplication

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton

class Homefragment : Fragment() {
    private lateinit var sqLiteHelper: SQLiteHelper

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_homefragment, container, false)
        // Inflate the layout for this fragment
        val username: TextView = view.findViewById(R.id.username)
        val signUpLink: TextView = view.findViewById(R.id.signuplink)
        val password: TextView = view.findViewById(R.id.password)
        val buttonlogin: MaterialButton = view.findViewById(R.id.loginbtn)
        sqLiteHelper = SQLiteHelper(this.requireContext()) // FORSE ERRORE

        buttonlogin.setOnClickListener {
            sqLiteHelper.getUsers()
            if (sqLiteHelper.existUser(username.text.toString(),password.text.toString())
            ) {
                User.currentUser= sqLiteHelper.getUser(username.text.toString(),password.text.toString())
               // User.id_user= sqLiteHelper.getUserId()
                Toast.makeText(activity, "Login sucessfull", Toast.LENGTH_SHORT).show()
                println("User.currentUser")
                println(User.currentUser!!.email.toString())
                findNavController().navigate(R.id.action_homefragment_to_dataFragment)
            } else {
                Toast.makeText(activity, "Wrong username or password", Toast.LENGTH_SHORT).show()

            }
        }

        signUpLink.setOnClickListener{
            findNavController().navigate(R.id.action_homefragment_to_signUpFragment)
        }



        return view

    }

}