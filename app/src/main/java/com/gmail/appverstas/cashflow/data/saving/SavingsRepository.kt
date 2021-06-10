package com.gmail.appverstas.cashflow.data.saving

import com.gmail.appverstas.cashflow.data.saving.models.SavingItem


class SavingsRepository(private val savingsDao: SavingsDao) {

    val getAllSavingsData = savingsDao.getAllData()

    suspend fun insertSavingItem(savingItem: SavingItem){
        savingsDao.insertSavingItem(savingItem)
    }

    suspend fun updateSavingItem(savingItem: SavingItem){
        savingsDao.updateSavingItem(savingItem)
    }

    suspend fun deleteSavingItem(savingItem: SavingItem){
        savingsDao.deleteSavingItem(savingItem)
    }
}