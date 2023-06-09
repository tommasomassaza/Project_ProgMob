package com.example.myloginapplication.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myloginapplication.R

class HomeFragment : Fragment() {
    private val TAG: String = javaClass.simpleName
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inizializza le SharedPreferences
        // Ottieni le SharedPreferences, se non esistono, vengono create
        sharedPreferences = requireActivity().getSharedPreferences("login_preferences", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        // sharedPreferences.getBoolean("first_login", true) legge il valore booleano associato alla chiave "first_login" dalle SharedPreferences.
        // Se la chiave non esiste, viene restituito il valore predefinito "true".
        val isFirstLogin = sharedPreferences.getBoolean("first_login", true)


        if(isFirstLogin) {
            // Primo login dell'utente, esegui la navigazione al LoginFragment
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
            Log.i(TAG, "Redirect to fragment_login")
        } else{
            Log.i(TAG, "Already authenticated")
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}