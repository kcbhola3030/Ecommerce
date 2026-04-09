package com.example.ecommerce.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.ecommerce.databinding.ActivityLoginBinding
import com.example.ecommerce.viewmodel.AuthViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    lateinit var viewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("ShopEasySession", MODE_PRIVATE)
        if (prefs.getBoolean("is_logged_in", false)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }


        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        setupClickListeners()
        observeViewModel()

    }
    private fun setupClickListeners() {

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            viewModel.login(email, password)
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
    private fun observeViewModel() {

        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.btnLogin.isEnabled = false
            } else {
                binding.progressBar.visibility = View.GONE
                binding.btnLogin.isEnabled = true
            }
        }

        viewModel.loginSuccess.observe(this) { response ->

            response.user?.let { user ->
                val prefs = getSharedPreferences("ShopEasySession", MODE_PRIVATE)
                prefs.edit().apply {
                    putBoolean("is_logged_in", true)
                    putString("user_id", user.user_id)
                    putString("full_name", user.full_name)
                    putString("email_id", user.email_id)
                    putString("mobile_no", user.mobile_no)
                    apply()
                }
            }

            Toast.makeText(this, "Welcome ${response.user?.full_name}!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        viewModel.loginError.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}