package com.example.myapplication1.workout.ui.details

import android.os.Bundle
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
import com.example.myapplication1.news.ui.viewmodels.NewsViewModel
import com.example.myapplication1.workout.db.RoutineDatabase
import com.example.myapplication1.workout.repository.RoutineRepository
import com.example.myapplication1.workout.ui.viewmodels.RoutineViewModelProvider
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WorkOutDetailFragment : Fragment(R.layout.fragment_work_out_detail) {

    private val viewModel: WorkOutDetailsViewModel by lazy {
        val database = RoutineDatabase.getDatabase(requireContext())
        val repository = RoutineRepository(database)
        val factory = RoutineViewModelProvider(repository)
        ViewModelProvider(this, factory)[WorkOutDetailsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentWorkOutDetailBinding.inflate(inflater, container, false)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.routines.collectLatest { routines->
                    binding.testText.text = routines.data?.routines[0]?.results?.get(0)?.description?:""
                }
            }
        }

        return binding.root
    }

}