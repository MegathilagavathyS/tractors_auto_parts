package com.kumaravadivel.tractorautoparts.data

import com.google.firebase.firestore.FirebaseFirestore
import com.kumaravadivel.tractorautoparts.data.models.AutoPart
import com.kumaravadivel.tractorautoparts.data.models.TractorBrand
import com.kumaravadivel.tractorautoparts.data.models.TractorModel
import com.kumaravadivel.tractorautoparts.data.models.Dealer
import kotlinx.coroutines.tasks.await

class FirebaseDataInitializer {
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun initializeSampleData() {
        try {
            // Initialize Tractor Brands
            initializeTractorBrands()
            
            // Initialize Auto Parts
            initializeAutoParts()
            
            // Initialize Dealers
            initializeDealers()
            
        } catch (e: Exception) {
            // Handle error
        }
    }

    private suspend fun initializeTractorBrands() {
        val brands = listOf(
            TractorBrand(
                id = "mahindra",
                name = "Mahindra",
                models = listOf(
                    TractorModel("mahindra-575", "Mahindra 575", "mahindra"),
                    TractorModel("mahindra-275", "Mahindra 275", "mahindra"),
                    TractorModel("mahindra-475", "Mahindra 475", "mahindra")
                )
            ),
            TractorBrand(
                id = "john_deere",
                name = "John Deere",
                models = listOf(
                    TractorModel("jd-5045d", "John Deere 5045D", "john_deere"),
                    TractorModel("jd-5105", "John Deere 5105", "john_deere"),
                    TractorModel("jd-3025", "John Deere 3025", "john_deere")
                )
            ),
            TractorBrand(
                id = "swaraj",
                name = "Swaraj",
                models = listOf(
                    TractorModel("swaraj-735", "Swaraj 735", "swaraj"),
                    TractorModel("swaraj-855", "Swaraj 855", "swaraj"),
                    TractorModel("swaraj-963", "Swaraj 963", "swaraj")
                )
            ),
            TractorBrand(
                id = "tafe",
                name = "TAFE",
                models = listOf(
                    TractorModel("tafe-7502", "TAFE 7502", "tafe"),
                    TractorModel("tafe-9302", "TAFE 9302", "tafe"),
                    TractorModel("tafe-35", "TAFE 35", "tafe")
                )
            )
        )

        for (brand in brands) {
            firestore.collection("tractor_brands").document(brand.id).set(brand).await()
        }
    }

    private suspend fun initializeAutoParts() {
        val parts = listOf(
            AutoPart(
                id = "oil_filter_001",
                name = "Engine Oil Filter",
                description = "High-quality engine oil filter for optimal engine performance",
                price = 250.0,
                isAvailable = true,
                stockQuantity = 50,
                compatibleModels = listOf("Mahindra 575", "Mahindra 275", "John Deere 5045D"),
                brandId = "mahindra",
                imageUrl = ""
            ),
            AutoPart(
                id = "air_filter_001",
                name = "Air Filter",
                description = "Premium air filter for clean air intake",
                price = 180.0,
                isAvailable = true,
                stockQuantity = 75,
                compatibleModels = listOf("Swaraj 735", "Swaraj 855", "TAFE 7502"),
                brandId = "swaraj",
                imageUrl = ""
            ),
            AutoPart(
                id = "fuel_filter_001",
                name = "Fuel Filter",
                description = "Efficient fuel filter for clean fuel supply",
                price = 320.0,
                isAvailable = true,
                stockQuantity = 30,
                compatibleModels = listOf("John Deere 5105", "John Deere 3025"),
                brandId = "john_deere",
                imageUrl = ""
            ),
            AutoPart(
                id = "clutch_plate_001",
                name = "Clutch Plate",
                description = "Durable clutch plate for smooth gear shifting",
                price = 850.0,
                isAvailable = false,
                stockQuantity = 0,
                compatibleModels = listOf("Mahindra 475", "TAFE 35"),
                brandId = "mahindra",
                imageUrl = ""
            ),
            AutoPart(
                id = "brake_shoe_001",
                name = "Brake Shoe",
                description = "Reliable brake shoe for effective braking",
                price = 450.0,
                isAvailable = true,
                stockQuantity = 25,
                compatibleModels = listOf("Swaraj 963", "TAFE 9302"),
                brandId = "swaraj",
                imageUrl = ""
            ),
            AutoPart(
                id = "spark_plug_001",
                name = "Spark Plug",
                description = "High-performance spark plug for efficient ignition",
                price = 120.0,
                isAvailable = true,
                stockQuantity = 100,
                compatibleModels = listOf("Mahindra 575", "John Deere 5045D", "Swaraj 735"),
                brandId = "mahindra",
                imageUrl = ""
            ),
            AutoPart(
                id = "radiator_001",
                name = "Radiator",
                description = "Heavy-duty radiator for optimal engine cooling",
                price = 2500.0,
                isAvailable = true,
                stockQuantity = 15,
                compatibleModels = listOf("John Deere 5105", "TAFE 7502"),
                brandId = "john_deere",
                imageUrl = ""
            ),
            AutoPart(
                id = "battery_001",
                name = "Tractor Battery",
                description = "Long-lasting battery for reliable power",
                price = 3200.0,
                isAvailable = true,
                stockQuantity = 20,
                compatibleModels = listOf("Mahindra 275", "Swaraj 855", "TAFE 35"),
                brandId = "mahindra",
                imageUrl = ""
            )
        )

        for (part in parts) {
            firestore.collection("auto_parts").add(part).await()
        }
    }

    private suspend fun initializeDealers() {
        val dealers = listOf(
            Dealer(
                id = "dealer_001",
                name = "Kumaravadivel Tractor Parts",
                location = "Chennai, Tamil Nadu",
                phoneNumber = "+91-9876543210",
                email = "chennai@kumaravadivel.com",
                address = "123 Industrial Area, Chennai - 600001"
            ),
            Dealer(
                id = "dealer_002",
                name = "Coimbatore Tractor Solutions",
                location = "Coimbatore, Tamil Nadu",
                phoneNumber = "+91-9876543211",
                email = "coimbatore@kumaravadivel.com",
                address = "456 Peelamedu, Coimbatore - 641004"
            ),
            Dealer(
                id = "dealer_003",
                name = "Madurai Agricultural Parts",
                location = "Madurai, Tamil Nadu",
                phoneNumber = "+91-9876543212",
                email = "madurai@kumaravadivel.com",
                address = "789 Simmakkal, Madurai - 625001"
            ),
            Dealer(
                id = "dealer_004",
                name = "Tiruchirappalli Tractor Hub",
                location = "Tiruchirappalli, Tamil Nadu",
                phoneNumber = "+91-9876543213",
                email = "trichy@kumaravadivel.com",
                address = "321 Cantonment, Tiruchirappalli - 620001"
            )
        )

        for (dealer in dealers) {
            firestore.collection("dealers").document(dealer.id).set(dealer).await()
        }
    }
}
