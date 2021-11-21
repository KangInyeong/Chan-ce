package com.kang.chan_ce

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.internal.ContextUtils.getActivity
import com.kang.chan_ce.databinding.ActivityMainBinding
import me.relex.circleindicator.CircleIndicator3

class MainActivity : AppCompatActivity() {

    private val num_page = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 가로스와이프 광고 배너 view fragment

        // 가로스와이프 광고 배너 view fragment
        var mPager = binding.viewPagerAdbanner
        var pagerAdapter = AdAdapter(this,num_page)
        mPager.setAdapter(pagerAdapter)

        var mIndicator = binding.indicator
        mIndicator.setViewPager(mPager)
        mIndicator.createIndicators(num_page, 0)

        mPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL)

        // 처음 시작하는 이미지 설정

        // 처음 시작하는 이미지 설정
        mPager.setCurrentItem(1000) // 시작지점

        mPager.setOffscreenPageLimit(4) //최대 이미지 수


        mPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (positionOffsetPixels == 0) {
                    mPager.setCurrentItem(position)
                }
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mIndicator.animatePageSelected(position % num_page)
            }
         })

        binding.btnMyPage.setOnClickListener {
            val intent = Intent(this, MypageActivity::class.java)
            startActivity(intent)
        }

        binding.btnSubscribe.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
    }
}