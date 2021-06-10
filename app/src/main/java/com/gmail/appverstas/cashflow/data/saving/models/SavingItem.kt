package com.gmail.appverstas.cashflow.data.saving.models

import android.content.Context
import android.os.Parcelable
import androidx.core.content.ContextCompat

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gmail.appverstas.cashflow.R
import kotlinx.android.parcel.Parcelize


/**
 *Veli-Matti Tikkanen, 7.2.2021
 */
@Entity(tableName = "saving_table")
@Parcelize
class SavingItem(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        var title: String,
        var amount: Double,
        var type: String
): Parcelable {

        fun getBackgroundColor(context: Context): Int{
                if(type.equals("Investment")){
                        return ContextCompat.getColor(context, R.color.yellow_investment)
                }
                return ContextCompat.getColor(context, R.color.blue_saving)
        }

        companion object{

                fun getSavingType(boolean: Boolean): String{
                        return when(boolean){
                                true -> "Saving"
                                false -> "Investment"
                        }
                }



        }


}