package com.example.testapplication

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Build
import com.example.testapplication.dagger.component.DaggerAppComponent
import com.example.testapplication.dagger.model.android.integration.AppDelegate
import com.example.testapplication.testModel.TestModelA
import com.google.gson.Gson
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


class App : Application(), HasAndroidInjector {

    companion object {

        fun getCpu(): String {
            val abis = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Build.SUPPORTED_ABIS
            } else {
                arrayOf(Build.CPU_ABI, Build.CPU_ABI2)
            }
            val abiStr = StringBuilder()
            for (abi in abis) {
                abiStr.append(abi)
                abiStr.append(',')
            }
            return abiStr.toString()
        }
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var gson: Gson

    var appDelegate: AppDelegate? = null

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        appDelegate = appDelegate?: AppDelegate(this)
    }

    override fun onCreate() {
        super.onCreate()
        injectThis()
        appDelegate?.onCreate(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        appDelegate?.onTerminate(this)
    }

    private fun injectThis() {
        DaggerAppComponent.builder()
            .application(this)
            .setModelA(TestModelA())
            .build()
            .inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

}