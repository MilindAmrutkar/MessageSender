package com.example.messagesender.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.messagesender.data.models.Contact
import com.example.messagesender.ui.MainViewModel


@Composable
fun TabScreen(viewModel: MainViewModel, contacts: List<Contact>) {
    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Contacts", "Messages")

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            TabRow(selectedTabIndex = tabIndex){
                tabs.forEachIndexed { index, title ->
                    Tab(selected = tabIndex == index, onClick = { tabIndex = index }, text = { Text(text = title) })
                }
            }

            when (tabIndex) {
                0 -> ContactsTab(contacts = contacts)
                1 -> MessageTab(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun ContactsTab(contacts: List<Contact>) {
    LazyColumn {
        items(contacts) { contact ->
            Text(text = contact.firstName, Modifier.clickable {
//                ContactDetailScreen(contact = contact)
            })
        }
    }
}

@Composable
fun MessageTab(viewModel: MainViewModel) {
    LazyColumn {
        items(viewModel.messages.value) { message ->
            Text("${message.content} (sent at ${message.timeStamp})")
        }
    }
}

@Composable
fun ContactDetailScreen(contact: Contact) {
    Column {
        Text(text = "Name: ${contact.firstName}")
        Text(text = "Phone: ${contact.phoneNumber}")
        Button(onClick = {  }) {
            Text(text = "Send Message")
        }
    }
}