package org.bl.coffeecounter.ui.coffeecount

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.bl.coffeecounter.R


class CoffeeViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val date: TextView
        val time: TextView
        val flavour: TextView
        val cost: TextView
        val deleteButton: ImageButton


        init {
            date = view.findViewById(R.id.date)
            time = view.findViewById(R.id.time)
            flavour = view.findViewById(R.id.flavour)
            cost = view.findViewById(R.id.cost)
            deleteButton = view.findViewById(R.id.deleteButton)
        }
}