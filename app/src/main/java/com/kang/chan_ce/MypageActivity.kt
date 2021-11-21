package com.kang.chan_ce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
<<<<<<< HEAD
<<<<<<< HEAD
import com.kang.chan_ce.databinding.ActivityMypageBinding
import com.kang.chan_ce.databinding.ActivitySearchBinding
=======
=======
import com.kang.chan_ce.databinding.ActivityMypageBinding
import com.kang.chan_ce.databinding.ActivitySearchBinding
>>>>>>> 7301763e8ff15d33391cfc38fc4164877540a0e9
import android.widget.Button
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent

<<<<<<< HEAD
>>>>>>> 43797e6f8239f64c03af91b0677a7537efb0f783
=======
>>>>>>> 7301763e8ff15d33391cfc38fc4164877540a0e9

class MypageActivity : AppCompatActivity() {

    // 로그아웃 구현을 위한 변수
    var auth : FirebaseAuth ?= null
    var googleSignInClient : GoogleSignInClient ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
<<<<<<< HEAD
<<<<<<< HEAD
=======

>>>>>>> 7301763e8ff15d33391cfc38fc4164877540a0e9
        val binding = ActivityMypageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMainPage.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
<<<<<<< HEAD
=======
=======

>>>>>>> 7301763e8ff15d33391cfc38fc4164877540a0e9
        setContentView(R.layout.activity_mypage)

        // 구글 로그아웃을 위해 로그인 세션 가져오기
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // firebaseauth를 사용하기 위한 인스턴스 get
        auth = FirebaseAuth.getInstance()

        // 구글 로그아웃 버튼 ID 가져오기
        var btn_qr = findViewById<Button>(R.id.btn_qr);

        btn_qr.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            googleSignInClient?.signOut()

            var logoutIntent = Intent (this, MainActivity::class.java)
            logoutIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(logoutIntent)
        }

<<<<<<< HEAD

>>>>>>> 43797e6f8239f64c03af91b0677a7537efb0f783
=======
>>>>>>> 7301763e8ff15d33391cfc38fc4164877540a0e9
    }
}