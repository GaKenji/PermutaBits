package com.example.permutabittools.ui.Ipv4Fragments

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.permutabittools.R
import com.example.permutabittools.viewModels.ipv4viewModels.Ipv4MaskViewModel

class Ipv4MaskFragment : Fragment() {

    companion object {
        fun newInstance() = Ipv4MaskFragment()
    }

    private val viewModel: Ipv4MaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_ipv4_mask, container, false)
    }
}