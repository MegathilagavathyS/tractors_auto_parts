package com.kumaravadivel.tractorautoparts.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.kumaravadivel.tractorautoparts.data.models.TractorBrand
import com.kumaravadivel.tractorautoparts.data.models.TractorModel
import com.kumaravadivel.tractorautoparts.data.models.AutoPart
import kotlinx.coroutines.tasks.await
import com.google.android.gms.tasks.Task

data class TractorBrandInfo(
    val name: String = "",
    val models: List<String> = emptyList()
)

data class TractorModelInfo(
    val name: String = "",
    val brand: String = "",
    val createdAt: com.google.firebase.Timestamp = com.google.firebase.Timestamp.now()
)

class EnhancedFirestoreManager {
    private val firestore = FirebaseFirestore.getInstance()
    
    // Get all tractor brands with auto-initialization fallback
    suspend fun getTractorBrands(): Result<List<TractorBrand>> {
        return try {
            val snapshot = firestore.collection("tractor_brands")
                .get()
                .await()
            
            val brands = snapshot.documents.mapNotNull { doc ->
                val name = doc.getString("name") ?: return@mapNotNull null
                TractorBrand(
                    id = doc.id,
                    name = name
                )
            }
            
            // If no brands found, initialize data automatically
            if (brands.isEmpty()) {
                try {
                    val initializer = TractorDataInitializer()
                    initializer.initializeTractorData()
                    
                    // Try loading again after initialization
                    val newSnapshot = firestore.collection("tractor_brands")
                        .get()
                        .await()
                    
                    val newBrands = newSnapshot.documents.mapNotNull { doc ->
                        val name = doc.getString("name") ?: return@mapNotNull null
                        TractorBrand(
                            id = doc.id,
                            name = name
                        )
                    }
                    Result.success(newBrands)
                } catch (e: Exception) {
                    // If initialization fails, return mock data as fallback
                    Result.success(getMockBrands())
                }
            } else {
                Result.success(brands)
            }
        } catch (e: Exception) {
            // Return mock data as fallback
            Result.success(getMockBrands())
        }
    }
    
    // Mock brands as fallback
    private fun getMockBrands(): List<TractorBrand> {
        return listOf(
            TractorBrand("mahindra", "Mahindra"),
            TractorBrand("swaraj", "Swaraj"),
            TractorBrand("john_deere", "John Deere"),
            TractorBrand("tafe", "TAFE"),
            TractorBrand("massey_ferguson", "Massey Ferguson"),
            TractorBrand("kubota", "Kubota"),
            TractorBrand("new_holland", "New Holland"),
            TractorBrand("eicher", "Eicher"),
            TractorBrand("farmtrac", "Farmtrac"),
            TractorBrand("sonalika", "Sonalika")
        )
    }
    
    // Get models for a specific brand with fallback
    suspend fun getModelsForBrand(brandId: String): Result<List<TractorModel>> {
        return try {
            val snapshot = firestore.collection("tractor_brands")
                .document(brandId)
                .collection("models")
                .orderBy("name")
                .get()
                .await()
            
            val models = snapshot.documents.mapNotNull { doc ->
                val name = doc.getString("name") ?: return@mapNotNull null
                TractorModel(
                    id = doc.id,
                    name = name,
                    brandId = brandId
                )
            }
            
            // If no models found, return mock models as fallback
            if (models.isEmpty()) {
                android.util.Log.d("EnhancedFirestoreManager", "No models found for $brandId, using fallback")
                Result.success(getMockModelsForBrand(brandId))
            } else {
                android.util.Log.d("EnhancedFirestoreManager", "✅ Found ${models.size} models for $brandId: ${models.map { it.name }}")
                Result.success(models)
            }
        } catch (e: Exception) {
            android.util.Log.e("EnhancedFirestoreManager", "❌ Error loading models for $brandId: ${e.message}")
            // Return mock models as fallback
            Result.success(getMockModelsForBrand(brandId))
        }
    }
    
    // Mock models as fallback
    private fun getMockModelsForBrand(brandName: String): List<TractorModel> {
        return when (brandName) {
            "mahindra" -> listOf(
                TractorModel("265_di", "265 DI", "mahindra"),
                TractorModel("275_di", "275 DI", "mahindra"),
                TractorModel("415_di", "415 DI", "mahindra"),
                TractorModel("475_di", "475 DI", "mahindra"),
                TractorModel("475_di_xp_plus", "475 DI XP Plus", "mahindra"),
                TractorModel("575_di", "575 DI", "mahindra"),
                TractorModel("575_di_xp_plus", "575 DI XP Plus", "mahindra"),
                TractorModel("585_di", "585 DI", "mahindra"),
                TractorModel("585_di_xp_plus", "585 DI XP Plus", "mahindra"),
                TractorModel("595_di", "595 DI", "mahindra"),
                TractorModel("605_di", "605 DI", "mahindra"),
                TractorModel("615_di", "615 DI", "mahindra"),
                TractorModel("675_di", "675 DI", "mahindra"),
                TractorModel("675_di_xp_plus", "675 DI XP Plus", "mahindra"),
                TractorModel("705_di", "705 DI", "mahindra"),
                TractorModel("725_di", "725 DI", "mahindra")
            )
            "swaraj" -> listOf(
                TractorModel("735_fe", "735 FE", "swaraj"),
                TractorModel("744_fe", "744 FE", "swaraj"),
                TractorModel("744_xm", "744 XM", "swaraj"),
                TractorModel("855_fe", "855 FE", "swaraj"),
                TractorModel("855_xm", "855 XM", "swaraj"),
                TractorModel("963_fe", "963 FE", "swaraj"),
                TractorModel("978_fe", "978 FE", "swaraj"),
                TractorModel("1051_fe", "1051 FE", "swaraj"),
                TractorModel("717_fe", "717 FE", "swaraj"),
                TractorModel("724_fe", "724 FE", "swaraj"),
                TractorModel("734_fe", "734 FE", "swaraj"),
                TractorModel("744_xt", "744 XT", "swaraj"),
                TractorModel("825_fe", "825 FE", "swaraj"),
                TractorModel("834_fe", "834 FE", "swaraj")
            )
            "john_deere" -> listOf(
                TractorModel("5045d", "5045D", "john_deere"),
                TractorModel("5105", "5105", "john_deere"),
                TractorModel("5310", "5310", "john_deere"),
                TractorModel("5047d", "5047D", "john_deere"),
                TractorModel("5050d", "5050D", "john_deere"),
                TractorModel("5060d", "5060D", "john_deere"),
                TractorModel("5070d", "5070D", "john_deere"),
                TractorModel("5080d", "5080D", "john_deere"),
                TractorModel("5090d", "5090D", "john_deere"),
                TractorModel("5115d", "5115D", "john_deere"),
                TractorModel("5125d", "5125D", "john_deere"),
                TractorModel("5145d", "5145D", "john_deere"),
                TractorModel("5165d", "5165D", "john_deere"),
                TractorModel("5215d", "5215D", "john_deere")
            )
            "tafe" -> listOf(
                TractorModel("tafe_35", "TAFE 35", "tafe"),
                TractorModel("tafe_45", "TAFE 45", "tafe"),
                TractorModel("tafe_5502", "TAFE 5502", "tafe"),
                TractorModel("tafe_7502", "TAFE 7502", "tafe"),
                TractorModel("tafe_8520", "TAFE 8520", "tafe"),
                TractorModel("tafe_9530", "TAFE 9530", "tafe"),
                TractorModel("tafe_9500", "TAFE 9500", "tafe"),
                TractorModel("tafe_6027", "TAFE 6027", "tafe")
            )
            "massey_ferguson" -> listOf(
                TractorModel("mf_1035", "MF 1035", "massey_ferguson"),
                TractorModel("mf_1035_di", "MF 1035 DI", "massey_ferguson"),
                TractorModel("mf_241", "MF 241", "massey_ferguson"),
                TractorModel("mf_241_di", "MF 241 DI", "massey_ferguson"),
                TractorModel("mf_250", "MF 250", "massey_ferguson"),
                TractorModel("mf_260", "MF 260", "massey_ferguson"),
                TractorModel("mf_290", "MF 290", "massey_ferguson"),
                TractorModel("mf_375", "MF 375", "massey_ferguson"),
                TractorModel("mf_385", "MF 385", "massey_ferguson"),
                TractorModel("mf_4255", "MF 4255", "massey_ferguson")
            )
            "kubota" -> listOf(
                TractorModel("mu4501", "MU4501", "kubota"),
                TractorModel("mu4502", "MU4502", "kubota"),
                TractorModel("mu5501", "MU5501", "kubota"),
                TractorModel("mu5502", "MU5502", "kubota"),
                TractorModel("b2420", "B2420", "kubota"),
                TractorModel("b2741", "B2741", "kubota"),
                TractorModel("l3408", "L3408", "kubota"),
                TractorModel("m4956", "M4956", "kubota")
            )
            "new_holland" -> listOf(
                TractorModel("nh_3037", "NH 3037", "new_holland"),
                TractorModel("nh_3038", "NH 3038", "new_holland"),
                TractorModel("nh_3039", "NH 3039", "new_holland"),
                TractorModel("nh_3047", "NH 3047", "new_holland"),
                TractorModel("nh_3049", "NH 3049", "new_holland"),
                TractorModel("nh_3050", "NH 3050", "new_holland"),
                TractorModel("nh_3055", "NH 3055", "new_holland"),
                TractorModel("nh_3060", "NH 3060", "new_holland"),
                TractorModel("nh_3065", "NH 3065", "new_holland"),
                TractorModel("nh_3070", "NH 3070", "new_holland")
            )
            "eicher" -> listOf(
                TractorModel("e_247", "E 247", "eicher"),
                TractorModel("e_248", "E 248", "eicher"),
                TractorModel("e_380", "E 380", "eicher"),
                TractorModel("e_380_t", "E 380 T", "eicher"),
                TractorModel("e_410", "E 410", "eicher"),
                TractorModel("e_410_t", "E 410 T", "eicher"),
                TractorModel("e_480", "E 480", "eicher"),
                TractorModel("e_480_t", "E 480 T", "eicher"),
                TractorModel("e_510", "E 510", "eicher"),
                TractorModel("e_510_t", "E 510 T", "eicher"),
                TractorModel("e_540", "E 540", "eicher"),
                TractorModel("e_540_t", "E 540 T", "eicher")
            )
            "farmtrac" -> listOf(
                TractorModel("ft_60", "FT 60", "farmtrac"),
                TractorModel("ft_65", "FT 65", "farmtrac"),
                TractorModel("ft_70", "FT 70", "farmtrac"),
                TractorModel("ft_75", "FT 75", "farmtrac"),
                TractorModel("ft_80", "FT 80", "farmtrac"),
                TractorModel("ft_90", "FT 90", "farmtrac"),
                TractorModel("ft_100", "FT 100", "farmtrac"),
                TractorModel("ft_110", "FT 110", "farmtrac"),
                TractorModel("ft_120", "FT 120", "farmtrac"),
                TractorModel("ft_130", "FT 130", "farmtrac")
            )
            "sonalika" -> listOf(
                TractorModel("di_35", "DI 35", "sonalika"),
                TractorModel("di_40", "DI 40", "sonalika"),
                TractorModel("di_45", "DI 45", "sonalika"),
                TractorModel("di_50", "DI 50", "sonalika"),
                TractorModel("di_50_sarkar", "DI 50 Sarkar", "sonalika"),
                TractorModel("di_55", "DI 55", "sonalika"),
                TractorModel("di_60", "DI 60", "sonalika"),
                TractorModel("di_60_sarkar", "DI 60 Sarkar", "sonalika"),
                TractorModel("di_65", "DI 65", "sonalika")
            )
            else -> listOf(
                TractorModel("model1", "Model 1", brandName),
                TractorModel("model2", "Model 2", brandName)
            )
        }
    }
    
    // Get all auto parts from all brands and models
    suspend fun getAllAutoParts(): Result<List<AutoPart>> {
        return try {
            val allParts = mutableListOf<AutoPart>()
            
            // Get all brands
            val brandsSnapshot = firestore.collection("spare_parts")
                .get()
                .await()
            
            for (brandDoc in brandsSnapshot.documents) {
                val brandName = brandDoc.id
                
                // Get all models for this brand
                val modelsSnapshot = brandDoc.reference.collection("models")
                    .get()
                    .await()
                
                for (modelDoc in modelsSnapshot.documents) {
                    val modelName = modelDoc.id
                    
                    // Get all parts for this model
                    val partsSnapshot = modelDoc.reference.collection("parts")
                        .orderBy("index")
                        .get()
                        .await()
                    
                    for (partDoc in partsSnapshot.documents) {
                        val part = partDoc.toObject(AutoPart::class.java)
                        part?.let {
                            allParts.add(it.copy(
                                id = partDoc.id,
                                brandId = brandName,
                                compatibleModels = listOf(modelName)
                            ))
                        }
                    }
                }
            }
            
            Result.success(allParts)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // Get parts for specific brand and model
    suspend fun getPartsForBrandAndModel(brandName: String, modelName: String): Result<List<AutoPart>> {
        return try {
            val snapshot = firestore.collection("spare_parts")
                .document(brandName)
                .collection("models")
                .document(modelName)
                .collection("parts")
                .orderBy("index")
                .get()
                .await()
            
            val parts = snapshot.documents.mapNotNull { doc ->
                val part = doc.toObject(AutoPart::class.java)
                part?.let {
                    it.copy(
                        id = doc.id,
                        brandId = brandName,
                        compatibleModels = listOf(modelName)
                    )
                }
            }
            
            // If no parts found, return mock parts as fallback
            if (parts.isEmpty()) {
                android.util.Log.d("EnhancedFirestoreManager", "No parts found for $brandName $modelName, using fallback")
                Result.success(getMockPartsForBrandAndModel(brandName, modelName))
            } else {
                android.util.Log.d("EnhancedFirestoreManager", "✅ Found ${parts.size} parts for $brandName $modelName")
                Result.success(parts)
            }
        } catch (e: Exception) {
            android.util.Log.e("EnhancedFirestoreManager", "❌ Error loading parts for $brandName $modelName: ${e.message}")
            // Return mock parts as fallback
            Result.success(getMockPartsForBrandAndModel(brandName, modelName))
        }
    }
    
    // Mock parts as fallback
    private fun getMockPartsForBrandAndModel(brandName: String, modelName: String): List<AutoPart> {
        val commonParts = listOf(
            "Engine Oil Filter", "Fuel Filter", "Air Filter", "Clutch Plate",
            "Brake Shoe", "Radiator", "Piston Kit", "Head Gasket", "Water Pump",
            "Starter Motor", "Alternator", "Hydraulic Pump", "Steering Rod",
            "Gear Lever", "Battery"
        )
        
        return commonParts.mapIndexed { index, partName ->
            AutoPart(
                id = "${brandName}_${modelName}_${index}",
                name = partName,
                description = "High quality ${partName} for ${brandName} ${modelName}",
                price = generateRandomPrice(partName),
                stockQuantity = (10..100).random(),
                isAvailable = true,
                brandId = brandName,
                compatibleModels = listOf(modelName),
                imageUrl = ""
            )
        }
    }
    
    private fun generateRandomPrice(partName: String): Double {
        return when {
            partName.contains("Filter") -> (50..500).random().toDouble()
            partName.contains("Piston") -> (500..2000).random().toDouble()
            partName.contains("Clutch") -> (800..3000).random().toDouble()
            partName.contains("Brake") -> (200..800).random().toDouble()
            partName.contains("Radiator") -> (1000..4000).random().toDouble()
            partName.contains("Starter") -> (1500..5000).random().toDouble()
            partName.contains("Alternator") -> (2000..6000).random().toDouble()
            partName.contains("Battery") -> (300..1500).random().toDouble()
            else -> (100..2000).random().toDouble()
        }
    }
    
    // Get parts for specific brand (all models)
    suspend fun getPartsForBrand(brandName: String): Result<List<AutoPart>> {
        return try {
            val allBrandParts = mutableListOf<AutoPart>()
            
            // Get all models for this brand
            val modelsSnapshot = firestore.collection("spare_parts")
                .document(brandName)
                .collection("models")
                .get()
                .await()
            
            for (modelDoc in modelsSnapshot.documents) {
                val modelName = modelDoc.id
                
                // Get all parts for this model
                val partsSnapshot = modelDoc.reference.collection("parts")
                    .orderBy("index")
                    .get()
                    .await()
                
                for (partDoc in partsSnapshot.documents) {
                    val part = partDoc.toObject(AutoPart::class.java)
                    part?.let {
                        allBrandParts.add(it.copy(
                            id = partDoc.id,
                            brandId = brandName,
                            compatibleModels = listOf(modelName)
                        ))
                    }
                }
            }
            
            Result.success(allBrandParts)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // Search parts by name
    suspend fun searchParts(query: String): Result<List<AutoPart>> {
        return try {
            val allPartsResult = getAllAutoParts()
            if (allPartsResult.isFailure) {
                return Result.failure(allPartsResult.exceptionOrNull() ?: Exception("Unknown error"))
            }
            
            val allParts = allPartsResult.getOrNull() ?: emptyList()
            val filteredParts = allParts.filter { part ->
                part.name.contains(query, ignoreCase = true) ||
                part.description.contains(query, ignoreCase = true) ||
                part.brandId.contains(query, ignoreCase = true) ||
                part.compatibleModels.any { it.contains(query, ignoreCase = true) }
            }
            
            Result.success(filteredParts)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // Add new part
    suspend fun addAutoPart(part: AutoPart): Result<String> {
        return try {
            val partData = mapOf(
                "name" to part.name,
                "description" to part.description,
                "price" to part.price,
                "isAvailable" to part.isAvailable,
                "stockQuantity" to part.stockQuantity,
                "brand" to part.brandId,
                "category" to when {
                    part.name.contains("Filter") -> "Filters"
                    part.name.contains("Piston") || part.name.contains("Cylinder") -> "Engine"
                    part.name.contains("Brake") -> "Brakes"
                    part.name.contains("Clutch") -> "Clutch"
                    part.name.contains("Radiator") || part.name.contains("Water Pump") -> "Cooling"
                    part.name.contains("Battery") -> "Electrical"
                    part.name.contains("Starter") || part.name.contains("Alternator") -> "Electrical"
                    part.name.contains("Hydraulic") -> "Hydraulic"
                    part.name.contains("Steering") -> "Steering"
                    part.name.contains("Gear") -> "Transmission"
                    else -> "General"
                },
                "imageUrl" to part.imageUrl,
                "compatibleModels" to part.compatibleModels,
                "createdAt" to com.google.firebase.Timestamp.now()
            )
            
            val docRef = firestore.collection("spare_parts")
                .document(part.brandId)
                .collection("models")
                .document(part.compatibleModels.firstOrNull() ?: "unknown")
                .collection("parts")
                .add(partData)
                .await()
            
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // Update part
    suspend fun updateAutoPart(partId: String, part: AutoPart): Result<Unit> {
        return try {
            val partData = mapOf(
                "name" to part.name,
                "description" to part.description,
                "price" to part.price,
                "isAvailable" to part.isAvailable,
                "stockQuantity" to part.stockQuantity,
                "brand" to part.brandId,
                "category" to when {
                    part.name.contains("Filter") -> "Filters"
                    part.name.contains("Piston") || part.name.contains("Cylinder") -> "Engine"
                    part.name.contains("Brake") -> "Brakes"
                    part.name.contains("Clutch") -> "Clutch"
                    part.name.contains("Radiator") || part.name.contains("Water Pump") -> "Cooling"
                    part.name.contains("Battery") -> "Electrical"
                    part.name.contains("Starter") || part.name.contains("Alternator") -> "Electrical"
                    part.name.contains("Hydraulic") -> "Hydraulic"
                    part.name.contains("Steering") -> "Steering"
                    part.name.contains("Gear") -> "Transmission"
                    else -> "General"
                },
                "imageUrl" to part.imageUrl,
                "compatibleModels" to part.compatibleModels,
                "updatedAt" to com.google.firebase.Timestamp.now()
            )
            
            // Find the part in all collections and update it
            val brandsSnapshot = firestore.collection("spare_parts").get().await()
            
            var partUpdated = false
            for (brandDoc in brandsSnapshot.documents) {
                val modelsSnapshot = brandDoc.reference.collection("models").get().await()
                
                for (modelDoc in modelsSnapshot.documents) {
                    try {
                        brandDoc.reference.collection("models")
                            .document(modelDoc.id)
                            .collection("parts")
                            .document(partId)
                            .update(partData)
                            .await()
                        partUpdated = true
                        break
                    } catch (e: Exception) {
                        // Document not found, continue searching
                    }
                }
                
                if (partUpdated) break
            }
            
            if (partUpdated) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Part not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // Delete part
    suspend fun deleteAutoPart(partId: String): Result<Unit> {
        return try {
            val brandsSnapshot = firestore.collection("spare_parts").get().await()
            
            var partDeleted = false
            for (brandDoc in brandsSnapshot.documents) {
                val modelsSnapshot = brandDoc.reference.collection("models").get().await()
                
                for (modelDoc in modelsSnapshot.documents) {
                    try {
                        brandDoc.reference.collection("models")
                            .document(modelDoc.id)
                            .collection("parts")
                            .document(partId)
                            .delete()
                            .await()
                        partDeleted = true
                        break
                    } catch (e: Exception) {
                        // Document not found, continue searching
                    }
                }
                
                if (partDeleted) break
            }
            
            if (partDeleted) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Part not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // Initialize all data (call this once to populate Firebase)
    suspend fun initializeAllData(): Result<Unit> {
        return try {
            val initializer = TractorDataInitializer()
            initializer.initializeTractorData()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
