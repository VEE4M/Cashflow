package com.gmail.appverstas.cashflow.data.expenses

import com.gmail.appverstas.cashflow.data.expenses.models.ExpenseItem

/**
 *Veli-Matti Tikkanen, 26.1.2021
 */
class ExpenseRepository( private val expenseDao: ExpenseDao) {

    var getAllExpenseData = expenseDao.getAllData()

    suspend fun insertExpense(expenseItem: ExpenseItem){
        expenseDao.insertExpense(expenseItem)
    }

    suspend fun updateExpense(expenseItem: ExpenseItem){
        expenseDao.updateExpense(expenseItem)
    }

    suspend fun deleteExpense(expenseItem: ExpenseItem){
        expenseDao.deleteExpense(expenseItem)
    }

}