package com.example.app_loja_de_bolos

import android.app.Application
import com.example.app_loja_de_bolos.login.di.loginModule
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
        }
    }
}