package com.kang.chan_ce

//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
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
import android.widget.EditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.auth.User
import com.kang.chan_ce.databinding.ActivitySignupBinding
import kotlinx.android.synthetic.main.activity_mypage.*
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    lateinit var userEmail: String
    lateinit var userPassword: String
    lateinit var createAccountInputsArray: Array<EditText>

    lateinit var userName: String

    private lateinit var binding: ActivitySignupBinding

    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)

        setContentView(binding.root)

        createAccountInputsArray = arrayOf(binding.editEmail, binding.editPw, binding.editPw2)



        //가입
        binding.btnSignup.setOnClickListener {

            userName = binding.editName.text.toString().trim()

            Intent(this, MainActivity::class.java).apply {
                putExtra("username",userName) //왜 안돼????
            }
            signIn()
            startActivity(intent)
        }

        //로그인으로 돌아가기
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            Toast.makeText(this, "please sign into your account", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid
        user?.let {
            startActivity(Intent(this, MainActivity::class.java))
            Toast.makeText(this, "welcome back", Toast.LENGTH_SHORT).show()
        }
    }

    private fun notEmpty(): Boolean = binding.editEmail.toString().trim().isNotEmpty() &&
            binding.editPw.text.toString().trim().isNotEmpty() &&
            binding.editPw2.text.toString().trim().isNotEmpty()

    private fun identicalPassword(): Boolean {
        var identical = false
        if (notEmpty() && binding.editPw.text.toString().trim() == binding.editPw2.text.toString().trim()) {
            identical = true
        } else if (!notEmpty()) {
            createAccountInputsArray.forEach { input ->
                if (input.text.toString().trim().isEmpty()) {
                    input.error = "${input.hint} is required"
                }
            }
        } else {
            Toast.makeText(this, "password not matching", Toast.LENGTH_SHORT).show()
        }
        return identical
    }

    private fun signIn() {
        if (identicalPassword()) {

            userEmail = binding.editEmail.text.toString().trim()
            userPassword = binding.editPw.text.toString().trim()
            userName = binding.editName.text.toString()

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
//                        Toast.makeText(this, "account sucessfully created", Toast.LENGTH_SHORT)

                        database = FirebaseDatabase.getInstance().getReference("Users")
                        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
                        val uid = user?.uid
                        val ulocation = ""
                        val userType = "email"
                        val userdata = UserData(userType, userName, uid, userEmail, ulocation)
                        if (uid != null) {
                            database.child(uid).setValue(userdata)
                        }

                        Toast.makeText(this, "Wellcome $userName", Toast.LENGTH_SHORT)
                            .show()
                        sendEmailVerification()
                        Intent(this, MainActivity::class.java).apply {
                            putExtra("userName",userName)
                            putExtra("usersType",userType)
                        }
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "fail to authentic", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }


    private fun sendEmailVerification() {
        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        user?.let {
            it.sendEmailVerification().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "email sent to $userEmail", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}

