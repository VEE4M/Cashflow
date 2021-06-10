package com.gmail.appverstas.cashflow.data.saving


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.gmail.appverstas.cashflow.MainActivity
import com.gmail.appverstas.cashflow.R
import com.gmail.appverstas.cashflow.data.saving.fragments.SavingAndInvestingFragmentDirections
import com.gmail.appverstas.cashflow.data.saving.models.SavingItem
import kotlinx.android.synthetic.main.list_row_saving.view.*

/**
 *Veli-Matti Tikkanen, 8.2.2021
 */
class SavingListAdapter(): RecyclerView.Adapter<SavingListAdapter.ViewHolder>() {


    var savingsList= emptyList<SavingItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_row_saving, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.text_saving_title.setText(savingsList[position].title)
        holder.itemView.text_saving_amount.setText(savingsList[position].amount.toString()+"€")
        holder.itemView.text_saving_type.setText(savingsList[position].type)

        holder.itemView.setOnClickListener {
            val action = SavingAndInvestingFragmentDirections.actionSavingAndInvestingFragmentToEditItemFragment(savingsList[position])
            holder.itemView.findNavController().navigate(action)
        }

        holder.itemView.list_row_saving.setBackgroundColor(savingsList[position].getBackgroundColor(holder.itemView.context))
    }

    override fun getItemCount(): Int {
        return savingsList.size
    }

    fun updateData(updatedSavingsList: List<SavingItem>){
        savingsList = updatedSavingsList
        notifyDataSetChanged()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)
}