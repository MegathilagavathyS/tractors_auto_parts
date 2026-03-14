package com.kumaravadivel.tractorautoparts.payment

import android.app.Activity
import android.content.Context
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import com.kumaravadivel.tractorautoparts.data.CartManager
import org.json.JSONObject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay

class RazorpayManager(
    private val context: Context,
    private val cartManager: CartManager,
    private val onPaymentSuccess: (String) -> Unit,
    private val onPaymentError: (String) -> Unit,
    private val onProcessingStateChanged: (Boolean) -> Unit
) : PaymentResultListener {

    private val checkout: Checkout by lazy {
        Checkout().apply {
            setKeyID("rzp_test_1DP5mmOlF5GJT") // Test mode key
        }
    }

    fun startPayment(amount: Double, description: String) {
        try {
            onProcessingStateChanged(true)
            android.util.Log.d("Razorpay", "Starting payment for amount: $amount")
            
            val options = JSONObject().apply {
                put("name", "Tractor Auto Parts")
                put("description", description)
                put("currency", "INR")
                put("amount", (amount * 100).toInt()) // Amount in paise
                
                // Prefill customer details (optional)
                put("prefill", JSONObject().apply {
                    put("email", "customer@example.com")
                    put("contact", "+919999999999")
                })
                
                // Theme settings
                put("theme", JSONObject().apply {
                    put("color", "#4CAF50") // Green color matching your app theme
                })
                
                // Test mode settings
                put("modal", JSONObject().apply {
                    put("backdropclose", false)
                    put("escape", false)
                    put("handleback", true)
                })
            }
            
            // Get activity from context
            val activity = context as? Activity
            if (activity != null) {
                checkout.open(activity, options)
            } else {
                onPaymentError("Error: Unable to start payment - Invalid activity context")
                onProcessingStateChanged(false)
            }
            
        } catch (e: Exception) {
            android.util.Log.e("Razorpay", "Error starting payment: ${e.message}")
            onPaymentError("Error starting payment: ${e.message}")
            onProcessingStateChanged(false)
        }
    }

    override fun onPaymentSuccess(razorpayPaymentID: String?) {
        android.util.Log.d("Razorpay", "Payment Success: ID = $razorpayPaymentID")
        
        onProcessingStateChanged(false)
        
        // Clear cart after successful payment
        cartManager.clearCart()
        
        // Notify success
        onPaymentSuccess(razorpayPaymentID ?: "Unknown")
    }
    
    override fun onPaymentError(code: Int, response: String?) {
        android.util.Log.e("Razorpay", "Payment Error: Code = $code, Response = $response")
        
        onProcessingStateChanged(false)
        onPaymentError("Payment Failed: ${response ?: "Unknown error (Code: $code)"}")
    }
}
