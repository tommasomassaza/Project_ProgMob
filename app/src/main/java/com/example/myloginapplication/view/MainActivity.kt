package com.example.myloginapplication.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.myloginapplication.R
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{
    private val TAG: String = javaClass.simpleName

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("login_preferences", Context.MODE_PRIVATE)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController


        // associo il menu del navigation drawer al NavController
        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener(this)

        drawerLayout = findViewById(R.id.Drawer_layout)
        toolbar = findViewById(R.id.toolbar)
        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_nav,
            R.string.close_nav
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Rimuovi il listener del toggle quando il fragment viene distrutto
        drawerLayout.removeDrawerListener(toggle)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            // Il menu laterale è aperto, chiudi il menu
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_logout -> {
                val editor = sharedPreferences.edit()
                editor.putBoolean("first_login", true)
                editor.apply()

                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
                Log.i(TAG, "Logout")
                navController.navigate(R.id.action_homeFragment_to_loginFragment)
            }
            else -> super.onOptionsItemSelected(item)
        }

        // chiudo il menù al click
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

}