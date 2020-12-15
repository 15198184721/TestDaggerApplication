package com.example.testapplication

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.testapplication.base.BaseDaggerActivity
import com.example.testapplication.testModel.TestModelA
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : BaseDaggerActivity() {

    @Inject
    lateinit var modelA: TestModelA

    @Inject
    lateinit var vMModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        vMModel = createVM()
        setContentView(R.layout.activity_main)
        src_yuanshi.text = "${vMModel}\n${modelA}"
        testInputCheck.setOnClickListener {
            vMModel.loadUsers()
        }
        testGo.setOnClickListener {
            startActivity(Intent(this, MainTransparentActivity::class.java))
        }
        vMModel.getUsers().observe(this, Observer { users ->
            src_yuanshi.text = "${users.size}"
        })
    }
}
