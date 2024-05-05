package com.markus.contactsapp.feature_contact.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.markus.contactsapp.feature_contact.presentation.add_edit_contact.AddEditContactScreen
import com.markus.contactsapp.feature_contact.presentation.contacts.ContactsScreen
import com.markus.contactsapp.feature_contact.presentation.util.Screen.AddEditContactScreen
import com.markus.contactsapp.feature_contact.presentation.util.Screen.ContactScreen
import com.markus.contactsapp.ui.theme.ContactsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = ContactScreen.route
                    ) {
                        composable(route = ContactScreen.route) {
                            ContactsScreen(navController = navController)
                        }
                        composable(
                            route = AddEditContactScreen.route +
                                    "?contactId={contactId}&contactLabel={contactLabel}",
                            arguments = listOf(
                                navArgument(
                                    name = "contactId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "contactLabel"
                                ) {
                                    type = NavType.StringType
                                    defaultValue = ""
                                }
                            )
                        ) {
                            val label = it.arguments?.getString("contactLabel") ?: ""
                            AddEditContactScreen(
                                contactLabel = label,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}