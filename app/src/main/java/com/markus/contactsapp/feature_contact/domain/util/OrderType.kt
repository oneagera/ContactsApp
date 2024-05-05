package com.markus.contactsapp.feature_contact.domain.util

sealed class OrderType {
    data object Ascending: OrderType()
    data object Descending: OrderType()
}