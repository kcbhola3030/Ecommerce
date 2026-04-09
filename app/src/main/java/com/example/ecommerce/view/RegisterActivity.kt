package com.example.ecommerce.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.ecommerce.R
import com.example.ecommerce.databinding.ActivityRegisterBinding
import com.example.ecommerce.viewmodel.AuthViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        setupClickListeners()
        observeViewModel()
    }
    private fun setupClickListeners() {

        binding.btnRegister.setOnClickListener {
            val fullName = binding.etFullName.text.toString().trim()
            val mobile   = binding.etMobile.text.toString().trim()
            val email    = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            viewModel.register(fullName, mobile, email, password)
        }

        binding.tvLogin.setOnClickListener {
            finish()
        }
    }

    private fun observeViewModel() {

        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.btnRegister.isEnabled = false
            } else {
                binding.progressBar.visibility = View.GONE
                binding.btnRegister.isEnabled = true
            }
        }

        viewModel.registerSuccess.observe(this) { response ->
            Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        viewModel.registerError.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}