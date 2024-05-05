package com.markus.contactsapp.feature_contact.presentation.contacts

import com.markus.contactsapp.feature_contact.domain.model.Contact
import com.markus.contactsapp.feature_contact.domain.util.ContactOrder
import com.markus.contactsapp.feature_contact.domain.util.OrderType

data class ContactsState(
    val contacts: List<Contact> = emptyList(),
    val contactOrder: ContactOrder = ContactOrder.Name(OrderType.Ascending),
    val isOrderSectionVisible: Boolean = false
)

