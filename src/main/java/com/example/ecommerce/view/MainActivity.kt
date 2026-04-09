package com.example.ecommerce.view

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecommerce.R
import com.example.ecommerce.databinding.ActivityMainBinding
import com.example.ecommerce.viewmodel.ProductViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var viewModel: ProductViewModel

    private val prefs by lazy {
        getSharedPreferences("ShopEasySession", MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        setupToolbar()
        setupNavController()
        setupDrawer()
        setupNavHeader()
        observeViewModel()

        viewModel.getCategories()

    }

    private fun setupNavController() {
        val navHostFragment = binding.navHostFragment
            .getFragment<NavHostFragment>()
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.cartFragment),
            binding.drawerLayout
        )
        setupActionBarWithNavController(navController!!, appBarConfiguration)

        navController!!.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment, R.id.cartFragment -> {
                    binding.toolbar.setNavigationOnClickListener {
                        binding.drawerLayout.openDrawer(
                            androidx.core.view.GravityCompat.START
                        )
                    }
                }
                else -> {
                    binding.toolbar.setNavigationOnClickListener {
                        navController!!.navigateUp()
                    }
                }
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController!!.navigateUp() || super.onSupportNavigateUp()
    }
    private fun setupNavHeader() {
        val headerView = binding.navigationView.getHeaderView(0)

        headerView.findViewById<TextView>(R.id.tv_user_name).text =
            "Welcome ${prefs.getString("full_name", "")}"
        headerView.findViewById<TextView>(R.id.tv_user_email).text =
            prefs.getString("email_id", "")
        headerView.findViewById<TextView>(R.id.tv_user_phone).text =
            prefs.getString("mobile_no", "")
    }



    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun setupDrawer() {


        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_logout -> logout()
                else -> navController?.navigate(menuItem.itemId)
            }

            menuItem.isChecked = true
            binding.drawerLayout.closeDrawers()
            true
        }
    }
    private fun observeViewModel() {


        viewModel.isLoading.observe(this) { isLoading ->

        }

        viewModel.error.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun logout() {
        prefs.edit().clear().apply()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}