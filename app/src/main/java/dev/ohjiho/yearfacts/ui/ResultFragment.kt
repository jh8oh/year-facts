package dev.ohjiho.yearfacts.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import dev.ohjiho.yearfacts.databinding.FragmentResultBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private val yearFactsViewModel: YearFactsViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentResultBinding.inflate(inflater, container, false)

        yearFactsViewModel.displayYear.observe(viewLifecycleOwner) {
            binding.yearText.text = it
        }

        yearFactsViewModel.displayYearFactString.observe(viewLifecycleOwner) {
            binding.yearFactText.text = it
        }

        binding.rerollButton.setOnClickListener {
            yearFactsViewModel.reroll()
        }

        binding.randomButton.setOnClickListener {
            yearFactsViewModel.random()
        }

        binding.backButton.setOnClickListener {
            val action = ResultFragmentDirections.toSearchFragment()
            binding.root.findNavController().navigate(action)
        }

        yearFactsViewModel.error.observe(viewLifecycleOwner) { error ->
            if (error) {
                val errorToast = yearFactsViewModel.errorMessage.value?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT)
                }
                errorToast?.show()
                yearFactsViewModel.error.value = false
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}