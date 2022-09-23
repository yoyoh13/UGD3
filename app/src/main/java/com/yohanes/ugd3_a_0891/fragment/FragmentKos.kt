package com.yohanes.ugd3_a_0891.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yohanes.ugd3_a_0891.R
import com.yohanes.ugd3_a_0891.entity.Kos
import com.yohanes.ugd3_a_0891.rvKosAdapter

class FragmentKos : Fragment(R.layout.fragment_kos) {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_kos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        val adapter : rvKosAdapter = rvKosAdapter(Kos.listofKos)

        val rvKos : RecyclerView = view.findViewById(R.id.rv_kos)

        rvKos.layoutManager = layoutManager
        rvKos.setHasFixedSize(true)
        rvKos.adapter = adapter
    }
}
