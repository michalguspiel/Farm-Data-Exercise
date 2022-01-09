package com.erdees.farmdataexercise.coreUtils.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.erdees.farmdataexercise.coreUtils.utils.Screen
import com.erdees.farmdataexercise.ui.theme.BackgroundColor

@Composable
fun MyTopAppBar(screen: String, navController: NavController) {

    var expandedMenu by remember { mutableStateOf(false) }

    TopAppBar(
        backgroundColor = BackgroundColor,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = screen
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = {
                            expandedMenu = !expandedMenu
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.MoreVert,
                            contentDescription = null,
                        )
                    }
                }
            }
        },
        actions = {
            DropdownMenu(
                expanded = expandedMenu,
                onDismissRequest = {
                    expandedMenu = !expandedMenu
                }
            ) {
                DropdownMenuItem(
                    onClick = {
                        navController.navigate(Screen.ProfileScreen.route)
                    }
                ) {
                    Text(
                        text = "My account"
                    )
                }
            }
        }
    )
}
