package com.kumaravadivel.tractorautoparts.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kumaravadivel.tractorautoparts.data.models.AutoPart
import com.kumaravadivel.tractorautoparts.utils.PartsInitializer
import kotlinx.coroutines.launch
import kotlinx.coroutines.cancel
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartsAdminScreen(
    onBack: () -> Unit
) {
    var parts by remember { mutableStateOf<List<AutoPart>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var isAddingParts by remember { mutableStateOf(false) }
    var statusMessage by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    // Cleanup on dispose
    DisposableEffect(Unit) {
        onDispose {
            // Cancel any ongoing coroutines when screen is destroyed
            coroutineScope.cancel()
        }
    }

    // Load current parts count with real-time listener
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            // Initial load
            val count = PartsInitializer.checkPartsCount()
            statusMessage = "Current parts in database: $count"
        }
        
        // Set up real-time listener for changes
        try {
            val firestore = FirebaseFirestore.getInstance()
            firestore.collection("auto_parts")
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        statusMessage = "Error listening to changes: ${error.message}"
                        Log.e("PartsAdmin", "Snapshot listener error", error)
                        return@addSnapshotListener
                    }
                    
                    val count = snapshot?.size() ?: 0
                    statusMessage = "Current parts in database: $count (Real-time)"
                    Log.d("PartsAdmin", "Real-time update: $count parts")
                }
        } catch (e: Exception) {
            statusMessage = "Failed to set up real-time listener: ${e.message}"
            Log.e("PartsAdmin", "Real-time listener setup failed", e)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "Parts Admin",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Status Message
        Text(
            text = statusMessage,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Add Sample Parts Button
        Button(
            onClick = {
                coroutineScope.launch {
                    isAddingParts = true
                    try {
                        val result = PartsInitializer.addSampleParts()
                        result.onSuccess { count ->
                            statusMessage = "Successfully added $count sample parts to Firestore!"
                            Log.d("PartsAdmin", "Added $count parts")
                        }.onFailure { error ->
                            statusMessage = "Failed to add parts: ${error.message}"
                            Log.e("PartsAdmin", "Failed to add parts", error)
                        }
                    } catch (e: Exception) {
                        statusMessage = "Error: ${e.message}"
                        Log.e("PartsAdmin", "Error adding parts", e)
                    }
                    isAddingParts = false
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isAddingParts
        ) {
            if (isAddingParts) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Adding Parts...")
            } else {
                Icon(Icons.Default.Add, contentDescription = "Add")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add Sample Parts")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Instructions
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Instructions:",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text("1. Click 'Add Sample Parts' to populate Firestore")
                Text("2. Parts will be visible in the app")
                Text("3. Check Firebase Console → Firestore → auto_parts")
                Text("4. Go back and browse parts in the app")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Refresh button
        OutlinedButton(
            onClick = {
                coroutineScope.launch {
                    val count = PartsInitializer.checkPartsCount()
                    statusMessage = "Current parts in database: $count (Manual refresh)"
                    Log.d("PartsAdmin", "Manual refresh: $count parts")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Refresh Parts Count")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Force Sync button
        Button(
            onClick = {
                coroutineScope.launch {
                    try {
                        val firestore = FirebaseFirestore.getInstance()
                        val snapshot = firestore.collection("auto_parts").get().await()
                        val count = snapshot.size()
                        statusMessage = "Force synced: $count parts from Firebase"
                        Log.d("PartsAdmin", "Force sync completed: $count parts")
                    } catch (e: Exception) {
                        statusMessage = "Sync failed: ${e.message}"
                        Log.e("PartsAdmin", "Force sync error", e)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Force Sync with Firebase")
        }
    }
}
