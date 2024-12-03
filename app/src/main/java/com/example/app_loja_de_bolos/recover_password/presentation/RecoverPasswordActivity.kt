package com.example.app_loja_de_bolos.recover_password.presentation

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
import com.example.app_loja_de_bolos.databinding.ActivityRecoverPasswordBinding
import com.example.app_loja_de_bolos.login.presentation.LoginActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecoverPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecoverPasswordBinding
    private val viewModel: RecoverPasswordViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRecoverPasswordBinding.inflate(layoutInflater)
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

        with(binding) {
            btnRecoverPassword.setOnClickListener {
                viewModel.onRecoverPasswordClicked(getEmailText())
            }

            btnBackToLogin.setOnClickListener {
                viewModel.onBackToLoginClicked()
            }
        }
    }

    private fun executeAction(action: RecoverPasswordAction) {
        when (action) {
            RecoverPasswordAction.NAVIGATE_LOGIN -> navigateLogin()
            RecoverPasswordAction.SHOW_ERROR_MSG -> showMessage("Um erro ocorreu. Tente novamente!")
        }
    }

    private fun navigateLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
//        finish()
    }

    private fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    private fun getEmailText() =
        binding.etEmail.text.toString()
}