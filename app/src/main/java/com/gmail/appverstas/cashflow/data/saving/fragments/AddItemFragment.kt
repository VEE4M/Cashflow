package com.gmail.appverstas.cashflow.data.saving.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gmail.appverstas.cashflow.R
import com.gmail.appverstas.cashflow.data.SharedMethods
import com.gmail.appverstas.cashflow.data.saving.SavingViewModel
import com.gmail.appverstas.cashflow.data.saving.models.SavingItem
import kotlinx.android.synthetic.main.fragment_saving_and_investing_add.*

class AddItemFragment : Fragment() {


    val savingViewModel: SavingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_saving_and_investing_add, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add_fragments, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.app_bar_save -> saveToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveToDb() {
        var title = et_saving_investing_title.text.toString()
        var amount = et_saving_investing_amount.text.toString().toDouble()
        var validation = SharedMethods.verifyDataFormat(title, amount)
        if(validation){
            var newItem = SavingItem(
                    0,
                    title,
                    amount,
                    SavingItem.getSavingType(radio_btn_saving.isChecked)
            )
            savingViewModel.insertSaving(newItem)
            Toast.makeText(requireContext(), "Saved!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addItemFragment_to_savingAndInvestingFragment)
        }else{
            Toast.makeText(requireContext(), "Please fill all the fields!", Toast.LENGTH_SHORT).show()
        }
    }

}