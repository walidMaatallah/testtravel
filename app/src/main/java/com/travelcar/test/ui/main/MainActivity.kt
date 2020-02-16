package com.travelcar.test.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.travelcar.test.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainPagerAdapter = MainPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = mainPagerAdapter
        tabs.setupWithViewPager(view_pager)
    }
}