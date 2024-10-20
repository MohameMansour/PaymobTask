package com.example.paymobtask.app

import androidx.multidex.MultiDexApplication
import com.example.paymobtask.di.RoomModule
import com.example.paymobtask.di.networkModule
import com.example.paymobtask.di.repoModule
import com.example.paymobtask.di.usecasesModule
import com.example.paymobtask.di.vmsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PaymobTask : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidLogger()
            androidContext(this@PaymobTask)
            modules(
                vmsModule,
                repoModule,
                usecasesModule,
                networkModule,
                RoomModule
            )
        }
    }

}