package com.example.testapplication.dagger.model

import dagger.Module

/***
 * Fragment的dagger注入model
 *
 * 该类提供整个module中fragment的实例(这里需要注意所拥有该Fragment的Activity必须实现HasSupportFragmentInjector接口)
 * 例如:
 *   @ContributesAndroidInjector
 *   abstract fun contributeXXFragment(): XXFragment
 */
@Module
abstract class FragmentModule {

}