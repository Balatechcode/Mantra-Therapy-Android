package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val MantraTherapyColorScheme = lightColorScheme(
    primary = SandalwoodPrimary,
    onPrimary = OnSandalwood,
    primaryContainer = SandalwoodContainer,
    onPrimaryContainer = OnSandalwoodContainer,

    secondary = OchreGoldSecondary,
    onSecondary = OnOchreGold,
    secondaryContainer = OchreGoldContainer,
    onSecondaryContainer = OnOchreGoldContainer,

    tertiary = BotanicalSageTertiary,
    onTertiary = OnBotanicalSage,
    tertiaryContainer = BotanicalSageContainer,
    onTertiaryContainer = OnBotanicalSageContainer,

    background = SacredParchmentBg,
    onBackground = DeepUmberText,
    surface = SacredParchmentSurface,
    onSurface = DeepUmberText,
    surfaceVariant = SurfaceContainerHighest,
    onSurfaceVariant = WarmClayMuted,

    outline = SubtleSandstoneOutline,
    outlineVariant = SubtleSandstoneBorder,
    error = ErrorRed,
    onError = OnError
)

@Composable
fun MantraTherapyTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MantraTherapyColorScheme,
        typography = Typography,
        content = content
    )
}
