package com.yohanes.ugd3_a_0891

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
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

    private lateinit var sharePreference: SharePreference

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
            var checkLogin = false
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

            CoroutineScope(Dispatchers.IO).launch {
                val user = db.userDao().getUser(username)

                if (user == null) {
                    inputUsername.setError("Username tidak ditemukan")
                } else if(user.password == password) {

                    withContext(Dispatchers.Main) {
                        val moveHome = Intent(this@MainActivity, HomeActivity::class.java)
                        startActivity(moveHome)
                        sharePreference.setUser(user)

                    }
                } else {
                    inputPassword.setError("Password salah")
                }
            }
        }



    }
}