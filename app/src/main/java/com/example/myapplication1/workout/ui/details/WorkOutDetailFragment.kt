package com.example.myapplication1.workout.ui.details

import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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

        val imageView: ImageView = binding.muscleImagePrimary
        val imageView2: ImageView = binding.muscleImageSecondary


        viewModel.getAllInfos(args.workoutId)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.exerciseInfos.collectLatest { infoResponse->
                    val filtered = infoResponse.data?.results?.filter {
                        ((it.translations?.size?:0)>0) && (it.translations?.any { translation -> translation.language==2 && translation.description.isNotEmpty() })==true
                    }

                    val filteredWithLang = filtered?.find {
                        it.translations?.any{tra-> tra.language==2}==true
                    }

                    Log.d("ThisIsDetails","${args.workoutId}")
                    val withImages = infoResponse.data?.results?. filter {
                        (it.muscles?.size ?: 0) > 0 && (it.translations?.any{tran-> tran.language==2} == true)
                    }


                    withImages?.get(0).let{ data->
                        binding.description.text = Html.fromHtml(filteredWithLang?.translations?.find {
                            it.language==2
                        }?.description?:"<h1>No Description</h1>")


                        data?.category?.let { binding.header.text = it.name }


                       // data?.muscles_secondary?.get(0)?.image_url_main?.let{
                           // val fullUrl = "https://wger.de$it"
//                            imageView2.load(fullUrl) {
//                                placeholder(ContextCompat.getDrawable(requireContext(), R.drawable.profile))
//                                error(ContextCompat.getDrawable(requireContext(), R.drawable.ic_launcher_background))
//                            }

                        //}




                    }
                }
            }
        }

        return binding.root
    }




}