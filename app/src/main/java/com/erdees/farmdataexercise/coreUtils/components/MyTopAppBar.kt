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
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.erdees.farmdataexercise.R
import com.erdees.farmdataexercise.coreUtils.utils.Screen
import com.erdees.farmdataexercise.ui.theme.BackgroundColor

@Composable
fun MyTopAppBar(modifier: Modifier = Modifier ,screen: Screen, navController: NavController) {

    var expandedMenu by remember { mutableStateOf(false) }

    TopAppBar(
        backgroundColor = BackgroundColor,
        title = {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = when(screen){
                        Screen.ProfileScreen -> stringResource(id = R.string.profile)
                        Screen.AddFarmDataScreen -> stringResource(id = R.string.add_farm_data)
                        else -> stringResource(id = R.string.select_farm)
                    }
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
                when(screen) {
                    Screen.SelectFarmScreen -> {
                        DropdownMenuItem(
                            onClick = {
                                navController.navigate(Screen.ProfileScreen.route)
                            }
                        ) {
                            Text(
                                text = stringResource(id = R.string.my_account)
                            )
                        }
                    }
                    Screen.SelectFarmDataScreen -> {
                        DropdownMenuItem(
                            onClick = {
                                navController.navigate(Screen.ProfileScreen.route)
                            }
                        ) {
                            Text(
                                text = stringResource(id = R.string.my_account)
                            )
                        }
                    }
                        else -> {DropdownMenuItem(
                            onClick = {
                                navController.navigate(Screen.SelectFarmScreen.route)
                            }
                        ) {
                            Text(
                                text = stringResource(id = R.string.browse_farm_data)
                            )
                        }}
                }


            }
        }
    )
}
