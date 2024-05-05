package com.markus.contactsapp.feature_contact.presentation.contacts

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.markus.contactsapp.feature_contact.presentation.contacts.components.ContactItem
import com.markus.contactsapp.feature_contact.presentation.util.Screen.AddEditContactScreen

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ContactsScreen(
    navController: NavController,
    viewModel: ContactsViewModel = hiltViewModel(),
    modifier: Modifier = Modifier.padding(16.dp),
) {
    //origin
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }


    val searchText by viewModel.searchText.collectAsState()
    val contacts by viewModel.contacts.collectAsState()
    // val contacts by viewModel1.contacts.collectAsState()
    val history by viewModel.history.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(AddEditContactScreen.route)
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add contact")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            SearchBar(
                modifier =
                if (active) {
                    Modifier.fillMaxWidth()
                } else {
                    Modifier
                        .fillMaxWidth()
                        .then(modifier)
                },
                query = searchText,
                onQueryChange = viewModel::onSearchTextChange,
                onSearch = {
                    viewModel.onSearch(searchText)
                    active = false
                },
                active = active,
                onActiveChange = { active = it },
                placeholder = { Text(text = "Search Contacts") },
                leadingIcon = {
                    if (active) {
                        Icon(
                            modifier = Modifier
                                .clickable {
                                    if (searchText.isNotEmpty()) {
                                        viewModel.clearSearchText()
                                    } else {
                                        active = false
                                    }
                                },
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Exit Search"
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon"
                        )
                    }
                },
                trailingIcon = {
                    if (!active) {
                        Icon(
                            modifier = Modifier
                                .clickable { viewModel.clearSearchText() },
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More Vertical"
                        )
                    }
                }
            ) {
                LazyColumn(
                    modifier.fillMaxWidth()
                ) {
                    /*if (searchText.isBlank()) {
                        items(history) { searchHistory ->
                            Row(modifier = Modifier.padding(all = 14.dp)) {
                                Icon(
                                    modifier = Modifier.padding(end = 10.dp),
                                    imageVector = Icons.Default.History,
                                    contentDescription = "History Icon"
                                )
                                Text(text = searchHistory)
                            }
                        }
                    } else {*/
                    items(contacts) { contact ->
                        Text(
                            text = contact.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp))
                    }
                }
            }
            //}
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(modifier),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "All Contacts")
                    Row(//modifier = Modifier.align(ali),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Label,
                            contentDescription = "label"
                        )
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = "filter"
                        )
                    }
                }
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.contacts) { contact ->
                        ContactItem(
                            contact = contact,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(
                                        AddEditContactScreen.route +
                                                "?contactId=${contact.id}"
                                    )
                                }
                        )
                    }
                }

            }
        }
    }
}