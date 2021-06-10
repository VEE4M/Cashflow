package com.gmail.appverstas.cashflow

import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.gmail.appverstas.cashflow.data.expenses.ExpenseViewModel
import com.gmail.appverstas.cashflow.data.expenses.models.ExpenseItem
import com.gmail.appverstas.cashflow.data.income.IncomeViewModel
import com.gmail.appverstas.cashflow.data.income.models.IncomeItem
import com.gmail.appverstas.cashflow.data.saving.SavingViewModel
import com.gmail.appverstas.cashflow.data.saving.models.SavingItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_overview.*


class OverviewFragment : Fragment() {

    private val incomeViewModel: IncomeViewModel by viewModels()
    private val expenseViewModel: ExpenseViewModel by viewModels()
    private val savingViewModel: SavingViewModel by viewModels()

    private var currentTotalIncome = 0.0
    private var currentTotalExpense = 0.0
    private var currentTotalSavings = 0.0
    private var unAllocatedAmounts = 0.0

    private lateinit var pieChart: PieChart

    var currentTotalInvestments = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_overview, container, false)

        val incomeLayout = view.findViewById<LinearLayout>(R.id.info_layout_income)
        val expenseLayout = view.findViewById<LinearLayout>(R.id.info_layout_expense)
        val savingsLayout = view.findViewById<LinearLayout>(R.id.info_layout_savings)
        val investmentsLayout = view.findViewById<LinearLayout>(R.id.info_layout_investments)

        pieChart = view.findViewById(R.id.pieChart)

        incomeLayout.setOnClickListener {
            findNavController().navigate(R.id.action_overviewFragment_to_incomeFragment)
        }

        expenseLayout.setOnClickListener {
            findNavController().navigate(R.id.action_overviewFragment_to_expensesFragment)
        }

        savingsLayout.setOnClickListener {
            findNavController().navigate(R.id.action_overviewFragment_to_savingAndInvestingFragment)
        }
        investmentsLayout.setOnClickListener {
            findNavController().navigate(R.id.action_overviewFragment_to_savingAndInvestingFragment)
        }

        incomeViewModel.getAllData.observe(viewLifecycleOwner, { updatedIncomeList ->
            currentTotalIncome = getCurrentTotalIncome(updatedIncomeList)
            unAllocatedAmounts = getUnAllocatedAmount()
            updateTextViews()
            displayPieChart()
        })

        expenseViewModel.getAllData.observe(viewLifecycleOwner, { updatedExpenseList ->
            currentTotalExpense = getCurrentTotalExpense(updatedExpenseList)
            unAllocatedAmounts = getUnAllocatedAmount()
            updateTextViews()
            displayPieChart()
        })

        savingViewModel.getAllData.observe(viewLifecycleOwner,  { updatedSavingsList ->
            currentTotalSavings = getCurrentTotalSavings(updatedSavingsList)
            currentTotalInvestments = getCurrentTotalInvestments(updatedSavingsList)
            unAllocatedAmounts = getUnAllocatedAmount()
            updateTextViews()
            displayPieChart()
        })

        return view
    }

    private fun getCurrentTotalIncome(incomeList: List<IncomeItem>): Double{
        var counter = 0.0
        for(incomeItem in incomeList){
            counter += incomeItem.netAmount
        }
        return counter
    }

    private fun getCurrentTotalExpense(expenseList: List<ExpenseItem>): Double{
        var counter = 0.0
        for(expenseItem in expenseList){
            counter += expenseItem.amount
        }
        return counter
    }

    private fun getCurrentTotalSavings(updatedSavingsList: List<SavingItem> ): Double{
        val savingList = updatedSavingsList.filter { savingItem -> savingItem.type.equals("Saving") }
        var counter = 0.0
        for(item in savingList){
            counter += item.amount
        }
        return counter
    }

    private fun getCurrentTotalInvestments(updatedSavingsList: List<SavingItem>): Double{
        val investmentsList = updatedSavingsList.filter { savingItem -> savingItem.type.equals("Investment") }
        var counter = 0.0
        for(item in investmentsList){
            counter += item.amount
        }
        return counter
    }

    private fun getUnAllocatedAmount(): Double{
        val amount = currentTotalIncome - currentTotalExpense - currentTotalSavings - currentTotalInvestments
        return if (amount > 0){
            amount
        }else{
            0.0
        }
    }

    private fun updateTextViews(){
        val unAllocatedAmount = currentTotalIncome - currentTotalExpense - currentTotalSavings - currentTotalInvestments
        text_total_income_overview_fragment.text = getString(R.string.monthly_income, currentTotalIncome)
        text_total_expense_overview_fragment.text = getString(R.string.monthly_expense, currentTotalExpense)
        text_total_savings_overview_fragment.text = getString(R.string.monthly_savings, currentTotalSavings)
        text_total_investments_overview_fragment.text = getString(R.string.monthly_investments, currentTotalInvestments)
        text_unallocated.text = getString(R.string.unallocated_amount, unAllocatedAmount)

    }

    private fun addColor(string: String): Int{
        return when (string){
            "currentTotalSavings" -> resources.getColor(R.color.blue_saving)
            "currentTotalExpense" -> resources.getColor(R.color.pink_expense)
            "currentTotalInvestments" -> resources.getColor(R.color.yellow_investment)
            "unAllocatedAmounts" -> resources.getColor(R.color.green_unallocated)
            else -> Color.CYAN
        }
    }

    private fun displayPieChart(){
        Log.d(TAG, "displayPieChart: called: $currentTotalIncome , $currentTotalExpense , $currentTotalSavings , $currentTotalInvestments" )
        pieChart.setTransparentCircleColor(Color.BLACK)

        val pieEntryList = ArrayList<PieEntry>()
        val colorList = mutableListOf<Int>()
        if (currentTotalSavings > 0) {
            pieEntryList.add(PieEntry(currentTotalSavings.toFloat(), "Saving"))
            colorList.add(resources.getColor(R.color.blue_saving))
        }
        if (currentTotalExpense > 0 ) {
            pieEntryList.add(PieEntry(currentTotalExpense.toFloat(), "Expenses"))
            colorList.add(resources.getColor(R.color.pink_expense))
        }
        if (currentTotalInvestments > 0) {
            pieEntryList.add(PieEntry(currentTotalInvestments.toFloat(), "Investments"))
            colorList.add(resources.getColor(R.color.yellow_investment))
        }
        if (unAllocatedAmounts > 0) {
            pieEntryList.add(PieEntry(unAllocatedAmounts.toFloat(), "Unallocated"))
            colorList.add(resources.getColor(R.color.green_unallocated))
        }


        val pieDataSet = PieDataSet(pieEntryList, "")
        pieDataSet.sliceSpace = 2f
        pieDataSet.colors = colorList
        val pieData = PieData(pieDataSet)
        pieChart.data = pieData
        pieChart.description = null
        pieChart.legend.textColor = Color.WHITE
        pieChart.setEntryLabelTextSize(10f)
        pieChart.holeRadius = 45f
        pieChart.transparentCircleRadius = 50f
        pieChart.setHoleColor(Color.BLACK)
        pieChart.setEntryLabelColor(Color.BLACK)
        pieChart.animate()
        pieChart.invalidate();
        pieChart.refreshDrawableState();
    }

}