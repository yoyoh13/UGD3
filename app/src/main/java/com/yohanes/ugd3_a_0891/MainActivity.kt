package com.yohanes.ugd3_a_0891

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private lateinit var inputUsername: TextInputLayout
    private lateinit var inputPassword: TextInputLayout

    lateinit var mBundle: Bundle

    lateinit var vUsername: String
    lateinit var vPassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var syarat = false

        supportActionBar?.hide()

        inputUsername = findViewById(R.id.inputLayoutUsername)
        inputPassword = findViewById(R.id.inputLayoutPassword)


        if(intent.getBundleExtra("register") != null){
            getBundle()
            syarat = true
        }

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

            if(!syarat){
                val builder: AlertDialog.Builder  = AlertDialog.Builder(this@MainActivity);
                builder.setMessage("Buat Akun Terlebih Dahulu")
                builder.setTitle("Error Message...")
                    .setPositiveButton("YES", object : DialogInterface.OnClickListener{
                        override fun onClick(dialogInterface: DialogInterface, i: Int){
                        }
                    })
                    .show()
                return@setOnClickListener
            }

            if(username.isEmpty()) {
                inputUsername.setError("Username must be filled with text")
                checkLogin = false
            }

            if(password.isEmpty()) {
                inputPassword.setError("Password must be filled with text")
                checkLogin = false
            }

            if (username !=vUsername){
                inputUsername.setError("Username not found")
                checkLogin = false
            }

            if (password !=password){
                inputPassword.setError("Wrong password")
                checkLogin = false
            }

            if(username == vUsername && password == vPassword) checkLogin = true


            if(!checkLogin) return@setOnClickListener
            val moveHome = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(moveHome)
        }



    }
    fun getBundle() {
        mBundle = intent.getBundleExtra("register")!!

        vUsername = mBundle.getString("username")!!
        vPassword = mBundle.getString("password")!!

        inputUsername.getEditText()?.setText(vUsername)
    }
}