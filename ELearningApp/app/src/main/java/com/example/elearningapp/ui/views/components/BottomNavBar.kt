package com.example.elearningapp.ui.views.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.elearningapp.navigation.MenuNavDestination
import com.example.elearningapp.navigation.bottomNavScreens
import com.example.elearningapp.ui.theme.ELearningAppTheme

// Built with inspiration from: https://developer.android.com/codelabs/jetpack-compose-navigation#0

private val NavBarHeight = 80.dp
private const val fadeDuration = 100

@Composable
fun BottomNavBar(screens: List<MenuNavDestination>, onSelected: (MenuNavDestination) -> Unit, currentDestination: MenuNavDestination) {

    //NavBar surface
    Surface(
        Modifier
            .fillMaxWidth()
            .height(NavBarHeight), color = MaterialTheme.colors.secondary,
        shape = RoundedCornerShape(5.dp,5.dp),
        elevation = 12.dp
    ) {
        //Content row
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
    //Color and aspect values
    val greyColor = Color.LightGray
    val colorSelected = MaterialTheme.colors.primary
    val animationSpec = remember {
        tween<Color>(
            durationMillis = fadeDuration,
            easing = LinearEasing,
            delayMillis = fadeDuration
        )
    }
    val tabColor by animateColorAsState(
        targetValue = if (selected) colorSelected else greyColor,
        animationSpec = animationSpec
    )
    //Icon content column
    Column(modifier = Modifier
        .padding(16.dp)
        .animateContentSize()
        .height(NavBarHeight)
        .selectable(
            selected = selected,
            onClick = onSelected,
            role = Role.Tab
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(imageVector = icon, contentDescription = text, tint = tabColor)
        if (selected) {
            Spacer(Modifier.height(6.dp))
        }
        Text(text, color = tabColor)
    }
}


@Preview(showBackground = true)
@Composable
fun BottomNavBarPreview() {
    ELearningAppTheme {
        BottomNavBar(bottomNavScreens,{},MenuNavDestination.Overview)
    }
}