package com.yohanes.ugd3_a_0891

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.transition.MaterialFadeThrough


class FragmentSearch : Fragment(R.layout.fragment_search) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        exitTransition = MaterialFadeThrough()
    }
}