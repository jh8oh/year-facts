package dev.ohjiho.yearfacts.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import dev.ohjiho.yearfacts.R
import dev.ohjiho.yearfacts.databinding.FragmentSearchBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val yearFactsViewModel: YearFactsViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.fragment_search_era_array,
            android.R.layout.simple_spinner_item
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.eraSpinner.adapter = it
        }

        binding.searchButton.setOnClickListener {
            if (TextUtils.isEmpty(binding.yearEditText.text)) {
                Toast.makeText(requireContext(), R.string.empty_year_error, Toast.LENGTH_SHORT).show()
            } else {
                yearFactsViewModel.search(Integer.parseInt(binding.yearEditText.text.toString()), isSpinnerEraAD())

                val action = SearchFragmentDirections.toResultFragment()
                binding.root.findNavController().navigate(action)
            }
        }

        binding.randomButton.setOnClickListener {
            yearFactsViewModel.random()

            val action = SearchFragmentDirections.toResultFragment()
            binding.root.findNavController().navigate(action)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isSpinnerEraAD(): Boolean {
        return binding.eraSpinner.selectedItemPosition == 0
    }
}