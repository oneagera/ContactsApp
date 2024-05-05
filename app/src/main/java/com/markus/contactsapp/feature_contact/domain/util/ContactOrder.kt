package com.markus.contactsapp.feature_contact.domain.util

sealed class ContactOrder(val orderType: OrderType) {
    class Name(orderType: OrderType) : ContactOrder(orderType)

    fun copy(orderType: OrderType): ContactOrder {
        return when (this) {
            is Name -> Name(orderType)
        }
    }
}