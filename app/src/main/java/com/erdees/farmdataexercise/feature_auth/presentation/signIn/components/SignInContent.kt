package com.erdees.farmdataexercise.feature_auth.presentation.signIn.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdees.farmdataexercise.coreUtils.Constants.CONTINUE_TAG
import com.erdees.farmdataexercise.coreUtils.Constants.SIGN_UP_TAG
import com.erdees.farmdataexercise.coreUtils.utils.Screen
import com.erdees.farmdataexercise.feature_auth.presentation.signIn.SignInViewModel
import com.erdees.farmdataexercise.ui.theme.*

@Composable
fun SignInContent(
    signInViewModel: SignInViewModel = hiltViewModel(),
    navController: NavController
) {

    val noAccountAnnotatedString = buildAnnotatedString {
        append("No account? ")
        pushStringAnnotation(SIGN_UP_TAG, "")
        withStyle(style = SpanStyle(Color.Blue)) { append("Sign Up!") }
        pop()
    }

    val continueAnonymouslyAnnotatedString = buildAnnotatedString {
        pushStringAnnotation(CONTINUE_TAG, "")
        withStyle(style = SpanStyle(Color.Blue)) { append("Continue without account!") }
        pop()
    }

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundColor)
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.padding(24.dp))
        Text(
            text = "SIGN IN",
            style = Typography.h4,
            textAlign = TextAlign.Center,
            color = OnPrimary,
        )
        Spacer(Modifier.padding(24.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.padding(2.dp),
            leadingIcon = { Icon(Icons.Outlined.MailOutline, "Email icon", tint = OnPrimary) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = OnPrimaryLightest,
                backgroundColor = Yellow300
            ), placeholder = { Text(text = "Enter your email") },
            singleLine = true
        )
        Spacer(Modifier.padding(12.dp))
        TextField(
            value = password, onValueChange = { password = it },
            leadingIcon = { Icon(Icons.Outlined.Lock, "Email icon", tint = OnPrimary) },
            textStyle = Typography.body2,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = OnPrimaryLightest,
                backgroundColor = Yellow300
            ), placeholder = { Text(text = "Enter your password") },
            singleLine = true
        )

        Spacer(Modifier.padding(12.dp))
        Button(
            onClick = {
                signInViewModel.signInWithEmail(
                    email,
                    password,
                )
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Yellow100),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 10.dp,
                pressedElevation = 16.dp,
                disabledElevation = 0.dp
            ),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                text = "Sign in",
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp),
                fontWeight = FontWeight.W500
            )
        }
        Spacer(modifier = Modifier.padding(6.dp))
        ClickableText(text = noAccountAnnotatedString, onClick = { offset ->
            noAccountAnnotatedString.getStringAnnotations(SIGN_UP_TAG, start = offset, end = offset)
                .firstOrNull().let {
                    navController.navigate(Screen.SignUpScreen.route)
                }
        })
        Spacer(modifier = Modifier.padding(6.dp))
        ClickableText(text = continueAnonymouslyAnnotatedString, onClick = { offset ->
            continueAnonymouslyAnnotatedString.getStringAnnotations(CONTINUE_TAG,start = offset, end = offset).firstOrNull().let {
                navController.navigate(Screen.SelectFarmDataScreen.route)
            }
        } )

    }
}
