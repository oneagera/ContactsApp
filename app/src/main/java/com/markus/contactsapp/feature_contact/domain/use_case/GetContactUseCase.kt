package com.markus.contactsapp.feature_contact.domain.use_case

import com.markus.contactsapp.feature_contact.domain.model.Contact
import com.markus.contactsapp.feature_contact.domain.repository.ContactRepository

class GetContactUseCase(
    private val repository: ContactRepository
) {
    suspend operator fun invoke(id: Int): Contact? {
        return repository.getContactById(id)
    }
}