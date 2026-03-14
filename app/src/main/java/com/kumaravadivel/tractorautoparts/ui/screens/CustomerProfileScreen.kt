package com.kumaravadivel.tractorautoparts.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kumaravadivel.tractorautoparts.data.models.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerProfileScreen(
    user: User,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF9C27B0),
                        Color(0xFF7B1FA2)
                    )
                )
            )
    ) {
        // Profile Header
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .shadow(8.dp, RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF9C27B0)
                        )
                    }
                    
                    Text(
                        text = "My Profile",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF9C27B0)
                    )
                    
                    IconButton(onClick = { /* Edit profile */ }) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Color(0xFF9C27B0)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(20.dp))
                
                // User Info
                ProfileInfoRow(
                    icon = Icons.Default.Person,
                    label = "Name",
                    value = user.name.ifEmpty { "Not set" }
                )
                
                ProfileInfoRow(
                    icon = Icons.Default.Email,
                    label = "Email",
                    value = user.email
                )
                
                ProfileInfoRow(
                    icon = Icons.Default.Phone,
                    label = "Phone",
                    value = user.phoneNumber.ifEmpty { "Not set" }
                )
                
                ProfileInfoRow(
                    icon = Icons.Default.Home,
                    label = "Address",
                    value = user.address.ifEmpty { "Not set" }
                )
            }
        }

        // Built-in Parts Section
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(8.dp, RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Build,
                                contentDescription = "Parts",
                                tint = Color(0xFF9C27B0),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Built-in Parts Catalog",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF212121)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = "Essential parts available for each tractor brand:",
                            fontSize = 14.sp,
                            color = Color(0xFF757575)
                        )
                    }
                }
            }
            
            items(BuiltInPartsData.getAllBrandParts()) { brandParts ->
                BrandPartsCard(brandParts = brandParts)
            }
        }
    }
}

@Composable
private fun ProfileInfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = label,
            tint = Color(0xFF9C27B0),
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color.Gray
            )
            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF212121)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BrandPartsCard(
    brandParts: BrandParts
) {
    var expanded by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = brandParts.brandName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121)
                )
                
                Text(
                    text = "${brandParts.parts.size} parts",
                    fontSize = 12.sp,
                    color = Color(0xFF9C27B0),
                    modifier = Modifier
                        .background(
                            Color(0xFF9C27B0).copy(alpha = 0.1f),
                            RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            if (expanded) {
                brandParts.parts.forEach { part ->
                    PartItem(part = part)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            
            Button(
                onClick = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF9C27B0)
                )
            ) {
                Icon(
                    if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (expanded) "Collapse" else "Expand"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (expanded) "Show Less" else "Show Parts")
            }
        }
    }
}

@Composable
private fun PartItem(part: BuiltInPart) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color(0xFFF5F5F5),
                RoundedCornerShape(8.dp)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Default.Settings,
            contentDescription = "Part",
            tint = Color(0xFF757575),
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = part.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF212121)
            )
            Text(
                text = part.description,
                fontSize = 12.sp,
                color = Color(0xFF757575)
            )
        }
        Text(
            text = "₹${part.price}",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF4CAF50)
        )
    }
}

// Data classes for built-in parts
data class BrandParts(
    val brandName: String,
    val parts: List<BuiltInPart>
)

data class BuiltInPart(
    val name: String,
    val description: String,
    val price: Double
)

object BuiltInPartsData {
    fun getAllBrandParts(): List<BrandParts> = listOf(
        BrandParts(
            brandName = "Mahindra",
            parts = listOf(
                BuiltInPart("Engine Oil Filter", "High-quality oil filter for engine protection", 450.0),
                BuiltInPart("Air Filter", "Premium air filter for clean air intake", 320.0),
                BuiltInPart("Fuel Filter", "Efficient fuel filter for clean fuel supply", 380.0),
                BuiltInPart("Clutch Plate", "Durable clutch plate for smooth gear shifting", 850.0),
                BuiltInPart("Brake Shoes", "Reliable brake shoes for effective braking", 550.0),
                BuiltInPart("Spark Plug", "High-performance spark plug for efficient ignition", 180.0),
                BuiltInPart("Battery", "Long-lasting battery for reliable power", 4500.0),
                BuiltInPart("Starter Motor", "Powerful starter motor for reliable starting", 2800.0)
            )
        ),
        BrandParts(
            brandName = "John Deere",
            parts = listOf(
                BuiltInPart("Engine Oil Filter", "Advanced oil filter for John Deere engines", 480.0),
                BuiltInPart("Air Filter", "High-efficiency air filter system", 350.0),
                BuiltInPart("Fuel Filter", "Premium fuel filter for optimal performance", 420.0),
                BuiltInPart("Clutch Plate", "Heavy-duty clutch plate for John Deere", 920.0),
                BuiltInPart("Brake Shoes", "Industrial-grade brake shoes", 580.0),
                BuiltInPart("Spark Plug", "Performance spark plug set", 200.0),
                BuiltInPart("Battery", "Professional-grade battery", 4800.0),
                BuiltInPart("Radiator", "Heavy-duty aluminum radiator", 3200.0)
            )
        ),
        BrandParts(
            brandName = "Swaraj",
            parts = listOf(
                BuiltInPart("Engine Oil Filter", "Specialized oil filter for Swaraj engines", 420.0),
                BuiltInPart("Air Filter", "Efficient air filtration system", 300.0),
                BuiltInPart("Fuel Filter", "Reliable fuel filter for Swaraj tractors", 360.0),
                BuiltInPart("Clutch Plate", "Durable clutch plate for Swaraj models", 780.0),
                BuiltInPart("Brake Shoes", "High-quality brake shoes", 520.0),
                BuiltInPart("Spark Plug", "Efficient spark plug for Swaraj", 160.0),
                BuiltInPart("Headlight", "Bright LED headlight for Swaraj", 420.0),
                BuiltInPart("Alternator", "Reliable alternator for electrical system", 2200.0)
            )
        ),
        BrandParts(
            brandName = "TAFE",
            parts = listOf(
                BuiltInPart("Engine Oil Filter", "Robust oil filter for TAFE engines", 460.0),
                BuiltInPart("Air Filter", "Advanced air filter for TAFE models", 340.0),
                BuiltInPart("Fuel Filter", "High-quality fuel filter system", 400.0),
                BuiltInPart("Clutch Plate", "Industrial clutch plate for TAFE", 880.0),
                BuiltInPart("Brake Shoes", "Professional brake shoes", 600.0),
                BuiltInPart("Spark Plug", "Performance spark plug for TAFE", 190.0),
                BuiltInPart("Water Pump", "Reliable water pump for cooling system", 1800.0),
                BuiltInPart("Alternator", "Heavy-duty alternator for TAFE", 2400.0)
            )
        )
    )
}
