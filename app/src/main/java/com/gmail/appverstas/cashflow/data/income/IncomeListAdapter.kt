package com.gmail.appverstas.cashflow.data.income

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.gmail.appverstas.cashflow.R
import com.gmail.appverstas.cashflow.data.income.fragments.IncomeFragmentDirections
import com.gmail.appverstas.cashflow.data.income.models.IncomeItem
import kotlinx.android.synthetic.main.list_row_income.view.*

/**
 *Veli-Matti Tikkanen, 22.1.2021
 */
class IncomeListAdapter: RecyclerView.Adapter<IncomeListAdapter.ViewHolder>() {

    private var incomeList = emptyList<IncomeItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_row_income, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.text_income_title.text = incomeList[position].title
        holder.itemView.text_income_amount.text = incomeList[position].netAmount.toString() + "€"
        holder.itemView.text_income_type.text = incomeList[position].getIncomeTypeAsString()
        holder.itemView.list_row_income.setOnClickListener {
            val action = IncomeFragmentDirections.actionIncomeFragmentToEditIncomeFragment(incomeList[position])
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return incomeList.size
    }

    fun setData(updatedIncomeList: List<IncomeItem>){
        this.incomeList = updatedIncomeList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

}