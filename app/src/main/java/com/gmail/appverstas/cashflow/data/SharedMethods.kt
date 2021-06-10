package com.gmail.appverstas.cashflow.data


class SharedMethods {


    companion object {

        fun verifyDataFormat(name: String, netAmount: Double): Boolean {
            return (name.length > 0 && netAmount > 0)
        }

    }
}