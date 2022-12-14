package com.yohanes.ugd3_a_0891

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.room.Room
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.yohanes.ugd3_a_0891.api.UserAPI
import com.yohanes.ugd3_a_0891.databinding.ActivityMainBinding
import com.yohanes.ugd3_a_0891.models.User
import com.yohanes.ugd3_a_0891.room.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class MainActivity : AppCompatActivity() {

    private lateinit var inputUsername: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var binding: ActivityMainBinding
    lateinit var  mBundle: Bundle
    lateinit var vUsername: String
    lateinit var vPassword : String
    private var queue: RequestQueue? = null

    private lateinit var db: UserDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        inputUsername = findViewById(R.id.inputLayoutUsername)
        inputPassword = findViewById(R.id.inputLayoutPassword)

        val btnLogin: Button = findViewById(R.id.btnLogin)
        val btnRegister: Button = findViewById(R.id.btnRegister)
        queue = Volley.newRequestQueue(this)

        btnRegister.setOnClickListener {
            val intent = Intent(this, NextActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener (View.OnClickListener {
            var checkLogin = true
            val username: String = inputUsername.getEditText()?.getText().toString()
            val password: String = inputPassword.getEditText()?.getText().toString()

//            if(username.isEmpty()) {
//                inputUsername.setError("Username must be filled with text")
//                checkLogin = false
//            }
//
//            if(password.isEmpty()) {
//                inputPassword.setError("Password must be filled with text")
//                checkLogin = false
//            }

            val stringRequest: StringRequest = object :
                StringRequest(Method.POST, UserAPI.LOGIN_URL,
                    Response.Listener { response ->
                        val gson = Gson()

                        val jsonObject = JSONObject(response)
                        val data = jsonObject.getJSONObject("data")
                        val user = gson.fromJson(data.toString(), User::class.java)

                        val moveHome = Intent(this, HomeActivity::class.java)
                        val sp = getSharedPreferences("USER_LOGIN", Context.MODE_PRIVATE)
                        val editor = sp.edit()

                        editor.apply {
                            putInt("id", user.id!!.toInt())
                            putString("username", user.username)
                            putString("password", user.password)
                        }.apply()

                        startActivity(moveHome)
                        finish()
                    }, Response.ErrorListener { error ->

                        try {
                            val responseBody =
                                String(error.networkResponse.data, StandardCharsets.UTF_8)
                            val errors = JSONObject(responseBody)
                            Toast.makeText(
                                this,
                                errors.getString("message"),
                                Toast.LENGTH_SHORT
                            ).show()
                        } catch (e: java.lang.Exception) {
                            Toast.makeText(this, "Login Gagal!!", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = java.util.HashMap<String, String>()
                    headers["Accept"] = "application/json"
                    return headers
                }

                @Throws(AuthFailureError::class)
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params["username"] = username
                    params["password"] = password
                    return params
                }

            }
            queue!!.add(stringRequest)

            //  }
        })
    }

}
