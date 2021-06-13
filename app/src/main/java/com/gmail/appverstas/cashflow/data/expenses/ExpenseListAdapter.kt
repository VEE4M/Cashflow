package com.gmail.appverstas.cashflow.data.expenses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.gmail.appverstas.cashflow.R
import com.gmail.appverstas.cashflow.data.expenses.fragments.ExpensesFragmentDirections
import com.gmail.appverstas.cashflow.data.expenses.models.ExpenseItem
import kotlinx.android.synthetic.main.list_row_expense.view.*

/**
 *Veli-Matti Tikkanen, 25.1.2021
 */
class ExpenseListAdapter : RecyclerView.Adapter<ExpenseListAdapter.ViewHolder>() {

    private var expenseList = emptyList<ExpenseItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_row_expense, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.text_expense_title.text = expenseList[position].title
        holder.itemView.text_expense_amount.text = expenseList[position].amount.toString() + "€"
        holder.itemView.text_expense_type.text = expenseList[position].getExpenseTypeAsString()
        holder.itemView.list_row_expense.setOnClickListener {
            val action = ExpensesFragmentDirections.actionExpensesFragmentToEditExpenseFragment(expenseList[position])
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return expenseList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    fun updateData(updatedExpenseList: List<ExpenseItem>){
        expenseList = updatedExpenseList
        notifyDataSetChanged()
    }


}