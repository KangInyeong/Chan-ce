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
import android.content.pm.PackageManager

import android.content.pm.PackageInfo
import android.util.Base64
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import net.daum.mf.map.api.MapView
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class MainActivity : AppCompatActivity() {

/*    private fun getAppKeyHash() {
        try {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                var md: MessageDigest
                md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val something = String(Base64.encode(md.digest(), 0))
                Log.e("Hash key", something)
            }
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e("name not found", e.toString())
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
    }*/

    val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    val useremail = user?.email
    val uid = user?.uid
    val userid = uid.toString()

    var username = user?.displayName

    private val num_page = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

/*        getAppKeyHash()*/

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference()

        if(username == ""||username == null){
            val email_list = useremail?.split("@")
            if (username != null) {
                username = email_list!![0]
            }
        }

//        val username = intent.getStringExtra("username").toString() //이게 왜 안되냐구 ㅠ

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
//            Toast.makeText( this, "login $username", Toast.LENGTH_SHORT ).show()
            val intent = Intent(this, MypageActivity::class.java).apply {
                putExtra("userName", username)
            }
            startActivity(intent)
        }

        binding.btnSubscribe.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
    }
}