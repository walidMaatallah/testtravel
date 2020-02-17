package com.travelcar.test.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.viewpager.widget.ViewPager
import com.travelcar.test.R
import com.travelcar.test.ui.main.car.FragmentCallback
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private lateinit var fragmentCallback: FragmentCallback

    fun setOnDataListener(fragmentLister: FragmentCallback) {
        fragmentCallback = fragmentLister
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainPagerAdapter = MainPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = mainPagerAdapter
        tabs.setupWithViewPager(view_pager)


        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                    if(position == 1) search.visibility = View.GONE
                    else search.visibility = View.VISIBLE
            }
        })

        /*fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                fragmentCallback.onFilter(newText)
                Toast.makeText(this@MainActivity, newText, Toast.LENGTH_SHORT).show()
                return true
            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchItem = menu.findItem(R.id.action_search)
        // Optional: if you want to expand SearchView from icon to edittext view
        searchItem.expandActionView()

        val searchView = searchItem.actionView as SearchView

        return true
    }

}