package com.kang.chan_ce

import android.app.Activity
import android.hardware.Sensor
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.hardware.SensorEvent
import android.util.Log
import com.kang.chan_ce.ShakeActivity
import android.widget.Toast




class ShakeActivity : Activity(), SensorEventListener {

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

        // 시스템 서비스에서 센서메니져 획득
        m_senMng = getSystemService(SENSOR_SERVICE) as SensorManager

        // TYPE_ACCELEROMETER의 기본 센서객체를 획득
        m_senAccelerometer = m_senMng!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    // 흔들기가 시작되면 호출되는 함수
    override fun onStart() {
        Log.i("kmsTest", "onStart()")
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
                }

                // 마지막 위치 저장
                // m_fLastX = event.values[0]; 그냥 배열의 0번 인덱스가 X값
                m_fLastX = event.values[SensorManager.DATA_X]
                m_fLastY = event.values[SensorManager.DATA_Y]
                m_fLastZ = event.values[SensorManager.DATA_Z]
            }
        }
    }

/*    private var sensorManager: SensorManager? = null
    private var accelerormeterSensor: Sensor? = null

    private var mShakeTime:Long = 0
    private val SHAKE_SKIP_TIME = 500
    private val SHAKE_THRESHOLD_GRAVITY = 2.7f

    var mShakeCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerormeterSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    public override fun onResume() {
        super.onResume()
        if (accelerormeterSensor != null) sensorManager!!.registerListener(
            this, accelerormeterSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    public override fun onPause() {
        super.onPause()
        if (sensorManager != null) sensorManager!!.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {

            var axisX = event.values[0]
            var axisY = event.values[1]
            var axisZ = event.values[2]

            var gravityX = axisX / SensorManager.GRAVITY_EARTH
            var gravityY = axisY / SensorManager.GRAVITY_EARTH
            var gravityZ = axisZ / SensorManager.GRAVITY_EARTH

            var f = gravityX * gravityX + gravityY * gravityY + gravityZ * gravityZ
            var squareD = Math.sqrt((f.toDouble()))
            var gForce = squareD.toFloat()
            if (gForce > SHAKE_THRESHOLD_GRAVITY) {
                var currentTime = System.currentTimeMillis()
                if (mShakeTime + SHAKE_SKIP_TIME > currentTime) {
                    return
                }
                mShakeTime = currentTime
                mShakeCount++;
                Log.e("정보", "흔들림!!!")
            }
        }
    }*/

}