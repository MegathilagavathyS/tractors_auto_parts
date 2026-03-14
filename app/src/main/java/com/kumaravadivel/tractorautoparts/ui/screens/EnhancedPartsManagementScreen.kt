package com.kumaravadivel.tractorautoparts.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kumaravadivel.tractorautoparts.data.models.AutoPart
import com.kumaravadivel.tractorautoparts.data.FirestoreManager
import kotlinx.coroutines.launch
import android.util.Log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnhancedPartsManagementScreen(
    onBack: () -> Unit,
    onTest: () -> Unit = {}
) {
    var parts by remember { mutableStateOf<List<AutoPart>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var statusMessage by remember { mutableStateOf("") }
    var showAddPartDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val firestoreManager = FirestoreManager()

    fun loadParts() {
        coroutineScope.launch {
            isLoading = true
            try {
                val result = firestoreManager.getAutoParts()
                result.onSuccess { partsList ->
                    parts = partsList
                    statusMessage = "Loaded ${partsList.size} parts"
                }.onFailure { error ->
                    statusMessage = "Failed to load parts: ${error.message}"
                }
            } catch (e: Exception) {
                statusMessage = "Error loading parts: ${e.message}"
            }
            isLoading = false
        }
    }

    fun deletePart(partId: String) {
        coroutineScope.launch {
            try {
                val result = firestoreManager.deleteAutoPart(partId)
                result.onSuccess {
                    statusMessage = "Part deleted successfully"
                    loadParts() // Refresh list
                }.onFailure { error ->
                    statusMessage = "Failed to delete part: ${error.message}"
                }
            } catch (e: Exception) {
                statusMessage = "Error deleting part: ${e.message}"
            }
        }
    }

    fun addPart(part: AutoPart) {
        coroutineScope.launch {
            isLoading = true
            try {
                val result = firestoreManager.addAutoPart(part)
                result.onSuccess {
                    statusMessage = "Part added successfully"
                    showAddPartDialog = false
                    loadParts() // Refresh list
                }.onFailure { error ->
                    statusMessage = "Failed to add part: ${error.message}"
                }
            } catch (e: Exception) {
                statusMessage = "Error adding part: ${e.message}"
            }
            isLoading = false
        }
    }

    // Load parts
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            loadParts()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF4CAF50),
                        Color(0xFF388E3C)
                    )
                )
            )
    ) {
        // Header
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .shadow(8.dp, RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF4CAF50)
                    )
                }
                
                Text(
                    text = "Parts Management",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121)
                )
                
                IconButton(onClick = onTest) {
                    Icon(
                        Icons.Default.CloudUpload,
                        contentDescription = "Test Data",
                        tint = Color(0xFF4CAF50)
                    )
                }
            }
                
                // Status Message
                if (statusMessage.isNotEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = if (statusMessage.contains("success")) 
                                Color(0xFFE8F5E8) else Color(0xFFFFEBEE)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                if (statusMessage.contains("success")) Icons.Default.CheckCircle 
                                else Icons.Default.Error,
                                contentDescription = "Status",
                                tint = if (statusMessage.contains("success")) Color(0xFF4CAF50) 
                                else Color(0xFFF44336),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = statusMessage,
                                color = if (statusMessage.contains("success")) Color(0xFF4CAF50) 
                                else Color(0xFFF44336),
                                fontSize = 14.sp,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                
                // Parts Count
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total Parts: ${parts.size}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4CAF50)
                    )
                    
                    Button(
                        onClick = { loadParts() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = "Refresh",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Refresh")
                    }
                }
            }
        }

        // Parts List
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = 4.dp
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(parts) { part ->
                    PartManagementCard(
                        part = part,
                        onEdit = { /* TODO: Implement edit functionality */ },
                        onDelete = { deletePart(part.id) }
                    )
                }
            }
        }

        // Add Part Dialog
        if (showAddPartDialog) {
            AddPartDialog(
                onDismiss = { showAddPartDialog = false },
                onAddPart = { part -> addPart(part) }
            )
        }
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PartManagementCard(
    part: AutoPart,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = part.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF212121)
                    )
                    Text(
                        text = part.description,
                        fontSize = 14.sp,
                        color = Color(0xFF757575),
                        maxLines = 2
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "₹${part.price}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4CAF50)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = if (part.isAvailable) "In Stock" else "Out of Stock",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (part.isAvailable) Color(0xFF4CAF50) else Color(0xFFF44336),
                            modifier = Modifier
                                .background(
                                    if (part.isAvailable) Color(0xFFE8F5E8) else Color(0xFFFFEBEE),
                                    RoundedCornerShape(4.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
                
                Row {
                    IconButton(
                        onClick = onEdit,
                        modifier = Modifier
                            .background(
                                Color(0xFF2196F3).copy(alpha = 0.1f),
                                RoundedCornerShape(8.dp)
                            )
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Color(0xFF2196F3),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier
                            .background(
                                Color(0xFFF44336).copy(alpha = 0.1f),
                                RoundedCornerShape(8.dp)
                            )
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color(0xFFF44336),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Additional Details
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Stock: ${part.stockQuantity}",
                    fontSize = 14.sp,
                    color = Color(0xFF757575)
                )
                Text(
                    text = "Brand: ${part.brandId}",
                    fontSize = 14.sp,
                    color = Color(0xFF757575)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddPartDialog(
    onDismiss: () -> Unit,
    onAddPart: (AutoPart) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var stockQuantity by remember { mutableStateOf("") }
    var availability by remember { mutableStateOf(true) }
    var brandId by remember { mutableStateOf("") }
    var compatibleModels by remember { mutableStateOf("") }

    fun createPart(): AutoPart {
        return AutoPart(
            id = System.currentTimeMillis().toString(),
            name = name,
            description = description,
            price = price.toDoubleOrNull() ?: 0.0,
            isAvailable = availability,
            stockQuantity = stockQuantity.toIntOrNull() ?: 0,
            brandId = brandId,
            compatibleModels = compatibleModels.split(",").map { it.trim() },
            imageUrl = ""
        )
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Add New Part",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50)
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Name Field
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Part Name") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF4CAF50),
                    unfocusedBorderColor = Color(0xFFE0E0E0)
                ),
                shape = RoundedCornerShape(8.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Description Field
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF4CAF50),
                    unfocusedBorderColor = Color(0xFFE0E0E0)
                ),
                shape = RoundedCornerShape(8.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Price Field
            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Price (₹)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF4CAF50),
                    unfocusedBorderColor = Color(0xFFE0E0E0)
                ),
                shape = RoundedCornerShape(8.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Stock Quantity Field
            OutlinedTextField(
                value = stockQuantity,
                onValueChange = { stockQuantity = it },
                label = { Text("Stock Quantity") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF4CAF50),
                    unfocusedBorderColor = Color(0xFFE0E0E0)
                ),
                shape = RoundedCornerShape(8.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Brand Field
            OutlinedTextField(
                value = brandId,
                onValueChange = { brandId = it },
                label = { Text("Brand ID") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF4CAF50),
                    unfocusedBorderColor = Color(0xFFE0E0E0)
                ),
                shape = RoundedCornerShape(8.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Compatible Models Field
            OutlinedTextField(
                value = compatibleModels,
                onValueChange = { compatibleModels = it },
                label = { Text("Compatible Models (comma separated)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF4CAF50),
                    unfocusedBorderColor = Color(0xFFE0E0E0)
                ),
                shape = RoundedCornerShape(8.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Availability Checkbox
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = availability,
                    onCheckedChange = { availability = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF4CAF50)
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Available",
                    fontSize = 16.sp,
                    color = Color(0xFF212121)
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF757575)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Cancel")
                }
                
                Button(
                    onClick = { 
                        if (name.isNotBlank() && description.isNotBlank() && price.isNotBlank() && stockQuantity.isNotBlank()) {
                            onAddPart(createPart())
                        }
                    },
                    modifier = Modifier.weight(1f),
                    enabled = name.isNotBlank() && description.isNotBlank() && price.isNotBlank() && stockQuantity.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Add Part")
                }
            }
        }
    }
}
