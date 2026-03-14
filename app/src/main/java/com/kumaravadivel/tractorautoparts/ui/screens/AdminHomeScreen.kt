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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kumaravadivel.tractorautoparts.data.models.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminHomeScreen(
    user: User,
    onPartsManagement: () -> Unit,
    onDealerInformation: () -> Unit,
    onProfile: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE91E63),
                        Color(0xFFC2185B)
                    )
                )
            )
    ) {
        // Admin Header
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
                        Icons.Default.AdminPanelSettings,
                        contentDescription = "Admin",
                        tint = Color(0xFFE91E63),
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Admin Panel",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = user.name.ifEmpty { "Administrator" },
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE91E63)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = "Manage inventory and system settings",
                    fontSize = 16.sp,
                    color = Color(0xFF424242),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }

        // Admin Navigation Cards
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(getAdminNavigationItems(
                onPartsManagement = onPartsManagement,
                onDealerInformation = onDealerInformation,
                onProfile = onProfile
            )) { item ->
                AdminNavigationCard(
                    title = item.title,
                    description = item.description,
                    icon = item.icon,
                    color = item.color,
                    badge = item.badge,
                    onClick = item.onClick
                )
            }
        }
    }
}

data class AdminNavigationItem(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val color: Color,
    val badge: String? = null,
    val onClick: () -> Unit
)

private fun getAdminNavigationItems(
    onPartsManagement: () -> Unit = {},
    onDealerInformation: () -> Unit = {},
    onProfile: () -> Unit = {}
): List<AdminNavigationItem> = listOf(
    AdminNavigationItem(
        title = "Parts Management",
        description = "Add, edit, and delete tractor parts inventory",
        icon = Icons.Default.Inventory,
        color = Color(0xFF4CAF50),
        badge = "ADMIN",
        onClick = onPartsManagement
    ),
    AdminNavigationItem(
        title = "Dealer Information",
        description = "Manage dealer locations and contact details",
        icon = Icons.Default.Store,
        color = Color(0xFFFF9800),
        onClick = onDealerInformation
    ),
    AdminNavigationItem(
        title = "Admin Profile",
        description = "View admin account and system settings",
        icon = Icons.Default.AdminPanelSettings,
        color = Color(0xFF9C27B0),
        onClick = onProfile
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AdminNavigationCard(
    title: String,
    description: String,
    icon: ImageVector,
    color: Color,
    badge: String? = null,
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
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF212121)
                    )
                    
                    badge?.let {
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp)),
                            color = color.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = it,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = color,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
                
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
