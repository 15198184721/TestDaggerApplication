package com.example.testapplication.dagger.model

import com.example.testapplication.testModel.TestModelA
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Module(
    includes = [
        AndroidSupportInjectionModule::class,
        ActivityModule::class,
        FragmentModule::class,
        VMModule::class
    ]
)
class AppModule {

    @Singleton
    @Provides
    fun provideGson(): Gson = Gson()

    /**
     * 因为在自定义的builder中已经设置。所以不需要在提供
     */
//    @Singleton
//    @Provides
//    fun provideTestModelA(): TestModelA = TestModelA()
}