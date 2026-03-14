@file:OptIn(ExperimentalMaterial3Api::class)

package com.kumaravadivel.tractorautoparts.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kumaravadivel.tractorautoparts.R

@Composable
fun HomeScreen(
    onSelectTractorModel: () -> Unit,
    onViewAutoParts: () -> Unit,
    onDealerInformation: () -> Unit,
    onProfile: () -> Unit,
    onPartsAdmin: () -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxSize()) {

        TopAppBar(
            title = {
                Text(
                    text = "Kumaravadivel Tractor Auto Parts",
                    fontWeight = FontWeight.Bold
                )
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                Text(
                    text = "Welcome to our Auto Parts Management System",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Medium
                )
            }

            items(
                getNavigationItems(
                    onSelectTractorModel,
                    onViewAutoParts,
                    onDealerInformation,
                    onProfile,
                    onPartsAdmin
                )
            ) { item ->
                NavigationCard(
                    title = stringResource(item.title),
                    description = item.description,
                    icon = item.icon,
                    onClick = item.onClick
                )
            }
        }
    }
}

data class NavigationItem(
    val title: Int,
    val description: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

private fun getNavigationItems(
    onSelectTractorModel: () -> Unit,
    onViewAutoParts: () -> Unit,
    onDealerInformation: () -> Unit,
    onProfile: () -> Unit,
    onPartsAdmin: () -> Unit
): List<NavigationItem> = listOf(
    NavigationItem(
        R.string.select_tractor_model,
        "Choose your tractor brand and model to find compatible parts",
        Icons.Default.Build,
        onSelectTractorModel
    ),
    NavigationItem(
        R.string.view_auto_parts,
        "Browse our extensive collection of tractor spare parts",
        Icons.Default.Settings,
        onViewAutoParts
    ),
    NavigationItem(
        R.string.dealer_information,
        "Find dealer locations and contact information",
        Icons.Default.Store,
        onDealerInformation
    ),
    NavigationItem(
        R.string.profile,
        "Manage your account and preferences",
        Icons.Default.Person,
        onProfile
    ),
    NavigationItem(
        R.string.profile, // Using existing string resource
        "Add sample parts to Firestore database",
        Icons.Default.Add,
        onPartsAdmin
    )
)

@Composable
private fun NavigationCard(
    title: String,
    description: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Icon(
                Icons.Default.KeyboardArrowRight,
                contentDescription = null
            )
        }
    }
}
