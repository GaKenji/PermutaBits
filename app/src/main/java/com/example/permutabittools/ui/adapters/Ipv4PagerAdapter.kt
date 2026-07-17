package com.example.permutabittools.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.permutabittools.ui.Ipv4Fragments.Ipv4CidrFragment
import com.example.permutabittools.ui.Ipv4Fragments.Ipv4HostsFragment
import com.example.permutabittools.ui.Ipv4Fragments.Ipv4MaskFragment
import com.example.permutabittools.ui.Ipv4Fragments.Ipv4VlsmFragment

class Ipv4PagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        // Indica qual fragment será criada de acordo com a posição do tab selecionado
        return when(position){
            0 -> Ipv4CidrFragment()
            1 -> Ipv4MaskFragment()
            2 -> Ipv4HostsFragment()
            else -> Ipv4VlsmFragment()
        }
    }

    override fun getItemCount(): Int = 4 //Quantia de tabs no TabLayout
}