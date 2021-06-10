package com.gmail.appverstas.cashflow.data.saving

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gmail.appverstas.cashflow.data.saving.models.SavingItem


@Dao
interface SavingsDao {


    @Query ("SELECT * FROM saving_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<SavingItem>>

    @Insert
    suspend fun insertSavingItem(savingItem: SavingItem)

    @Update
    suspend fun updateSavingItem(savingItem: SavingItem)

    @Delete
    suspend fun deleteSavingItem(savingItem: SavingItem)

}