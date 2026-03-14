package com.kumaravadivel.tractorautoparts.utils

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.kumaravadivel.tractorautoparts.data.models.AutoPart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

object PartsInitializer {
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun addSampleParts() = withContext(Dispatchers.IO) {
        try {
            val parts = listOf(
                AutoPart(
                    id = "engine_oil_mahindra_001",
                    name = "Mahindra Engine Oil",
                    description = "High-quality engine oil for Mahindra tractors - 15W40 grade",
                    price = 450.0,
                    isAvailable = true,
                    stockQuantity = 25,
                    compatibleModels = listOf("Mahindra 575", "Mahindra 275", "Mahindra 475"),
                    brandId = "mahindra",
                    imageUrl = ""
                ),
                AutoPart(
                    id = "air_filter_johndeere_001",
                    name = "John Deere Air Filter",
                    description = "Premium air filter for John Deere tractors - High efficiency",
                    price = 320.0,
                    isAvailable = true,
                    stockQuantity = 15,
                    compatibleModels = listOf("John Deere 5045D", "John Deere 5105", "John Deere 3025"),
                    brandId = "john_deere",
                    imageUrl = ""
                ),
                AutoPart(
                    id = "fuel_filter_swaraj_001",
                    name = "Swaraj Fuel Filter",
                    description = "Efficient fuel filter for Swaraj tractors - Clean fuel supply",
                    price = 280.0,
                    isAvailable = true,
                    stockQuantity = 30,
                    compatibleModels = listOf("Swaraj 735", "Swaraj 855", "Swaraj 963"),
                    brandId = "swaraj",
                    imageUrl = ""
                ),
                AutoPart(
                    id = "clutch_plate_tafe_001",
                    name = "TAFE Clutch Plate",
                    description = "Durable clutch plate for TAFE tractors - Smooth gear shifting",
                    price = 750.0,
                    isAvailable = true,
                    stockQuantity = 10,
                    compatibleModels = listOf("TAFE 7502", "TAFE 9302", "TAFE 35"),
                    brandId = "tafe",
                    imageUrl = ""
                ),
                AutoPart(
                    id = "brake_shoes_universal_001",
                    name = "Universal Brake Shoes",
                    description = "Heavy-duty brake shoes compatible with all major tractor brands",
                    price = 550.0,
                    isAvailable = true,
                    stockQuantity = 20,
                    compatibleModels = listOf("Mahindra 575", "John Deere 5045D", "Swaraj 735", "TAFE 7502"),
                    brandId = "universal",
                    imageUrl = ""
                ),
                AutoPart(
                    id = "spark_plug_mahindra_002",
                    name = "Mahindra Spark Plug Set",
                    description = "High-performance spark plug set for Mahindra tractors - 4 pieces",
                    price = 180.0,
                    isAvailable = true,
                    stockQuantity = 50,
                    compatibleModels = listOf("Mahindra 575", "Mahindra 275", "Mahindra 475"),
                    brandId = "mahindra",
                    imageUrl = ""
                ),
                AutoPart(
                    id = "radiator_johndeere_002",
                    name = "John Deere Radiator",
                    description = "Heavy-duty aluminum radiator for John Deere tractors",
                    price = 3200.0,
                    isAvailable = true,
                    stockQuantity = 8,
                    compatibleModels = listOf("John Deere 5045D", "John Deere 5105"),
                    brandId = "john_deere",
                    imageUrl = ""
                ),
                AutoPart(
                    id = "battery_universal_002",
                    name = "Universal Tractor Battery",
                    description = "Long-lasting 12V battery for all tractor models - 100Ah",
                    price = 4500.0,
                    isAvailable = true,
                    stockQuantity = 12,
                    compatibleModels = listOf("Mahindra 575", "John Deere 5045D", "Swaraj 735", "TAFE 7502"),
                    brandId = "universal",
                    imageUrl = ""
                ),
                AutoPart(
                    id = "headlight_swaraj_002",
                    name = "Swaraj LED Headlight",
                    description = "Bright LED headlight for Swaraj tractors - High visibility",
                    price = 420.0,
                    isAvailable = true,
                    stockQuantity = 35,
                    compatibleModels = listOf("Swaraj 735", "Swaraj 855", "Swaraj 963"),
                    brandId = "swaraj",
                    imageUrl = ""
                ),
                AutoPart(
                    id = "starter_motor_tafe_002",
                    name = "TAFE Starter Motor",
                    description = "Powerful starter motor for TAFE tractors - Reliable starting",
                    price = 2800.0,
                    isAvailable = true,
                    stockQuantity = 6,
                    compatibleModels = listOf("TAFE 7502", "TAFE 9302", "TAFE 35"),
                    brandId = "tafe",
                    imageUrl = ""
                )
            )

            // Clear existing parts first
            val existingDocs = firestore.collection("auto_parts").get().await()
            for (document in existingDocs.documents) {
                document.reference.delete()
            }

            // Add new parts
            parts.forEach { part ->
                firestore.collection("auto_parts").document(part.id).set(part).await()
                Log.d("PartsInitializer", "Added part: ${part.name}")
            }

            Log.d("PartsInitializer", "Successfully added ${parts.size} parts to Firestore")
            Result.success(parts.size)

        } catch (e: Exception) {
            Log.e("PartsInitializer", "Failed to add parts: ${e.message}", e)
            Result.failure(e)
        }
    }

    suspend fun checkPartsCount(): Int = withContext(Dispatchers.IO) {
        try {
            val snapshot = firestore.collection("auto_parts").get().await()
            snapshot.size()
        } catch (e: Exception) {
            Log.e("PartsInitializer", "Failed to check parts count: ${e.message}", e)
            0
        }
    }
}
