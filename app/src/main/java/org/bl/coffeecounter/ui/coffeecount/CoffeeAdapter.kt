package org.bl.coffeecounter.ui.coffeecount

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.bl.coffeecounter.R
import org.bl.coffeecounter.db.entities.Coffee
import org.bl.coffeecounter.util.formatCurrency
import org.bl.coffeecounter.util.formatDate
import org.bl.coffeecounter.util.formatTime
import java.util.function.Consumer

class CoffeeAdapter(var data: List<Coffee>, val deleteListener: Consumer<Int>): RecyclerView.Adapter<CoffeeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_coffee, parent, false)
        return CoffeeViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoffeeViewHolder, position: Int) {
        val coffee = data.get(position)
        holder.date.text = formatDate(coffee.localDateTime)
        holder.time.text = formatTime(coffee.localDateTime)
        holder.flavour.text = coffee.flavour
        holder.cost.text = formatCurrency(coffee.cost / 100.0)
        holder.deleteButton.setOnClickListener { deleteListener.accept(coffee.id) }
    }

    override fun getItemCount(): Int {
        return data.size
    }


}