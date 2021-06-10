package com.gmail.appverstas.cashflow.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gmail.appverstas.cashflow.data.expenses.ExpenseDao
import com.gmail.appverstas.cashflow.data.expenses.models.ExpenseItem
import com.gmail.appverstas.cashflow.data.income.IncomeDao
import com.gmail.appverstas.cashflow.data.income.models.IncomeItem
import com.gmail.appverstas.cashflow.data.saving.SavingsDao
import com.gmail.appverstas.cashflow.data.saving.models.SavingItem

@Database(entities = [IncomeItem::class, ExpenseItem::class, SavingItem::class], version = 3, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class CashflowDatabase: RoomDatabase() {

    abstract fun incomeDao(): IncomeDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun savingsDao(): SavingsDao


    companion object{

        @Volatile
        private var INSTANCE: CashflowDatabase? = null

        fun getDatabase(context: Context): CashflowDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        CashflowDatabase::class.java,
                        "cashflow_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}