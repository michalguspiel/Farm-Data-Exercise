package com.erdees.farmdataexercise.feature_auth.presentation.signUp.components

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erdees.farmdataexercise.R
import com.erdees.farmdataexercise.coreUtils.Constants.SIGN_UP_TAG
import com.erdees.farmdataexercise.coreUtils.components.MyButton
import com.erdees.farmdataexercise.coreUtils.utils.Screen
import com.erdees.farmdataexercise.feature_auth.presentation.signUp.SignUpViewModel
import com.erdees.farmdataexercise.ui.theme.*

@Composable
fun SignUpContent(
    viewModel: SignUpViewModel = hiltViewModel(),
    navController: NavController
) {

    val annotatedString = buildAnnotatedString {
        append(stringResource(id = R.string.got_an_account))
        pushStringAnnotation(SIGN_UP_TAG, "")
        withStyle(style = SpanStyle(Color.Blue)) { append(stringResource(id = R.string.sign_in_excl)) }
        pop()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundColor)
            .padding(LocalSpacing.current.default)
            .verticalScroll(
                rememberScrollState()
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.padding(LocalSpacing.current.large))
        Text(
            text = stringResource(id = R.string.sign_up),
            style = Typography.h4,
            textAlign = TextAlign.Center,
            color = OnPrimary,
        )
        Spacer(Modifier.padding(LocalSpacing.current.large))
        TextField(
            value = viewModel.email.value,
            onValueChange = { viewModel.email.value = it },
            modifier = Modifier.padding(LocalSpacing.current.xxSmall),
            leadingIcon = {
                Icon(
                    Icons.Outlined.MailOutline,
                    stringResource(id = R.string.icon),
                    tint = OnPrimary
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            shape = RoundedCornerShape(LocalCorner.current.default),
            colors = TextFieldDefaults.textFieldColors(
                textColor = OnPrimaryLightest,
                backgroundColor = Yellow300
            ), placeholder = { Text(text = stringResource(id = R.string.enter_mail)) },
            singleLine = true
        )
        Spacer(Modifier.padding(LocalSpacing.current.default))
        Column(Modifier.width(IntrinsicSize.Min)) {
            TextField(
                value = viewModel.password.value, onValueChange = { viewModel.password.value = it },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Lock,
                        stringResource(id = R.string.icon),
                        tint = OnPrimary
                    )
                },
                textStyle = Typography.body2,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(LocalCorner.current.default),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = OnPrimaryLightest,
                    backgroundColor = Yellow300
                ), placeholder = { Text(text = stringResource(id = R.string.enter_password)) },
                singleLine = true
            )
            Text(
                text = stringResource(id = R.string.password_req),
                style = Typography.caption,
                modifier = Modifier.padding(horizontal = LocalSpacing.current.xxSmall)
            )
        }
        Spacer(Modifier.padding(LocalSpacing.current.default))
        Column {
            TextField(
                value = viewModel.confirmPassword.value, onValueChange = { viewModel.confirmPassword.value = it },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Lock,
                        stringResource(id = R.string.icon),
                        tint = OnPrimary
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                textStyle = Typography.body2,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                shape = RoundedCornerShape(LocalCorner.current.default),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = OnPrimaryLightest,
                    backgroundColor = Yellow300
                ), placeholder = { Text(text = stringResource(id = R.string.confirm_password)) },
                singleLine = true
            )
            Text(
                text = stringResource(id = R.string.make_sure_passwords_are_same),
                style = Typography.caption,
                modifier = Modifier.padding(horizontal = LocalSpacing.current.xxSmall)
            )
        }
        Spacer(Modifier.padding(LocalSpacing.current.default))
        TextField(
            value = viewModel.firstName.value, onValueChange = { viewModel.firstName.value = it },
            leadingIcon = {
                Icon(
                    Icons.Outlined.Person,
                    stringResource(id = R.string.icon),
                    tint = OnPrimary
                )
            },
            textStyle = Typography.body2,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            shape = RoundedCornerShape(LocalCorner.current.default),
            colors = TextFieldDefaults.textFieldColors(
                textColor = OnPrimaryLightest,
                backgroundColor = Yellow300
            ), placeholder = { Text(text = stringResource(R.string.enter_first_name)) },
            singleLine = true
        )
        Spacer(Modifier.padding(LocalSpacing.current.default))
        TextField(
            value = viewModel.lastName.value, onValueChange = { viewModel.lastName.value = it },
            leadingIcon = {
                Icon(
                    Icons.Outlined.Person,
                    stringResource(id = R.string.icon),
                    tint = OnPrimary
                )
            },
            textStyle = Typography.body2,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            shape = RoundedCornerShape(LocalCorner.current.default),
            colors = TextFieldDefaults.textFieldColors(
                textColor = OnPrimaryLightest,
                backgroundColor = Yellow300
            ), placeholder = { Text(text = stringResource(R.string.enter_last_name)) },
            singleLine = true
        )
        Spacer(Modifier.padding(LocalSpacing.current.default))
        MyButton(onClick = {
            viewModel.startSigningUpWithEmail()
        }, text = stringResource(R.string.sign_up))
        Spacer(modifier = Modifier.padding(LocalSpacing.current.default))
        ClickableText(text = annotatedString, style = Typography.body1, onClick = { offset ->
            annotatedString.getStringAnnotations(SIGN_UP_TAG, start = offset, end = offset)
                .firstOrNull().let {
                    navController.navigate(Screen.SignInScreen.route)
                }
        })
    }
}

