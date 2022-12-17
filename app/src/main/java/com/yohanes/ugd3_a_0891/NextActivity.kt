package com.yohanes.ugd3_a_0891

import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.room.Room
import com.google.android.material.textfield.TextInputLayout
import com.yohanes.ugd3_a_0891.databinding.ActivityNextBinding
import com.yohanes.ugd3_a_0891.room.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import com.yohanes.ugd3_a_0891.room.User as User

class NextActivity : AppCompatActivity() {

    private var binding: ActivityNextBinding? = null
    private lateinit var db: UserDB

    private val registerNotification = "register_notification"
    private val regNotivication = 1

    private lateinit var inputLayoutUsername: TextInputLayout
    private lateinit var inputLayoutPassword: TextInputLayout
    private lateinit var inputLayoutEmail: TextInputLayout
    private lateinit var inputLayoutTanggal: TextInputLayout
    private lateinit var inputLayoutTelepon: TextInputLayout
    private lateinit var inputLayoutAlamat: TextInputLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNextBinding.inflate(layoutInflater)
        val view = binding!!.root
        setContentView(view)
        val myCalendar = Calendar.getInstance()

        createRegNotification()

        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable(myCalendar)
        }

        binding!!.inputLayoutTanggal.getEditText()?.setOnClickListener {
            DatePickerDialog (this, datePicker, myCalendar.get(Calendar.YEAR),
                myCalendar.get((Calendar.MONTH)),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        inputLayoutUsername = findViewById(R.id.inputLayoutUsername)
        inputLayoutPassword = findViewById(R.id.inputLayoutPassword)
        inputLayoutEmail = findViewById(R.id.inputLayoutEmail)
        inputLayoutTanggal = findViewById(R.id.inputLayoutTanggal)
        inputLayoutTelepon = findViewById(R.id.inputLayoutTelepon)
        inputLayoutAlamat = findViewById(R.id.inputLayoutAlamat)




        db = Room.databaseBuilder(applicationContext, UserDB::class.java, "user.db").build()
        binding!!.btnRegister?.setOnClickListener {
            var checkRegs = true
            val username: String = inputLayoutUsername.getEditText()?.getText().toString()
            val password: String = inputLayoutPassword.getEditText()?.getText().toString()
            val email: String = inputLayoutEmail.getEditText()?.getText().toString()
            val tanggal: String = inputLayoutTanggal.getEditText()?.getText().toString()
            val telp: String = inputLayoutTelepon.getEditText()?.getText().toString()
            val alamat: String = inputLayoutAlamat.getEditText()?.getText().toString()

            if(username.isEmpty()){
                inputLayoutUsername.setError("Username tidak boleh kosong")
                checkRegs = false
            }
            if(password.isEmpty()){
                inputLayoutPassword.setError("Password tidak boleh kosong")
                checkRegs = false
            }
            if(email.isEmpty()){
                inputLayoutEmail.setError("Email tidak boleh kosong")
                checkRegs = false
            }
            if(tanggal.isEmpty()){
                inputLayoutTanggal.setError("Tanggal tidak boleh kosong")
                checkRegs = false
            }
            if(telp.isEmpty()){
                inputLayoutTelepon.setError("No Telp tidak boleh kosong")
                checkRegs = false
            }
            if(alamat.isEmpty()){
                inputLayoutAlamat.setError("Alamat tidak boleh kosong")
                checkRegs = false
            }
            if(!checkRegs) return@setOnClickListener
            CoroutineScope(Dispatchers.IO).launch {
                db.userDao().addUser(
                    User(0,
                        binding!!.inputLayoutUsername.getEditText()?.getText().toString(),
                        binding!!.inputLayoutPassword.getEditText()?.getText().toString(),
                        binding!!.inputLayoutEmail.getEditText()?.getText().toString(),
                        binding!!.inputLayoutTanggal.getEditText()?.getText().toString(),
                        binding!!.inputLayoutTelepon.getEditText()?.getText().toString(),
                        binding!!.inputLayoutAlamat.getEditText()?.getText().toString()
                    )
                )
                finish()
            }
        }
        sendRegNotification()
    }

    fun updateLable(myCalendar: Calendar) {
        val myFormat = "dd/mm/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        binding!!.inputLayoutTanggal.getEditText()?.setText(sdf.format((myCalendar.time)))
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun createRegNotification(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Title"
            val descriptionText = "Notification Description"

            val registNotification = NotificationChannel(
                registerNotification,
                name,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(registNotification)
        }
    }

    private fun sendRegNotification(){

        val builder = NotificationCompat.Builder(this, registerNotification)
            .setSmallIcon(R.drawable.ic_user_24)
            .setContentTitle(binding?.tietUsername?.text.toString())
            .setPriority(NotificationCompat. PRIORITY_LOW)

        with(NotificationManagerCompat.from(this)){
            notify(regNotivication, builder.build())
        }
    }

}