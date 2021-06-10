package com.gmail.appverstas.cashflow.data.expenses.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 *Veli-Matti Tikkanen, 25.1.2021
 */
@Entity(tableName = "expense_table")
@Parcelize
class ExpenseItem(
       @PrimaryKey(autoGenerate = true)
       val id: Int,
       var title: String,
       var type: ExpenseType,
       var amount: Double
): Parcelable {

       fun getExpenseTypeAsInt(): Int{
              return when(type){
                     ExpenseType.UTILITY_BILL -> 0
                     ExpenseType.DEBT -> 1
                     ExpenseType.RENT -> 2
                     ExpenseType.GROCERIES -> 3
                     ExpenseType.ENTERTAINMENT -> 4
                     ExpenseType.INSURANCE -> 5
                     ExpenseType.OTHER -> 6
              }
       }

       fun getExpenseTypeAsString(): String{
              return when(type){
                     ExpenseType.UTILITY_BILL -> "Utility Bill"
                     ExpenseType.DEBT -> "Debt"
                     ExpenseType.RENT -> "Rent"
                     ExpenseType.GROCERIES -> "Groceries"
                     ExpenseType.ENTERTAINMENT -> "Entertainment"
                     ExpenseType.INSURANCE -> "Insurance"
                     ExpenseType.OTHER -> "Other"
              }
       }

       companion object{

              fun parseExpenseType(expenseType: String): ExpenseType{
                     return when(expenseType){
                            "Debt" -> ExpenseType.DEBT
                            "Rent" -> ExpenseType.RENT
                            "Groceries" -> ExpenseType.GROCERIES
                            "Entertainment" -> ExpenseType.ENTERTAINMENT
                            "Insurance" -> ExpenseType.INSURANCE
                            "Other" -> ExpenseType.OTHER
                            else -> ExpenseType.UTILITY_BILL
                     }
              }

       }

}