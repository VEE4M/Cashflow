package com.gmail.appverstas.cashflow.data.income

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gmail.appverstas.cashflow.data.income.models.IncomeItem

/**
 *Veli-Matti Tikkanen, 20.1.2021
 */
@Dao
interface IncomeDao {

    @Query("SELECT * FROM income_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<IncomeItem>>

    @Insert
    suspend fun insertIncome(incomeItem: IncomeItem)

    @Delete
    suspend fun deleteIncome(incomeItem: IncomeItem)

    @Update
    suspend fun updateIncome(incomeItem: IncomeItem)


}