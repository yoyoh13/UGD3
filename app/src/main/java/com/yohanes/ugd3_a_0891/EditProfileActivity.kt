package com.yohanes.ugd3_a_0891

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*

class EditProfileActivity : AppCompatActivity() {
    private lateinit var inputUsername: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var inputEmail: TextInputLayout
    private lateinit var inputTanggalLahir: TextInputLayout
    private lateinit var inputTelepon: TextInputLayout
    private lateinit var inputAlamat: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        supportActionBar?.hide()

        inputUsername = findViewById(R.id.inputLayoutUsername)
        inputPassword = findViewById(R.id.inputLayoutPassword)
        inputEmail = findViewById(R.id.inputLayoutEmail)
        inputTanggalLahir = findViewById(R.id.inputLayoutTanggal)
        inputTelepon = findViewById(R.id.inputLayoutTelepon)
        inputAlamat = findViewById(R.id.inputLayoutAlamat)

        inputTanggalLahir.getEditText()?.setFocusable(false)

        val btnSave: Button = findViewById(R.id.btnSave)

        val myCalendar = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable(myCalendar)
        }

        inputTanggalLahir.getEditText()?.setOnClickListener {
            DatePickerDialog (this, datePicker, myCalendar.get(Calendar.YEAR),
                myCalendar.get((Calendar.MONTH)),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        btnSave.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            val mBundle = Bundle()

            val username: String = inputUsername.getEditText()?.getText().toString()
            val password: String = inputPassword.getEditText()?.getText().toString()
            val email: String = inputEmail.getEditText()?.getText().toString()
            val tanggalLahir: String = inputTanggalLahir.getEditText()?.getText().toString()
            val telepon: String = inputTelepon.getEditText()?.getText().toString()
            val alamat: String = inputAlamat.getEditText()?.getText().toString()

            var checkRegister = false

            if (username.isEmpty()){
                inputUsername.setError("Username must be filled with text")
                checkRegister = false
            }

            if(password.isEmpty()) {
                inputPassword.setError("Password must be filled with text")
                checkRegister = false
            }

            if(email.isEmpty()) {
                inputEmail.setError("Email must be filled with text")
                checkRegister = false
            }

            if(tanggalLahir.isEmpty()) {
                inputTanggalLahir.setError("Tanggal Lahir must be filled with text")
                checkRegister = false
            }

            if(telepon.isEmpty()) {
                inputTelepon.setError("Telepon must be filled with text")
                checkRegister = false
            }

            if(alamat.isEmpty()) {
                inputAlamat.setError("Alamat must be filled with text")
                checkRegister = false
            }

            if (username.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty() &&
                tanggalLahir.isNotEmpty() && telepon.isNotEmpty() && alamat.isNotEmpty())
                checkRegister = true

            if (!checkRegister) return@setOnClickListener

            mBundle.putString("username", username)
            mBundle.putString("password", password)

            intent.putExtra("register", mBundle)
            startActivity(intent)
        }

    }


    fun updateLable(myCalendar: Calendar) {
        val myFormat = "dd/mm/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        inputTanggalLahir.getEditText()?.setText(sdf.format((myCalendar.time)))
    }
}