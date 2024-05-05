package com.markus.contactsapp.feature_contact.domain.use_case

import com.markus.contactsapp.feature_contact.domain.model.Contact
import com.markus.contactsapp.feature_contact.domain.repository.ContactRepository
import com.markus.contactsapp.feature_contact.domain.util.ContactOrder
import com.markus.contactsapp.feature_contact.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetContactsUseCase(
    private val repository: ContactRepository
) {
    operator fun invoke(
        contactOrder: ContactOrder = ContactOrder.Name(OrderType.Ascending)
    ): Flow<List<Contact>> {
        return repository.getContacts().map { contacts ->
            when(contactOrder.orderType) {
                is OrderType.Ascending -> {
                    when(contactOrder) {
                        is ContactOrder.Name -> contacts.sortedBy { it.name.lowercase() }
                    }
                }
                is OrderType.Descending -> {
                    when(contactOrder) {
                        is ContactOrder.Name -> contacts.sortedByDescending { it.name.lowercase() }
                    }
                }
            }
        }
    }
}