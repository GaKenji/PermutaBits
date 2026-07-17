package com.example.permutabittools.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.permutabittools.databinding.FragmentValidadorLogicaBinding
import com.example.permutabittools.viewModels.ExpressoesLogicasViewModel

class ValidadorExpressoesLogicasFragment : Fragment() {

    private var _binding: FragmentValidadorLogicaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(ExpressoesLogicasViewModel::class.java)

        _binding = FragmentValidadorLogicaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textSlideshow
        slideshowViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}