package com.yohanes.ugd3_a_0891

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.itextpdf.barcodes.BarcodeQRCode
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.io.source.ByteArrayOutputStream
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.property.HorizontalAlignment
import com.itextpdf.layout.property.TextAlignment
import com.yohanes.ugd3_a_0891.api.UserAPI
import com.yohanes.ugd3_a_0891.databinding.ActivityNextBinding
import com.yohanes.ugd3_a_0891.room.UserDB
import org.json.JSONObject
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import com.yohanes.ugd3_a_0891.room.User as User

class NextActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNextBinding
    private lateinit var db: UserDB

    private val registerNotification = "register_notification"
    private val regNotivication = 1
    private var queue: RequestQueue? = null

//    private lateinit var inputLayoutUsername: TextInputLayout
//    private lateinit var inputLayoutPassword: TextInputLayout
//    private lateinit var inputLayoutEmail: TextInputLayout
//    private lateinit var inputLayoutTanggal: TextInputLayout
//    private lateinit var inputLayoutTelepon: TextInputLayout
//    private lateinit var inputLayoutAlamat: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        queue = Volley.newRequestQueue(this)
        binding = ActivityNextBinding.inflate(layoutInflater)
        val view = binding!!.root
        setContentView(view)

        val myCalendar = Calendar.getInstance()

        createRegNotification()

        binding.btnRegister.setOnClickListener {
            createUser()
        }

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
    }

    private fun createUser(){
        val user = User(
            binding.tietUsername.text.toString(),
            binding.tietPassword.text.toString(),
            binding.tietEmail.text.toString(),
            binding.tietTgl.text.toString(),
            binding.tietTelp.text.toString(),
            binding.tietAlamat.text.toString()
        )
        val stringRequest: StringRequest =
            object: StringRequest(Method.POST, UserAPI.ADD_URL, Response.Listener { response ->
                Toast.makeText(this, "Data berhasil ditambahkan!", Toast.LENGTH_SHORT).show();
                sendRegNotification()

                val returnIntent = Intent()
                setResult(RESULT_OK, returnIntent)
                finish()


            }, Response.ErrorListener { error ->

                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception){
                    Toast.makeText(this,"Register Gagal!!", Toast.LENGTH_SHORT).show()
                }
            }){
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Accept"] = "application/json"
                    return headers
                }

                @Throws(AuthFailureError::class)
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params["username"] = user.username
                    params["password"] = user.password
                    params["email"] = user.email
                    params["tglLahir"] = user.tglLahir
                    params["telepon"] = user.telepon
                    params["Alamat"] = user.alamat

                    return params
                }
            }

        queue!!.add(stringRequest)
    }

    fun updateLable(myCalendar: Calendar) {
        val myFormat = "dd/mm/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        binding!!.inputLayoutTanggal.getEditText()?.setText(sdf.format((myCalendar.time)))
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

    @SuppressLint("ObsoleteSdkInt")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Throws(
        FileNotFoundException::class
    )

    private fun createPdf(username: String, password: String, email: String, tglLahir: String, telepon: String, Alamat: String) {
        val pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()
        val file = File(pdfPath, "Member_Healing.pdf")
        FileOutputStream(file)

        val writer = PdfWriter(file)
        val pdfDocument = PdfDocument(writer)
        val document = Document(pdfDocument)
        pdfDocument.defaultPageSize = PageSize.A4
        document.setMargins(5f, 5f, 5f, 5f)
        @SuppressLint("UseCompatLoadingForDrawables") val d = getDrawable(R.drawable.kost)

        //Tambah Gambar
        val bitmap = (d as BitmapDrawable?)!!.bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val bitmapData = stream.toByteArray()
        val imageData = ImageDataFactory.create(bitmapData)
        val image = Image(imageData)
        val namapengguna = Paragraph("Identitas").setBold().setFontSize(24f)
            .setTextAlignment(TextAlignment.CENTER)
        val group = Paragraph(
            """
                 Berikut adalah 
                 Informasi Pengguna
                 """.trimIndent()).setTextAlignment(TextAlignment.CENTER).setFontSize(12f)

        //Proses pembuatan table
        val width = floatArrayOf(100f,100f)
        val table = Table(width)

        table.setHorizontalAlignment(HorizontalAlignment.CENTER)
        table.addCell(Cell().add(Paragraph("Nama Pengguna")))
        table.addCell(Cell().add(Paragraph(username)))
        table.addCell(Cell().add(Paragraph("Tanggal Lahir")))
        table.addCell(Cell().add(Paragraph(tglLahir)))
        table.addCell(Cell().add(Paragraph("Email")))
        table.addCell(Cell().add(Paragraph(email)))
        table.addCell(Cell().add(Paragraph("Nomor Telepon")))
        table.addCell(Cell().add(Paragraph(telepon)))
        table.addCell(Cell().add(Paragraph("Alamat")))
        table.addCell(Cell().add(Paragraph(Alamat)))
        table.addCell(Cell().add(Paragraph("Password")))
        table.addCell(Cell().add(Paragraph(password)))
        val dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        table.addCell(Cell().add(Paragraph("Tanggal Buat PDF")))
        table.addCell(Cell().add(Paragraph(LocalDate.now().format(dateTimeFormatter))))
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss a")
        table.addCell(Cell().add(Paragraph("Pukul Pembuatan")))
        table.addCell(Cell().add(Paragraph(LocalTime.now().format(timeFormatter))))

        //Pembuatan QR CODE secara generate menggunakan IText 7
        val barcodeQRCode = BarcodeQRCode(
            """
                    $username
                    $tglLahir
                    $email
                    $telepon
                    $Alamat
                    $password
                    ${LocalDate.now().format(dateTimeFormatter)}
                    ${LocalTime.now().format(timeFormatter)}
                    """.trimIndent())
        val qrCodeObject = barcodeQRCode.createFormXObject(ColorConstants.BLACK, pdfDocument)
        val qrCodeImage = Image(qrCodeObject).setWidth(80f).setHorizontalAlignment(
            HorizontalAlignment.CENTER)

        document.add(image)
        document.add(namapengguna)
        document.add(group)
        document.add(table)
        document.add(qrCodeImage)

        document.close()
        Toast.makeText(this, "PDF Berhasil dibuat!", Toast.LENGTH_SHORT).show();
    }

}