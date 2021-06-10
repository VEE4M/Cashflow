package com.gmail.appverstas.cashflow.data.expenses

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.gmail.appverstas.cashflow.data.CashflowDatabase
import com.gmail.appverstas.cashflow.data.expenses.models.ExpenseItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *Veli-Matti Tikkanen, 26.1.2021
 */
class ExpenseViewModel(application: Application): AndroidViewModel(application) {

    private val expenseDao = CashflowDatabase.getDatabase(application).expenseDao()
    private val repository: ExpenseRepository

    val getAllData: LiveData<List<ExpenseItem>>

    init {
        repository = ExpenseRepository(expenseDao)
        getAllData = repository.getAllExpenseData
    }

    fun insertExpense(expenseItem: ExpenseItem){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertExpense(expenseItem)
        }
    }

    fun updateExpense(expenseItem: ExpenseItem){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateExpense(expenseItem)
        }
    }

    fun deleteExpense(expenseItem: ExpenseItem){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteExpense(expenseItem)
        }
    }


}