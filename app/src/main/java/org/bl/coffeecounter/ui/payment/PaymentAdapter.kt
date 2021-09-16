package org.bl.coffeecounter.ui.payment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.bl.coffeecounter.R
import org.bl.coffeecounter.db.entities.Payment
import org.bl.coffeecounter.ui.coffeecount.CoffeeViewHolder
import org.bl.coffeecounter.util.formatCurrency
import org.bl.coffeecounter.util.formatDate
import org.bl.coffeecounter.util.formatTime
import java.util.function.Consumer

class PaymentAdapter (var data: List<Payment>, private val deleteListener: Consumer<Int>): RecyclerView.Adapter<PaymentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_payment, parent, false)
        return PaymentViewHolder(view)
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        val coffee = data[position]
        holder.date.text = formatDate(coffee.localDateTime)
        holder.time.text = formatTime(coffee.localDateTime)
        holder.amount.text = formatCurrency(coffee.amount / 100.0)
        holder.deleteButton.setOnClickListener { deleteListener.accept(coffee.id) }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}