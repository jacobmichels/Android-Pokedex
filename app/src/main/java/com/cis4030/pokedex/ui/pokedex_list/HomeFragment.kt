package com.cis4030.pokedex.ui.pokedex_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cis4030.pokedex.R
import com.cis4030.pokedex.viewmodels.SharedViewModel

class HomeFragment : Fragment() {

    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        val root = inflater.inflate(R.layout.pokedex_list_fragment, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        viewModel.typeList.observe(viewLifecycleOwner, Observer {
            textView.text = it.size.toString()
        })
        return root
    }
}