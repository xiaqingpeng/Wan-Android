# Wan-Android

基于 Jetpack Compose 构建的客户端，采用 MVVM 架构，多模块工程结构。

---

## 截图

> 首页 · 广场 · 导航 · 我的

---

## 技术栈

| 分类 | 库 / 版本 |
|------|-----------|
| 语言 | Kotlin 2.0.21 |
| UI | Jetpack Compose + Material3 |
| 导航 | Navigation Compose 2.8.4 |
| 网络 | Retrofit 2.9.0 + OkHttp 4.11.0 |
| 图片加载 | Coil 2.4.0 |
| 异步 | Kotlin Coroutines + Flow |
| 架构 | MVVM（ViewModel + StateFlow） |
| WebView | AndroidX WebKit 1.9.0 |
| 构建 | AGP 8.9.1 · Gradle 8.11.1 · compileSdk 36 |

---

## 项目结构

```
Wan-Android
├── app          # 主模块：UI 页面、ViewModel、导航
│   └── src/main/java/com/ssz/wan
│       ├── compose/         # 公共 Compose 组件（Banner、文章列表、WebView 等）
│       ├── main/
│       │   ├── home/        # 首页（文章列表 + 轮播图）
│       │   ├── square/      # 广场
│       │   ├── navigation/  # 导航
│       │   ├── mine/        # 我的（收藏、设置、用户信息）
│       │   └── webview/     # 文章 WebView 页
│       ├── navigation/      # 路由定义与导航图
│       └── ui/theme/        # 主题、颜色、字体
├── login        # 登录/注册模块
└── network      # 网络模块
    └── src/main/java/com/ssz/network
        ├── model/           # 数据模型（Article、Banner、NavCategory 等）
        ├── repository/      # 数据仓库（ArticleRepository、LoginRepository 等）
        ├── ApiService.kt    # Retrofit 接口定义
        ├── RetrofitInstance.kt
        ├── AuthInterceptor.kt
        ├── LoginStateManager.kt
        └── PersistentCookieJar.kt
```

---

## 功能模块

- **首页** — 文章列表 + 轮播 Banner，支持下拉刷新和上拉加载更多
- **广场** — 用户分享文章，支持收藏/取消收藏
- **导航** — 分类导航，点击跳转 WebView
- **我的** — 用户信息、积分展示、收藏列表、设置、退出登录
- **登录/注册** — 独立 login 模块，Cookie 持久化保持登录态
- **WebView** — 内置浏览器，支持返回手势

---

## 快速开始

### 环境要求

- Android Studio Ladybug 或更高版本
- JDK 11+
- Android SDK 36

### 克隆与运行

```bash
git clone https://github.com/shenshizhong/Wan-Android.git
cd Wan-Android
./gradlew assembleDebug
```

> 国内网络建议配置镜像，项目已在 `settings.gradle.kts` 中预置腾讯云 Maven 镜像。

### Gradle 下载加速

`gradle/wrapper/gradle-wrapper.properties` 中已将 Gradle 发行包地址替换为腾讯云镜像：

```
distributionUrl=https://mirrors.cloud.tencent.com/gradle/gradle-8.11.1-bin.zip
```

---

## 架构说明

```
UI (Compose Screen)
    ↓ collectAsState
ViewModel (StateFlow)
    ↓ collect
Repository (Flow)
    ↓
DataSource（Mock / Network）
```

- **单向数据流**：UI 只读取 StateFlow，通过调用 ViewModel 方法触发状态变更
- **密封类状态**：每个页面使用 `sealed class UiState`（Loading / Success / Error）统一管理 UI 状态
- **Mock 数据**：当前 Repository 层使用内置假数据，网络恢复后只需替换 Repository 实现即可，ViewModel 和 UI 无需改动

---

## License

```
MIT License
```
