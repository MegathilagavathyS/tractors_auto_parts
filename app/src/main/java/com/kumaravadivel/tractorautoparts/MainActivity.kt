package com.kumaravadivel.tractorautoparts

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kumaravadivel.tractorautoparts.data.FirebaseDataInitializer
import com.kumaravadivel.tractorautoparts.navigation.AppNavigation
import com.kumaravadivel.tractorautoparts.ui.theme.TractorAutoPartsTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Test Firebase connection
        testFirebaseConnection()
        
        // Initialize sample data (optional - for testing)
        initializeSampleData()
        
        setContent {
            TractorAutoPartsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
    
    private fun testFirebaseConnection() {
        try {
            // Test Firebase Auth
            val auth = FirebaseAuth.getInstance()
            Log.d("FirebaseTest", "Firebase Auth available: ${auth != null}")
            
            // Test Firestore
            val db = FirebaseFirestore.getInstance()
            Log.d("FirebaseTest", "Firestore available: ${db != null}")
            
            // Test reading data from Firestore
            db.collection("auto_parts")
                .get()
                .addOnSuccessListener { result ->
                    Log.d("FirestoreTest", "Successfully read ${result.size()} parts from database")
                }
                .addOnFailureListener { e ->
                    Log.e("FirestoreTest", "Failed to read parts: ${e.message}")
                }
            
            Log.d("FirebaseTest", "Firebase connection test completed successfully")
        } catch (e: Exception) {
            Log.e("FirebaseTest", "Firebase connection failed: ${e.message}", e)
        }
    }
    
    private fun initializeSampleData() {
        val initializer = FirebaseDataInitializer()
        lifecycleScope.launch {
            try {
                // Only initialize if database is empty
                val db = FirebaseFirestore.getInstance()
                db.collection("auto_parts")
                    .get()
                    .addOnSuccessListener { result ->
                        if (result.isEmpty) {
                            Log.d("FirebaseSetup", "Database is empty, initializing sample data...")
                            launch {
                                try {
                                    initializer.initializeSampleData()
                                    Log.d("FirebaseSetup", "Sample data initialized successfully")
                                } catch (e: Exception) {
                                    Log.e("FirebaseSetup", "Failed to initialize sample data: ${e.message}")
                                }
                            }
                        } else {
                            Log.d("FirebaseSetup", "Database already contains ${result.size()} parts")
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.e("FirebaseSetup", "Failed to check database: ${e.message}")
                    }
            } catch (e: Exception) {
                Log.e("FirebaseSetup", "Failed to initialize sample data: ${e.message}")
            }
        }
    }
}
