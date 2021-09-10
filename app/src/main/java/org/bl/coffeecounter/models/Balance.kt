package org.bl.coffeecounter.models

import org.bl.coffeecounter.util.formatCurrency

class Balance (var income: Int, var expenses: Int) {

    val balance: Double get() = (income - expenses).toDouble()

    override fun toString(): String {
        return formatCurrency(balance / 100.0)
    }
}