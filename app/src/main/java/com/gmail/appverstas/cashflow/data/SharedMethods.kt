package com.gmail.appverstas.cashflow.data


class SharedMethods {

    companion object {

        fun verifyDataFormat(name: String, netAmount: Double): Boolean {
            return (name.isNotEmpty() && netAmount > 0)
        }

    }
}