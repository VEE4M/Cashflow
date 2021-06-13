package com.gmail.appverstas.cashflow.data.expenses.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gmail.appverstas.cashflow.R
import com.gmail.appverstas.cashflow.data.SharedMethods
import com.gmail.appverstas.cashflow.data.expenses.ExpenseViewModel
import com.gmail.appverstas.cashflow.data.expenses.models.ExpenseItem
import kotlinx.android.synthetic.main.fragment_expense_edit.*
import kotlinx.android.synthetic.main.fragment_expense_edit.view.*

class EditExpenseFragment : Fragment() {

    private val args by navArgs<EditExpenseFragmentArgs>()
    private val expenseViewModel: ExpenseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_expense_edit, container, false)
        view.et_edit_expense_title.setText(args.currentExpenseItem.title)
        view.et_edit_expense_amount.setText(args.currentExpenseItem.amount.toString())
        view.spinner_edit_expense_type.setSelection(args.currentExpenseItem.getExpenseTypeAsInt())
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_edit_fragments, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.app_bar_save -> saveToDb()
            R.id.app_bar_delete -> deleteExpense()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteExpense() {
        expenseViewModel.deleteExpense(args.currentExpenseItem)
        Toast.makeText(requireContext(), getString(R.string.toast_deleted), Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_editExpenseFragment_to_expensesFragment)
    }

    private fun saveToDb() {
        val newTitle = et_edit_expense_title.text.toString()
        val newType = spinner_edit_expense_type.selectedItem.toString()
        val newAmount = et_edit_expense_amount.text.toString().toDouble()
        val validation = SharedMethods.verifyDataFormat(newTitle, newAmount)
        if(validation){
            val updatedExpense = ExpenseItem(
                    args.currentExpenseItem.id,
                    newTitle,
                    ExpenseItem.parseExpenseType(newType),
                    newAmount
            )
            expenseViewModel.updateExpense(updatedExpense)
            Toast.makeText(requireContext(), getString(R.string.toast_updated), Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_editExpenseFragment_to_expensesFragment)
        }else{
            Toast.makeText(requireContext(), getString(R.string.toast_please_fill_all_fields), Toast.LENGTH_SHORT).show()
        }
    }




}