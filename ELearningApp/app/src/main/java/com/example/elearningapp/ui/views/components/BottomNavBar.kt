package com.example.elearningapp.ui.views.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.elearningapp.NavDestination

private val NavBarHeight = 80.dp
private const val colorOpacity = 0.20f
private const val fadeDuration = 100

    @Composable
fun BottomNavBar(screens: List<NavDestination>, onSelected: (NavDestination) -> Unit, currentDestination: NavDestination) {
    Surface(
        Modifier
            .fillMaxWidth()
            .height(NavBarHeight), color = MaterialTheme.colors.secondary,
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.onSurface.copy(alpha = colorOpacity)),
        shape = RoundedCornerShape(5.dp,5.dp)
    ) {
            Row(Modifier.selectableGroup(), horizontalArrangement = Arrangement.SpaceEvenly) {
                screens.forEach { screen ->
                    NavBarTab(
                        text = screen.label,
                        icon = screen.icon,
                        onSelected = {onSelected(screen)},
                        selected = currentDestination == screen
                    )
                }
            }
        }
}

@Composable
private fun NavBarTab(text: String, icon: ImageVector, onSelected: () -> Unit, selected: Boolean) {
    val greyColor = MaterialTheme.colors.onSurface.copy(alpha = colorOpacity)
    val colorSelected = MaterialTheme.colors.primary
    val tabColor = if (selected) colorSelected else greyColor
    Column(modifier = Modifier
        .padding(16.dp)
        .animateContentSize()
        .height(NavBarHeight)
        .selectable(
            selected = selected,
            onClick = onSelected,
            role = Role.Tab,
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(
                bounded = false,
                radius = Dp.Unspecified,
                color = Color.Unspecified
            )
        )
        .clearAndSetSemantics { contentDescription = text },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(imageVector = icon, contentDescription = text, tint = tabColor)
        if (selected) {
            Spacer(Modifier.height(6.dp))
        }
        Text(text, color = tabColor)
    }
}