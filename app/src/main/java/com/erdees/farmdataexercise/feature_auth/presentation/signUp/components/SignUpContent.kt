package com.erdees.farmdataexercise.feature_auth.presentation.signUp.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdees.farmdataexercise.coreUtils.components.MyButton
import com.erdees.farmdataexercise.coreUtils.utils.Screen
import com.erdees.farmdataexercise.feature_auth.presentation.signUp.SignUpViewModel
import com.erdees.farmdataexercise.ui.theme.*

@Composable
fun SignUpContent(
    signUpViewModel: SignUpViewModel = hiltViewModel(),
    navController: NavController
) {

    val annotatedString = buildAnnotatedString {
        append("Got an account? ")
        pushStringAnnotation("sign_in", "")
        withStyle(style = SpanStyle(Color.Blue)) { append("Sign In!") }
        pop()
    }

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var confirmPassword by remember {
        mutableStateOf("")
    }
    var firstName by remember {
        mutableStateOf("")
    }
    var lastName by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundColor)
            .padding(12.dp)
            .verticalScroll(
                rememberScrollState()
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.padding(24.dp))
        Text(
            text = "SIGN UP",
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
        Column(Modifier.width(IntrinsicSize.Min)) {
            TextField(
                value = password, onValueChange = { password = it },
                leadingIcon = { Icon(Icons.Outlined.Lock, "Lock icon", tint = OnPrimary) },
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
            Text(
                text = "Make sure your password is longer than 7 characters and contains at least one digit.",
                style = Typography.caption,
                modifier = Modifier.padding(horizontal = 2.dp)
            )
        }
        Spacer(Modifier.padding(12.dp))
        Column() {
            TextField(
                value = confirmPassword, onValueChange = { confirmPassword = it },
                leadingIcon = { Icon(Icons.Outlined.Lock, "Lock icon", tint = OnPrimary) },
                visualTransformation = PasswordVisualTransformation(),
                textStyle = Typography.body2,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = OnPrimaryLightest,
                    backgroundColor = Yellow300
                ), placeholder = { Text(text = "Confirm your password") },
                singleLine = true
            )
            Text(text = "Make sure passwords are the same", style = Typography.caption,
                modifier = Modifier.padding(horizontal = 2.dp))
        }
        Spacer(Modifier.padding(12.dp))
        TextField(
            value = firstName, onValueChange = { firstName = it },
            leadingIcon = { Icon(Icons.Outlined.Person, "Person icon", tint = OnPrimary) },
            textStyle = Typography.body2,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = OnPrimaryLightest,
                backgroundColor = Yellow300
            ), placeholder = { Text(text = "Enter your first name") },
            singleLine = true
        )
        Spacer(Modifier.padding(12.dp))
        TextField(
            value = lastName, onValueChange = { lastName = it },
            leadingIcon = { Icon(Icons.Outlined.Person, "Person icon", tint = OnPrimary) },
            textStyle = Typography.body2,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = OnPrimaryLightest,
                backgroundColor = Yellow300
            ), placeholder = { Text(text = "Enter your last name") },
            singleLine = true
        )
        Spacer(Modifier.padding(12.dp))
        MyButton(onClick = {
            signUpViewModel.startSigningUpWithEmail(
                password,
                confirmPassword,
                email,
                firstName,
                lastName
            )
        }, text = "Sign up")
        Spacer(modifier = Modifier.padding(8.dp))
        ClickableText(text = annotatedString, style = Typography.body1, onClick = { offset ->
            annotatedString.getStringAnnotations("sign_in", start = offset, end = offset)
                .firstOrNull().let {
                    navController.navigate(Screen.SignInScreen.route)
                    Log.i("TAG", "Text Clicked!! Navigation goes!")
                }
        })
    }
}

