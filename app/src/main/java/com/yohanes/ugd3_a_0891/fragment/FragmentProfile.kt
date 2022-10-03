package com.yohanes.ugd3_a_0891.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.google.android.material.transition.MaterialFadeThrough
import com.yohanes.ugd3_a_0891.R
import com.yohanes.ugd3_a_0891.SharePreference
import com.yohanes.ugd3_a_0891.databinding.FragmentProfileBinding
import com.yohanes.ugd3_a_0891.room.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentProfile : Fragment(R.layout.fragment_profile) {

    lateinit var binding: FragmentProfileBinding

    private lateinit var db: UserDB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)

        val bundle = arguments
        val message = bundle!!.getString("username")

        db = Room.databaseBuilder(requireActivity().applicationContext, UserDB::class.java, "user.db").build()
        binding = FragmentProfileBinding.inflate(layoutInflater)

//        CoroutineScope(Dispatchers.IO).launch {
//            val user = db.userDao().getUser(message!!)
//
//            binding.Username.setText(user!!.username)
//            binding.email.setText(user.email)
//            binding.nomorTelepon.setText(user.telepon)
//            binding.alamat.setText(user.Alamat)
//
//        }


        exitTransition = MaterialFadeThrough()
        return view


    }

}