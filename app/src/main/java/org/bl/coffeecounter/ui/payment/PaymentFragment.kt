package org.bl.coffeecounter.ui.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.bl.coffeecounter.BR
import org.bl.coffeecounter.CoffeeCounterApplication
import org.bl.coffeecounter.R
import org.bl.coffeecounter.databinding.PaymentFragmentBinding
import java.util.function.Consumer

class PaymentFragment : Fragment() {

    private val paymentViewModel: PaymentViewModel by activityViewModels {
        PaymentViewModelFactory((activity?.application as CoffeeCounterApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        val binding: PaymentFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.payment_fragment, container, false)
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, paymentViewModel)

        val paymentButtn: Button = binding.root.findViewById(R.id.pay)
        paymentButtn.setOnClickListener(View.OnClickListener {
            paymentViewModel.pay()
            findNavController().popBackStack()
        })

        val paymentsList: RecyclerView = binding.root.findViewById(R.id.payments)
        paymentsList.layoutManager = LinearLayoutManager(requireContext())
        paymentsList.adapter = PaymentAdapter(ArrayList(0), Consumer { id -> paymentViewModel.delete(id) })
        paymentViewModel.allPayment.observe(viewLifecycleOwner, Observer {
            val allPayment = it.toTypedArray()
            allPayment.sortWith { a, b ->
                b.localDateTime.compareTo(a.localDateTime)
            }
            (paymentsList.adapter as PaymentAdapter).data = allPayment.toList()
            paymentsList.adapter?.notifyDataSetChanged()
        })
        return binding.root
    }


}