package com.yohanes.ugd3_a_0891.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.transition.MaterialFadeThrough
import com.yohanes.ugd3_a_0891.R


class FragmentChat : Fragment(R.layout.fragment_chat) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        exitTransition = MaterialFadeThrough()
    }
}