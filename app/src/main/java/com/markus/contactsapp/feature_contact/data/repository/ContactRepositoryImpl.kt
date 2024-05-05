package com.markus.contactsapp.feature_contact.data.repository

import com.markus.contactsapp.feature_contact.data.data_source.ContactDao
import com.markus.contactsapp.feature_contact.domain.model.Contact
import com.markus.contactsapp.feature_contact.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow

class ContactRepositoryImpl(
    private val dao: ContactDao
) : ContactRepository {
    override fun getContacts(): Flow<List<Contact>> {
        return dao.getContacts()
    }

    override suspend fun getContactById(id: Int): Contact? {
        return dao.getContactById(id)
    }

    override suspend fun insertContact(contact: Contact) {
        return dao.insertContact(contact)
    }

    override suspend fun deleteContact(contact: Contact) {
        return dao.deleteContact(contact)
    }
}