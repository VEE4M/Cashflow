package com.gmail.appverstas.cashflow.data.income.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.list_row_income.view.*

/**
 *Veli-Matti Tikkanen, 19.1.2021
 */
@Entity(tableName = "income_table")
@Parcelize
class IncomeItem(
        @PrimaryKey(autoGenerate = true)
        var id: Int,
        var title: String,
        var type: IncomeType,
        var netAmount: Double): Parcelable {


    fun getIncomeTypeAsInt(): Int{
        return when(type){
            IncomeType.CAPITAL_GAINS -> 0
            IncomeType.JOB -> 1
        }
    }

    fun getIncomeTypeAsString(): String{
        return when(type){
            IncomeType.CAPITAL_GAINS -> "Capital gains"
            IncomeType.JOB -> "Job"
        }
    }


    companion object {

        fun parseIncomeType(incomeType: String): IncomeType {
            return when(incomeType){
                "Capital gains" -> IncomeType.CAPITAL_GAINS
                "Job" -> IncomeType.JOB
                else -> IncomeType.CAPITAL_GAINS
            }
        }

    }

}