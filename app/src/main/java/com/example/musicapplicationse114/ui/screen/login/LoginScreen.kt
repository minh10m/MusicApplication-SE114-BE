package com.example.musicapplicationse114.ui.screen.login

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicapplicationse114.R

@Composable
fun LoginScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top // Top để mình kiểm soát thứ tự dễ hơn
        ) {
            Spacer(modifier = Modifier.height(80.dp)) // Cách lề trên

            // Logo
            Image(
                painter = painterResource(id = R.drawable.musico_with_icons),
                contentDescription = "Musico logo with icons",
                modifier = Modifier
                    .size(width = 214.dp, height = 74.dp)
            )

            Spacer(modifier = Modifier.height(100.dp)) // Khoảng cách dưới logo

            // Login Text
            Row()
            {
                Text(
                    text = "Login",
                    fontSize = 30.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )

                Spacer(modifier = Modifier.width(5.dp))

                // Icon
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "AccountCircle",
                    modifier = Modifier.size(35.dp),
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sign in Text
            Text(
                text = "Please sign in to continue.",
                fontSize = 20.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(60.dp))

            Email()

            Spacer(modifier = Modifier.height(20.dp))

            Password()

            Spacer(modifier = Modifier.height(28.dp))

            Button(onClick = {}) {
                Row {
                    Text("Sign In",
                        fontSize = 20.sp)

                    Spacer(modifier = Modifier.width(5.dp))

                    Icon(Icons.Filled.ArrowForward, contentDescription = null)
                }
            }

            Spacer(modifier = Modifier.height(120.dp))

            Row (verticalAlignment = Alignment.CenterVertically){
                Text("Don't have an account?",
                    fontSize = 20.sp,
                    color = Color.Gray)
                Spacer(modifier = Modifier.width(1.5.dp))
                TextButton(onClick = {}) {Text("Sign Up",
                    fontSize = 22.sp) }
            }

        }
    }
}

@Composable
fun Email()
{
    var text by remember { mutableStateOf("") }
    //val focusRequester = FocusRequester()
    TextField(
        value = text, onValueChange = {
            text = it },
        label = {Text("Account")},
        leadingIcon = {Icon(Icons.Filled.Person, contentDescription = null)},
//        trailingIcon = { IconButton(onClick = {text = "";focusRequester.requestFocus()}){
//            Icon(Icons.Filled.Clear, contentDescription = null)
//        } },
        modifier = Modifier
            .shadow(25.dp, shape = RoundedCornerShape(20.dp), )
    )
}

fun Icon(imageVector: Unit) {

}

@Composable
fun Password()
{
    var text by remember { mutableStateOf("") }
    //val focusRequester = FocusRequester()
    var isShowPassword by remember { mutableStateOf(false) }
    TextField(
        value = text, onValueChange = {
            text = it },
        label = {Text("Password")},
        visualTransformation = if(isShowPassword) VisualTransformation.None
        else PasswordVisualTransformation(),
        leadingIcon = {Icon(Icons.Filled.Lock, contentDescription = null)},
        trailingIcon = { IconButton(onClick = {isShowPassword = !isShowPassword}){
            Icon(imageVector = if(isShowPassword) Icons.Filled.CheckCircle else Icons.Filled.Check,
                contentDescription = null)
        } },
        modifier = Modifier.shadow(25.dp, shape = RoundedCornerShape(20.dp))
    )
}
fun TextField() {
    TODO("Not yet implemented")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview()
{
    LoginScreen()
}