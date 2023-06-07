package com.example.myloginapplication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.myloginapplication.R
import com.google.android.material.navigation.NavigationView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DataFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DataFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val drawer_layout: DrawerLayout? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_data, container, false)
        var drawer_Layout: DrawerLayout = view.findViewById(R.id.Drawer_layout)
        val nav_view: NavigationView = view.findViewById(R.id.nav_view)
        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        val toggle : ActionBarDrawerToggle = ActionBarDrawerToggle(
            requireActivity(),
            drawer_Layout,
            toolbar,
            R.string.open_nav,
            R.string.close_nav
        )

        drawer_Layout.addDrawerListener(toggle)
        toggle.syncState()

        return view
    }






}