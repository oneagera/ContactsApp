package com.markus.contactsapp.feature_contact.domain.use_case

data class ContactUseCases(
    val getContacts: GetContactsUseCase,
    val getContact: GetContactUseCase,
    val addContact: AddContactUseCase,
    val deleteContact: DeleteContactUseCase
)