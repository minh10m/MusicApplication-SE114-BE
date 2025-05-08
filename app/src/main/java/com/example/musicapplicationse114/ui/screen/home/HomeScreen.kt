package com.example.musicapplicationse114.ui.screen.home

import android.graphics.drawable.Icon
import android.media.Image
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.musicapplicationse114.MainViewModel
import com.example.musicapplicationse114.R
import com.example.musicapplicationse114.Screen
import com.example.musicapplicationse114.common.enum.TimeOfDay

@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel, mainViewModel: MainViewModel, username: String)
{
    val state = viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.setTimeOfDay()
        viewModel.updateUserName(username)
        Log.i("username", viewModel.getUserName())
        Log.i("timeOfDay", viewModel.getTimeOfDay().toString())
    }

    val greeting = when(state.value.timeOfDay){
        TimeOfDay.MORNING -> "Good Morning"
        TimeOfDay.AFTERNOON -> "Good Afternoon"
        TimeOfDay.EVENING -> "Good Evening"
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    )
    {
        //Icon(FontAwesomeIcons.Solid.Music, contentDescription = "Library")
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp,
            )
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            //gồm hi Logan, good evening, chuông và ảnh đại diện
            //Cố định trên mành hình không bị mất đi khi scroll dọc
            Row (verticalAlignment = Alignment.CenterVertically){
                Column(horizontalAlignment = Alignment.Start) {
                    Row (verticalAlignment = Alignment.CenterVertically){
                        //Spacer(modifier = Modifier.height(20.dp))
                        Image(
                            painter = painterResource(id = R.drawable.hello),
                            contentDescription = null,
                            modifier = Modifier
                                .size(23.dp)
                        )

                        Spacer(modifier = Modifier.width(2.dp))

                        Row() {
                            Text(
                                "Hi ${state.value.username},",
                                fontSize = 20.sp,
                                color = Color.White
                            )
                            Log.i("username", state.value.username)
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(greeting,
                        fontSize = 24.sp,
                        color = Color.White,
                        fontWeight = FontWeight.W700
                    )
                    Log.i("greeting", greeting)
                }
                Spacer(modifier = Modifier.width(95.dp))
                Image(painter = painterResource(id = R.drawable.bell),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            //do something
                        }
                )
                Spacer(modifier = Modifier.width(15.dp))
                Image(painter = painterResource(id = R.drawable.logan),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp))
            }

            Spacer(modifier = Modifier.height(30.dp))

            //gồm dòng chữ For you...
            //Cố định trên mành hình không bị mất đi khi scroll dọc
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box (modifier = Modifier.
                background(color = Color.DarkGray,
                    shape = RoundedCornerShape(18.dp))
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                ){
                    TextButton(onClick = {navController.navigate(Screen.Login.route)}) {Text(
                        "For you",
                        fontSize = 19.sp,
                        color = Color.LightGray
                    )
                    }
                }
                Spacer(modifier = Modifier.width(25.dp))
                Text("Relax",
                    fontSize = 19.sp,
                    color = Color.LightGray
                )
                Spacer(modifier = Modifier.width(25.dp))
                Text("Work out",
                    fontSize = 19.sp,
                    color = Color.LightGray)
                Spacer(modifier = Modifier.width(25.dp))
                Text("Travel",
                    maxLines = 1,
                    fontSize = 19.sp,
                    color = Color.LightGray)
            }

            Spacer(modifier = Modifier.height(30.dp))
            FeaturingToday()
            Spacer(modifier = Modifier.height(18.dp))
            RecentlyPlayed()
            Spacer(modifier = Modifier.height(12.dp))
            Box {
                MixedForYou()
                NavigationBar()
            }

        }

    }
}

@Composable
//Featuring Today
fun FeaturingToday()
{
    Image(painter = painterResource(R.drawable.feature),
        contentDescription = null,
        modifier = Modifier
            .size(width = 400.dp, height = 200.dp)
            .offset(x = -10.dp))
}

@Composable
fun RecentlyPlayed()
{
    Image(painter = painterResource(R.drawable.recent_play),
        contentDescription = null,
        modifier = Modifier
            .size(width = 400.dp, height = 200.dp)
            .offset(x = -10.dp))
}

@Composable
//Mixed for you
fun MixedForYou()
{
    Image(painter = painterResource(R.drawable.mix_for_you),
        contentDescription = null,
        modifier = Modifier
            .size(width = 480.dp, height = 250.dp)
            .offset(x = -10.dp))
}

@Composable
//Navigation bar
fun NavigationBar()
{
    Box (modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)
        .offset(y = 145.dp)
        .background(Color.Black.copy(alpha = 0.8f))
    ){
        Spacer(modifier = Modifier.height(20.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.width(60.dp))
            Column (horizontalAlignment = Alignment.CenterHorizontally){
                Icon(Icons.Filled.Home, contentDescription = null,tint = Color.LightGray, modifier = Modifier
                    .size(30.dp))
                Spacer(modifier = Modifier.height(3.dp))
                Text("Home",
                    fontSize = 12.sp,
                    color = Color.LightGray)
            }
            Spacer(modifier = Modifier.width(60.dp))
            Column (horizontalAlignment = Alignment.CenterHorizontally){
                Icon(Icons.Filled.Search, contentDescription = null,tint = Color.LightGray, modifier = Modifier
                    .size(30.dp))
                Spacer(modifier = Modifier.height(3.dp))
                Text("Search",
                    fontSize = 12.sp,
                    color = Color.LightGray)
            }
            Spacer(modifier = Modifier.width(60.dp))
            Column (horizontalAlignment = Alignment.CenterHorizontally){
                Icon(Icons.Filled.Menu, contentDescription = null,tint = Color.LightGray, modifier = Modifier
                    .size(30.dp))
                Spacer(modifier = Modifier.height(3.dp))
                Text("Library",
                    fontSize = 12.sp,
                    color = Color.LightGray)
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview()
{
    val navController = rememberNavController()
    val username = ""
    HomeScreen(navController = navController, viewModel = HomeViewModel(null, null), mainViewModel = MainViewModel(), username)
}