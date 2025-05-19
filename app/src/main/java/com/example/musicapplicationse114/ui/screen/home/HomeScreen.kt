package com.example.musicapplicationse114.ui.screen.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.musicapplicationse114.MainViewModel
import com.example.musicapplicationse114.R
import com.example.musicapplicationse114.common.enum.TimeOfDay
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class TabItem(val text : String, val screen : @Composable () -> Unit)

@Composable
fun Home() {
    Column() {
        //featuring today
        Image(
            painter = painterResource(R.drawable.feature),
            contentDescription = null,
            modifier = Modifier
                .size(width = 400.dp, height = 200.dp)
                .offset(x = -10.dp)
        )

//    Spacer(modifier = Modifier.height(0.dp))

//            recently Played
        Image(
            painter = painterResource(R.drawable.recent_play),
            contentDescription = null,
            modifier = Modifier
                .size(width = 400.dp, height = 180.dp)
                .offset(x = -10.dp)
        )

        Spacer(modifier = Modifier.height(0.dp))
        Box {
            //Mix for you
            Image(
                painter = painterResource(R.drawable.mix_for_you),
                contentDescription = null,
                modifier = Modifier
                    .size(width = 480.dp, height = 250.dp)
                    .offset(x = -10.dp)
            )

            //navigation bar tam thoi

        }
    }
}

@Composable
fun Relax1() {
    Column {
        Image(
            painter = painterResource(R.drawable.relax),
            contentDescription = null,
            modifier = Modifier
                .size(width = 1000.dp, height = 1000.dp)
                .offset(x = -10.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .offset(y = 575.dp)
                .background(Color.Black.copy(alpha = 0.8f))
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.width(60.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Filled.Home,
                        contentDescription = null,
                        tint = Color.LightGray,
                        modifier = Modifier
                            .size(30.dp)
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        "Home",
                        fontSize = 12.sp,
                        color = Color.LightGray
                    )
                }
                Spacer(modifier = Modifier.width(60.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = null,
                        tint = Color.LightGray,
                        modifier = Modifier
                            .size(30.dp)
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        "Search",
                        fontSize = 12.sp,
                        color = Color.LightGray
                    )
                }
                Spacer(modifier = Modifier.width(60.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Filled.Menu,
                        contentDescription = null,
                        tint = Color.LightGray,
                        modifier = Modifier
                            .size(30.dp)
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        "Library",
                        fontSize = 12.sp,
                        color = Color.LightGray
                    )
                }
            }
        }
    }
}

@Composable
fun Workout()
{

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel, mainViewModel: MainViewModel, username : String) {
    val state = viewModel.uiState.collectAsState()
    var tabIndex by rememberSaveable { mutableStateOf(0) }

    var tabs = listOf(
        TabItem("For you",) {
            Home()
        },
        TabItem("Relax",) {
            Relax1()
        },
        TabItem("Workout") {
            Workout()
        },
        TabItem("Travel") {
            Workout()
        }
    )

    var pagerState = rememberPagerState(
        pageCount = { tabs.size - 2 }
    )
    var showLoading by remember { mutableStateOf(false) }

    // Khi showLoading = true, hiển thị loading indicator
    if (showLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    // Tắt loading sau 1 giây
    LaunchedEffect(showLoading) {
        if (showLoading) {
            delay(1000)
            showLoading = false
        }
    }
    var coroutineScope = rememberCoroutineScope()
    LaunchedEffect(username) {
        viewModel.setTimeOfDay()
        viewModel.updateUserName(username)
        Log.i("username", viewModel.getUserName())
        Log.i("timeOfDay", viewModel.getTimeOfDay().toString())
    }

    val greeting = when (state.value.timeOfDay) {
        TimeOfDay.MORNING -> "Good Morning"
        TimeOfDay.AFTERNOON -> "Good Afternoon"
        TimeOfDay.EVENING -> "Good Evening"
    }
    Scaffold(containerColor = Color.Black,
        bottomBar = {NavigationBar(navController){showLoading = true} }
    ) { innerPadding ->
//    Box(modifier = Modifier.fillMaxSize()) {
//        Surface(
//            modifier = Modifier.fillMaxSize(),
//            color = Color.Black
//        )
//        {
        //Icon(FontAwesomeIcons.Solid.Music, contentDescription = "Library")
        Column(
            modifier = Modifier
                .padding(
                    start = 20.dp,
                )
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            //gồm hi Logan, good evening, chuông và ảnh đại diện
            //Cố định trên mành hình không bị mất đi khi scroll dọc
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(horizontalAlignment = Alignment.Start) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        //Spacer(modifier = Modifier.height(20.dp))
                        Image(
                            painter = painterResource(id = R.drawable.hello),
                            contentDescription = null,
                            modifier = Modifier
                                .size(23.dp)
                        )
//
//                            Spacer(modifier = Modifier.width(5.dp))

                        Row() {
                            Text(
                                "Hi ${state.value.username},",
                                fontSize = 18.sp,
                                color = Color.White
                            )
                            Log.i("username", state.value.username)
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        greeting,
                        fontSize = 21.sp,
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
                Image(
                    painter = painterResource(id = R.drawable.logan),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                )
            }
            Spacer(modifier = Modifier.height(15.dp))

            TabRow(
                selectedTabIndex = tabIndex,
                modifier = Modifier.background(Color.Black),
                containerColor = Color.Black,
                contentColor = Color.LightGray,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                        color = Color.DarkGray, // Hoặc màu bạn muốn
                        height = 3.dp
                        // Chiều cao của indicator
                    )
                },
                divider = {}
            ) {
                tabs.forEachIndexed { index, tabItem ->
                    val isSelected = tabIndex == index
                    Tab(
                        modifier = Modifier.width(30.dp),
                        selected = isSelected,
                        onClick = {
                            if (index == 0 || index == 1) {
                                tabIndex = index
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            }
                        },
                        text = { Box() { Text(tabItem.text, fontSize = 15.sp) } })
                }
            }

            Spacer(modifier = Modifier.height(5.dp))
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                userScrollEnabled = false, verticalAlignment = Alignment.Top
            ) { page ->
                when (page) {
                    0 -> Home()
                    1 -> Relax1()
                    else -> {}
                }
            }
        }
    }
}


@Composable
fun NavigationBar(navController: NavController, onHomeReselected: () -> Unit) {
    val currentRoute = currentRoute(navController = navController)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent) // 👈 Nền trong suốt hoàn toàn
            .padding(bottom = 16.dp)       // 👈 Dưới một chút để tránh đụng gesture bar
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp), // 👈 Thêm padding cho gọn gàng thay vì height cứng
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavBarItem(
                icon = Icons.Filled.Home,
                label = "Home",
                selected = currentRoute == "home",
                onClick = {
                    if (currentRoute == "home") onHomeReselected()
                    else navController.navigate("home") {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )

            NavBarItem(
                icon = Icons.Filled.Search,
                label = "Search",
                selected = currentRoute == "search",
                onClick = {
                    navController.navigate("search") {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )

            NavBarItem(
                icon = Icons.Filled.Menu,
                label = "Library",
                selected = currentRoute == "library",
                onClick = {
                    navController.navigate("library") {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun NavBarItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val color = if (selected) Color.White else Color.LightGray

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(onClick = onClick) {
            Icon(icon, contentDescription = label, tint = color, modifier = Modifier.size(30.dp))
        }
        Text(label, fontSize = 12.sp, color = color)
    }
}


@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview()
{
    val navController = rememberNavController()
    val username = ""
    HomeScreen(navController = navController, viewModel = HomeViewModel(null, null), mainViewModel = MainViewModel(), username)
}