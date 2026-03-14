package com.kumaravadivel.tractorautoparts.data

import com.kumaravadivel.tractorautoparts.data.models.AutoPart
import com.kumaravadivel.tractorautoparts.data.models.CartItem

object CartManager {
    private val _cartItems = mutableListOf<CartItem>()
    val cartItems: List<CartItem> get() = _cartItems.toList()
    
    // Add item to cart
    fun addToCart(part: AutoPart, quantity: Int = 1): Boolean {
        android.util.Log.d("CartManager", "Adding to cart: ${part.name}, quantity: $quantity, stock: ${part.stockQuantity}")
        
        // Check if part is available and has sufficient stock
        if (!part.isAvailable || part.stockQuantity < quantity) {
            android.util.Log.w("CartManager", "Cannot add ${part.name}: Not available or insufficient stock")
            return false
        }
        
        // Check if item already exists in cart
        val existingItemIndex = _cartItems.indexOfFirst { it.part.id == part.id }
        
        return if (existingItemIndex >= 0) {
            // Update quantity of existing item
            val existingItem = _cartItems[existingItemIndex]
            val newQuantity = existingItem.quantity + quantity
            
            // Check if new quantity exceeds stock
            if (newQuantity > part.stockQuantity) {
                android.util.Log.w("CartManager", "Cannot add ${part.name}: New quantity $newQuantity exceeds stock ${part.stockQuantity}")
                return false
            }
            
            _cartItems[existingItemIndex] = existingItem.copy(quantity = newQuantity)
            android.util.Log.d("CartManager", "Updated ${part.name} quantity to $newQuantity")
            true
        } else {
            // Add new item to cart
            _cartItems.add(CartItem(
                id = "cart_${part.id}_${System.currentTimeMillis()}",
                part = part,
                quantity = quantity
            ))
            android.util.Log.d("CartManager", "Added new item ${part.name} to cart")
            true
        }
    }
    
    // Remove item from cart
    fun removeFromCart(cartItemId: String) {
        _cartItems.removeAll { it.id == cartItemId }
    }
    
    // Update item quantity
    fun updateQuantity(cartItemId: String, newQuantity: Int): Boolean {
        val itemIndex = _cartItems.indexOfFirst { it.id == cartItemId }
        if (itemIndex >= 0) {
            val item = _cartItems[itemIndex]
            // Check if new quantity is valid
            if (newQuantity <= 0) {
                removeFromCart(cartItemId)
                return true
            }
            if (newQuantity > item.part.stockQuantity) {
                return false
            }
            _cartItems[itemIndex] = item.copy(quantity = newQuantity)
            return true
        }
        return false
    }
    
    // Get cart total
    fun getCartTotal(): Double {
        return _cartItems.sumOf { it.part.price * it.quantity }
    }
    
    // Get total items count
    fun getCartItemsCount(): Int {
        return _cartItems.sumOf { it.quantity }
    }
    
    // Clear cart
    fun clearCart() {
        _cartItems.clear()
    }
    
    // Check if part is in cart
    fun isPartInCart(partId: String): Boolean {
        return _cartItems.any { it.part.id == partId }
    }
    
    // Get item quantity in cart
    fun getItemQuantity(partId: String): Int {
        return _cartItems.find { it.part.id == partId }?.quantity ?: 0
    }
}
