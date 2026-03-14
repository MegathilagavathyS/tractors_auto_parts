package com.kumaravadivel.tractorautoparts.data.models

enum class UserRole {
    CUSTOMER,
    ADMIN
}

data class User(
    val uid: String = "",
    val email: String = "",
    val name: String = "",
    val role: UserRole = UserRole.CUSTOMER,
    val phoneNumber: String = "",
    val address: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
