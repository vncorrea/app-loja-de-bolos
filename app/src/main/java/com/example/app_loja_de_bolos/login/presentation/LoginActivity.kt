package com.example.app_loja_de_bolos.login.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.app_loja_de_bolos.R
import com.example.app_loja_de_bolos.databinding.ActivityLoginBinding
import com.example.app_loja_de_bolos.home.presentation.HomeActivity
import com.example.app_loja_de_bolos.recover_password.presentation.RecoverPasswordActivity
import com.example.app_loja_de_bolos.register.presentation.RegisterActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiAction.collect { action ->
                    executeAction(action)
                }
            }
        }

        viewModel.onViewCreated()

        with(binding) {
            btnLogin.setOnClickListener {
                viewModel.onLoginClicked(getEmailText(), getPasswordText())
            }

            btnRegister.setOnClickListener {
                viewModel.onCreateAccountClicked()
            }

            btnForgotPassword.setOnClickListener {
                viewModel.onForgotPasswordClicked()
            }
        }
    }

    private fun executeAction(action: LoginAction) {
        when(action) {
            LoginAction.NAVIGATE_HOME -> navigateHome()
            LoginAction.NAVIGATE_REGISTER -> navigateRegister()
            LoginAction.SHOW_ERROR_MSG -> showMessage("An error occurred. Try again.")
            LoginAction.NAVIGATE_RECOVER_PASSWORD -> navigateRecoverPassword()
        }
    }

    private fun navigateHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateRecoverPassword() {
        val intent = Intent(this, RecoverPasswordActivity::class.java)
        startActivity(intent)
    }

    private fun navigateRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    private fun getEmailText() =
        binding.etEmail.text.toString()

    private fun getPasswordText() =
        binding.etPassword.text.toString()
}