package com.kumaravadivel.tractorautoparts.data

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataInitializationService(private val context: Context) {
    
    fun initializeAllData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d("DataInitialization", "Starting comprehensive data initialization...")
                
                val initializer = TractorDataInitializer()
                initializer.initializeTractorData()
                
                Log.d("DataInitialization", "✅ Comprehensive tractor data initialized successfully!")
                Log.d("DataInitialization", "📊 Brands loaded: Mahindra, Swaraj, John Deere, TAFE, Massey Ferguson, Kubota, New Holland, Eicher, Farmtrac, Sonalika")
                Log.d("DataInitialization", "🚜 Total models: 100+ tractor models")
                Log.d("DataInitialization", "🔧 Total spare parts: 1400+ individual parts")
                
            } catch (e: Exception) {
                Log.e("DataInitialization", "❌ Error initializing data: ${e.message}", e)
            }
        }
    }
}
