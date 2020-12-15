package com.example.testapplication.base

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Lazy
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * 基础的Activity类。处理Dagger注解的处理类
 */
open class BaseDaggerActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var vmFactory: Lazy<ViewModelProvider.Factory>


    //依赖注入的对象。当前的对象需要使用哪个对象去注入
    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    /**
     * 此方法为获取ViewModel的方法
     * @return T
     */
    inline fun <reified T : ViewModel> createVM(): T = ViewModelProvider(this, vmFactory.get()).get(T::class.java)
}