package com.markus.contactsapp.feature_contact.presentation.contacts

import com.markus.contactsapp.feature_contact.domain.model.Contact
import com.markus.contactsapp.feature_contact.domain.util.ContactOrder

sealed class ContactsEvent {
    data class Order(val contactOrder: ContactOrder) : ContactsEvent()
    data class DeleteContact(val contact: Contact) : ContactsEvent()
    data object ToggleOrderSection : ContactsEvent()
}
