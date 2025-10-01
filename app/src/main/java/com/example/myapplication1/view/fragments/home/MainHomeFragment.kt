package com.example.myapplication1.view.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication1.R
import com.example.myapplication1.databinding.FragmentHomeMainBinding

class MainHomeFragment : Fragment(R.layout.fragment_home_main) {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    companion object {

        fun newInstance(param1: String, param2: String) =
            MainHomeFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}