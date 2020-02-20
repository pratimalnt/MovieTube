package com.pratima.movietube.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.pratima.movietube.R
import com.pratima.movietube.view.home.search.SearchActivity


class MainActivity : AppCompatActivity() {

    private var mTabLayout: TabLayout? = null
    private var mViewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        mViewPager = findViewById(R.id.view_pager)
        mTabLayout = findViewById(R.id.tabs)

        mTabLayout!!.addTab(mTabLayout!!.newTab().setText("Movie"))
        mTabLayout!!.addTab(mTabLayout!!.newTab().setText("Tv Shows"))
        mTabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = ViewPagerAdapter(this, supportFragmentManager)
        mViewPager!!.adapter = adapter
        mViewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(mTabLayout))

        mTabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                mViewPager!!.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    // actions on click menu items
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_search -> {
            goToSearchActivity()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun goToSearchActivity() {
        startActivity(Intent(this, SearchActivity::class.java))
    }


}