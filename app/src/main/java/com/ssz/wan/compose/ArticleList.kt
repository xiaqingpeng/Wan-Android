package com.ssz.wan.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ssz.network.model.Article
import com.ssz.network.model.Banner

/**
 * 文章列表
 */
@Composable
fun ArticleList(
    articles: List<Article>,
    onArticleClick: (String) -> Unit,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {},
    isLoadingMore: Boolean = false,
    hasMore: Boolean = true,
    onLoadMore: () -> Unit = {},
    banners: List<Banner> = emptyList(),
    onBannerClick: (String) -> Unit = {}
) {
    PullRefreshLazyColumn(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        isLoadingMore = isLoadingMore,
        hasMore = hasMore,
        onLoadMore = onLoadMore,
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        // Banner作为第一个item
        if (banners.isNotEmpty()) {
            item {
                BannerView(
                    banners = banners,
                    onBannerClick = onBannerClick
                )
            }
        }
        
        // 文章列表
        items(articles) { article ->
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                ArticleCard(
                    article = article,
                    onArticleClick = onArticleClick
                )
            }
        }
    }
}

/**
 * 文章 item
 */
@Composable
fun ArticleCard(
    article: Article,
    onArticleClick: (String) -> Unit
) {
    val link = article.link
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = !link.isNullOrBlank()) {
                link?.let(onArticleClick)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = article.title ?: "无标题",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                minLines = 2,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "作者：" + (article.author ?: article.shareUser ?: "佚名"),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = article.superChapterName?.let { sc ->
                    val ch = article.chapterName ?: ""
                    if (ch.isNotBlank()) "$sc / $ch" else sc
                } ?: (article.chapterName ?: "未知分类"),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
            article.desc?.takeIf { it.isNotBlank() }?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 6.dp)
                )
            }
            article.niceDate?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 6.dp)
                )
            }
        }
    }
}


