package com.gmail.appverstas.cashflow.data.income.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gmail.appverstas.cashflow.R
import com.gmail.appverstas.cashflow.data.SharedMethods
import com.gmail.appverstas.cashflow.data.income.IncomeViewModel
import com.gmail.appverstas.cashflow.data.income.models.IncomeItem
import kotlinx.android.synthetic.main.fragment_income_add.*


class AddIncomeFragment : Fragment() {

    private val incomeViewModel: IncomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_income_add, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add_fragments, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.app_bar_save){
            addItemToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addItemToDb(){
        val incomeTitle = et_income_title.text.toString()
        val incomeAmount = et_income_net_amount.text.toString().toDouble()
        val validation = SharedMethods.verifyDataFormat(incomeTitle, incomeAmount)
        if(validation){
            val newIncome = IncomeItem(
                    0,
                    incomeTitle,
                    IncomeItem.parseIncomeType(spinner_income_type.selectedItem.toString()),
                    incomeAmount)
            incomeViewModel.insertIncome(newIncome)
            Toast.makeText(requireContext(), getString(R.string.toast_saved), Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addIncomeFragment_to_incomeFragment)
        }else{
            Toast.makeText(requireContext(), getString(R.string.toast_please_fill_all_fields), Toast.LENGTH_SHORT).show()
        }
    }

}