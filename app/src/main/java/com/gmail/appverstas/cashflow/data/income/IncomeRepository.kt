package com.gmail.appverstas.cashflow.data.income

import androidx.lifecycle.LiveData
import com.gmail.appverstas.cashflow.data.income.models.IncomeItem

/**
 *Veli-Matti Tikkanen, 21.1.2021
 */
class IncomeRepository (private val incomeDao: IncomeDao) {

    val getAllIncomeData: LiveData<List<IncomeItem>> = incomeDao.getAllData()

    suspend fun insertIncome(incomeItem: IncomeItem){
        incomeDao.insertIncome(incomeItem)
    }

    suspend fun deleteIncome(incomeItem: IncomeItem){
        incomeDao.deleteIncome(incomeItem)
    }

    suspend fun updateIncome(incomeItem: IncomeItem){
        incomeDao.updateIncome(incomeItem)
    }

}