package com.markus.contactsapp.feature_contact.domain.use_case

import com.markus.contactsapp.feature_contact.domain.model.Contact
import com.markus.contactsapp.feature_contact.domain.repository.ContactRepository

class AddContactUseCase(
    private val repository: ContactRepository
) {
    suspend operator fun invoke(contact: Contact) {
        if (
            contact.firstName.isBlank() &&
            contact.lastName.isBlank() &&
            contact.phone1.isBlank() &&
            contact.phone2.isBlank() &&
            contact.email.isBlank()
        ) {
            repository.deleteContact(contact)
        } else {
            repository.insertContact(contact)
        }
    }
}