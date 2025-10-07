package com.example.myapplication1.workout.ui.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.myapplication1.R
import com.example.myapplication1.databinding.FragmentWorkOutDetailBinding
import com.example.myapplication1.workout.db.ExerciseInfoDatabase
import com.example.myapplication1.workout.repository.ExerciseInfoRepository
import com.example.myapplication1.workout.ui.viewmodels.ExerciseInfoViewModelProvider
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WorkOutDetailFragment : Fragment(R.layout.fragment_work_out_detail) {

    private val viewModel: WorkOutDetailsViewModel by lazy {
        val database = ExerciseInfoDatabase.getDatabase(requireContext())
        val repository = ExerciseInfoRepository(database)
        val factory = ExerciseInfoViewModelProvider(repository)
        ViewModelProvider(this, factory)[WorkOutDetailsViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentWorkOutDetailBinding.inflate(inflater, container, false)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.exerciseInfos.collectLatest { infoResponse->
                    Log.d("ThisIsDetails","${infoResponse.data}")
                    binding.header.text = infoResponse.data?.results?.get(0).toString()
                }
            }
        }

        return binding.root
    }




}