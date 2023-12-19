package com.example.uas__ppapb.loginRegister

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.uas__ppapb.R
import com.example.uas__ppapb.databinding.MainLoginRegisterBinding
import com.google.android.material.tabs.TabLayout

class MainLoginRegisterActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: AdapterLoginRegister
    private lateinit var binding: MainLoginRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainLoginRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Move these lines here after setContentView
        tabLayout = findViewById(R.id.tablayout_register_login)
        viewPager2 = findViewById(R.id.viewpager_register_login)
        adapter = AdapterLoginRegister(supportFragmentManager, lifecycle)

        tabLayout.addTab(tabLayout.newTab().setText("Login"))
        tabLayout.addTab(tabLayout.newTab().setText("Register"))

        viewPager2.adapter = adapter

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    viewPager2.currentItem = tab.position
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                viewPager2.currentItem = tab!!.position
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
                viewPager2.currentItem = tab!!.position
            }
        })

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })
    }
}
