package com.example.permutabittools.ui.Ipv4Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.permutabittools.R
import com.example.permutabittools.databinding.FragmentMascaraDeRedeIpv4Binding
import com.example.permutabittools.ui.adapters.Ipv4PagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MascaraRedesIPv4Fragment : Fragment() {

    private var _binding: FragmentMascaraDeRedeIpv4Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMascaraDeRedeIpv4Binding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPagerConfig()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
    }

    private fun viewPagerConfig(){
        val adapter = Ipv4PagerAdapter(this) //Instancia do adapter do ViewPage passando a fragment
        binding.viewPagerIpv4.adapter = adapter //Atribuição do adapter ao ViewPage
        val tabTitles = resources.getStringArray(R.array.modes).toList() //Lista de tabs/campos do TabLayout

        TabLayoutMediator(binding.tabLayoutIpv4, binding.viewPagerIpv4) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

}