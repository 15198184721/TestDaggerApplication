package com.uni.kuaihuo.lib.aplication.android

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/***
 *
 * ViewModel通过Dagger注入的标识注解
 * 主要是为了区别不同的ViewModel
 */

@MustBeDocumented
@Target(
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)