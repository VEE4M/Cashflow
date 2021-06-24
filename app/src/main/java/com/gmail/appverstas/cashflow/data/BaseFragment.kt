package com.gmail.appverstas.cashflow.data

import androidx.fragment.app.Fragment


abstract class BaseFragment(layoutId: Int): Fragment(layoutId) {

    fun verifyDataFormat(name: String, netAmount: Double): Boolean {
        return (name.isNotEmpty() && netAmount > 0)
    }

}