package org.bl.coffeecounter.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import org.bl.coffeecounter.BR
import org.bl.coffeecounter.CoffeeCounterApplication
import org.bl.coffeecounter.R
import org.bl.coffeecounter.databinding.MainFragmentBinding


class MainFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModelFactory((activity?.application as CoffeeCounterApplication).repository)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        val binding: MainFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, mainViewModel)

        val paymentButtn: Button = binding.root.findViewById(R.id.paymentButton)
        paymentButtn.setOnClickListener(View.OnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToPaymentFragment())
        })

        val coffeeCountCard: CardView = binding.root.findViewById(R.id.coffeeCountCard)
        coffeeCountCard.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToCoffeeCountFragment())
        }

        return binding.root
    }
}