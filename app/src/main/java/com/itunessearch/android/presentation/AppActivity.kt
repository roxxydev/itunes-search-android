package com.itunessearch.android.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.itunessearch.android.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
