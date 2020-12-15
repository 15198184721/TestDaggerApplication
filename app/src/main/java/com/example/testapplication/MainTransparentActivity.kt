package com.example.testapplication

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.testapplication.base.BaseDaggerActivity
import com.example.testapplication.testModel.TestModelA
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainTransparentActivity : BaseDaggerActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transparent_main)
    }
}
