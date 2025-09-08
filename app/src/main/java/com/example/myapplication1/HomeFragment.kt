package com.example.myapplication1

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment;


class HomeFragment : Fragment() {

    private var messageToEduc: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        messageToEduc = view.findViewById<EditText>(R.id.messageToEduc)
        val sendToEducBtn = view.findViewById<Button>(R.id.sendToEducBtn)

        sendToEducBtn.setOnClickListener {
            Toast.makeText(requireContext(), messageToEduc?.text.toString(), Toast.LENGTH_LONG).show()

            requireActivity().findViewById<ImageView>(R.id.home_id).imageTintMode = PorterDuff.Mode.DST_IN
            requireActivity().findViewById<ImageView>(R.id.educ_id).imageTintMode = PorterDuff.Mode.DST_OUT

            val secondFragment = TestFragment.newInstance(messageToEduc?.text.toString())
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, secondFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onPause() {
        super.onPause()
        messageToEduc?.text?.clear()
    }

    override fun onResume() {
        super.onResume()

    }

}