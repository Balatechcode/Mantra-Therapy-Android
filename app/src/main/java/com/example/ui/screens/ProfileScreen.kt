package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.data.PractitionerProfile
import com.example.ui.components.SacredMandalaWatermark
import com.example.ui.theme.BotanicalSageTertiary
import com.example.ui.theme.DeepUmberText
import com.example.ui.theme.DeepUmberTitle
import com.example.ui.theme.OchreGoldFixed
import com.example.ui.theme.OchreGoldSecondary
import com.example.ui.theme.PristineVellum
import com.example.ui.theme.SacredParchmentBg
import com.example.ui.theme.SandalwoodContainer
import com.example.ui.theme.SandalwoodPrimary
import com.example.ui.theme.SubtleSandstoneBorder
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerHighest
import com.example.ui.theme.WarmClaySubtle
import com.example.ui.theme.WarmGold

@Composable
fun ProfileScreen(
    profile: PractitionerProfile,
    totalRepetitions: Int,
    totalSessions: Int,
    vibrationEnabled: Boolean,
    onToggleVibration: () -> Unit,
    onSaveProfile: (name: String, path: String, time: String, avatar: String, dailyGoal: Int, sankalpa: String) -> Unit,
    onResetProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    var isEditProfileDialogOpen by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SacredParchmentBg)
            .testTag("profile_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Profile Header
            Box(modifier = Modifier.fillMaxWidth()) {
                SacredMandalaWatermark(
                    modifier = Modifier
                        .size(110.dp)
                        .align(Alignment.TopEnd),
                    color = SandalwoodPrimary,
                    alpha = 0.08f
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { isEditProfileDialogOpen = true }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(68.dp)
                                .clip(CircleShape)
                                .border(2.dp, WarmGold.copy(alpha = 0.8f), CircleShape)
                                .background(PristineVellum)
                                .padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                                    .background(SurfaceContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = profile.avatarSymbol,
                                    fontFamily = FontFamily.Serif,
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SandalwoodContainer
                                )
                            }
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = profile.name,
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 19.sp,
                                color = DeepUmberTitle
                            )
                            Text(
                                text = profile.path,
                                fontSize = 12.sp,
                                color = WarmClaySubtle
                            )
                            Text(
                                text = profile.practiceTime,
                                fontSize = 11.sp,
                                color = OchreGoldSecondary,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    // Edit Profile Button
                    OutlinedButton(
                        onClick = { isEditProfileDialogOpen = true },
                        modifier = Modifier
                            .testTag("edit_profile_button")
                            .padding(start = 6.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, SubtleSandstoneBorder),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = PristineVellum,
                            contentColor = SandalwoodPrimary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Edit Profile",
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Edit",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Daily Sankalpa / Intention Card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = PristineVellum,
                border = androidx.compose.foundation.BorderStroke(1.dp, SubtleSandstoneBorder),
                shadowElevation = 2.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Spa,
                                contentDescription = null,
                                tint = BotanicalSageTertiary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "DAILY SANKALPA (SACRED INTENTION)",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 1.sp,
                                color = WarmClaySubtle
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = OchreGoldFixed.copy(alpha = 0.5f)
                        ) {
                            Text(
                                text = "Goal: ${profile.dailyGoal} Beads",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = OchreGoldSecondary,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Text(
                        text = "\"${profile.sankalpa}\"",
                        fontFamily = FontFamily.Serif,
                        fontStyle = FontStyle.Italic,
                        fontSize = 13.sp,
                        lineHeight = 20.sp,
                        color = DeepUmberText
                    )
                }
            }

            // Stats Card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = PristineVellum,
                border = androidx.compose.foundation.BorderStroke(1.dp, SubtleSandstoneBorder),
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 14.dp, horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "REPETITIONS",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 0.8.sp,
                            color = WarmClaySubtle
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = "%,d".format(totalRepetitions),
                            fontFamily = FontFamily.Serif,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = SandalwoodPrimary
                        )
                    }

                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(28.dp)
                            .background(SubtleSandstoneBorder)
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "SESSIONS",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 0.8.sp,
                            color = WarmClaySubtle
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = "$totalSessions",
                            fontFamily = FontFamily.Serif,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = SandalwoodPrimary
                        )
                    }

                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(28.dp)
                            .background(SubtleSandstoneBorder)
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "DAILY TARGET",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 0.8.sp,
                            color = WarmClaySubtle
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = "${profile.dailyGoal}",
                            fontFamily = FontFamily.Serif,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = OchreGoldSecondary
                        )
                    }
                }
            }

            // Preferences
            Text(
                text = "PRACTICE PREFERENCES",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.sp,
                color = SandalwoodPrimary
            )

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = PristineVellum,
                border = androidx.compose.foundation.BorderStroke(1.dp, SubtleSandstoneBorder),
                shadowElevation = 2.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Vibration setting
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Vibration,
                                contentDescription = null,
                                tint = SandalwoodContainer,
                                modifier = Modifier.size(20.dp)
                            )
                            Column {
                                Text(
                                    text = "Bead Haptic Feedback",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = DeepUmberTitle
                                )
                                Text(
                                    text = "Gentle tactile click on each Japa tap",
                                    fontSize = 11.sp,
                                    color = WarmClaySubtle
                                )
                            }
                        }

                        Switch(
                            checked = vibrationEnabled,
                            onCheckedChange = { onToggleVibration() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = PristineVellum,
                                checkedTrackColor = SandalwoodContainer
                            ),
                            modifier = Modifier.testTag("vibration_switch")
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                            .height(1.dp)
                            .background(SubtleSandstoneBorder.copy(alpha = 0.6f))
                    )

                    // Daily Reminder
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Notifications,
                                contentDescription = null,
                                tint = OchreGoldSecondary,
                                modifier = Modifier.size(20.dp)
                            )
                            Column {
                                Text(
                                    text = "Daily Sadhana Reminder",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = DeepUmberTitle
                                )
                                Text(
                                    text = profile.practiceTime,
                                    fontSize = 11.sp,
                                    color = WarmClaySubtle
                                )
                            }
                        }

                        Text(
                            text = "Active",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = BotanicalSageTertiary
                        )
                    }
                }
            }

            // Sacred Lineage & Authorship Card
            Text(
                text = "SACRED LINEAGE & SOURCE",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.sp,
                color = SandalwoodPrimary
            )

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = PristineVellum,
                border = androidx.compose.foundation.BorderStroke(1.dp, SubtleSandstoneBorder),
                shadowElevation = 2.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.MenuBook,
                            contentDescription = null,
                            tint = SandalwoodContainer,
                            modifier = Modifier.size(22.dp)
                        )
                        Column {
                            Text(
                                text = "Mantra Therapy & Preksha Dhyana",
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp,
                                color = DeepUmberTitle
                            )
                            Text(
                                text = "Authored by Acharya Mahapragya",
                                fontSize = 12.sp,
                                color = OchreGoldSecondary
                            )
                        }
                    }

                    Text(
                        text = "This application presents authentic sacred chants, procedures, and meanings curated from Acharya Mahapragya's seminal literature on sound vibration, contemplation, and inner transformation.",
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        color = DeepUmberText
                    )
                }
            }

            // About App
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = SurfaceContainerHigh.copy(alpha = 0.5f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = null,
                        tint = WarmClaySubtle,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "Mantra Therapy v1.0 • Offline Digital Sadhana Sanctuary",
                        fontSize = 11.sp,
                        color = WarmClaySubtle
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Edit Profile Modal Dialog
        if (isEditProfileDialogOpen) {
            EditProfileDialog(
                currentProfile = profile,
                onDismiss = { isEditProfileDialogOpen = false },
                onSave = { name, path, time, avatar, goal, sankalpa ->
                    onSaveProfile(name, path, time, avatar, goal, sankalpa)
                    isEditProfileDialogOpen = false
                },
                onResetToDefaults = {
                    onResetProfile()
                    isEditProfileDialogOpen = false
                }
            )
        }
    }
}

@Composable
fun EditProfileDialog(
    currentProfile: PractitionerProfile,
    onDismiss: () -> Unit,
    onSave: (name: String, path: String, time: String, avatar: String, dailyGoal: Int, sankalpa: String) -> Unit,
    onResetToDefaults: () -> Unit
) {
    var name by remember { mutableStateOf(currentProfile.name) }
    var path by remember { mutableStateOf(currentProfile.path) }
    var practiceTime by remember { mutableStateOf(currentProfile.practiceTime) }
    var selectedAvatar by remember { mutableStateOf(currentProfile.avatarSymbol) }
    var dailyGoal by remember { mutableIntStateOf(currentProfile.dailyGoal) }
    var sankalpa by remember { mutableStateOf(currentProfile.sankalpa) }

    val dialogScrollState = rememberScrollState()

    AlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier
            .fillMaxWidth(0.94f)
            .widthIn(max = 520.dp)
            .testTag("edit_profile_dialog"),
        shape = RoundedCornerShape(20.dp),
        containerColor = PristineVellum,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Edit Profile",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        color = DeepUmberTitle
                    )
                    Text(
                        text = "Sadhaka identity & daily vows",
                        fontSize = 12.sp,
                        color = WarmClaySubtle
                    )
                }
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = WarmClaySubtle
                    )
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .verticalScroll(dialogScrollState)
                    .padding(vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Sacred Avatar Picker
                Text(
                    text = "SACRED GLYPH AVATAR",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.sp,
                    color = SandalwoodPrimary
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    PractitionerProfile.AVATAR_OPTIONS.forEach { glyph ->
                        val isSelected = selectedAvatar == glyph
                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .clip(CircleShape)
                                .border(
                                    width = if (isSelected) 2.dp else 1.dp,
                                    color = if (isSelected) SandalwoodContainer else SubtleSandstoneBorder,
                                    shape = CircleShape
                                )
                                .background(if (isSelected) OchreGoldFixed.copy(alpha = 0.35f) else SurfaceContainer)
                                .clickable { selectedAvatar = glyph }
                                .testTag("avatar_option_$glyph"),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = glyph,
                                fontSize = 20.sp,
                                color = if (isSelected) SandalwoodPrimary else DeepUmberText,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }

                // Practitioner Name Field
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "PRACTITIONER NAME",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 1.sp,
                        color = SandalwoodPrimary
                    )
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        placeholder = { Text("e.g. Sadhaka Practitioner") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("edit_profile_name_input"),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = SandalwoodContainer,
                            unfocusedBorderColor = SubtleSandstoneBorder,
                            focusedTextColor = DeepUmberTitle,
                            unfocusedTextColor = DeepUmberText
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                }

                // Spiritual Path / Tradition
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "SPIRITUAL PATH / TRADITION",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 1.sp,
                        color = SandalwoodPrimary
                    )
                    OutlinedTextField(
                        value = path,
                        onValueChange = { path = it },
                        placeholder = { Text("e.g. Preksha Meditation & Japa") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("edit_profile_path_input"),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = SandalwoodContainer,
                            unfocusedBorderColor = SubtleSandstoneBorder,
                            focusedTextColor = DeepUmberTitle,
                            unfocusedTextColor = DeepUmberText
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )

                    // Quick Path chips
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        PractitionerProfile.PATH_PRESETS.forEach { preset ->
                            FilterChip(
                                selected = path == preset,
                                onClick = { path = preset },
                                label = { Text(preset, fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = OchreGoldFixed,
                                    selectedLabelColor = SandalwoodPrimary
                                )
                            )
                        }
                    }
                }

                // Practice Muhurta / Time
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "PRACTICE MUHURTA (TIME)",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 1.sp,
                        color = SandalwoodPrimary
                    )
                    OutlinedTextField(
                        value = practiceTime,
                        onValueChange = { practiceTime = it },
                        placeholder = { Text("e.g. Brahma Muhurta Practice") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("edit_profile_time_input"),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = SandalwoodContainer,
                            unfocusedBorderColor = SubtleSandstoneBorder,
                            focusedTextColor = DeepUmberTitle,
                            unfocusedTextColor = DeepUmberText
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )

                    // Quick Time presets
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        PractitionerProfile.TIME_PRESETS.forEach { timeOption ->
                            FilterChip(
                                selected = practiceTime == timeOption,
                                onClick = { practiceTime = timeOption },
                                label = { Text(timeOption, fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = OchreGoldFixed,
                                    selectedLabelColor = SandalwoodPrimary
                                )
                            )
                        }
                    }
                }

                // Daily Japa Goal
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "DAILY REPETITION TARGET (BEADS)",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 1.sp,
                        color = SandalwoodPrimary
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        PractitionerProfile.GOAL_OPTIONS.forEach { goalOption ->
                            val isSelected = dailyGoal == goalOption
                            FilterChip(
                                selected = isSelected,
                                onClick = { dailyGoal = goalOption },
                                label = { Text("$goalOption Beads", fontSize = 12.sp) },
                                modifier = Modifier.weight(1f),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = SandalwoodContainer,
                                    selectedLabelColor = PristineVellum
                                )
                            )
                        }
                    }
                }

                // Personal Sankalpa / Sacred Intention
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "PERSONAL SANKALPA (SACRED INTENTION)",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 1.sp,
                        color = SandalwoodPrimary
                    )
                    OutlinedTextField(
                        value = sankalpa,
                        onValueChange = { sankalpa = it },
                        placeholder = { Text("What is your daily intention?") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("edit_profile_sankalpa_input"),
                        minLines = 2,
                        maxLines = 4,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = SandalwoodContainer,
                            unfocusedBorderColor = SubtleSandstoneBorder,
                            focusedTextColor = DeepUmberTitle,
                            unfocusedTextColor = DeepUmberText
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(name, path, practiceTime, selectedAvatar, dailyGoal, sankalpa)
                },
                colors = ButtonDefaults.buttonColors(containerColor = SandalwoodContainer),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.testTag("edit_profile_save_button")
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Save Profile", color = PristineVellum)
            }
        },
        dismissButton = {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                TextButton(
                    onClick = onResetToDefaults,
                    modifier = Modifier.testTag("edit_profile_reset_button")
                ) {
                    Icon(
                        imageVector = Icons.Filled.RestartAlt,
                        contentDescription = null,
                        tint = WarmClaySubtle,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text("Reset", color = WarmClaySubtle, fontSize = 12.sp)
                }

                OutlinedButton(
                    onClick = onDismiss,
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, SubtleSandstoneBorder),
                    modifier = Modifier.testTag("edit_profile_cancel_button")
                ) {
                    Text("Cancel", color = DeepUmberText)
                }
            }
        }
    )
}
