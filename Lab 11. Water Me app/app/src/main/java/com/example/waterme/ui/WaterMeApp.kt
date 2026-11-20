package com.example.waterme.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Forest
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.Yard
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.waterme.FIVE_SECONDS
import com.example.waterme.ONE_DAY
import com.example.waterme.R
import com.example.waterme.SEVEN_DAYS
import com.example.waterme.THIRTY_DAYS
import com.example.waterme.data.DataSource
import com.example.waterme.data.Reminder
import com.example.waterme.model.Plant
import com.example.waterme.ui.theme.PlantEvergreen
import com.example.waterme.ui.theme.PlantFlower
import com.example.waterme.ui.theme.PlantFruit
import com.example.waterme.ui.theme.PlantHouseplant
import com.example.waterme.ui.theme.PlantRoot
import com.example.waterme.ui.theme.PlantSucculent
import com.example.waterme.ui.theme.WaterMeTheme
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaterMeApp(waterViewModel: WaterViewModel = viewModel(factory = WaterViewModel.Factory)) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = stringResource(R.string.app_name),
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = stringResource(R.string.app_tagline),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.WaterDrop,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        PlantListContent(
            plants = waterViewModel.plants,
            onScheduleReminder = { reminder ->
                waterViewModel.scheduleReminder(reminder)
                scope.launch {
                    snackbarHostState.showSnackbar(
                        context.getString(
                            R.string.reminder_scheduled,
                            reminder.plantName,
                            context.getString(reminder.durationRes)
                        )
                    )
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun PlantListContent(
    plants: List<Plant>,
    onScheduleReminder: (Reminder) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedPlant by rememberSaveable { mutableStateOf(plants[0]) }
    var showReminderDialog by rememberSaveable { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = dimensionResource(id = R.dimen.padding_medium),
            top = dimensionResource(id = R.dimen.padding_small),
            end = dimensionResource(id = R.dimen.padding_medium),
            bottom = dimensionResource(id = R.dimen.padding_large)
        ),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        item {
            IntroCard()
        }
        items(items = plants, key = { it.name }) { plant ->
            PlantListItem(
                plant = plant,
                onItemSelect = { selected ->
                    selectedPlant = selected
                    showReminderDialog = true
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    if (showReminderDialog) {
        ReminderDialogContent(
            onDialogDismiss = { showReminderDialog = false },
            plantName = stringResource(selectedPlant.name),
            onScheduleReminder = onScheduleReminder
        )
    }
}

@Composable
private fun IntroCard(modifier: Modifier = Modifier) {
    val colors = MaterialTheme.colorScheme
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(Brush.linearGradient(listOf(colors.primary, colors.tertiary)))
            .padding(dimensionResource(id = R.dimen.padding_large))
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
            ) {
                Text(
                    text = stringResource(R.string.intro_title),
                    style = MaterialTheme.typography.headlineSmall,
                    color = colors.onPrimary
                )
                Text(
                    text = stringResource(R.string.intro_body),
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.onPrimary.copy(alpha = 0.88f)
                )
            }
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_medium)))
            Icon(
                imageVector = Icons.Filled.LocalFlorist,
                contentDescription = null,
                tint = colors.onPrimary.copy(alpha = 0.90f),
                modifier = Modifier.size(64.dp)
            )
        }
    }
}

@Composable
fun PlantListItem(
    plant: Plant,
    onItemSelect: (Plant) -> Unit,
    modifier: Modifier = Modifier
) {
    val accent = plantAccent(plant)

    ElevatedCard(
        modifier = modifier.clickable { onItemSelect(plant) },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(accent.copy(alpha = 0.16f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = plantIcon(plant),
                    contentDescription = null,
                    tint = accent,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_medium)))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = stringResource(plant.name),
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = stringResource(plant.type),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = stringResource(plant.description),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
            ) {
                WaterScheduleChip(scheduleRes = plant.schedule)
                Icon(
                    imageVector = Icons.Filled.ChevronRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Composable
private fun WaterScheduleChip(@StringRes scheduleRes: Int) {
    Surface(
        color = MaterialTheme.colorScheme.tertiaryContainer,
        contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
        shape = CircleShape
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.WaterDrop,
                contentDescription = null,
                modifier = Modifier.size(14.dp)
            )
            Text(
                text = "${stringResource(R.string.water)} ${stringResource(scheduleRes)}",
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Composable
fun ReminderDialogContent(
    onDialogDismiss: () -> Unit,
    plantName: String,
    onScheduleReminder: (Reminder) -> Unit,
    modifier: Modifier = Modifier
) {
    val reminders = listOf(
        Reminder(R.string.five_seconds, FIVE_SECONDS, TimeUnit.SECONDS, plantName),
        Reminder(R.string.one_day, ONE_DAY, TimeUnit.DAYS, plantName),
        Reminder(R.string.one_week, SEVEN_DAYS, TimeUnit.DAYS, plantName),
        Reminder(R.string.one_month, THIRTY_DAYS, TimeUnit.DAYS, plantName)
    )

    AlertDialog(
        onDismissRequest = onDialogDismiss,
        modifier = modifier,
        icon = {
            Icon(
                imageVector = Icons.Filled.WaterDrop,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        },
        title = {
            Text(
                text = stringResource(R.string.remind_me, plantName),
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                reminders.forEach { reminder ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                onScheduleReminder(reminder)
                                onDialogDismiss()
                            }
                            .padding(horizontal = 12.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Timer,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = stringResource(reminder.durationRes),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            imageVector = Icons.Filled.ChevronRight,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDialogDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

private fun plantIcon(plant: Plant): ImageVector = when (plant.type) {
    R.string.succulent -> Icons.Filled.Spa
    R.string.root -> Icons.Filled.Agriculture
    R.string.flower -> Icons.Filled.LocalFlorist
    R.string.houseplant -> Icons.Filled.Eco
    R.string.broadleaf_evergreen -> Icons.Filled.Forest
    R.string.fruit -> Icons.Filled.Yard
    else -> Icons.Filled.LocalFlorist
}

private fun plantAccent(plant: Plant): Color = when (plant.type) {
    R.string.succulent -> PlantSucculent
    R.string.root -> PlantRoot
    R.string.flower -> PlantFlower
    R.string.houseplant -> PlantHouseplant
    R.string.broadleaf_evergreen -> PlantEvergreen
    R.string.fruit -> PlantFruit
    else -> PlantSucculent
}

@Preview(showBackground = true, backgroundColor = 0xFFFBFDF6)
@Composable
private fun PlantListContentPreview() {
    WaterMeTheme {
        PlantListContent(
            plants = DataSource.plants,
            onScheduleReminder = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFBFDF6)
@Composable
private fun ReminderDialogPreview() {
    WaterMeTheme {
        ReminderDialogContent(
            onDialogDismiss = {},
            plantName = "Peony",
            onScheduleReminder = {}
        )
    }
}
