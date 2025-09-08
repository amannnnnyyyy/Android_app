package com.example.myapplication1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class TestFragment : Fragment() {

    companion object{
        private const val ARG_MESSAGE = "some_message"

        fun newInstance(message:String): TestFragment{
            val fragment = TestFragment()
            val bundle = Bundle()
            bundle.putString(ARG_MESSAGE, message)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_test, container,false)
        val message = arguments?.getString(ARG_MESSAGE)?:"No Message Found"
        val textView = view.findViewById<TextView>(R.id.test_text_view)
        textView.text = message
        return view
    }

}