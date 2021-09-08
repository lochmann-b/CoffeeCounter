package org.bl.coffeecounter.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import org.bl.coffeecounter.CoffeeCounterApplication
import org.bl.coffeecounter.R
import java.util.*


class MainFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModelFactory((activity?.application as CoffeeCounterApplication).repository)
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        val ret = inflater.inflate(R.layout.main_fragment, container, false)
        val label: TextView = ret.findViewById(R.id.coffeeCount)
        val resetButton = ret.findViewById<Button>(R.id.resetButton)

        mainViewModel.allCoffee.observe(viewLifecycleOwner, Observer { coffee ->
            coffee?.let {
                label.text = it.size.toString() ?: "0"
            }
        })

        resetButton.setOnClickListener {
            mainViewModel.reset()
        }
        return ret;
    }
}