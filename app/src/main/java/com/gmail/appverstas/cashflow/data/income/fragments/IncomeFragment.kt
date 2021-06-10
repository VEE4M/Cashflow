package com.gmail.appverstas.cashflow.data.income.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.gmail.appverstas.cashflow.R
import com.gmail.appverstas.cashflow.data.income.IncomeListAdapter
import com.gmail.appverstas.cashflow.data.income.IncomeViewModel
import com.gmail.appverstas.cashflow.data.income.models.IncomeItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_income.view.*

class IncomeFragment : Fragment() {

    val incomeViewModel: IncomeViewModel by viewModels()
    val adapter: IncomeListAdapter by lazy { IncomeListAdapter() }
    private lateinit var totalIncomeText: TextView
    private lateinit var pieChartIncome: PieChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_income, container, false)
        val recyclerView = view.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        totalIncomeText = view.findViewById<TextView>(R.id.text_total_income)
        pieChartIncome = view.findViewById<PieChart>(R.id.pieChart_income)
        incomeViewModel.getAllData.observe(viewLifecycleOwner, Observer { updatedIncomeList ->
            adapter.setData(updatedIncomeList)
            updateTotalIncomeText(updatedIncomeList)
            displayPieChart(updatedIncomeList)
        })

        val addIncomeFab = view.findViewById<FloatingActionButton>(R.id.fab_add_income)

        addIncomeFab.setOnClickListener {
            findNavController().navigate(R.id.action_incomeFragment_to_addIncomeFragment)
        }


        return view
    }


    private fun updateTotalIncomeText(updatedIncomeList: List<IncomeItem>){
        var counter = 0.0
        for (incomeItem in updatedIncomeList){
            counter = counter + incomeItem.netAmount
        }
        totalIncomeText.setText("Total: $counter€")
    }

    private fun displayPieChart(updatedIncomeList: List<IncomeItem>){
        pieChartIncome.setTransparentCircleColor(Color.BLACK)

        var pieEntryList = ArrayList<PieEntry>()

        for (entry in updatedIncomeList){
            pieEntryList.add(PieEntry(entry.netAmount.toFloat(), entry.title))
        }

        var pieDataSet = PieDataSet(pieEntryList, "")
        pieDataSet.sliceSpace = 2f
        pieDataSet.colors = ColorTemplate.JOYFUL_COLORS.toList()
        var pieData = PieData(pieDataSet)
        pieChartIncome.data = pieData
        pieChartIncome.description = null
        pieChartIncome.legend.textColor = Color.WHITE
        pieChartIncome.setEntryLabelTextSize(10f)
        pieChartIncome.holeRadius = 45f
        pieChartIncome.transparentCircleRadius = 50f
        pieChartIncome.setHoleColor(Color.BLACK)
        pieChartIncome.setEntryLabelColor(Color.BLACK)
        pieChartIncome.animate()
        pieChartIncome.invalidate();
        pieChartIncome.refreshDrawableState();
    }

}