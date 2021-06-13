package com.gmail.appverstas.cashflow.data.income.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gmail.appverstas.cashflow.R
import com.gmail.appverstas.cashflow.data.SharedMethods
import com.gmail.appverstas.cashflow.data.income.IncomeViewModel
import com.gmail.appverstas.cashflow.data.income.models.IncomeItem
import kotlinx.android.synthetic.main.fragment_income_edit.*
import kotlinx.android.synthetic.main.fragment_income_edit.view.*


class EditIncomeFragment : Fragment() {

    private val args by navArgs<EditIncomeFragmentArgs>()
    private val incomeViewModel: IncomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_income_edit, container, false)
        view.et_edit_income_title.setText(args.currentItem.title)
        view.et_edit_income_net_amount.setText(args.currentItem.netAmount.toString())
        view.spinner_edit_income_type.setSelection(args.currentItem.getIncomeTypeAsInt())
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_edit_fragments, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.app_bar_save -> updateItem()
            R.id.app_bar_delete -> confirmDeletion()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmDeletion() {
        incomeViewModel.deleteIncome(args.currentItem)
        Toast.makeText(requireContext(), getString(R.string.toast_deleted), Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_editIncomeFragment_to_incomeFragment)
    }

    private fun updateItem() {
        val incomeTitle = et_edit_income_title.text.toString()
        val incomeAmount = et_edit_income_net_amount.text.toString().toDouble()
        val validation = SharedMethods.verifyDataFormat(incomeTitle, incomeAmount)
        if(validation){
            val updatedIncome = IncomeItem(
                args.currentItem.id,
                incomeTitle,
                IncomeItem.parseIncomeType(spinner_edit_income_type.selectedItem.toString()),
                incomeAmount
            )
            incomeViewModel.updateIncome(updatedIncome)
            Toast.makeText(requireContext(), getString(R.string.toast_updated), Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_editIncomeFragment_to_incomeFragment)
        }else{
            Toast.makeText(requireContext(), getString(R.string.toast_please_fill_all_fields), Toast.LENGTH_SHORT).show()
        }
    }


}