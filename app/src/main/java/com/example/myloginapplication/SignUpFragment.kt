package com.example.myloginapplication

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton

class SignUpFragment : Fragment() {
    private lateinit var sqLiteHelper: SQLiteHelper
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        val buttonsignup: MaterialButton = view.findViewById(R.id.signupbtn)
        val insert_name : EditText = view.findViewById(R.id.namesignup)
        val insert_surname : EditText = view.findViewById(R.id.surnamesignup)
        val insert_email : EditText = view.findViewById(R.id.emailsignup)
        val insert_password1 : EditText = view.findViewById(R.id.password1)
        val insert_password2 : EditText = view.findViewById(R.id.password2)
        sqLiteHelper = SQLiteHelper(this.requireContext()) // FORSE ERRORE


        buttonsignup.setOnClickListener {
            if(insert_name.text.toString().isEmpty() ||
                insert_surname.text.toString().isEmpty() ||
                insert_email.text.toString().isEmpty() ||
                insert_password1.text.toString().isEmpty() ||
                insert_password2.text.toString().isEmpty()){
                Toast.makeText(activity,"Inserire i campi mancanti",Toast.LENGTH_SHORT).show()

            }else if(insert_password1.text.toString() != insert_password2.text.toString()){
                Toast.makeText(activity,"Le password non corrispondono",Toast.LENGTH_SHORT).show()
            }else if(sqLiteHelper.existUser(insert_email.text.toString(),insert_password1.text.toString())){
                    Toast.makeText(activity,"Profilo esistente",Toast.LENGTH_SHORT).show()
            }else{
                val user = User(insert_name.text.toString(),insert_surname.text.toString(),
                                insert_email.text.toString(),insert_password1.text.toString())
                val status = sqLiteHelper.insertUser(user)
                if (status <= -1){
                    Toast.makeText(activity,"NOT SUCCESS",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(activity,"SIGN UP SUCCESS",Toast.LENGTH_SHORT).show()
                    Toast.makeText(activity,sqLiteHelper.getUsers().toString(),Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_signUpFragment_to_homefragment)
                }

            }


        }


        // Inflate the layout for this fragment
        return return view
    }
}