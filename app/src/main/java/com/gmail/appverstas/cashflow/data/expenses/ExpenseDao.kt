package com.gmail.appverstas.cashflow.data.expenses

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gmail.appverstas.cashflow.data.expenses.models.ExpenseItem

/**
 *Veli-Matti Tikkanen, 26.1.2021
 */

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM expense_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<ExpenseItem>>

    @Insert
    suspend fun insertExpense(expenseItem: ExpenseItem)

    @Update
    suspend fun updateExpense(expenseItem: ExpenseItem)

    @Delete
    suspend fun deleteExpense(expenseItem: ExpenseItem)


}