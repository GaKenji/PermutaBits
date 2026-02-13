package com.example.permutabittools.ui.gallery

import com.example.permutabittools.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.permutabittools.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bases = resources.getStringArray(R.array.bases_numericas).toList()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, bases)
        binding.exposedDropdownTeste.setAdapter(adapter)

        binding.exposedDropdownTeste.setOnItemClickListener { parent, _, position, _ ->
            val txt = parent.getItemAtPosition(position).toString()
            binding.testeTxtView.setText(txt)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}