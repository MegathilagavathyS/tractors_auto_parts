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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.content.Intent
import android.net.Uri
import android.util.Log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceGarageScreen(
    onBack: () -> Unit,
    isCustomer: Boolean = true
) {
    val context = LocalContext.current

    // Sample service garage data
    val garageData = ServiceGarageData.getGarageInfo()

    fun makePhoneCall(phoneNumber: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            context.startActivity(intent)
            Log.d("ServiceGarage", "Initiating call to: $phoneNumber")
        } catch (e: Exception) {
            Log.e("ServiceGarage", "Error making phone call", e)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFF9800),
                        Color(0xFFF57C00)
                    )
                )
            )
    ) {
        // Header
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
                            tint = Color(0xFFFF9800)
                        )
                    }
                    
                    Text(
                        text = "Service Garage",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF9800)
                    )
                    
                    Icon(
                        Icons.Default.Build,
                        contentDescription = "Garage",
                        tint = Color(0xFFFF9800),
                        modifier = Modifier.size(32.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Find tractor service centers and contact our expert technicians",
                    fontSize = 14.sp,
                    color = Color(0xFF757575),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }

        // Service Garage Content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            // Main Garage Information
            item {
                GarageInfoCard(garage = garageData)
            }
            
            // Servants/Technicians (only for customers)
            if (isCustomer) {
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
                                    Icons.Default.People,
                                    contentDescription = "Technicians",
                                    tint = Color(0xFFFF9800),
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "Our Expert Technicians",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF212121)
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Text(
                                text = "Contact our certified tractor mechanics for professional service",
                                fontSize = 14.sp,
                                color = Color(0xFF757575)
                            )
                        }
                    }
                }
                
                items(garageData.technicians) { technician ->
                    TechnicianCard(
                        technician = technician,
                        onCall = { makePhoneCall(technician.phone) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GarageInfoCard(garage: ServiceGarage) {
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
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Garage,
                    contentDescription = "Garage",
                    tint = Color(0xFFFF9800),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = garage.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Basic Info
            InfoRow(
                icon = Icons.Default.LocationOn,
                label = "Address",
                value = garage.address
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            InfoRow(
                icon = Icons.Default.Phone,
                label = "Phone",
                value = garage.phone
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            InfoRow(
                icon = Icons.Default.Schedule,
                label = "Working Hours",
                value = garage.workingHours
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Expand/Collapse for more details
            Button(
                onClick = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF9800)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (expanded) "Collapse" else "Expand"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (expanded) "Show Less" else "Show More Details")
            }
            
            if (expanded) {
                Spacer(modifier = Modifier.height(16.dp))
                
                // Additional Details
                InfoRow(
                    icon = Icons.Default.Email,
                    label = "Email",
                    value = garage.email
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                InfoRow(
                    icon = Icons.Default.LocalOffer,
                    label = "Services",
                    value = garage.services.joinToString(", ")
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                InfoRow(
                    icon = Icons.Default.Star,
                    label = "Specialization",
                    value = garage.specialization
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TechnicianCard(
    technician: Technician,
    onCall: () -> Unit
) {
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
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                Color(0xFFFF9800).copy(alpha = 0.1f),
                                RoundedCornerShape(24.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Engineering,
                            contentDescription = "Technician",
                            tint = Color(0xFFFF9800),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Column {
                        Text(
                            text = technician.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF212121)
                        )
                        Text(
                            text = technician.role,
                            fontSize = 14.sp,
                            color = Color(0xFF757575)
                        )
                    }
                }
                
                IconButton(
                    onClick = onCall,
                    modifier = Modifier
                        .background(
                            Color(0xFF4CAF50).copy(alpha = 0.1f),
                            RoundedCornerShape(8.dp)
                        )
                ) {
                    Icon(
                        Icons.Default.Call,
                        contentDescription = "Call",
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Technician Details
            InfoRow(
                icon = Icons.Default.Phone,
                label = "Mobile",
                value = technician.phone
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            InfoRow(
                icon = Icons.Default.Work,
                label = "Experience",
                value = technician.experience
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            InfoRow(
                icon = Icons.Default.Build,
                label = "Specialization",
                value = technician.specialization
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Call Button
            Button(
                onClick = onCall,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    Icons.Default.Call,
                    contentDescription = "Call",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Call Now: ${technician.phone}")
            }
        }
    }
}

@Composable
private fun InfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = label,
            tint = Color(0xFFFF9800),
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
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF212121)
            )
        }
    }
}

// Data classes
data class ServiceGarage(
    val name: String,
    val address: String,
    val phone: String,
    val email: String,
    val workingHours: String,
    val services: List<String>,
    val specialization: String,
    val technicians: List<Technician>
)

data class Technician(
    val name: String,
    val role: String,
    val phone: String,
    val experience: String,
    val specialization: String
)

object ServiceGarageData {
    fun getGarageInfo(): ServiceGarage {
        return ServiceGarage(
            name = "Kumaravadivel Tractor Service Center",
            address = "17, Kuthiraipallam road, Kangayam, Tirupur - 638701, Tamil Nadu",
            phone = "+91 9847463378",
            email = "service@kumaravadiveltractors.com",
            workingHours = "Mon-Sat: 8:00 AM - 6:00 PM, Sun: 9:00 AM - 1:00 PM",
            services = listOf("Engine Repair", "Transmission Service", "Hydraulic Systems", "Electrical Work", "General Maintenance"),
            specialization = "All Brand Tractor Service & Repair",
            technicians = listOf(
                Technician(
                    name = "R. Kumar",
                    role = "Senior Mechanic",
                    phone = "+91 98765 43443",
                    experience = "15+ years",
                    specialization = "Engine & Transmission Specialist"
                ),
                Technician(
                    name = "M. Velu",
                    role = "Master Technician",
                    phone = "+91 98765 43212",
                    experience = "12+ years",
                    specialization = "Hydraulic & Electrical Systems"
                ),
                Technician(
                    name = "S. Arun",
                    role = "Service Engineer",
                    phone = "+91 98765 43213",
                    experience = "8+ years",
                    specialization = "General Maintenance & Diagnostics"
                ),
                Technician(
                    name = "K. Prakash",
                    role = "Junior Mechanic",
                    phone = "+91 98765 43214",
                    experience = "5+ years",
                    specialization = "Routine Service & Oil Change"
                )
            )
        )
    }
}
