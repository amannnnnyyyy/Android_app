package com.example.myapplication1.workout.ui.details

import android.graphics.drawable.PictureDrawable
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.myapplication1.R
import com.example.myapplication1.databinding.FragmentWorkOutDetailBinding
import com.example.myapplication1.workout.db.ExerciseInfoDatabase
import com.example.myapplication1.workout.repository.ExerciseInfoRepository
import com.example.myapplication1.workout.ui.viewmodels.ExerciseInfoViewModelProvider
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.example.myapplication1.workout.adapters.MuscleInformationViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator


class WorkOutDetailFragment : Fragment(R.layout.fragment_work_out_detail) {

    private val viewModel: WorkOutDetailsViewModel by lazy {
        val database = ExerciseInfoDatabase.getDatabase(requireContext())
        val repository = ExerciseInfoRepository(database)
        val factory = ExerciseInfoViewModelProvider(repository)
        ViewModelProvider(this, factory)[WorkOutDetailsViewModel::class.java]
    }

    private val args: WorkOutDetailFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentWorkOutDetailBinding.inflate(inflater, container, false)


        viewModel.getAllInfos(args.workoutId)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.exerciseInfos.collectLatest { infoResponse->
                    val categories = infoResponse.data?.results?.map {
                        it.category?.name
                    }
                    Log.i("CheckCategory","$categories")
                    val filtered = infoResponse.data?.results?.filter {
                        ((it.translations?.size?:0)>0) && (it.translations?.any { translation -> translation.language==2 && translation.description.isNotEmpty() })==true
                    }
                    val viewPager = binding.viewPager
                    filtered?.let {
                        val adapter = MuscleInformationViewPagerAdapter(it)
                        viewPager.adapter = adapter
                        TabLayoutMediator(binding.tabLayout, viewPager){ tab, position ->
                            tab.text = "Target Muscle ${(position+1).toString()}"
                        }.attach()
                    }
                }

            }
        }


        return binding.root
    }




}