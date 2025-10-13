package com.example.myapplication1.view.fragments.home

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.text.RelativeDateTimeFormatter
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.compose.ui.graphics.Path
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.VISIBILITY_PRIVATE
import androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC
import androidx.core.app.NotificationCompat.VISIBILITY_SECRET
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import com.example.myapplication1.R
import com.example.myapplication1.databinding.FragmentHomeMainBinding
import com.google.android.material.navigation.NavigationView
import androidx.core.view.get
import androidx.core.view.iterator
import com.example.myapplication1.Logger
import com.example.myapplication1.news.ui.main.NewsMainFragment

class MainHomeFragment : Fragment(R.layout.fragment_home_main),
    NavigationView.OnNavigationItemSelectedListener {

        private lateinit var drawerLayout: DrawerLayout
        private lateinit var navigationView: NavigationView
        var clicked: Int = 0


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onAttach(context: Context) {
        super.onAttach(context)
            showNotif()

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeMainBinding.inflate(inflater, container, false)

        drawerLayout = binding.drawerLayout
        navigationView = binding.navView
        val toolbar = binding.toolbar2

        navigationView.bringToFront()

//        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)

        //val toggle = ActionBarDrawerToggle(requireActivity(), drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer)

        binding.toggle.setOnClickListener {
            drawerLayout.open()
        }

//        drawerLayout.addDrawerListener(toggle)
//        toggle.syncState()


        binding.chat.setOnClickListener {
            navigateToChat()
        }

        binding.workout.setOnClickListener {
            navigateToWorkOut()
        }

        binding.news.setOnClickListener {
            navigateToNews()
        }

        binding.paint.setOnClickListener {
            navigateToPaint()
        }

        val bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.world_news)




        val manager = NotificationManagerCompat.from(requireContext())
        binding.notifications.setOnClickListener {


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                Log.i("notificationStatus","Version is okay")

                val channel = NotificationChannel(
                    "channel id",
                    "News channel",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                manager.createNotificationChannel(channel)
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    showNotif()

                    Log.i("notificationStatus","Is it denying")
                }else{
                    Log.i("notificationStatus","showing notification $clicked")
                    val intent = Intent(requireActivity(), NewsMainFragment::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        putExtra("FRAGMENT_TO_OPEN", "MySpecificFragment")
                    }
                    val pendingIntent: PendingIntent = PendingIntent.getActivity(requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE)

                    val compat = NotificationCompat.Builder(requireContext(), "channel id")
                        .setContentTitle("News")
                        .setContentText("You have ${clicked+1} new ${if (clicked==0)"notification"; else "notifications"} from the news channel. Come check ${if (clicked==0)"it"; else "them"} out.")
                        .setSmallIcon(R.drawable.news)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setLargeIcon(bitmap)
                        .setVisibility(
                            when(clicked){
                                1->VISIBILITY_PRIVATE
                                2->VISIBILITY_SECRET
                                else-> VISIBILITY_PUBLIC
                            }
                        )
                        .addAction(R.drawable.left_arrow,"Open News", pendingIntent)
                        .addAction(R.drawable.world_news, "cancel", pendingIntent)
                        .addAction(0,"who cares",pendingIntent)
                        .setAutoCancel(true)
                        .setPublicVersion(NotificationCompat.Builder(requireContext(), "Secret")
                            .setContentTitle("Hidden notification ${clicked+1}")
                            .setContentText("Go to app to see the details")
                            .build())
                    manager.notify(clicked, compat.build())
                    clicked++
                }

            }else{
                Log.i("notificationStatus","version not okay")
            }
        }

        binding.cancelNotifications.setOnClickListener {
            Log.i("notificationStatus","cancelling")
            manager.cancel(clicked-1)
            if (clicked==0) clicked = 0 else clicked--
        }



        navigationView.setNavigationItemSelectedListener(this)

        return binding.root
    }
    
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun showNotif(){
        val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            isGranted->
            if (isGranted){
                Log.i("notificationStatus","Granted")
            }else{
                Log.i("notificationStatus","Denied")
            }
        }

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            return
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId){
            R.id.chat -> navigateToChat()
            R.id.workout -> navigateToWorkOut()
            R.id.news -> navigateToNews()
            R.id.paint -> navigateToPaint()
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }



    fun navigateToChat(){
//        for (menu in navigationView.menu){
//            menu.isCheckable = false
//        }
        val nav = findNavController()
        val direction = MainHomeFragmentDirections.actionMainHomeFragmentToChatHolderFragment()
        nav.navigate(direction)
    }

    fun navigateToWorkOut(){
        val nav = findNavController()
        val direction = MainHomeFragmentDirections.actionMainHomeFragmentToWorkOutHomeFragment()
        nav.navigate(direction)
    }

    fun navigateToNews(){
        val nav = findNavController()
        val direction = MainHomeFragmentDirections.actionMainHomeFragmentToNewsMainFragment()
        nav.navigate(direction)
    }

    fun navigateToPaint(){
        val nav = findNavController()
        val direction = MainHomeFragmentDirections.actionMainHomeFragmentToDrawingMain()
        nav.navigate(direction)
    }

    companion object {

        fun newInstance(param1: String, param2: String) =
            MainHomeFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}