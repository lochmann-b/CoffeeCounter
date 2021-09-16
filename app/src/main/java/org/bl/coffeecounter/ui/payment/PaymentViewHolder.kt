package org.bl.coffeecounter.ui.payment

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.bl.coffeecounter.R

class PaymentViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val date: TextView = view.findViewById(R.id.date)
    val time: TextView = view.findViewById(R.id.time)
    val amount: TextView = view.findViewById(R.id.amount)
    val deleteButton: ImageButton = view.findViewById(R.id.deleteButton)
}