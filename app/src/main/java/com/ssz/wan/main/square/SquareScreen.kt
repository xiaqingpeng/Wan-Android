package com.ssz.wan.main.square

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssz.wan.main.ErrorMessage
import com.ssz.wan.main.LoadingIndicator
import com.ssz.wan.main.square.viewmodel.SquareUiState
import com.ssz.wan.main.square.viewmodel.SquareViewModel
import com.ssz.wan.compose.SquareArticleList

/**
 * 广场页面：显示广场文章列表
 */
@Composable
fun SquareScreen(
    viewModel: SquareViewModel = viewModel(),
    onArticleClick: (String) -> Unit
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    // 在广场页面按返回键直接退出应用
    BackHandler {
        (context as? androidx.activity.ComponentActivity)?.finish()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            is SquareUiState.Loading -> LoadingIndicator()
            is SquareUiState.Success -> SquareArticleList(
                articles = state.articles,
                onArticleClick = onArticleClick,
                onCollectClick = { article, isCollect ->
                    viewModel.collectArticle(article, isCollect)
                }
            )
            is SquareUiState.Error -> ErrorMessage(
                message = state.message,
                onRetry = { viewModel.load() }
            )
        }
    }
}

