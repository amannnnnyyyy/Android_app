package com.example.myapplication1

import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.marginStart
import androidx.fragment.app.Fragment
class MainActivity : AppCompatActivity() {
    private var previousUnderlinedButton:ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val intent: Intent = Intent(this, SignInActivity::class.java)
        startActivityForResult(intent, 200)
        setContentView(R.layout.menu_layout)


        val homeButton = findViewById<ImageView>(R.id.home_id)
        val educButton = findViewById<ImageView>(R.id.educ_id)
        val info = findViewById<ImageView>(R.id.info)
        val health = findViewById<ImageView>(R.id.health)
        val google = findViewById<ImageView>(R.id.google)
        val calcButton = findViewById<ImageView>(R.id.calc_id)
//        calcButton.setOnClickListener {
//            val intent = Intent(this, CalculatorActivity::class.java)
//            setResult(200, intent)
//            finish()
//        }

        for (x in listOf(info, health, google, calcButton)){
            x.setOnClickListener {
                setUnderLine(x)
            }
        }


        val homeFragment = HomeFragment()
        val testFragment = TestFragment()
        val defaultFragment = DefaultFragment()

        //default fragment display
        supportFragmentManager.beginTransaction().apply{
            replace(R.id.frameLayout, defaultFragment)
            addToBackStack("default")
            setReorderingAllowed(true)
            commit()
        }

        homeButton.setOnClickListener {
            setUnderLine(homeButton)

            val messageToSendToEduc = "This message is sent from Home using view model"

            val secondFragment = TestFragment.newInstance(messageToSendToEduc)
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.frameLayout, secondFragment)
//                .addToBackStack(null)
//                .commit()

            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, homeFragment);
                addToBackStack("homeFragment")
                setReorderingAllowed(true)
                commit()
            }
        }

        educButton.setOnClickListener {
            setUnderLine(educButton)
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, testFragment);
                addToBackStack("testFragment")
                setReorderingAllowed(true)
                commit()
            }
        }

    }

    fun setUnderLine(current: ImageView){
        current.background = ContextCompat.getDrawable(this, R.drawable.image_view_shape)
        previousUnderlinedButton?.setBackgroundResource(android.R.color.transparent)
        previousUnderlinedButton = current
    }
}
