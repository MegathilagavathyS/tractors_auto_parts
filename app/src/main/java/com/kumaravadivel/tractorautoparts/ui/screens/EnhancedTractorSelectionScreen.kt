package com.kumaravadivel.tractorautoparts.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.kumaravadivel.tractorautoparts.data.models.TractorBrand
import com.kumaravadivel.tractorautoparts.data.models.TractorModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnhancedTractorSelectionScreenNew(
    onContinue: (TractorBrand, TractorModel) -> Unit,
    onBack: () -> Unit
) {
    var selectedBrand by remember { mutableStateOf<TractorBrand?>(null) }
    var selectedModel by remember { mutableStateOf<TractorModel?>(null) }
    var brands by remember { mutableStateOf<List<TractorBrand>>(emptyList()) }
    var models by remember { mutableStateOf<List<TractorModel>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    
    val firestoreManager = EnhancedFirestoreManager()
    val coroutineScope = rememberCoroutineScope()

    // Debug logging
    LaunchedEffect(selectedBrand, selectedModel) {
        android.util.Log.d("TractorSelection", "Selected Brand: ${selectedBrand?.name}, Selected Model: ${selectedModel?.name}")
    }

    // Load brands from Firebase
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            isLoading = true
            try {
                val result = firestoreManager.getTractorBrands()
                result.onSuccess { brandsList ->
                    brands = brandsList
                    errorMessage = ""
                    android.util.Log.d("TractorSelection", "✅ Loaded ${brandsList.size} brands")
                }.onFailure { error ->
                    errorMessage = "Failed to load brands: ${error.message}"
                    android.util.Log.e("TractorSelection", "❌ Error loading brands: ${error.message}")
                }
            } catch (e: Exception) {
                errorMessage = "Error loading brands: ${e.message}"
                android.util.Log.e("TractorSelection", "❌ Exception loading brands: ${e.message}")
            }
            isLoading = false
        }
    }

    // Load models when brand is selected
    LaunchedEffect(selectedBrand) {
        selectedBrand?.let { brand ->
            coroutineScope.launch {
                isLoading = true
                try {
                    val result = firestoreManager.getModelsForBrand(brand.id)
                    result.onSuccess { modelsList ->
                        models = modelsList
                        selectedModel = null // Reset model selection
                        android.util.Log.d("TractorSelection", "✅ Loaded ${modelsList.size} models for ${brand.name}")
                    }.onFailure { error ->
                        errorMessage = "Failed to load models: ${error.message}"
                        android.util.Log.e("TractorSelection", "❌ Error loading models: ${error.message}")
                    }
                } catch (e: Exception) {
                    errorMessage = "Error loading models: ${e.message}"
                    android.util.Log.e("TractorSelection", "❌ Exception loading models: ${e.message}")
                }
                isLoading = false
            }
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
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
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
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF4CAF50)
                        )
                    }
                    
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Select Your Tractor",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4CAF50)
                        )
                        if (selectedBrand != null) {
                            Text(
                                text = "${selectedBrand?.name} → ${selectedModel?.name ?: "Select Model"}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.width(48.dp))
                }
            }
        }

        // Content Area - Reserve space for Continue button
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(48.dp),
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = if (selectedBrand == null) "Loading tractor brands..." else "Loading models...",
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
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            } else if (selectedBrand == null) {
                // Brand Selection
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 80.dp) // Reserve space for Continue button
                ) {
                    items(brands) { brand ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(4.dp, RoundedCornerShape(12.dp)),
                            colors = CardDefaults.cardColors(
                                containerColor = if (selectedBrand?.id == brand.id) Color(0xFF4CAF50) else Color.White
                            ),
                            shape = RoundedCornerShape(12.dp),
                            onClick = { selectedBrand = brand }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = brand.name,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (selectedBrand?.id == brand.id) Color.White else Color(0xFF212121)
                                    )
                                    Text(
                                        text = "Tractor brand",
                                        fontSize = 14.sp,
                                        color = if (selectedBrand?.id == brand.id) Color.White.copy(alpha = 0.8f) else Color.Gray
                                    )
                                }
                                
                                Icon(
                                    if (selectedBrand?.id == brand.id) Icons.Default.CheckCircle else Icons.Default.Circle,
                                    contentDescription = null,
                                    tint = if (selectedBrand?.id == brand.id) Color.White else Color(0xFF4CAF50),
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            } else {
                // Model Selection
                if (models.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No models available for ${selectedBrand?.name}",
                            color = Color.White,
                            fontSize = 16.sp,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(bottom = 80.dp) // Reserve space for Continue button
                    ) {
                        items(models) { model ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .shadow(4.dp, RoundedCornerShape(12.dp)),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (selectedModel?.id == model.id) Color(0xFF4CAF50) else Color.White
                                ),
                                shape = RoundedCornerShape(12.dp),
                                onClick = { selectedModel = model }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = model.name,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (selectedModel?.id == model.id) Color.White else Color(0xFF212121)
                                        )
                                        Text(
                                            text = "Model",
                                            fontSize = 14.sp,
                                            color = if (selectedModel?.id == model.id) Color.White.copy(alpha = 0.8f) else Color.Gray
                                        )
                                    }
                                    
                                    Icon(
                                        if (selectedModel?.id == model.id) Icons.Default.CheckCircle else Icons.Default.Circle,
                                        contentDescription = null,
                                        tint = if (selectedModel?.id == model.id) Color.White else Color(0xFF4CAF50),
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Continue Button - Fixed at bottom
        if (selectedBrand != null && selectedModel != null) {
            android.util.Log.d("TractorSelection", "✅ Continue button should be visible!")
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Button(
                    onClick = { 
                        android.util.Log.d("TractorSelection", "🚀 Continue button clicked!")
                        selectedBrand?.let { brand ->
                            selectedModel?.let { model ->
                                onContinue(brand, model)
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    enabled = selectedBrand != null && selectedModel != null,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Text(
                        text = "Continue to Parts",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
