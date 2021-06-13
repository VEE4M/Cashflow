package com.gmail.appverstas.cashflow.data.expenses.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.gmail.appverstas.cashflow.R
import com.gmail.appverstas.cashflow.data.expenses.ExpenseListAdapter
import com.gmail.appverstas.cashflow.data.expenses.ExpenseViewModel
import com.gmail.appverstas.cashflow.data.expenses.models.ExpenseItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_expenses.view.*


class ExpensesFragment : Fragment() {

    private val expenseViewModel: ExpenseViewModel by viewModels()
    private val adapter: ExpenseListAdapter by lazy { ExpenseListAdapter() }
    private lateinit var totalExpensesText: TextView
    private lateinit var pieChartExpenses: PieChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_expenses, container, false)
        val addExpenseFab = view.findViewById<FloatingActionButton>(R.id.fab_add_expense)
        val recyclerView = view.recycler_view_expenses
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        totalExpensesText = view.findViewById(R.id.text_total_expense)
        pieChartExpenses = view.findViewById(R.id.pieChart_expenses)
        pieChartExpenses.setNoDataText("")

        expenseViewModel.getAllData.observe(viewLifecycleOwner, { updatedExpenseList ->
            adapter.updateData(updatedExpenseList)
            updateTotalExpensesText(updatedExpenseList)
            displayPieChart(updatedExpenseList)
        })

        addExpenseFab.setOnClickListener {
            findNavController().navigate(R.id.action_expensesFragment_to_addExpenseFragment)
        }
        return view
    }

    private fun updateTotalExpensesText(updatedExpenseList: List<ExpenseItem>) {
        var counter = 0.0
        for(expenseItem in updatedExpenseList){
            counter += expenseItem.amount
        }
        totalExpensesText.text = getString(R.string.total_amount, counter)

    }

    private fun displayPieChart(updatedExpenseList: List<ExpenseItem>){
        pieChartExpenses.setTransparentCircleColor(Color.BLACK)

        val pieEntryList = ArrayList<PieEntry>()

        for (entry in updatedExpenseList){
            pieEntryList.add(PieEntry(entry.amount.toFloat(), entry.title))
        }

        val pieDataSet = PieDataSet(pieEntryList, "")
        pieDataSet.sliceSpace = 2f
        pieDataSet.colors = ColorTemplate.JOYFUL_COLORS.toList()
        val pieData = PieData(pieDataSet)
        pieChartExpenses.data = pieData
        pieChartExpenses.description = null
        pieChartExpenses.legend.textColor = Color.WHITE
        pieChartExpenses.setEntryLabelTextSize(10f)
        pieChartExpenses.holeRadius = 45f
        pieChartExpenses.transparentCircleRadius = 50f
        pieChartExpenses.setHoleColor(Color.BLACK)
        pieChartExpenses.setEntryLabelColor(Color.BLACK)
        pieChartExpenses.animate()
        pieChartExpenses.invalidate()
        pieChartExpenses.refreshDrawableState()
    }

}