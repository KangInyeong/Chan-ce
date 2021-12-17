package com.kang.chan_ce

import java.util.Random
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
import net.daum.mf.map.api.MapView
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.github.sundeepk.compactcalendarview.domain.Event
import java.text.SimpleDateFormat
import java.util.*
import android.R
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.text.Layout
import android.view.LayoutInflater
import android.widget.CalendarView

import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.net.ParseException
import com.github.sundeepk.compactcalendarview.CompactCalendarView.CompactCalendarViewListener
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.activity_main.*
import java.security.AccessController.getContext


class MainActivity : AppCompatActivity(), SensorEventListener{

    val TAG = "calendar test"
    private val dateFormatForDisplaying: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
    private val dateFormatForMonth: SimpleDateFormat = SimpleDateFormat("yyyy년 MM월", Locale.KOREA)
    private val dateFormatForMonth2: SimpleDateFormat = SimpleDateFormat("yyyy-MM", Locale.KOREA)

    private lateinit var database : DatabaseReference

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

    private val usertype = "google"
    private val num_page = 4     //광고배너 페이지 수

    // 맴버변수 (마지막과 현재값을 비교하여 변위를 계산하는 방식)
    private var m_lLastTime: Long = 0
    private var m_fSpeed = 0f
    private var m_fCurX = 0f
    private var m_fCurY = 0f
    private var m_fCurZ = 0f
    private var m_fLastX = 0f
    private  var m_fLastY = 0f
    private  var m_fLastZ = 0f

    // 임계값 설정
    private val SHAKE_THRESHOLD = 800

    // 매니저 객체
    private var m_senMng: SensorManager? = null
    private var m_senAccelerometer: Sensor? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //센서
        // 시스템 서비스에서 센서메니져 획득
        m_senMng = getSystemService(SENSOR_SERVICE) as SensorManager

        // TYPE_ACCELEROMETER의 기본 센서객체를 획득
        m_senAccelerometer = m_senMng!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)


        val udatabase = FirebaseDatabase.getInstance()
        val myRef = udatabase.reference

        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser

        val uid = user?.uid
        val userid = user?.uid.toString()

        var userName : String =""

        database = FirebaseDatabase.getInstance().getReference("Users")

        if(usertype == "google"){
            if (uid != null) {
                database.child(uid).child("userName").get().addOnSuccessListener {
                    userName = it.value as String
                }
            }
        }else {
            intent.getStringExtra("userName")
        }

        //가져와야 하는 값 #1 구독 주
        var subWeek = mutableListOf<String>() // user의 구독 날짜
        //가져와야 하는 값 #2 구독 요일
        var pickDay = mutableListOf<String>()// user의 픽업 요일

        var store = mutableListOf<String>()
        var size = 0
        val keyList = mutableListOf<String>()
        val userList = mutableListOf<Reserv>()
        userList.add(Reserv("none","none","none","none","none","none"))

        myRef.child(userid).get().addOnSuccessListener {
            myRef.child(userid).addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        Log.e("정보", "$snapshot")
                        var key = snapshot.key.toString()
                        keyList.add(key)
                        var user = snapshot.getValue<Reserv>()
                        if (user != null) {
                            userList.add(user)
                        }
                    }

                    size = userList.size
                    Log.e("정보","$userList")

                    for(i in 1 until size step 1){
                        store.add(userList[i].storeName.toString())
                        subWeek.add(userList[i].subWeek.toString())
                        pickDay.add(userList[i].pickDay.toString())
                    }

                    //가져와야 하는 값 #3 사용자가 구독한 음식점 개수 (size로)
                    var subNum = subWeek.size // 구독 음식점 개수
                    Log.e("개수","$subWeek, $subNum")

                    //calendar view
                    val compactCalendarView = calendar_view
                    val textView_month = textView_month
                    val textView_result = textView_result //event 표시
                    textView_month.text = dateFormatForMonth.format(compactCalendarView.firstDayOfCurrentMonth)
                    compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY)
/*                    val button_add_events = button_add_events*/


                    //이게 예약정보가 있으면 예약정보의 날짜를 가져오도록.
/*                    button_add_events.setOnClickListener(object : View.OnClickListener {
                        override fun onClick(view: View?) {

                            for (j in 0 until subNum){

                                //음식점마다 이벤트 색깔 다르게
                                var red = ((Math.random() * 255).toInt())
                                var green = ((Math.random() * 255).toInt())
                                var blue = ((Math.random() * 255).toInt())



                                var startDate = subWeek[j].split(" ")[0]
                                var startDay:Int = startDate.split("-")[2].toInt()// 구독 시작일
                                Log.e("시작", startDay.toString())

                                val date = compactCalendarView.firstDayOfCurrentMonth
                                val yyymm = dateFormatForMonth2.format(date)
                                var date1 = ""
                                var trans_date1: Date? = null
                                var addNum = mutableListOf<Int>() // 요일 -> addNum번째일

                                if (pickDay[j].contains("MON"))
                                    addNum.add(0)
                                if (pickDay[j].contains("TUE"))
                                    addNum.add(1)
                                if (pickDay[j].contains("WED"))
                                    addNum.add(2)
                                if (pickDay[j].contains("THU"))
                                    addNum.add(3)
                                if (pickDay[j].contains("FRI"))
                                    addNum.add(4)
                                if (pickDay[j].contains("SAT"))
                                    addNum.add(5)
                                if (pickDay[j].contains("SUN"))
                                    addNum.add(6)

//                                Log.e("넘", "$pickDay, $addNum")

                                for (i:Int in addNum){

                                    date1 = "$yyymm"+"-"+(startDay+i).toString() //"2021-11-01"
                                    Log.e("아","$date1")
                                    trans_date1 = null
                                    try {
                                        trans_date1 = dateFormatForDisplaying.parse(date1)
                                        Log.e("트라","$trans_date1")
                                    } catch (e: ParseException) {
                                        e.printStackTrace()
                                    }
                                    val time1 = trans_date1!!.time
                                    //가져와야 하는 값 #3 가게 이름
                                    var storeName = store[j]
                                    var ev1 = Event(Color.rgb(red,green,blue), time1, "$storeName")
                                    compactCalendarView.addEvent(ev1)
                                    Log.e("가게", "$ev1")
                                }

                            }

                        }
                    })*/



                    for (j in 0 until subNum){

                        //음식점마다 이벤트 색깔 다르게
                        var red = ((Math.random() * 255).toInt())
                        var green = ((Math.random() * 255).toInt())
                        var blue = ((Math.random() * 255).toInt())



                        var startDate = subWeek[j].split(" ")[0]
                        var startDay:Int = startDate.split("-")[2].toInt()// 구독 시작일
                        Log.e("시작", startDay.toString())

                        val date = compactCalendarView.firstDayOfCurrentMonth
                        val yyymm = dateFormatForMonth2.format(date)
                        var date1 = ""
                        var trans_date1: Date? = null
                        var addNum = mutableListOf<Int>() // 요일 -> addNum번째일

                        if (pickDay[j].contains("MON"))
                            addNum.add(0)
                        if (pickDay[j].contains("TUE"))
                            addNum.add(1)
                        if (pickDay[j].contains("WED"))
                            addNum.add(2)
                        if (pickDay[j].contains("THU"))
                            addNum.add(3)
                        if (pickDay[j].contains("FRI"))
                            addNum.add(4)
                        if (pickDay[j].contains("SAT"))
                            addNum.add(5)
                        if (pickDay[j].contains("SUN"))
                            addNum.add(6)

//                                Log.e("넘", "$pickDay, $addNum")

                        for (i:Int in addNum){

                            date1 = "$yyymm"+"-"+(startDay+i).toString() //"2021-11-01"
                            Log.e("아","$date1")
                            trans_date1 = null
                            try {
                                trans_date1 = dateFormatForDisplaying.parse(date1)
                                Log.e("트라","$trans_date1")
                            } catch (e: ParseException) {
                                e.printStackTrace()
                            }
                            val time1 = trans_date1!!.time
                            //가져와야 하는 값 #3 가게 이름
                            var storeName = store[j]
                            var ev1 = Event(Color.rgb(red,green,blue), time1, "$storeName")
                            compactCalendarView.addEvent(ev1)
                            Log.e("가게", "$ev1")
                        }

                    }



                    // 이벤트 관련 코드
                    compactCalendarView.setListener(object : CompactCalendarViewListener {
                        override fun onDayClick(dateClicked: Date?) {
                            val events = compactCalendarView.getEvents(dateClicked)
                            val transFormat = SimpleDateFormat("yyyy-MM-dd")
                            val date1 = transFormat.format(dateClicked)
                            var event_name = ""
                            var event_date = ""
                            if (events.size > 0) {
                                event_name = events[0].data.toString()
                                val time1 = events[0].timeInMillis
                                event_date = transFormat.format(Date(time1))
                                //여기서 예약 정보 간단하게 보여주기
                                textView_result.text = "PICK UP DAY to $event_name"
                            }else
                                textView_result.text = "NO PICK UP DAY"

                        }

                        override fun onMonthScroll(firstDayOfNewMonth: Date?) {
                            textView_month.text = dateFormatForMonth.format(firstDayOfNewMonth)
                        }
                    })

                }

                override fun onCancelled(error: DatabaseError) {


                }
            })
        }





/*        getAppKeyHash()*/

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
//            Toast.makeText( this, "login $userName", Toast.LENGTH_SHORT ).show()
            val intent = Intent(this, MypageActivity::class.java)
            startActivity(intent)
        }

        binding.btnSubscribe.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    // 흔들기가 시작되면 호출되는 함수
    override fun onStart() {
        Log.i("Test", "onStart()")
        super.onStart()
        if (m_senAccelerometer != null) m_senMng!!.registerListener(
            this,
            m_senAccelerometer,
            SensorManager.SENSOR_DELAY_GAME
        )
    }

    // 흔들기가 끝나면 호출되는 함수
    override fun onStop() {
        Log.i("kmsTest", "onStop()")
        super.onStop()
        if (m_senMng != null) m_senMng!!.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // 반드시 제 정의가 필요한 메서드지만 이 예제에서는 사용되지 않음
    }

    // 센서에 변화를 감지하기 위해 계속 호출되고 있는 함수
    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val lCurTime = System.currentTimeMillis()
            val lGabOfTime = lCurTime - m_lLastTime

            // 0.1초보다 오래되면 다음을 수행 (100ms)
            if (lGabOfTime > 100) {
                m_lLastTime = lCurTime
                m_fCurX = event.values[SensorManager.DATA_X]
                m_fCurY = event.values[SensorManager.DATA_Y]
                m_fCurZ = event.values[SensorManager.DATA_Z]

                // 변위의 절대값에  / lGabOfTime * 10000 하여 스피드 계산
                m_fSpeed =
                    Math.abs(m_fCurX + m_fCurY + m_fCurZ - m_fLastX - m_fLastY - m_fLastZ) / lGabOfTime * 10000

                // 임계값보다 크게 움직였을 경우 다음을 수행
                if (m_fSpeed > SHAKE_THRESHOLD) {
                    Log.i("Test", "Shake")
                    Toast.makeText(this, "흔들기!", Toast.LENGTH_SHORT).show()
                    //dialog 띄우기
                    val intent = Intent(this, QrActivity::class.java)
                    startActivity(intent)

                }

                // 마지막 위치 저장
                // m_fLastX = event.values[0]; 그냥 배열의 0번 인덱스가 X값
                m_fLastX = event.values[SensorManager.DATA_X]
                m_fLastY = event.values[SensorManager.DATA_Y]
                m_fLastZ = event.values[SensorManager.DATA_Z]
            }
        }
    }

}