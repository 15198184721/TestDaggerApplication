package com.example.testapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivityViewModel @Inject constructor() : ViewModel() {
    private val users: MutableLiveData<MutableList<String>> by lazy {
        MutableLiveData<MutableList<String>>(mutableListOf())
    }

    fun getUsers(): LiveData<MutableList<String>> {
        return users
    }

    fun loadUsers() {
        val tem = users.value
        tem?.add("${users.value?.size}")
        users.postValue(tem)
    }

    override fun onCleared() {
        super.onCleared()
        Log.e("viewmodel", "已经清除了ViewModel内存")
    }
}