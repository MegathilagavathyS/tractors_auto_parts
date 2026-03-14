package com.kumaravadivel.tractorautoparts.payment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import com.kumaravadivel.tractorautoparts.data.CartManager
import org.json.JSONObject

class RazorpayPaymentActivity : Activity(), PaymentResultListener {

    private lateinit var checkout: Checkout
    private lateinit var cartManager: CartManager
    private var paymentAmount: Double = 0.0
    private var paymentDescription: String = ""

    companion object {
        private const val EXTRA_AMOUNT = "payment_amount"
        private const val EXTRA_DESCRIPTION = "payment_description"
        
        // Static reference to cart manager
        @JvmStatic
        lateinit var staticCartManager: CartManager

        fun start(activity: Activity, amount: Double, description: String, cartManager: CartManager) {
            staticCartManager = cartManager
            val intent = Intent(activity, RazorpayPaymentActivity::class.java).apply {
                putExtra(EXTRA_AMOUNT, amount)
                putExtra(EXTRA_DESCRIPTION, description)
            }
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Get payment details
        paymentAmount = intent.getDoubleExtra(EXTRA_AMOUNT, 0.0)
        paymentDescription = intent.getStringExtra(EXTRA_DESCRIPTION) ?: ""
        
        // Get cart manager from static reference
        cartManager = staticCartManager
        
        android.util.Log.d("RazorpayPayment", "onCreate: amount=$paymentAmount, description=$paymentDescription")
        
        // Initialize Razorpay
        checkout = Checkout()
        checkout.setKeyID("rzp_test_1DP5mmOlF5GJT") // Test mode key
        
        android.util.Log.d("RazorpayPayment", "Checkout initialized with test key")
        
        // Start payment with a small delay to ensure Activity is fully created
        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            startRazorpayPayment()
        }, 500) // 500ms delay
    }

    private fun startRazorpayPayment() {
        try {
            android.util.Log.d("RazorpayPayment", "Starting payment for amount: $paymentAmount")
            android.util.Log.d("RazorpayPayment", "Checkout initialized: ${::checkout.isInitialized}")
            
            val options = JSONObject().apply {
                put("name", "Tractor Auto Parts")
                put("description", paymentDescription)
                put("currency", "INR")
                put("amount", (paymentAmount * 100).toInt()) // Amount in paise
                
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
            
            android.util.Log.d("RazorpayPayment", "Options created: $options")
            android.util.Log.d("RazorpayPayment", "Opening checkout...")
            
            // Open checkout
            checkout.open(this, options)
            
            android.util.Log.d("RazorpayPayment", "Checkout.open() called successfully")
            
        } catch (e: Exception) {
            android.util.Log.e("RazorpayPayment", "Error starting payment: ${e.message}")
            e.printStackTrace()
            finishWithError("Error starting payment: ${e.message}")
        }
    }

    override fun onPaymentSuccess(razorpayPaymentID: String?) {
        android.util.Log.d("RazorpayPayment", "Payment Success: ID = $razorpayPaymentID")
        
        // Clear cart after successful payment
        cartManager.clearCart()
        
        // Return success result
        val resultIntent = Intent().apply {
            putExtra("payment_success", true)
            putExtra("payment_id", razorpayPaymentID)
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }
    
    override fun onPaymentError(code: Int, response: String?) {
        android.util.Log.e("RazorpayPayment", "Payment Error: Code = $code, Response = $response")
        finishWithError("Payment Failed: ${response ?: "Unknown error (Code: $code)"}")
    }

    private fun finishWithError(error: String) {
        val resultIntent = Intent().apply {
            putExtra("payment_success", false)
            putExtra("payment_error", error)
        }
        setResult(RESULT_CANCELED, resultIntent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Clean up checkout if available
        if (::checkout.isInitialized) {
            try {
                // Razorpay Checkout doesn't have a clear() method
                // Just let it be garbage collected
            } catch (e: Exception) {
                android.util.Log.e("RazorpayPayment", "Error cleaning up checkout: ${e.message}")
            }
        }
    }
}
