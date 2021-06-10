package com.gmail.appverstas.cashflow.data.expenses.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gmail.appverstas.cashflow.R
import com.gmail.appverstas.cashflow.data.SharedMethods
import com.gmail.appverstas.cashflow.data.expenses.ExpenseViewModel
import com.gmail.appverstas.cashflow.data.expenses.models.ExpenseItem
import com.gmail.appverstas.cashflow.data.expenses.models.ExpenseType
import kotlinx.android.synthetic.main.fragment_expense_add.*

class AddExpenseFragment : Fragment() {

    val expenseViewModel: ExpenseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_expense_add, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.app_bar_save){
            saveToDatabase()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add_fragments, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun saveToDatabase(){
        var title = et_expense_title.text.toString()
        var type = spinner_expense_type.selectedItem.toString()
        var amount = et_expense_amount.text.toString().toDouble()
        var validation = SharedMethods.verifyDataFormat(title, amount)
        if(validation){
            var newExpense = ExpenseItem(
                    0,
                    title,
                    ExpenseItem.parseExpenseType(type),
                    amount
            )
            expenseViewModel.insertExpense(newExpense)
            Toast.makeText(requireContext(), "Expense saved!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addExpenseFragment_to_expensesFragment)
        }else{
            Toast.makeText(requireContext(), "Please fill all the fields!", Toast.LENGTH_SHORT).show()
        }
    }

}