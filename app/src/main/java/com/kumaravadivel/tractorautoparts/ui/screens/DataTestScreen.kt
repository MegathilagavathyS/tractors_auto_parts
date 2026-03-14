package com.kumaravadivel.tractorautoparts.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kumaravadivel.tractorautoparts.data.EnhancedFirestoreManager
import com.kumaravadivel.tractorautoparts.data.TractorDataInitializer
import com.kumaravadivel.tractorautoparts.data.models.TractorBrand
import com.kumaravadivel.tractorautoparts.data.models.TractorModel
import com.kumaravadivel.tractorautoparts.data.models.AutoPart
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataTestScreen(
    onBack: () -> Unit
) {
    var isInitializing by remember { mutableStateOf(false) }
    var initializationStatus by remember { mutableStateOf("") }
    var brands by remember { mutableStateOf<List<TractorBrand>>(emptyList()) }
    var models by remember { mutableStateOf<List<TractorModel>>(emptyList()) }
    var parts by remember { mutableStateOf<List<AutoPart>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    
    val firestoreManager = remember { EnhancedFirestoreManager() }
    val coroutineScope = rememberCoroutineScope()

    // Load all data to display
    fun loadAllData() {
        coroutineScope.launch {
            isLoading = true
            try {
                // Load brands
                val brandsResult = firestoreManager.getTractorBrands()
                brandsResult.onSuccess { brandsList ->
                    brands = brandsList
                    initializationStatus += "\n📊 Loaded ${brandsList.size} brands"
                }.onFailure { error ->
                    errorMessage += "\nBrand error: ${error.message}"
                }

                // Load all parts
                val partsResult = firestoreManager.getAllAutoParts()
                partsResult.onSuccess { partsList ->
                    parts = partsList
                    initializationStatus += "\n� Loaded ${partsList.size} parts"
                }.onFailure { error ->
                    errorMessage += "\nParts error: ${error.message}"
                }

            } catch (e: Exception) {
                errorMessage = "Error loading data: ${e.message}"
            }
            isLoading = false
        }
    }

    // Initialize all data
    fun initializeAllData() {
        coroutineScope.launch {
            isInitializing = true
            initializationStatus = "Initializing comprehensive tractor data..."
            
            try {
                val initializer = TractorDataInitializer()
                initializer.initializeTractorData()
                initializationStatus = "✅ Data initialized successfully!"
                
                // Load data to show results after initialization
                loadAllData()
                
            } catch (e: Exception) {
                initializationStatus = "❌ Error: ${e.message}"
            }
            isInitializing = false
        }
    }

    // Load data on screen start
    LaunchedEffect(Unit) {
        loadAllData()
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
                    text = "Data Test Screen",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121)
                )
                
                Spacer(modifier = Modifier.width(48.dp))
            }
        }

        // Initialize Button
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .shadow(4.dp, RoundedCornerShape(12.dp)),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Button(
                    onClick = { initializeAllData() },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isInitializing,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50),
                        contentColor = Color.White
                    )
                ) {
                    if (isInitializing) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Initializing...")
                    } else {
                        Icon(Icons.Default.CloudUpload, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Initialize All Data")
                    }
                }
                
                if (initializationStatus.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = initializationStatus,
                        color = if (initializationStatus.contains("✅")) Color(0xFF4CAF50) else Color.Red,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Data Display
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(48.dp),
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Loading comprehensive data...",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        } else if (errorMessage.isNotEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFEBEE)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.Error,
                            contentDescription = "Error",
                            tint = Color(0xFFF44336),
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = errorMessage,
                            color = Color(0xFFF44336),
                            fontSize = 14.sp
                        )
                    }
                }
            }
        } else {
            // Show Data Summary
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                // Brands Summary
                item {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "🚜 Tractor Brands (${brands.size})",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF4CAF50)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            brands.take(5).forEach { brand ->
                                Text(
                                    text = "• ${brand.name}",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }
                            if (brands.size > 5) {
                                Text(
                                    text = "... and ${brands.size - 5} more",
                                    fontSize = 14.sp,
                                    color = Color.Gray,
                                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                                )
                            }
                        }
                    }
                }

                // Parts Summary
                item {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "🔧 Spare Parts (${parts.size})",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF4CAF50)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            // Group parts by brand
                            val partsByBrand = parts.groupBy { it.brandId }
                            partsByBrand.entries.take(5).forEach { (brand, brandParts) ->
                                Text(
                                    text = "• $brand: ${brandParts.size} parts",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }
                            if (partsByBrand.size > 5) {
                                Text(
                                    text = "... and ${partsByBrand.size - 5} more brands",
                                    fontSize = 14.sp,
                                    color = Color.Gray,
                                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                                )
                            }
                        }
                    }
                }

                // Sample Parts
                item {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "📋 Sample Parts",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF4CAF50)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            parts.take(10).forEach { part ->
                                Text(
                                    text = "• ${part.name} (${part.brandId} ${part.compatibleModels.firstOrNull()})",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                            if (parts.size > 10) {
                                Text(
                                    text = "... and ${parts.size - 10} more parts",
                                    fontSize = 12.sp,
                                    color = Color.Gray,
                                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
