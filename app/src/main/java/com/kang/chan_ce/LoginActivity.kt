package com.kang.chan_ce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.kang.chan_ce.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {

    private var auth : FirebaseAuth? = null //firebase 인증 변수
    private var googleSignInClient : GoogleSignInClient? = null // google 로그인 연동 변수
    private var GOOGLE_LOGIN_CODE = 9001 //임의로 설정 가능

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance() // firebase의 authentication

        // Google Login button
        val btn_google = findViewById<Button>(R.id.btn_google);

        btn_google.setOnClickListener { googleLogin() }

        // Configure Google Sign In
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)
    }

    // Not Logout -> Auto login & direct signin
    public override fun onStart() {
        super.onStart()
        moveMainPage(auth?.currentUser)
    }

    // Login
    private fun signIn(email: String, password: String) {

        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth?.signInWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText( baseContext, "Successful login", Toast.LENGTH_SHORT ).show()
                        moveMainPage(auth?.currentUser)
                    } else {
                        Toast.makeText( baseContext, "Failure login", Toast.LENGTH_SHORT ).show()
                    }
                }
        }
    }

    // Google login function
    fun googleLogin(){
        var signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent,GOOGLE_LOGIN_CODE)
    }

    fun firebaseAuthWithGoogle(account : GoogleSignInAccount?){
        var credential = GoogleAuthProvider.getCredential(account?.idToken,null)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener{
                    task ->
                if(task.isSuccessful){
                    // Sign in success,
                    moveMainPage(task.result?.user)
                }else{
                    // If sign in fails,
                    Toast.makeText(this,task.exception?.message,Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if(requestCode == GOOGLE_LOGIN_CODE){
            var result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)!!

            if(result.isSuccess) {
                var accout = result.signInAccount
                firebaseAuthWithGoogle(accout)
                Toast.makeText(this,"Login Succes",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this,"Login Fail",Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Call MainActivity & pass user info
    fun moveMainPage(user: FirebaseUser?){
        if( user!= null){
            startActivity(Intent(this,MypageActivity::class.java))
            finish()
        }
    }
}


