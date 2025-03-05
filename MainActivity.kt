package com.zybooks.quotable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zybooks.quotable.ui.theme.AboutScreen
import com.zybooks.quotable.ui.theme.AddQuoteScreen
import com.zybooks.quotable.ui.theme.FavoriteQuotesScreen
import com.zybooks.quotable.ui.theme.QuotableTheme
import com.zybooks.quotable.ui.theme.Quote
import com.zybooks.quotable.ui.theme.QuoteListScreen
import com.zybooks.quotable.ui.theme.QuotesListViewModel
import kotlinx.serialization.Serializable

sealed class Routes{
    @Serializable
    data object Home

    @Serializable
    data object Favorites

    @Serializable
    data object AddQuote

    @Serializable
    data object About
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var quotesListViewModel = QuotesListViewModel()
        enableEdgeToEdge()
        setContent {
            QuotableTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavBar(navController)
                    }
                ){
                    innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = Routes.Home,
                        modifier = Modifier.padding(innerPadding),

                    ) {
                            composable<Routes.Home> {
                                Home(quotesListViewModel::getQuoteList, quotesListViewModel::toggleFavoriteQuote, modifier = Modifier.padding(innerPadding))
                            }
                            composable<Routes.AddQuote> {
                                AddQuote(quotesListViewModel::addQuote,
                                    modifier = Modifier.padding(innerPadding))
                            }
                            composable<Routes.Favorites> {
                                Favorites(quotesListViewModel::getFavoriteQuotes,
                                    quotesListViewModel::removeFavoriteQuote,
                                    quotesListViewModel::toggleFavoriteQuote,
                                    modifier = Modifier.padding(innerPadding))
                            }
                            composable<Routes.About> {
                                About(modifier = Modifier.padding(innerPadding))
                            }
                }
            }
        }
    }
}

enum class AppScreen(val route: Any, val title:String, val icon: ImageVector){
    HOME(Routes.Home, "Home", Icons.Filled.Home),
    FAVORITES(Routes.Favorites, "Favorites", Icons.Filled.Favorite),
    ADDQUOTE(Routes.AddQuote, "Add", Icons.Filled.Add),
    DEVELOPER(Routes.About, "About", Icons.Filled.Info),
}

@Composable
fun BottomNavBar(
   navController: NavController,
   modifier: Modifier = Modifier
){
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    NavigationBar{
       AppScreen.entries.forEach{item ->
           NavigationBarItem(
               selected = currentRoute?.endsWith(item.route.toString())==true,
               onClick ={
                   navController.navigate(item.route){
                       popUpTo(navController.graph.startDestinationId)
                   }
               },
               icon = {
                   Icon(item.icon, contentDescription = item.title)
               },
               label = {
                   Text(item.title)
               }

           )
       }
    }
}

@Composable
fun Home(getQuotesList: () -> List<Quote>, toggleFavoriteQuote: (Quote) -> Unit, modifier: Modifier) {
    Text("Home")
    QuoteListScreen(getQuotesList, toggleFavoriteQuote)
}

@Composable
fun AddQuote(addQuote: (String, String) -> Unit, modifier: Modifier) {
    Text("Add Quote")
    AddQuoteScreen(addQuote)
}

@Composable
fun Favorites(getFavoriteQuotes: () -> List<Quote>, removeFavoriteQuote: (Quote) -> Unit, toggleFavoriteQuote: (Quote) -> Unit, modifier: Modifier) {
    Text("Favorites")
    FavoriteQuotesScreen(getFavoriteQuotes,removeFavoriteQuote, toggleFavoriteQuote)
}

@Composable
fun About(modifier: Modifier) {
    AboutScreen()
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun NavigationBars(
//    modifier:Modifier
//) {
//    val navController = rememberNavController()
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Bottom Nav Bar") }
//            )
//        },
//        bottomBar = {
//            BottomNavBar(navController)
//        }
//    )
//    { innerPadding ->
//        NavHost(
//            navController = navController,
//            startDestination = Routes.Home,
//            modifier = modifier.padding(innerPadding)
//        ) {
//            composable<Routes.Home> {
//                Home()
//            }
//            composable<Routes.AddQuote> {
//                AddQuote()
//            }
//            composable<Routes.Favorites> {
//                Favorites()
//            }
//            composable<Routes.About> {
//                About()
//            }
//        }
//    }
//    }
}
