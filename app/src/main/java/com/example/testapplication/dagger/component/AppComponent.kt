package com.example.testapplication.dagger.component

import android.app.Application
import com.example.testapplication.App
import com.example.testapplication.dagger.model.ActivityModule
import com.example.testapplication.dagger.model.AppModule
import com.example.testapplication.testModel.TestModelA
import com.google.gson.Gson
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ActivityModule::class
    ]
)
interface AppComponent {
    /**
     * 自定义生成Builder 的注解。dagger会根据这个注解的类生成相关的Builder类
     */
    @Component.Builder
    interface Builder {
        @BindsInstance //自定义 Builder 的注解，就是毁在这个builder类中生成此注解定义的方法
        fun application(app: Application): Builder

        //自定义 Builder 的注解，就是毁在这个builder类中生成此注解定义的方法(可理解为在Builder中自定义生成一些属性)
        // 相当于使用了提供者 @Provides 对这个对象进行提供声明
        @BindsInstance
        fun setModelA(modelA: TestModelA): Builder

        fun build(): AppComponent
    }

    fun inject(app: App) {}
}