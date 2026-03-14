@file:OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class
)

package com.kumaravadivel.tractorautoparts.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kumaravadivel.tractorautoparts.R
import com.kumaravadivel.tractorautoparts.data.EnhancedFirestoreManager
import com.kumaravadivel.tractorautoparts.data.models.AutoPart
import java.text.NumberFormat
import java.util.*
import kotlinx.coroutines.launch

@Composable
fun AutoPartsListScreen(
    selectedBrandId: String = "",
    selectedModelId: String = "",
    onPartClick: (AutoPart) -> Unit,
    onBack: () -> Unit,
    onAddToCart: (AutoPart) -> Unit = {},
    onViewCart: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var parts by remember { mutableStateOf<List<AutoPart>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val firestoreManager = EnhancedFirestoreManager()
    val coroutineScope = rememberCoroutineScope()
    val cartManager = com.kumaravadivel.tractorautoparts.data.CartManager

    LaunchedEffect(selectedBrandId, selectedModelId) {
        coroutineScope.launch {
            isLoading = true
            try {
                val result = if (selectedBrandId.isNotEmpty() && selectedModelId.isNotEmpty()) {
                    // Load parts for specific brand and model
                    firestoreManager.getPartsForBrandAndModel(selectedBrandId, selectedModelId)
                } else {
                    // Load all parts
                    firestoreManager.getAllAutoParts()
                }
                result.onSuccess { partsList ->
                    parts = partsList
                    errorMessage = ""
                    android.util.Log.d("AutoPartsList", "✅ Loaded ${partsList.size} parts for brand: $selectedBrandId, model: $selectedModelId")
                }.onFailure { exception ->
                    errorMessage = "Failed to load parts: ${exception.message}"
                    android.util.Log.e("AutoPartsList", "❌ Error loading parts: ${exception.message}")
                    parts = emptyList()
                }
            } catch (e: Exception) {
                errorMessage = "Error loading parts: ${e.message}"
                android.util.Log.e("AutoPartsList", "❌ Exception loading parts: ${e.message}")
                parts = emptyList()
            }
            isLoading = false
        }
    }

    val filteredParts = remember(searchQuery, parts) {
        if (searchQuery.isBlank()) {
            parts
        } else {
            parts.filter { part ->
                part.name.contains(searchQuery, ignoreCase = true) ||
                        part.description.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        TopAppBar(
            title = {
                Text(
                    text = if (selectedModelId.isNotEmpty())
                        "Parts for $selectedModelId"
                    else "All Auto Parts",
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = onViewCart) {
                    Icon(
                        Icons.Default.ShoppingCart,
                        contentDescription = "Cart"
                    )
                }
            }
        )

        SearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            onSearch = { keyboardController?.hide() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (errorMessage.isNotEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else if (filteredParts.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (searchQuery.isNotEmpty()) "No parts found matching \"$searchQuery\"" else "No parts available",
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredParts) { part ->
                    AutoPartCard(
                        part = part, 
                        onClick = { onPartClick(part) },
                        onAddToCart = { 
                            android.util.Log.d("AutoPartsList", "Add to cart clicked for: ${part.name}")
                            val success = cartManager.addToCart(part)
                            android.util.Log.d("AutoPartsList", "Add to cart result: $success for ${part.name}")
                            if (success) {
                                android.util.Log.d("AutoPartsList", "Cart now has ${cartManager.getCartItemsCount()} items")
                                // Show success feedback
                                onAddToCart(part)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier,
        placeholder = { Text(stringResource(R.string.search_parts)) },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Search")
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearch() }),
        singleLine = true
    )
}

@Composable
private fun AutoPartCard(
    part: AutoPart,
    onClick: () -> Unit,
    onAddToCart: () -> Unit
) {
    val currencyFormat = remember {
        NumberFormat.getCurrencyInstance(Locale("en", "IN"))
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = part.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = part.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2
                    )
                }
                AvailabilityBadge(isAvailable = part.isAvailable)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = currencyFormat.format(part.price),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Button(
                    onClick = onAddToCart,
                    enabled = part.isAvailable,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (part.isAvailable) 
                            MaterialTheme.colorScheme.primary 
                        else 
                            MaterialTheme.colorScheme.error,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.width(120.dp)
                ) {
                    Text(
                        text = if (part.isAvailable) "Add to Cart" else "Out of Stock",
                        fontSize = 12.sp,
                        color = if (part.isAvailable) Color.White else Color.LightGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (part.isAvailable) {
                    Text(
                        text = "Stock: ${part.stockQuantity}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    Text(
                        text = "Out of Stock",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
private fun AvailabilityBadge(isAvailable: Boolean) {
    Surface(
        color = if (isAvailable)
            MaterialTheme.colorScheme.primaryContainer
        else
            MaterialTheme.colorScheme.errorContainer,
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = stringResource(
                if (isAvailable) R.string.available else R.string.out_of_stock
            ),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = if (isAvailable)
                MaterialTheme.colorScheme.onPrimaryContainer
            else
                MaterialTheme.colorScheme.onErrorContainer
        )
    }
}
