package com.gmail.appverstas.cashflow.data.saving.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.gmail.appverstas.cashflow.R
import com.gmail.appverstas.cashflow.data.saving.SavingListAdapter
import com.gmail.appverstas.cashflow.data.saving.SavingViewModel
import com.gmail.appverstas.cashflow.data.saving.models.SavingItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_saving_and_investing.*


class SavingAndInvestingFragment : Fragment() {

    val savingViewModel: SavingViewModel by viewModels()

    val adapter: SavingListAdapter by lazy { SavingListAdapter() }

    lateinit private var pieChartSavingsAndInvestments: PieChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_saving_and_investing, container, false)
        val fab = view.findViewById<FloatingActionButton>(R.id.fab_add_saving_item)
        val recyclerView = view.findViewById<RecyclerView>(R.id.savingsRecyclerView)
        pieChartSavingsAndInvestments = view.findViewById(R.id.pieChart_savings)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        savingViewModel.getAllData.observe(viewLifecycleOwner, Observer { updatedSavingsList ->
            adapter.updateData((updatedSavingsList.sortedBy {savingItem -> savingItem.type }).reversed())
            updateTotalText(updatedSavingsList)
            displayPieChart(updatedSavingsList)
        })

        fab.setOnClickListener {
            findNavController().navigate(R.id.action_savingAndInvestingFragment_to_addItemFragment)
        }

        return view
    }

    private fun updateTotalText(updatedSavingsList: List<SavingItem>){
        var counter = 0.0
        for(entry in updatedSavingsList){
            counter = counter + entry.amount
        }
        text_total_savings_and_investments.setText("Total: $counter€")
    }


    private fun displayPieChart(updatedSavingsList: List<SavingItem>){
        pieChartSavingsAndInvestments.setTransparentCircleColor(Color.BLACK)

        var pieEntryList = ArrayList<PieEntry>()

        for (entry in updatedSavingsList){
            pieEntryList.add(PieEntry(entry.amount.toFloat(), entry.title))
        }

        var pieDataSet = PieDataSet(pieEntryList, "")
        pieDataSet.sliceSpace = 2f
        pieDataSet.colors = ColorTemplate.JOYFUL_COLORS.toList()
        var pieData = PieData(pieDataSet)
        pieChartSavingsAndInvestments.data = pieData
        pieChartSavingsAndInvestments.description = null
        pieChartSavingsAndInvestments.legend.textColor = Color.WHITE
        pieChartSavingsAndInvestments.setEntryLabelTextSize(10f)
        pieChartSavingsAndInvestments.holeRadius = 45f
        pieChartSavingsAndInvestments.transparentCircleRadius = 50f
        pieChartSavingsAndInvestments.setHoleColor(Color.BLACK)
        pieChartSavingsAndInvestments.setEntryLabelColor(Color.BLACK)
        pieChartSavingsAndInvestments.animate()
        pieChartSavingsAndInvestments.invalidate();
        pieChartSavingsAndInvestments.refreshDrawableState();
    }

}