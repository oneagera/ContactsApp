package com.markus.contactsapp.feature_contact.presentation.contacts

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markus.contactsapp.feature_contact.domain.use_case.ContactUseCases
import com.markus.contactsapp.feature_contact.domain.util.ContactOrder
import com.markus.contactsapp.feature_contact.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val contactUseCases: ContactUseCases
) : ViewModel() {

    private val _state = mutableStateOf(ContactsState())
    val state: State<ContactsState> = _state
    private var getContactsJob: Job? = null

    init {
        getContacts(ContactOrder.Name(OrderType.Ascending))
    }

    fun onEvent(event: ContactsEvent) {
        when (event) {
            is ContactsEvent.Order -> {
                if (state.value.contactOrder::class == event.contactOrder::class &&
                    state.value.contactOrder.orderType == event.contactOrder.orderType
                ) {
                    return
                }
                getContacts(event.contactOrder)
            }

            is ContactsEvent.DeleteContact -> {
                viewModelScope.launch {
                    contactUseCases.deleteContact(event.contact)
                }
            }

            is ContactsEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getContacts(contactOrder: ContactOrder) {
        getContactsJob?.cancel()
        getContactsJob = contactUseCases.getContacts(contactOrder)
            .onEach { contacts ->
                _state.value = state.value.copy(
                    contacts = contacts,
                    contactOrder = contactOrder
                )
            }
            .launchIn(viewModelScope)
    }

    val allContacts = state.value.contacts

    private val _history = MutableStateFlow(listOf(""))
    val history = _history.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _contacts = MutableStateFlow(allContacts)

    val contacts = searchText
        .combine(_contacts) { text, contacts ->
            if (text.isBlank()) {
                contacts
            } else {
                contacts.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _contacts.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun clearSearchText(text: String = "") {
        _searchText.value = text
    }

    fun onSearch(text: String) {
        _history.value += text
    }
}
