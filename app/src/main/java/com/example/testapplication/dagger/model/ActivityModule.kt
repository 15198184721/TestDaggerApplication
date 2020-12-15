package com.example.testapplication.dagger.model

import com.example.testapplication.MainActivity
import com.example.testapplication.MainTransparentActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Activity的dagger注入model
 */
@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeMainTransparentActivity(): MainTransparentActivity
}