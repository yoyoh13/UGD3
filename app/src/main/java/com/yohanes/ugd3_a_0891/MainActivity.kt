package com.yohanes.ugd3_a_0891

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.room.Room
import com.google.android.material.textfield.TextInputLayout
import com.yohanes.ugd3_a_0891.room.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var inputUsername: TextInputLayout
    private lateinit var inputPassword: TextInputLayout

    private lateinit var db: UserDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        inputUsername = findViewById(R.id.inputLayoutUsername)
        inputPassword = findViewById(R.id.inputLayoutPassword)

        val btnLogin: Button = findViewById(R.id.btnLogin)
        val btnRegister: Button = findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val intent = Intent(this, NextActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            var checkLogin = true
            val username: String = inputUsername.getEditText()?.getText().toString()
            val password: String = inputPassword.getEditText()?.getText().toString()

            if(username.isEmpty()) {
                inputUsername.setError("Username must be filled with text")
                checkLogin = false
            }

            if(password.isEmpty()) {
                inputPassword.setError("Password must be filled with text")
                checkLogin = false
            }


            if(!checkLogin) return@setOnClickListener
            db = Room.databaseBuilder(applicationContext, UserDB::class.java, "user.db").build()
            CoroutineScope(Dispatchers.IO).launch {
                val user = db.userDao().getUser(username)

                if (user == null) {
                    withContext(Dispatchers.Main) {
                        inputUsername.setError("Username tidak ditemukan")
                    }

                } else if(user.password == password) {
                    Log.d("LoginActivity", "USER FOUND")
                    withContext(Dispatchers.Main) {
                        val mBundle = Bundle()
                        mBundle.putString("username",username)
                        val moveHome = Intent(this@MainActivity, HomeActivity::class.java)
                        moveHome.putExtra("login",mBundle)
                        startActivity(moveHome)

                    }
                } else {
                    inputPassword.setError("Password salah")
                }
            }
        }



    }
}