package com.yohanes.ugd3_a_0891.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.room.Room
import com.google.android.material.transition.MaterialFadeThrough
import com.yohanes.ugd3_a_0891.R
import com.yohanes.ugd3_a_0891.databinding.FragmentProfileBinding
import com.yohanes.ugd3_a_0891.room.UserDB

class FragmentProfile : Fragment(R.layout.fragment_profile) {


    private val myPreference = "myPref"
    private val username = "usernameKey"
    private val email = "emailKey"
    private val telepon = "teleponKey"
    private val alamat = "alamatKey"

    var sharedPreferences: SharedPreferences? = null

    private lateinit var db: UserDB

    lateinit var textViewUserame: TextView
    lateinit var textViewEmail: TextView
    lateinit var textViewTelepon: TextView
    lateinit var textViewAlamat: TextView

    lateinit var binding: FragmentProfileBinding


    lateinit var mBundle: Bundle

    lateinit var vUsername: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(this.getActivity()?.intent?.getBundleExtra("login") != null){
            getBundle()
        }

        binding = FragmentProfileBinding.inflate(layoutInflater)

        db = Room.databaseBuilder(this.requireActivity().applicationContext, UserDB::class.java, "user-db").build()

        db.userDao().getUser(vUsername)

        exitTransition = MaterialFadeThrough()
    }

    fun getBundle() {
        mBundle = this.requireActivity().intent?.getBundleExtra("register")!!

        vUsername = mBundle.getString("username")!!

    }

}