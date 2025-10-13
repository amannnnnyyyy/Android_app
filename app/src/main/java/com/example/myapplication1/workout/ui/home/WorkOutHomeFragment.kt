package com.example.myapplication1.workout.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication1.R
import com.example.myapplication1.databinding.FragmentWorkOutDetailBinding
import com.example.myapplication1.databinding.FragmentWorkOutHomeBinding
import com.example.myapplication1.news.ui.main.NewsMainFragmentDirections
import com.example.myapplication1.workout.adapters.ExerciseCategoryAdapter
import com.example.myapplication1.workout.db.ExerciseCategoryDatabase
import com.example.myapplication1.workout.models.Category
import com.example.myapplication1.workout.models.ExerciseCategory
import com.example.myapplication1.workout.repository.ExerciseCategoryRepository
import com.example.myapplication1.workout.ui.details.WorkOutDetailsViewModel
import com.example.myapplication1.workout.ui.viewmodels.ExerciseCategoryViewModelProvider
import com.example.myapplication1.workout.utils.Resource
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.toString

class WorkOutHomeFragment : Fragment(R.layout.fragment_work_out_home){

    private lateinit var exerciseCategoryAdapter: ExerciseCategoryAdapter

    private val viewModel: WorkoutHomeViewModel by lazy {
        val database = ExerciseCategoryDatabase.getDatabase(requireContext())
        val repository = ExerciseCategoryRepository(database)
        val factory = ExerciseCategoryViewModelProvider(repository)
        ViewModelProvider(this, factory)[WorkoutHomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentWorkOutHomeBinding.inflate(inflater, container, false)

        setUpRecycler(binding)

        exerciseCategoryAdapter.setOnItemClickListener {
            Log.i("thisIsIt","title: ${it.name}")
            it.id?.let { id->
                val nav = findNavController()
                val direction = WorkOutHomeFragmentDirections.actionWorkOutHomeFragmentToWorkOutDetailFragment(id)
                nav.navigate(direction)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.exerciseCategory.collectLatest { category->
                    Log.d("thisIsTheAnswer","waiting ${category.data}\n${category.message}")

                    if (category is Resource.Error){

                        binding.errorMessage.apply {
                            text = "${ category.message } \nPlease try again"
                            isVisible = true
                        }
                        binding.progressBar.isVisible = false
                        Toast.makeText(requireContext(),"Error Occurred: ${category.message}", Toast.LENGTH_SHORT).show()
                    }else if (category is Resource.Loading){
                        binding.errorMessage.isVisible = false
                        binding.progressBar.isVisible = true
                    }else if (category is Resource.Success){
                        binding.errorMessage.isVisible = false
                        binding.progressBar.isVisible = false
                        Log.i("checkCategory","Inside success")
                        val lists : MutableList<ExerciseCategory> = mutableListOf()
                        Log.i("checkCategory","Inside success ${category.data?.results?.size}")
                        if ((category.data?.results?.size?:0)>0){
                            category.data?.results?.toList()?.let{ it->
                                for (list in it) {
                                    //delay(100)
                                    lists.add(list)
                                    exerciseCategoryAdapter.differ.submitList(lists)
                                }
                            }
                        }
                    }
                }
            }
        }

        return binding.root
    }

    fun setUpRecycler(binding: FragmentWorkOutHomeBinding){
        exerciseCategoryAdapter = ExerciseCategoryAdapter()
        binding.exerciseCategoryRecyclerView.apply{
            adapter = exerciseCategoryAdapter
            layoutManager = LinearLayoutManager(activity)
            //addOnScrollListener(this@WorkOutDetailFragment.scrollListener)
        }
    }

}