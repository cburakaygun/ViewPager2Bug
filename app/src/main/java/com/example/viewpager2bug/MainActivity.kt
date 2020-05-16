package com.example.viewpager2bug

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2

    private lateinit var buttonPage0: Button
    private lateinit var buttonPage1: Button
    private lateinit var buttonPage2: Button
    private lateinit var buttonResetAdapter: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.view_pager)
        buttonPage0 = findViewById(R.id.button_page0)
        buttonPage1 = findViewById(R.id.button_page1)
        buttonPage2 = findViewById(R.id.button_page2)
        buttonResetAdapter = findViewById(R.id.button_reset_adapter)

        setUpViewPager()
        setUpButtons()
    }

    private fun setUpViewPager() {
        viewPager.let {
            it.offscreenPageLimit = 2
            it.adapter = createPagerAdapter()
        }
    }

    private fun setUpButtons() {
        buttonPage0.setOnClickListener {
            log("Button0 Click")
            selectPage(0)
        }
        buttonPage1.setOnClickListener {
            log("Button1 Click")
            selectPage(1)
        }
        buttonPage2.setOnClickListener {
            log("Button2 Click")
            selectPage(2)
        }
        buttonResetAdapter.setOnClickListener {
            log("ButtonResetAdapter Click")
            viewPager.adapter = createPagerAdapter()
        }
    }

    private fun createPagerAdapter(): MyPagerAdapter {
        return MyPagerAdapter(supportFragmentManager, lifecycle)
    }

    private fun selectPage(page: Int, smoothScroll: Boolean = false) {
        viewPager.setCurrentItem(page, smoothScroll)
    }

    private fun log(message: String) {
        Log.d("ViewPager2Bug", "MainActivity | $message")
    }

}

private class MyPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return PagerFragment.newInstance(position)
    }

}
