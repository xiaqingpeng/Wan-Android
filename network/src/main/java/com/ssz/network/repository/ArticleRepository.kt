package com.ssz.network.repository

import com.ssz.network.model.Article
import com.ssz.network.model.ArticlePage
import com.ssz.network.model.Banner
import com.ssz.network.model.BaseResponse
import com.ssz.network.model.NavArticle
import com.ssz.network.model.NavCategory
import com.ssz.network.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * 首页文章仓库（Mock 数据版本，网络不可用时使用）
 */
class ArticleRepository {

    // ----------------------------------------------------------------
    // Mock 数据
    // ----------------------------------------------------------------

    private val mockArticles = listOf(
        Article(1, "Jetpack Compose 入门指南", "https://developer.android.com/jetpack/compose", "Google", null, "2026-05-01", "Android", "Compose", null, null, false, 1746057600000L),
        Article(2, "Kotlin 协程最佳实践", "https://kotlinlang.org/docs/coroutines-guide.html", "JetBrains", null, "2026-04-28", "Kotlin", "协程", null, null, true, 1745798400000L),
        Article(3, "Android 架构组件详解", "https://developer.android.com/topic/architecture", "Google", null, "2026-04-25", "Android", "架构", null, null, false, 1745539200000L),
        Article(4, "Flow 与 LiveData 对比分析", "https://medium.com/androiddevelopers/a-safer-way-to-collect-flows-from-android-uis-23080b1f8bda", "Manuel Vivo", null, "2026-04-20", "Android", "Flow", null, null, false, 1745107200000L),
        Article(5, "Material Design 3 实战", "https://m3.material.io/", "Google", null, "2026-04-18", "UI", "Material", null, null, true, 1744934400000L),
        Article(6, "Hilt 依赖注入完全指南", "https://developer.android.com/training/dependency-injection/hilt-android", "Google", null, "2026-04-15", "Android", "Hilt", null, null, false, 1744675200000L),
        Article(7, "Room 数据库高级用法", "https://developer.android.com/training/data-storage/room", "Google", null, "2026-04-12", "Android", "Room", null, null, false, 1744416000000L),
        Article(8, "Navigation Compose 导航实践", "https://developer.android.com/jetpack/compose/navigation", "Google", null, "2026-04-10", "Android", "Navigation", null, null, true, 1744243200000L),
        Article(9, "Paging 3 分页加载详解", "https://developer.android.com/topic/libraries/architecture/paging/v3-overview", "Google", null, "2026-04-08", "Android", "Paging", null, null, false, 1744070400000L),
        Article(10, "WorkManager 后台任务调度", "https://developer.android.com/topic/libraries/architecture/workmanager", "Google", null, "2026-04-05", "Android", "WorkManager", null, null, false, 1743811200000L),
        Article(11, "Compose 动画系统全解析", "https://developer.android.com/jetpack/compose/animation", "Google", null, "2026-04-03", "Android", "Compose", null, null, true, 1743638400000L),
        Article(12, "OkHttp 拦截器原理与实践", "https://square.github.io/okhttp/features/interceptors/", "Square", null, "2026-04-01", "网络", "OkHttp", null, null, false, 1743465600000L),
        Article(13, "Retrofit 2 高级技巧", "https://square.github.io/retrofit/", "Square", null, "2026-03-28", "网络", "Retrofit", null, null, false, 1743120000000L),
        Article(14, "Coil 图片加载库使用指南", "https://coil-kt.github.io/coil/", "Coil", null, "2026-03-25", "图片", "Coil", null, null, true, 1742860800000L),
        Article(15, "DataStore 替代 SharedPreferences", "https://developer.android.com/topic/libraries/architecture/datastore", "Google", null, "2026-03-22", "Android", "DataStore", null, null, false, 1742601600000L),
    )

    private val mockSquareArticles = listOf(
        Article(101, "我的 Compose 学习笔记", "https://juejin.cn/post/compose-notes", "开发者小王", null, "2026-05-10", "广场", "Compose", null, null, false, 1746921600000L),
        Article(102, "分享一个好用的 Kotlin 扩展函数库", "https://github.com/kotlin-extensions", null, "分享者小李", "2026-05-09", "广场", "Kotlin", null, null, true, 1746835200000L),
        Article(103, "Android 性能优化实战经验", "https://juejin.cn/post/performance", "架构师老张", null, "2026-05-08", "广场", "性能优化", null, null, false, 1746748800000L),
        Article(104, "自定义 View 绘制技巧总结", "https://juejin.cn/post/custom-view", "UI 达人", null, "2026-05-07", "广场", "自定义View", null, null, false, 1746662400000L),
        Article(105, "Gradle 构建优化指南", "https://docs.gradle.org/current/userguide/performance.html", "构建专家", null, "2026-05-06", "广场", "Gradle", null, null, true, 1746576000000L),
        Article(106, "Android 单元测试最佳实践", "https://developer.android.com/training/testing/unit-testing", "测试工程师", null, "2026-05-05", "广场", "测试", null, null, false, 1746489600000L),
        Article(107, "Kotlin DSL 实战案例", "https://kotlinlang.org/docs/type-safe-builders.html", "Kotlin 爱好者", null, "2026-05-04", "广场", "Kotlin", null, null, false, 1746403200000L),
        Article(108, "深入理解 Android Binder 机制", "https://juejin.cn/post/binder", "系统开发者", null, "2026-05-03", "广场", "系统", null, null, true, 1746316800000L),
    )

    private val mockBanners = listOf(
        Banner(1, "", "https://cms-dumall.cdn.bcebos.com/cms_upload_pro/cms_1761926915809.jpg?x-bce-process=image/quality,q_80/format,f_auto/interlace,i_progressive", 1, 1, "Banner 1", 0, "https://www.wanandroid.com"),
        Banner(2, "", "https://cms-dumall.cdn.bcebos.com/cms_upload_pro/cms_1778056223067.jpg?x-bce-process=image/quality,q_80/format,f_auto/interlace,i_progressive", 1, 2, "Banner 2", 0, "https://www.wanandroid.com"),
        Banner(3, "", "https://cms-dumall.cdn.bcebos.com/cms_upload_pro/cms_1778056609133.jpg?x-bce-process=image/quality,q_80/format,f_auto/interlace,i_progressive", 1, 3, "Banner 3", 0, "https://www.wanandroid.com"),
    )

    private val mockNavCategories = listOf(
        NavCategory(
            cid = 1, name = "常用网站",
            articles = listOf(
                NavArticle(false, null, 0, "Google", false, 1, "常用网站", false, 0, null, null, null, false, null, 1001, false, "https://developer.android.com", "2026-01-01", null, null, null, null, 1735689600000L, 0, 0, null, null, 1, "常用网站", emptyList(), "Android 开发者", 0, 0, 1, 0),
                NavArticle(false, null, 0, "JetBrains", false, 1, "常用网站", false, 0, null, null, null, false, null, 1002, false, "https://kotlinlang.org", "2026-01-01", null, null, null, null, 1735689600000L, 0, 0, null, null, 1, "常用网站", emptyList(), "Kotlin 官网", 0, 0, 1, 0),
                NavArticle(false, null, 0, "Square", false, 1, "常用网站", false, 0, null, null, null, false, null, 1003, false, "https://square.github.io/okhttp/", "2026-01-01", null, null, null, null, 1735689600000L, 0, 0, null, null, 1, "常用网站", emptyList(), "OkHttp", 0, 0, 1, 0),
                NavArticle(false, null, 0, "Square", false, 1, "常用网站", false, 0, null, null, null, false, null, 1004, false, "https://square.github.io/retrofit/", "2026-01-01", null, null, null, null, 1735689600000L, 0, 0, null, null, 1, "常用网站", emptyList(), "Retrofit", 0, 0, 1, 0),
            )
        ),
        NavCategory(
            cid = 2, name = "开源库",
            articles = listOf(
                NavArticle(false, null, 0, "Google", false, 2, "开源库", false, 0, null, null, null, false, null, 2001, false, "https://github.com/google/accompanist", "2026-01-01", null, null, null, null, 1735689600000L, 0, 0, null, null, 2, "开源库", emptyList(), "Accompanist", 0, 0, 1, 0),
                NavArticle(false, null, 0, "Coil", false, 2, "开源库", false, 0, null, null, null, false, null, 2002, false, "https://coil-kt.github.io/coil/", "2026-01-01", null, null, null, null, 1735689600000L, 0, 0, null, null, 2, "开源库", emptyList(), "Coil 图片加载", 0, 0, 1, 0),
                NavArticle(false, null, 0, "airbnb", false, 2, "开源库", false, 0, null, null, null, false, null, 2003, false, "https://github.com/airbnb/lottie-android", "2026-01-01", null, null, null, null, 1735689600000L, 0, 0, null, null, 2, "开源库", emptyList(), "Lottie 动画", 0, 0, 1, 0),
            )
        ),
        NavCategory(
            cid = 3, name = "技术博客",
            articles = listOf(
                NavArticle(false, null, 0, "Google", false, 3, "技术博客", false, 0, null, null, null, false, null, 3001, false, "https://android-developers.googleblog.com/", "2026-01-01", null, null, null, null, 1735689600000L, 0, 0, null, null, 3, "技术博客", emptyList(), "Android 开发者博客", 0, 0, 1, 0),
                NavArticle(false, null, 0, "掘金", false, 3, "技术博客", false, 0, null, null, null, false, null, 3002, false, "https://juejin.cn/android", "2026-01-01", null, null, null, null, 1735689600000L, 0, 0, null, null, 3, "技术博客", emptyList(), "掘金 Android", 0, 0, 1, 0),
            )
        ),
    )

    private val mockCollectArticles = listOf(
        Article(5, "Material Design 3 实战", "https://m3.material.io/", "Google", null, "2026-04-18", "UI", "Material", null, null, true, 1744934400000L),
        Article(2, "Kotlin 协程最佳实践", "https://kotlinlang.org/docs/coroutines-guide.html", "JetBrains", null, "2026-04-28", "Kotlin", "协程", null, null, true, 1745798400000L),
        Article(8, "Navigation Compose 导航实践", "https://developer.android.com/jetpack/compose/navigation", "Google", null, "2026-04-10", "Android", "Navigation", null, null, true, 1744243200000L),
        Article(11, "Compose 动画系统全解析", "https://developer.android.com/jetpack/compose/animation", "Google", null, "2026-04-03", "Android", "Compose", null, null, true, 1743638400000L),
        Article(14, "Coil 图片加载库使用指南", "https://coil-kt.github.io/coil/", "Coil", null, "2026-03-25", "图片", "Coil", null, null, true, 1742860800000L),
    )

    // ----------------------------------------------------------------
    // 分页辅助：将列表按页切割
    // ----------------------------------------------------------------

    private fun <T> paginate(list: List<T>, page: Int, pageSize: Int = 10): ArticlePage {
        val total = list.size
        val pageCount = (total + pageSize - 1) / pageSize
        val from = (page * pageSize).coerceAtMost(total)
        val to = ((page + 1) * pageSize).coerceAtMost(total)
        @Suppress("UNCHECKED_CAST")
        val datas = list.subList(from, to) as List<Article>
        return ArticlePage(curPage = page, datas = datas, pageCount = pageCount, size = pageSize, total = total)
    }

    // ----------------------------------------------------------------
    // 公开接口（与原版签名完全一致）
    // ----------------------------------------------------------------

    fun getHomeArticles(page: Int, pageSize: Int? = null): Flow<Result<BaseResponse<ArticlePage>>> = flow {
        val size = pageSize ?: 10
        emit(Result.Success(BaseResponse(data = paginate(mockArticles, page, size), errorCode = 0, errorMsg = "")))
    }

    fun getSquareArticles(page: Int, pageSize: Int? = null): Flow<Result<BaseResponse<ArticlePage>>> = flow {
        val size = pageSize ?: 10
        emit(Result.Success(BaseResponse(data = paginate(mockSquareArticles, page, size), errorCode = 0, errorMsg = "")))
    }

    fun getBanners(): Flow<Result<BaseResponse<List<Banner>>>> = flow {
        emit(Result.Success(BaseResponse(data = mockBanners, errorCode = 0, errorMsg = "")))
    }

    fun getNavigation(): Flow<Result<BaseResponse<List<NavCategory>>>> = flow {
        emit(Result.Success(BaseResponse(data = mockNavCategories, errorCode = 0, errorMsg = "")))
    }

    fun getCollectList(page: Int, pageSize: Int? = null): Flow<Result<BaseResponse<ArticlePage>>> = flow {
        val size = pageSize ?: 10
        emit(Result.Success(BaseResponse(data = paginate(mockCollectArticles, page, size), errorCode = 0, errorMsg = "")))
    }

    suspend fun collect(id: Int): Result<BaseResponse<Any>> =
        Result.Success(BaseResponse(data = null, errorCode = 0, errorMsg = ""))

    suspend fun collectExternal(title: String, author: String, link: String): Result<BaseResponse<Any>> =
        Result.Success(BaseResponse(data = null, errorCode = 0, errorMsg = ""))

    suspend fun uncollect(id: Int): Result<BaseResponse<Any>> =
        Result.Success(BaseResponse(data = null, errorCode = 0, errorMsg = ""))
}