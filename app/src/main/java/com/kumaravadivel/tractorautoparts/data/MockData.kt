package com.kumaravadivel.tractorautoparts.data

import com.kumaravadivel.tractorautoparts.data.models.AutoPart
import com.kumaravadivel.tractorautoparts.data.models.Dealer
import com.kumaravadivel.tractorautoparts.data.models.TractorBrand
import com.kumaravadivel.tractorautoparts.data.models.TractorModel

object MockData {
    
    val tractorBrands = listOf(
        TractorBrand(
            id = "1",
            name = "Mahindra",
            models = listOf(
                TractorModel("1-1", "Mahindra 265 DI", "1"),
                TractorModel("1-2", "Mahindra 275 DI", "1"),
                TractorModel("1-3", "Mahindra 475 DI", "1"),
                TractorModel("1-4", "Mahindra 575 DI", "1")
            )
        ),
        TractorBrand(
            id = "2",
            name = "John Deere",
            models = listOf(
                TractorModel("2-1", "John Deere 5045D", "2"),
                TractorModel("2-2", "John Deere 5050D", "2"),
                TractorModel("2-3", "John Deere 5105", "2")
            )
        ),
        TractorBrand(
            id = "3",
            name = "Swaraj",
            models = listOf(
                TractorModel("3-1", "Swaraj 735 FE", "3"),
                TractorModel("3-2", "Swaraj 744 FE", "3"),
                TractorModel("3-3", "Swaraj 855 FE", "3")
            )
        ),
        TractorBrand(
            id = "4",
            name = "TAFE",
            models = listOf(
                TractorModel("4-1", "TAFE 9502", "4"),
                TractorModel("4-2", "TAFE 7502", "4"),
                TractorModel("4-3", "TAFE 5502", "4")
            )
        )
    )
    
    val autoParts = listOf(
        AutoPart(
            id = "1",
            name = "Engine Oil Filter",
            description = "High-quality engine oil filter for optimal engine performance and longevity",
            price = 250.0,
            isAvailable = true,
            stockQuantity = 50,
            compatibleModels = listOf("Mahindra 265 DI", "Mahindra 275 DI", "John Deere 5045D")
        ),
        AutoPart(
            id = "2",
            name = "Air Filter",
            description = "Premium air filter to protect engine from dust and debris",
            price = 180.0,
            isAvailable = true,
            stockQuantity = 30,
            compatibleModels = listOf("Mahindra 475 DI", "Swaraj 735 FE", "TAFE 9502")
        ),
        AutoPart(
            id = "3",
            name = "Fuel Filter",
            description = "Efficient fuel filter for clean fuel delivery to engine",
            price = 320.0,
            isAvailable = false,
            stockQuantity = 0,
            compatibleModels = listOf("John Deere 5050D", "John Deere 5105", "Swaraj 744 FE")
        ),
        AutoPart(
            id = "4",
            name = "Clutch Plate",
            description = "Durable clutch plate for smooth power transmission",
            price = 1200.0,
            isAvailable = true,
            stockQuantity = 15,
            compatibleModels = listOf("Mahindra 575 DI", "Swaraj 855 FE", "TAFE 7502")
        ),
        AutoPart(
            id = "5",
            name = "Brake Shoes",
            description = "High-performance brake shoes for reliable braking system",
            price = 450.0,
            isAvailable = true,
            stockQuantity = 25,
            compatibleModels = listOf("Mahindra 265 DI", "John Deere 5045D", "TAFE 5502")
        ),
        AutoPart(
            id = "6",
            name = "Spark Plug",
            description = "Quality spark plug for efficient ignition and fuel combustion",
            price = 85.0,
            isAvailable = true,
            stockQuantity = 100,
            compatibleModels = listOf("Swaraj 735 FE", "Swaraj 744 FE", "Mahindra 275 DI")
        ),
        AutoPart(
            id = "7",
            name = "Radiator Hose",
            description = "Flexible radiator hose for efficient cooling system",
            price = 280.0,
            isAvailable = false,
            stockQuantity = 0,
            compatibleModels = listOf("John Deere 5105", "Mahindra 475 DI", "TAFE 9502")
        ),
        AutoPart(
            id = "8",
            name = "Battery 12V",
            description = "Heavy-duty 12V battery for reliable starting power",
            price = 3500.0,
            isAvailable = true,
            stockQuantity = 10,
            compatibleModels = listOf("Mahindra 575 DI", "Swaraj 855 FE", "John Deere 5050D")
        )
    )
    
    val dealers = listOf(
        Dealer(
            id = "1",
            name = "Kumaravadivel Main Store",
            location = "123, Tractor Parts Street, Chennai, Tamil Nadu",
            phoneNumber = "+91-9876543210",
            email = "info@kumaravadivel.com"
        ),
        Dealer(
            id = "2",
            name = "Kumaravadivel Branch - Coimbatore",
            location = "456, Industrial Area, Coimbatore, Tamil Nadu",
            phoneNumber = "+91-9876543211",
            email = "coimbatore@kumaravadivel.com"
        ),
        Dealer(
            id = "3",
            name = "Kumaravadivel Branch - Madurai",
            location = "789, Highway Road, Madurai, Tamil Nadu",
            phoneNumber = "+91-9876543212",
            email = "madurai@kumaravadivel.com"
        ),
        Dealer(
            id = "4",
            name = "Kumaravadivel Branch - Trichy",
            location = "321, Market Street, Trichy, Tamil Nadu",
            phoneNumber = "+91-9876543213",
            email = "trichy@kumaravadivel.com"
        )
    )
}
