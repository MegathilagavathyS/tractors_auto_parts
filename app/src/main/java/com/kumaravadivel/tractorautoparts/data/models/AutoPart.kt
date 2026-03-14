package com.kumaravadivel.tractorautoparts.data.models

data class AutoPart(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val isAvailable: Boolean = true,
    val stockQuantity: Int = 0,
    val compatibleModels: List<String> = emptyList(),
    val brandId: String = "",
    val imageUrl: String = ""
)

data class CartItem(
    val id: String = "",
    val part: AutoPart,
    val quantity: Int = 1,
    val addedAt: Long = System.currentTimeMillis()
)

data class Dealer(
    val id: String = "",
    val name: String = "",
    val location: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val address: String = ""
)
