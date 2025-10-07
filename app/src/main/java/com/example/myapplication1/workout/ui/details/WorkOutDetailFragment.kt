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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication1.R
import com.example.myapplication1.databinding.FragmentWorkOutDetailBinding
import com.example.myapplication1.workout.adapters.ExerciseCategoryAdapter
import com.example.myapplication1.workout.db.ExerciseCategoryDatabase
import com.example.myapplication1.workout.repository.ExerciseCategoryRepository
import com.example.myapplication1.workout.ui.viewmodels.ExerciseCategoryViewModelProvider
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WorkOutDetailFragment : Fragment(R.layout.fragment_work_out_detail) {


    private lateinit var exerciseCategoryAdapter: ExerciseCategoryAdapter

    private val viewModel: WorkOutDetailsViewModel by lazy {
        val database = ExerciseCategoryDatabase.getDatabase(requireContext())
        val repository = ExerciseCategoryRepository(database)
        val factory = ExerciseCategoryViewModelProvider(repository)
        ViewModelProvider(this, factory)[WorkOutDetailsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentWorkOutDetailBinding.inflate(inflater, container, false)

        setUpRecycler(binding)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.exerciseCategory.collectLatest { category->
                    Log.d("thisIsTheAnswer","${category.data}\n${category.message}")
                    exerciseCategoryAdapter.differ.submitList(category.data?.results?.toList())
                }
            }
        }

        return binding.root
    }


    fun setUpRecycler(binding: FragmentWorkOutDetailBinding){
        exerciseCategoryAdapter = ExerciseCategoryAdapter()
        binding.exerciseCategoryRecyclerView.apply{
            adapter = exerciseCategoryAdapter
            layoutManager = LinearLayoutManager(activity)
            //addOnScrollListener(this@WorkOutDetailFragment.scrollListener)
        }
    }

}