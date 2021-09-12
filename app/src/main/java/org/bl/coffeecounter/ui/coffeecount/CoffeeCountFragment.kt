package org.bl.coffeecounter.ui.coffeecount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import org.bl.coffeecounter.BR
import org.bl.coffeecounter.CoffeeCounterApplication
import org.bl.coffeecounter.R
import org.bl.coffeecounter.databinding.CoffeeCountFragmentBinding

class CoffeeCountFragment : Fragment() {

    private val coffeeCountViewModel: CoffeeCountViewModel by activityViewModels {
        CoffeeCountViewModelFactory((activity?.application as CoffeeCounterApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        val binding: CoffeeCountFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.coffee_count_fragment, container, false)
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, coffeeCountViewModel)


        val coffeeList: ListView = binding.root.findViewById(R.id.coffeeList)

        coffeeCountViewModel.allCoffee.observe(viewLifecycleOwner, {coffee ->
            val allCoffee = coffee.toTypedArray()
            allCoffee.sortWith(Comparator { a, b ->
                b.localDateTime.compareTo(a.localDateTime)
            })
           coffeeList.adapter =ArrayAdapter(this.requireContext(), android.R.layout.simple_list_item_1,allCoffee)
        })


        return binding.root
    }

}