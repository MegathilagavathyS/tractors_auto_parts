package com.kumaravadivel.tractorautoparts.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kumaravadivel.tractorautoparts.R
import com.kumaravadivel.tractorautoparts.data.CartManager
import com.kumaravadivel.tractorautoparts.data.models.CartItem
import com.kumaravadivel.tractorautoparts.payment.RazorpayPaymentActivity
import java.text.NumberFormat
import java.util.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.app.Activity as AndroidActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    cartManager: CartManager,
    onBack: () -> Unit,
    onPaymentComplete: () -> Unit
) {
    var isProcessing by remember { mutableStateOf(false) }
    var paymentMessage by remember { mutableStateOf("") }
    var showMessage by remember { mutableStateOf(false) }
    
    val cartItems = cartManager.cartItems
    val cartTotal = cartManager.getCartTotal()
    val currencyFormat = remember {
        NumberFormat.getCurrencyInstance(Locale("en", "IN"))
    }
    
    val context = LocalContext.current
    
    // Activity Result Launcher for Razorpay Payment
    val paymentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        isProcessing = false
        android.util.Log.d("PaymentScreen", "Payment result received: ${result.resultCode}")
        
        if (result.resultCode == AndroidActivity.RESULT_OK) {
            val paymentSuccess = result.data?.getBooleanExtra("payment_success", false) ?: false
            val paymentId = result.data?.getStringExtra("payment_id") ?: ""
            
            if (paymentSuccess) {
                paymentMessage = "Payment Successful! Payment ID: $paymentId"
                showMessage = true
                android.util.Log.d("PaymentScreen", "Payment successful: $paymentId")
                
                // Navigate to completion after delay
                GlobalScope.launch {
                    delay(3000)
                    onPaymentComplete()
                }
            } else {
                paymentMessage = "Payment failed"
                showMessage = true
                android.util.Log.e("PaymentScreen", "Payment failed")
            }
        } else {
            val error = result.data?.getStringExtra("payment_error") ?: "Payment cancelled"
            paymentMessage = error
            showMessage = true
            android.util.Log.e("PaymentScreen", "Payment error: $error")
        }
    }
    
    // Start payment function
    fun startRazorpayPayment() {
        if (cartItems.isNotEmpty()) {
            isProcessing = true
            val description = "Payment for ${cartItems.size} items"
            android.util.Log.d("PaymentScreen", "Starting Razorpay payment: amount=$cartTotal, description=$description")
            
            try {
                RazorpayPaymentActivity.start(
                    activity = context as AndroidActivity,
                    amount = cartTotal,
                    description = description,
                    cartManager = cartManager
                )
            } catch (e: Exception) {
                android.util.Log.e("PaymentScreen", "Error starting payment: ${e.message}")
                paymentMessage = "Error starting payment: ${e.message}"
                showMessage = true
                isProcessing = false
            }
        } else {
            paymentMessage = "Cart is empty"
            showMessage = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Payment",
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Order Summary
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Order Summary",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Cart Items Summary
                LazyColumn(
                    modifier = Modifier.height(200.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(cartItems.take(3)) { cartItem ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${cartItem.quantity}x ${cartItem.part.name}",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = currencyFormat.format(cartItem.part.price * cartItem.quantity),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                
                if (cartItems.size > 3) {
                    Text(
                        text = "... and ${cartItems.size - 3} more items",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Total Amount
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total Amount:",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = currencyFormat.format(cartTotal),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Razorpay Payment Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Secure Payment",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Payment Info
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Security,
                        contentDescription = "Secure",
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Pay securely with Razorpay",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "• Test Mode Enabled\n• Use test card details\n• No actual charges will be made",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Pay with Razorpay Button
                Button(
                    onClick = { startRazorpayPayment() },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isProcessing && cartItems.isNotEmpty(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50),
                        contentColor = Color.White
                    )
                ) {
                    if (isProcessing) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Processing...",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Icon(
                            Icons.Default.Payment,
                            contentDescription = "Pay",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Pay ${currencyFormat.format(cartTotal)}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Test Card Info
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFF3E0)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(
                            text = "🧪 Test Card Details",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE65100)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Card Number: 4111 1111 1111 1111\nExpiry: Any future date\nCVV: Any 3 digits",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFE65100)
                        )
                    }
                }
            }
        }
        
        // Payment Message Dialog
        if (showMessage) {
            AlertDialog(
                onDismissRequest = { showMessage = false },
                title = {
                    Text(
                        text = if (paymentMessage.contains("Successful")) "Payment Status" else "Payment Error",
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Text(
                        text = paymentMessage,
                        color = if (paymentMessage.contains("Successful")) Color(0xFF4CAF50) else Color(0xFFF44336)
                    )
                },
                confirmButton = {
                    Button(
                        onClick = { showMessage = false }
                    ) {
                        Text("OK")
                    }
                }
            )
        }
    }
}
