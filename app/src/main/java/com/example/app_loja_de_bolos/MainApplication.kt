package com.example.app_loja_de_bolos

import android.app.Application
import com.example.app_loja_de_bolos.cake_category_list.di.cakeCategoryListModule
import com.example.app_loja_de_bolos.home.di.homeModule
import com.example.app_loja_de_bolos.login.di.loginModule
import com.example.app_loja_de_bolos.recover_password.di.recoverPasswordModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import com.example.app_loja_de_bolos.register.di.registerModule

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(loginModule)
            modules(registerModule)
            modules(recoverPasswordModule)
            modules(homeModule)
            modules(cakeCategoryListModule)
        }
    }
}