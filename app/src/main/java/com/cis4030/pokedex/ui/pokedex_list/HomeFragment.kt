package com.cis4030.pokedex.ui.pokedex_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cis4030.pokedex.R

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        homeViewModel =
                ViewModelProvider(activity, HomeViewModel.Factory(activity.application)).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.pokedex_list_fragment, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.typeList.observe(viewLifecycleOwner, Observer {
            textView.text = it.size.toString()
        })
        return root
    }
}