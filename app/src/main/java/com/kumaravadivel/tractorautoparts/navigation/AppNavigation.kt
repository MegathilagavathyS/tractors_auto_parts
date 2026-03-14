package com.kumaravadivel.tractorautoparts.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.navDeepLink
import com.kumaravadivel.tractorautoparts.data.models.AutoPart
import com.kumaravadivel.tractorautoparts.data.models.TractorBrand
import com.kumaravadivel.tractorautoparts.data.models.TractorModel
import com.kumaravadivel.tractorautoparts.data.models.User
import com.kumaravadivel.tractorautoparts.data.models.UserRole
import com.kumaravadivel.tractorautoparts.ui.screens.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import android.util.Log

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            EnhancedLoginScreen(
                navController = navController,
                onLoginSuccess = {
                    // Ensure navigation on main thread
                    kotlinx.coroutines.GlobalScope.launch(Dispatchers.Main) {
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                }
            )
        }

        composable("signup") {
            EnhancedSignUpScreen(navController = navController)
        }

        composable("home") {
            // Determine user role and show appropriate home screen
            val auth = FirebaseAuth.getInstance()
            val currentUser = auth.currentUser
            
            // For demo purposes, check if email contains "admin" to determine admin role
            val isAdmin = currentUser?.email?.contains("admin") == true
            val user = User(
                uid = currentUser?.uid ?: "",
                email = currentUser?.email ?: "",
                name = currentUser?.email?.split("@")?.firstOrNull() ?: "",
                role = if (isAdmin) UserRole.ADMIN else UserRole.CUSTOMER
            )
            
            if (isAdmin) {
                AdminHomeScreen(
                    user = user,
                    onPartsManagement = {
                        kotlinx.coroutines.GlobalScope.launch(Dispatchers.Main) {
                            navController.navigate("parts_management")
                        }
                    },
                    onDealerInformation = {
                        kotlinx.coroutines.GlobalScope.launch(Dispatchers.Main) {
                            navController.navigate("service_garage")
                        }
                    },
                    onProfile = {
                        kotlinx.coroutines.GlobalScope.launch(Dispatchers.Main) {
                            navController.navigate("admin_profile")
                        }
                    }
                )
            } else {
                CustomerHomeScreen(
                    user = user,
                    onSelectTractorModel = {
                        try {
                            android.util.Log.d("Navigation", "Navigating to tractor selection")
                            navController.navigate("tractor_selection")
                        } catch (e: Exception) {
                            android.util.Log.e("Navigation", "Error navigating to tractor selection: ${e.message}")
                        }
                    },
                    onViewAutoParts = {
                        try {
                            android.util.Log.d("Navigation", "Navigating to all auto parts")
                            navController.navigate("auto_parts_list/all/all")
                        } catch (e: Exception) {
                            android.util.Log.e("Navigation", "Error navigating to auto parts: ${e.message}")
                        }
                    },
                    onDealerInformation = {
                        try {
                            android.util.Log.d("Navigation", "Navigating to service garage")
                            navController.navigate("service_garage")
                        } catch (e: Exception) {
                            android.util.Log.e("Navigation", "Error navigating to service garage: ${e.message}")
                        }
                    },
                    onViewCart = {
                        try {
                            android.util.Log.d("Navigation", "Navigating to cart from home")
                            navController.navigate("cart")
                        } catch (e: Exception) {
                            android.util.Log.e("Navigation", "Error navigating to cart: ${e.message}")
                        }
                    },
                    onProfile = {
                        try {
                            android.util.Log.d("Navigation", "Navigating to customer profile")
                            navController.navigate("customer_profile")
                        } catch (e: Exception) {
                            android.util.Log.e("Navigation", "Error navigating to customer profile: ${e.message}")
                        }
                    }
                )
            }
        }

        composable("tractor_selection") {
            EnhancedTractorSelectionScreenNew(
                onContinue = { brand: TractorBrand, model: TractorModel ->
                    try {
                        android.util.Log.d("Navigation", "Navigating to parts for ${brand.name} ${model.name}")
                        navController.navigate("auto_parts_list/${brand.id}/${model.id}")
                    } catch (e: Exception) {
                        android.util.Log.e("Navigation", "Error navigating to parts: ${e.message}")
                    }
                },
                onBack = {
                    try {
                        navController.popBackStack()
                    } catch (e: Exception) {
                        android.util.Log.e("Navigation", "Error going back: ${e.message}")
                    }
                }
            )
        }

        composable("auto_parts_list/{brandId}/{modelId}") { backStackEntry ->
            val brandId = backStackEntry.arguments?.getString("brandId") ?: ""
            val modelId = backStackEntry.arguments?.getString("modelId") ?: ""
            val cartManager = com.kumaravadivel.tractorautoparts.data.CartManager
            AutoPartsListScreen(
                selectedBrandId = brandId,
                selectedModelId = modelId,
                onPartClick = { part: AutoPart ->
                    // Ensure part.id is not empty before navigation
                    if (part.id.isNotEmpty()) {
                        try {
                            android.util.Log.d("Navigation", "Navigating to part details for ${part.name}")
                            navController.navigate("part_details/${part.id}")
                        } catch (e: Exception) {
                            android.util.Log.e("Navigation", "Error navigating to part details: ${e.message}")
                        }
                    }
                },
                onAddToCart = { part: AutoPart ->
                    val success = cartManager.addToCart(part)
                    android.util.Log.d("Cart", "Added to cart: ${part.name}, Success: $success")
                },
                onViewCart = {
                    try {
                        android.util.Log.d("Navigation", "Navigating to cart")
                        navController.navigate("cart")
                    } catch (e: Exception) {
                        android.util.Log.e("Navigation", "Error navigating to cart: ${e.message}")
                    }
                },
                onBack = {
                    try {
                        navController.popBackStack()
                    } catch (e: Exception) {
                        android.util.Log.e("Navigation", "Error going back from parts: ${e.message}")
                    }
                }
            )
        }

        composable("part_details/{partId}") { backStackEntry ->
            val partId = backStackEntry.arguments?.getString("partId") ?: ""
            var part by remember { mutableStateOf<AutoPart?>(null) }
            var isLoading by remember { mutableStateOf(true) }
            val firestoreManager = com.kumaravadivel.tractorautoparts.data.EnhancedFirestoreManager()
            val coroutineScope = rememberCoroutineScope()
            val cartManager = com.kumaravadivel.tractorautoparts.data.CartManager

            // Load part from Firebase
            LaunchedEffect(partId) {
                if (partId.isNotEmpty()) {
                    coroutineScope.launch {
                        isLoading = true
                        try {
                            val result = firestoreManager.getAllAutoParts()
                            result.onSuccess { partsList ->
                                part = partsList.find { it.id == partId }
                                if (part == null) {
                                    Log.w("AppNavigation", "Part not found with ID: $partId")
                                }
                            }.onFailure { error ->
                                Log.e("AppNavigation", "Error loading part: ${error.message}")
                            }
                        } catch (e: Exception) {
                            Log.e("AppNavigation", "Exception loading part: ${e.message}")
                        }
                        isLoading = false
                    }
                }
            }

            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        CircularProgressIndicator()
                        Text(
                            text = "Loading part details...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else if (part != null) {
                PartDetailsScreen(
                    part = part!!,
                    onBack = {
                        try {
                            navController.popBackStack()
                        } catch (e: Exception) {
                            Log.e("Navigation", "Error going back: ${e.message}")
                        }
                    },
                    onAddToCart = { selectedPart ->
                        val success = cartManager.addToCart(selectedPart)
                        Log.d("Cart", "Added to cart: ${selectedPart.name}, Success: $success")
                        // Show success message or navigate to cart
                    }
                )
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            Icons.Default.Error,
                            contentDescription = "Error",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(64.dp)
                        )
                        Text(
                            text = "Part Not Found",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.error
                        )
                        Text(
                            text = "The part you're looking for doesn't exist or has been removed.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 32.dp)
                        )
                        Button(
                            onClick = {
                                try {
                                    navController.popBackStack()
                                } catch (e: Exception) {
                                    Log.e("Navigation", "Error going back: ${e.message}")
                                }
                            }
                        ) {
                            Text("Go Back")
                        }
                    }
                }
            }
        }

        composable("dealer_information") {
            ServiceGarageScreen(
                onBack = {
                    try {
                        navController.popBackStack()
                    } catch (e: Exception) {
                        android.util.Log.e("Navigation", "Error going back: ${e.message}")
                    }
                },
                isCustomer = false // Admin doesn't see technicians
            )
        }

        composable("service_garage") {
            ServiceGarageScreen(
                onBack = {
                    kotlinx.coroutines.GlobalScope.launch(Dispatchers.Main) {
                        navController.popBackStack()
                    }
                },
                isCustomer = true // Customer sees technicians with phone calls
            )
        }

        composable("parts_management") {
            EnhancedPartsManagementScreen(
                onBack = {
                    kotlinx.coroutines.GlobalScope.launch(Dispatchers.Main) {
                        navController.popBackStack()
                    }
                },
                onTest = {
                    kotlinx.coroutines.GlobalScope.launch(Dispatchers.Main) {
                        navController.navigate("data_test")
                    }
                }
            )
        }

        composable("data_test") {
            DataTestScreen(
                onBack = {
                    kotlinx.coroutines.GlobalScope.launch(Dispatchers.Main) {
                        navController.popBackStack()
                    }
                }
            )
        }

        composable("customer_profile") {
            val auth = FirebaseAuth.getInstance()
            val currentUser = auth.currentUser
            val user = User(
                uid = currentUser?.uid ?: "",
                email = currentUser?.email ?: "",
                name = currentUser?.email?.split("@")?.firstOrNull() ?: "",
                role = UserRole.CUSTOMER
            )
            
            EditableProfileScreen(
                user = user,
                onBack = {
                    kotlinx.coroutines.GlobalScope.launch(Dispatchers.Main) {
                        navController.popBackStack()
                    }
                }
            )
        }

        composable("admin_profile") {
            val auth = FirebaseAuth.getInstance()
            val currentUser = auth.currentUser
            val user = User(
                uid = currentUser?.uid ?: "",
                email = currentUser?.email ?: "",
                name = currentUser?.email?.split("@")?.firstOrNull() ?: "",
                role = UserRole.ADMIN
            )
            
            EditableProfileScreen(
                user = user,
                onBack = {
                    kotlinx.coroutines.GlobalScope.launch(Dispatchers.Main) {
                        navController.popBackStack()
                    }
                }
            )
        }

        composable("cart") {
            val cartManager = com.kumaravadivel.tractorautoparts.data.CartManager
            CartScreen(
                cartManager = cartManager,
                onBack = {
                    try {
                        navController.popBackStack()
                    } catch (e: Exception) {
                        android.util.Log.e("Navigation", "Error going back from cart: ${e.message}")
                    }
                },
                onCheckout = {
                    try {
                        android.util.Log.d("Navigation", "Navigating to payment")
                        navController.navigate("payment")
                    } catch (e: Exception) {
                        android.util.Log.e("Navigation", "Error navigating to payment: ${e.message}")
                    }
                }
            )
        }

        composable("payment") {
            val cartManager = com.kumaravadivel.tractorautoparts.data.CartManager
            PaymentScreen(
                cartManager = cartManager,
                onBack = {
                    try {
                        navController.popBackStack()
                    } catch (e: Exception) {
                        android.util.Log.e("Navigation", "Error going back from payment: ${e.message}")
                    }
                },
                onPaymentComplete = {
                    try {
                        android.util.Log.d("Navigation", "Payment completed, navigating to order confirmation")
                        navController.navigate("order_confirmation")
                    } catch (e: Exception) {
                        android.util.Log.e("Navigation", "Error navigating to order confirmation: ${e.message}")
                    }
                }
            )
        }

        composable("order_confirmation") {
            // Order confirmation screen can be added later
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = "Order Complete",
                        modifier = Modifier.size(64.dp),
                        tint = androidx.compose.ui.graphics.Color(0xFF4CAF50)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Order Placed Successfully!",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Thank you for your purchase. Your order has been confirmed and will be processed shortly.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            try {
                                navController.navigate("customer_home")
                            } catch (e: Exception) {
                                android.util.Log.e("Navigation", "Error navigating home: ${e.message}")
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = androidx.compose.ui.graphics.Color(0xFF4CAF50)
                        )
                    ) {
                        Text("Back to Home")
                    }
                }
            }
        }
    }
}
