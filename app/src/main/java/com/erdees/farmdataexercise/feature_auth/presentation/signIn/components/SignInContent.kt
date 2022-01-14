package com.erdees.farmdataexercise.feature_auth.presentation.signIn.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
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
import com.erdees.farmdataexercise.coreUtils.Constants.CONTINUE_TAG
import com.erdees.farmdataexercise.coreUtils.Constants.SIGN_IN_TAG
import com.erdees.farmdataexercise.coreUtils.TestTags
import com.erdees.farmdataexercise.coreUtils.components.MyButton
import com.erdees.farmdataexercise.coreUtils.utils.Screen
import com.erdees.farmdataexercise.feature_auth.presentation.signIn.SignInViewModel
import com.erdees.farmdataexercise.ui.theme.*

@Composable
fun SignInContent(
    viewModel: SignInViewModel = hiltViewModel(),
    navController: NavController
) {

    val noAccountAnnotatedString = buildAnnotatedString {
        append(stringResource(id = R.string.no_account))
        pushStringAnnotation(SIGN_IN_TAG, "")
        withStyle(style = SpanStyle(Color.Blue)) { append(stringResource(id = R.string.sign_up_excl)) }
        pop()
    }

    val continueAnonymouslyAnnotatedString = buildAnnotatedString {
        pushStringAnnotation(CONTINUE_TAG, "")
        withStyle(style = SpanStyle(Color.Blue)) { append(stringResource(id = R.string.continue_without_acc)) }
        pop()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundColor)
            .padding(LocalSpacing.current.medium)
            .verticalScroll(
                rememberScrollState()
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.padding(LocalSpacing.current.large))
        Text(
            text = stringResource(id = R.string.sign_in),
            style = Typography.h4,
            textAlign = TextAlign.Center,
            color = OnPrimary,
            modifier = Modifier.testTag(TestTags.SIGN_IN_TAG)
        )
        Spacer(Modifier.padding(LocalSpacing.current.default))
        TextField(
            value = viewModel.email.value,
            onValueChange = { viewModel.email.value = it },
            modifier = Modifier.padding(LocalSpacing.current.xxSmall),
            leadingIcon = { Icon(Icons.Outlined.MailOutline, stringResource(id = R.string.icon), tint = OnPrimary) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            shape = RoundedCornerShape(LocalCorner.current.default),
            colors = TextFieldDefaults.textFieldColors(
                textColor = OnPrimaryLightest,
                backgroundColor = Yellow300
            ), placeholder = { Text(text = stringResource(id = R.string.enter_mail)) },
            singleLine = true
        )
        Spacer(Modifier.padding(LocalSpacing.current.default))
        TextField(
            value = viewModel.password.value, onValueChange = { viewModel.password.value = it },
            leadingIcon = { Icon(Icons.Outlined.Lock, stringResource(id = R.string.icon), tint = OnPrimary) },
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

        Spacer(Modifier.padding(LocalSpacing.current.large))
        MyButton(onClick = {
            viewModel.signInWithEmail()
        }, text = stringResource(id = R.string.sign_in))
        Spacer(Modifier.padding(LocalSpacing.current.medium))
        ClickableText(
            text = noAccountAnnotatedString,
            style = Typography.body1,
            onClick = { offset ->
                noAccountAnnotatedString.getStringAnnotations(
                    SIGN_IN_TAG,
                    start = offset,
                    end = offset
                )
                    .firstOrNull().let {
                        navController.navigate(Screen.SignUpScreen.route)
                    }
            })
        Spacer(Modifier.padding(LocalSpacing.current.small))
        ClickableText(
            text = continueAnonymouslyAnnotatedString,
            style = Typography.body1,
            onClick = { offset ->
                continueAnonymouslyAnnotatedString.getStringAnnotations(
                    CONTINUE_TAG,
                    start = offset,
                    end = offset
                ).firstOrNull().let {
                    navController.navigate(Screen.SelectFarmScreen.route)
                }
            })

    }
}
