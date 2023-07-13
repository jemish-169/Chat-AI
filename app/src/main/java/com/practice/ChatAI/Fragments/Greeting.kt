package com.practice.ChatAI.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.practice.ChatAI.Activity.MainActivity
import com.practice.ChatAI.R

class Greeting : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as MainActivity).bottomview.visibility = View.GONE
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_greeting, container, false)

        val letsGoBtn = view.findViewById<TextView>(R.id.lets_go_btn)
        letsGoBtn.setOnClickListener {
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.frameLayout, BardAI())
            fragmentTransaction?.commit()
            (requireActivity() as MainActivity).bottomview.visibility = View.VISIBLE
        }

        return view
    }
}