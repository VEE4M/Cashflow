package com.gmail.appverstas.cashflow.data

import androidx.room.TypeConverter
import com.gmail.appverstas.cashflow.data.expenses.models.ExpenseType
import com.gmail.appverstas.cashflow.data.income.models.IncomeType

class TypeConverter {

    @TypeConverter
    fun toIncomeType(incomeType: String): IncomeType {
        return IncomeType.valueOf(incomeType)
    }

    @TypeConverter
    fun fromIncomeType(incomeType: IncomeType): String{
        return incomeType.name
    }

    @TypeConverter
    fun toExpenseType(expenseType: String): ExpenseType{
        return ExpenseType.valueOf(expenseType)
    }

    @TypeConverter
    fun fromExpenseType(expenseType: ExpenseType): String{
        return expenseType.name
    }

}