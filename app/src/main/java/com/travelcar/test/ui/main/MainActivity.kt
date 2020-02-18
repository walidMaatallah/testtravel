package com.travelcar.test.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.viewpager.widget.ViewPager
import com.travelcar.test.R
import com.travelcar.test.ui.main.car.list.CarsListFragmentCallback
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private lateinit var carsListFragmentCallback: CarsListFragmentCallback

    fun setOnDataListener(fragmentLister: CarsListFragmentCallback) {
        carsListFragmentCallback = fragmentLister
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    /**
     * init activity views
     */
    private fun initViews(){
        val mainPagerAdapter = MainPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = mainPagerAdapter
        tabs.setupWithViewPager(view_pager)
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}

            override fun onPageSelected(position: Int) {
                if(position == 1) search.visibility = View.GONE
                else search.visibility = View.VISIBLE
            }
        })

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                carsListFragmentCallback.onFilter(newText)
                return true
            }
        })
    }
}