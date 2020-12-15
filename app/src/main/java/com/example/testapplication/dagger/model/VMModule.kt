package com.example.testapplication.dagger.model

import androidx.lifecycle.ViewModel
import com.example.testapplication.MainActivityViewModel
import com.example.testapplication.dagger.model.android.FactoryModule
import com.uni.kuaihuo.lib.aplication.android.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * ViewModule的dagger注入model
 */
@Module(
    includes = [
        FactoryModule::class
    ]
)
abstract class VMModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainActivityViewModel(viewModel: MainActivityViewModel): ViewModel
}