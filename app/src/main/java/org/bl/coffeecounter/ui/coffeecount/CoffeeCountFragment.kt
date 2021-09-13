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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.bl.coffeecounter.BR
import org.bl.coffeecounter.CoffeeCounterApplication
import org.bl.coffeecounter.R
import org.bl.coffeecounter.databinding.CoffeeCountFragmentBinding
import org.bl.coffeecounter.db.entities.Coffee

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


        val coffeeList: RecyclerView = binding.root.findViewById(R.id.coffeeList)
        coffeeList.layoutManager = LinearLayoutManager(requireContext())
        coffeeList.adapter = CoffeeAdapter(ArrayList<Coffee>(0), {id -> coffeeCountViewModel.deleteCoffee(id)})
        coffeeCountViewModel.allCoffee.observe(viewLifecycleOwner, {coffee ->
            val allCoffee = coffee.toTypedArray()
            allCoffee.sortWith(Comparator { a, b ->
                b.localDateTime.compareTo(a.localDateTime)
            })
           (coffeeList.adapter as CoffeeAdapter).data = allCoffee.toList()
            coffeeList?.adapter?.notifyDataSetChanged()
        })


        return binding.root
    }

}