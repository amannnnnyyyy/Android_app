package com.example.myapplication1.workout.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.myapplication1.R
import com.example.myapplication1.databinding.FragmentWorkOutHomeBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.toString

class WorkOutHomeFragment : Fragment(R.layout.fragment_work_out_home),
    AdapterView.OnItemClickListener,
    AdapterView.OnItemSelectedListener {

    val whViewModel: WorkoutHomeViewModel by viewModels()

    private val TAG = "workout_fragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentWorkOutHomeBinding.inflate(inflater, container, false)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                whViewModel.stateFlow.collectLatest { str ->
                    binding.textView.text = str
                    binding.loading.visibility = View.GONE

                    binding.editT.addTextChangedListener(object : TextWatcher {
                        override fun afterTextChanged(s: Editable?) {}

                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                            whViewModel.changeStateFlow(s.toString())
                        }

                    })
                }
            }
        }



        binding.button.setOnClickListener {
            binding.loading.visibility = View.VISIBLE
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    whViewModel.onDefault.collectLatest {
                        delay(2000L)
                        binding.editT.text = null
                        whViewModel.changeToDefault()
                    }
                }
            }
        }


        val numberSpinner: Spinner = binding.numberSpinner

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.numbers_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            numberSpinner.adapter = adapter
        }

        numberSpinner.onItemSelectedListener = this



        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                whViewModel.sharedFlow.collectLatest { text ->
                    binding.done.setOnClickListener {
                        Snackbar.make(binding.root, "Counting to $text", Snackbar.LENGTH_SHORT).show()

                            lifecycleScope.launch {
                                whViewModel.triggerFlow().collectLatest { count ->
                                    binding.textView.text = count.toString()
                                }
                            }
                        }
                }
            }
        }

        binding.goToDetails.setOnClickListener {
            val nav = findNavController()
            val direction = WorkOutHomeFragmentDirections.actionWorkOutHomeFragmentToWorkOutDetailFragment()
            nav.navigate(direction)
        }



        return binding.root
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedNumber = parent?.getItemAtPosition(position).toString()
        whViewModel.setSharedFlow(selectedNumber.toInt())
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WorkOutHomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}