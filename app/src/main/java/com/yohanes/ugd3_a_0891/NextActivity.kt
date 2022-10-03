package com.yohanes.ugd3_a_0891

import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.room.Room
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



        db = Room.databaseBuilder(applicationContext, UserDB::class.java, "user-db").build()
        binding!!.btnRegister?.setOnClickListener {
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
