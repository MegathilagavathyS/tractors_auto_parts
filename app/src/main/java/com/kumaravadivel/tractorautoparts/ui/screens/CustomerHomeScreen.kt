package com.kumaravadivel.tractorautoparts.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kumaravadivel.tractorautoparts.R
import com.kumaravadivel.tractorautoparts.data.models.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerHomeScreen(
    user: User,
    onSelectTractorModel: () -> Unit,
    onViewAutoParts: () -> Unit,
    onDealerInformation: () -> Unit,
    onProfile: () -> Unit,
    onViewCart: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1E88E5),
                        Color(0xFF1565C0)
                    )
                )
            )
    ) {
        // Header with greeting
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
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = Color(0xFF1976D2),
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Welcome back,",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = user.name.ifEmpty { "Customer" },
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1976D2)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = "Find the perfect parts for your tractor",
                    fontSize = 16.sp,
                    color = Color(0xFF424242),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }

        // Navigation Cards
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(getCustomerNavigationItems(
                onSelectTractorModel = onSelectTractorModel,
                onViewAutoParts = onViewAutoParts,
                onDealerInformation = onDealerInformation,
                onViewCart = onViewCart,
                onProfile = onProfile
            )) { item ->
                CustomerNavigationCard(
                    title = item.title,
                    description = item.description,
                    icon = item.icon,
                    color = item.color,
                    onClick = item.onClick
                )
            }
        }
    }
}

data class CustomerNavigationItem(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val color: Color,
    val onClick: () -> Unit
)

private fun getCustomerNavigationItems(
    onSelectTractorModel: () -> Unit = {},
    onViewAutoParts: () -> Unit = {},
    onDealerInformation: () -> Unit = {},
    onViewCart: () -> Unit = {},
    onProfile: () -> Unit = {}
): List<CustomerNavigationItem> = listOf(
    CustomerNavigationItem(
        title = "Select Tractor Model",
        description = "Choose your tractor brand and model to find compatible parts",
        icon = Icons.Default.Build,
        color = Color(0xFF4CAF50),
        onClick = onSelectTractorModel
    ),
    CustomerNavigationItem(
        title = "View Auto Parts",
        description = "Browse our extensive collection of tractor spare parts",
        icon = Icons.Default.Settings,
        color = Color(0xFF2196F3),
        onClick = onViewAutoParts
    ),
    CustomerNavigationItem(
        title = "Shopping Cart",
        description = "View your cart and proceed to checkout",
        icon = Icons.Default.ShoppingCart,
        color = Color(0xFFFF5722),
        onClick = onViewCart
    ),
    CustomerNavigationItem(
        title = "Dealer Information",
        description = "Find dealer locations and contact information",
        icon = Icons.Default.Store,
        color = Color(0xFFFF9800),
        onClick = onDealerInformation
    ),
    CustomerNavigationItem(
        title = "My Profile",
        description = "View your account details and preferences",
        icon = Icons.Default.Person,
        color = Color(0xFF9C27B0),
        onClick = onProfile
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomerNavigationCard(
    title: String,
    description: String,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .shadow(12.dp, RoundedCornerShape(16.dp)),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        color.copy(alpha = 0.1f),
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    icon,
                    contentDescription = title,
                    tint = color,
                    modifier = Modifier.size(32.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(20.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = Color(0xFF757575),
                    lineHeight = 18.sp
                )
            }
            
            Icon(
                Icons.Default.ArrowForward,
                contentDescription = "Navigate",
                tint = Color(0xFFBDBDBD),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
