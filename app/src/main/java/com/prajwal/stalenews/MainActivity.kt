package com.prajwal.stalenews

import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.prajwal.stalenews.presentation.ui.FilterChips
import com.prajwal.stalenews.presentation.ui.NewsList
import com.prajwal.stalenews.presentation.viewmodels.NewsVM
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[NewsVM::class]
        viewModel.getTopHeadlines()
        setContent {
            val navController = rememberNavController()
            NavHost(navController, startDestination = "news") {
                composable("news") { NewsScreen(viewModel, navController = navController) }
                composable("webview/{url}") { backStackEntry ->
                    val url = backStackEntry.arguments?.getString("url") ?: ""
                    WebViewScreen(url = url)
                }
            }
        }
    }
}

@Composable
fun WebViewScreen(url: String) {
    AndroidView(factory = { context ->
        WebView(context).apply {
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    view?.loadUrl(request?.url.toString())
                    return true
                }
            }
            settings.javaScriptEnabled = true
            loadUrl(url)
        }
    })
}


@Composable
fun NewsScreen(viewModel: NewsVM, navController: NavHostController) {
    val newsSources by viewModel.newsLV.observeAsState(emptyList())
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        FilterChips(
            categories = listOf(
                "business",
                "entertainment",
                "general",
                "health",
                "science",
                "sports",
                "technology"
            ),
            selectedCategory = selectedCategory,
            onCategorySelected = { category ->
                selectedCategory = category
                viewModel.selectCategory(selectedCategory)
            }
        )
        NewsList(
            newsSources,
            onItemClick = { newsSource ->
                val encodedUrl =
                    URLEncoder.encode(newsSource.url, StandardCharsets.UTF_8.toString())
                navController.navigate("webview/$encodedUrl")
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val category =
        listOf("business", "entertainment", "general", "health", "science", "sports", "technology")
    FilterChips(category, "business", onCategorySelected = { category ->
    })
}