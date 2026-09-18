# 🐶 Woof App – Material Theming với Jetpack Compose

Hướng dẫn từng bước xây dựng ứng dụng **Woof** (danh sách chó cưng) áp dụng **Material Design 3**: màu sắc, typography, shape, dark theme, dynamic color và top app bar.

> Dựa theo codelab chính thức của Google: *Material Theming with Jetpack Compose*.

---

## 📋 Mục lục

1. [Yêu cầu & chuẩn bị](#1-yêu-cầu--chuẩn-bị)
2. [Tạo project](#2-tạo-project)
3. [Cấu trúc thư mục](#3-cấu-trúc-thư-mục)
4. [Code từng file Kotlin](#4-code-từng-file-kotlin)
5. [Material 3 Theme tổng quan](#5-material-3-theme-tổng-quan)
6. [Color Scheme](#6-color-scheme)
7. [Typography (Font chữ)](#7-typography-font-chữ)
8. [Shape](#8-shape)
9. [Dark Theme](#9-dark-theme)
10. [Dynamic Color](#10-dynamic-color)
11. [Top App Bar](#11-top-app-bar)
12. [LazyColumn + Card](#12-lazycolumn--card)
13. [Chạy và kiểm tra app](#13-chạy-và-kiểm-tra-app)

---

## 1. Yêu cầu & chuẩn bị

- **Android Studio** bản mới nhất (Hedgehog trở lên khuyến nghị).
- Kiến thức cơ bản về **Kotlin** (biến, hàm, class).
- Đã biết dựng layout cơ bản bằng **Compose** (`Row`, `Column`, `padding`, `Modifier`).
- Đã biết tạo danh sách đơn giản với `LazyColumn`.
- Kết nối Internet (để tải font tùy chỉnh và starter code nếu cần).

---

## 2. Tạo project

1. Mở **Android Studio** → **New Project**.
2. Chọn template **Empty Activity** (Compose).
3. Điền thông tin:
   - **Name**: `Woof`
   - **Package name**: `com.example.woof`
   - **Minimum SDK**: API 24 (Android 7.0) trở lên
   - **Language**: Kotlin
   - **Build configuration language**: Kotlin DSL (hoặc Groovy tùy bạn)
4. Nhấn **Finish** và chờ Gradle sync xong.

> 💡 Nếu bạn dùng code khởi điểm (starter code) từ Google, hãy clone repo và mở project đó trong Android Studio thay vì tạo mới.

---

## 3. Cấu trúc thư mục

```
app/
└── src/
    └── main/
        ├── java/com/example/woof/
        │   ├── MainActivity.kt
        │   ├── Dog.kt
        │   ├── DogsRepository.kt
        │   └── ui/theme/
        │       ├── Color.kt
        │       ├── Theme.kt
        │       ├── Type.kt
        │       └── Shape.kt
        ├── res/
        │   ├── drawable/          # hình ảnh chó (icon, ảnh minh họa)
        │   ├── font/               # font .ttf tùy chỉnh (vd: Abril Fatface, Montserrat)
        │   ├── values/
        │   │   └── strings.xml
        │   └── mipmap/             # app icon
        └── AndroidManifest.xml
```

**Giải thích nhanh:**
- `Dog.kt`: data class chứa thông tin 1 chú chó (tên, tuổi, ảnh…).
- `DogsRepository.kt`: danh sách dữ liệu mẫu (list các con chó).
- `ui/theme/`: toàn bộ file cấu hình Material Theme (màu, chữ, hình dạng).
- `res/font/`: chứa các file font `.ttf` bạn tải về và đổi tên theo chuẩn snake_case.

---

## 4. Code từng file Kotlin

### 4.1. `Dog.kt` – Data class

```kotlin
package com.example.woof

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Dog(
    @DrawableRes val imageResourceId: Int,
    @StringRes val name: Int,
    val age: Int,
    @StringRes val hobbies: Int
)
```

### 4.2. `DogsRepository.kt` – Nguồn dữ liệu mẫu

```kotlin
package com.example.woof

object DogsRepository {
    fun getDogs(): List<Dog> {
        return listOf(
            Dog(R.drawable.img_bella, R.string.dog_name_1, 3, R.string.dog_hobbies_1),
            Dog(R.drawable.img_rocky, R.string.dog_name_2, 5, R.string.dog_hobbies_2),
            Dog(R.drawable.img_tinkerbell, R.string.dog_name_3, 2, R.string.dog_hobbies_3),
            // ... thêm các con chó khác
        )
    }
}
```

### 4.3. `MainActivity.kt` – Điểm khởi chạy

```kotlin
package com.example.woof

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.woof.ui.theme.WoofTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WoofTheme {
                WoofApp()
            }
        }
    }
}

@Composable
fun WoofApp() {
    Scaffold(
        topBar = { WoofTopAppBar() }
    ) { innerPadding ->
        LazyColumn(contentPadding = innerPadding) {
            items(DogsRepository.getDogs()) { dog ->
                DogItem(
                    dog = dog,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                )
            }
        }
    }
}

@Preview
@Composable
fun WoofPreview() {
    WoofTheme(darkTheme = false) {
        WoofApp()
    }
}
```

---

## 5. Material 3 Theme tổng quan

Trong Compose, một theme Material 3 gồm 3 thành phần chính, được gói gọn trong hàm `MaterialTheme()`:

```kotlin
MaterialTheme(
    colorScheme = colorScheme,   // Color.kt
    typography = Typography,     // Type.kt
    shapes = Shapes,             // Shape.kt (tùy chọn)
    content = content
)
```

File `Theme.kt` sẽ định nghĩa hàm `WoofTheme()` bọc quanh toàn bộ nội dung app, quyết định app dùng bảng màu nào (sáng/tối/dynamic).

---

## 6. Color Scheme

### `Color.kt`

```kotlin
package com.example.woof.ui.theme

import androidx.compose.ui.graphics.Color

// Light theme
val md_theme_light_primary = Color(0xFF54662B)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_background = Color(0xFFFDFDF6)
val md_theme_light_onBackground = Color(0xFF1A1C16)
val md_theme_light_surface = Color(0xFFFDFDF6)
val md_theme_light_onSurface = Color(0xFF1A1C16)

// Dark theme
val md_theme_dark_primary = Color(0xFFBBD292)
val md_theme_dark_onPrimary = Color(0xFF263500)
val md_theme_dark_background = Color(0xFF1A1C16)
val md_theme_dark_onBackground = Color(0xFFE2E3DB)
val md_theme_dark_surface = Color(0xFF1A1C16)
val md_theme_dark_onSurface = Color(0xFFE2E3DB)
```

> 💡 Bạn có thể dùng [Material Theme Builder](https://m3.material.io/theme-builder) để tự sinh toàn bộ bảng màu (light/dark) từ 1 màu gốc, rồi copy vào file này.

### `Theme.kt`

```kotlin
package com.example.woof.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface
)

private val DarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface
)

@Composable
fun WoofTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color chỉ khả dụng trên Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
```

---

## 7. Typography (Font chữ)

### Bước 1 – Tải font tùy chỉnh

1. Vào [Google Fonts](https://fonts.google.com), tải font (vd: **Abril Fatface** cho tiêu đề, **Montserrat** cho nội dung).
2. Đưa file `.ttf` vào thư mục `res/font/`.
3. **Đổi tên** file theo chuẩn snake_case, ví dụ: `AbrilFatface-Regular.ttf` → `abril_fatface_regular.ttf`.

### Bước 2 – `Type.kt`

```kotlin
package com.example.woof.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.woof.R

val Abril = FontFamily(Font(R.font.abril_fatface_regular))
val Montserrat = FontFamily(Font(R.font.montserrat_regular))

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = Abril,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp
    )
)
```

Sử dụng trong UI:

```kotlin
Text(
    text = stringResource(dog.name),
    style = MaterialTheme.typography.displayLarge
)
```

---

## 8. Shape

`Shape.kt` định nghĩa bo góc cho các thành phần như `Card`, `Button`.

```kotlin
package com.example.woof.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes

val Shapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(0.dp, 24.dp, 0.dp, 24.dp) // bo góc bất đối xứng, ví dụ cho ảnh chó
)
```

Áp dụng vào `Image` của ảnh chó:

```kotlin
Image(
    modifier = Modifier
        .size(64.dp)
        .clip(MaterialTheme.shapes.small),
    painter = painterResource(dog.imageResourceId),
    contentDescription = null
)
```

---

## 9. Dark Theme

Compose tự phát hiện chế độ tối của hệ thống qua `isSystemInDarkTheme()`. Bạn chỉ cần:

1. Định nghĩa `DarkColorScheme` (đã làm ở mục 6).
2. `WoofTheme` tự chuyển sang `DarkColorScheme` khi `darkTheme = true`.
3. Kiểm tra bằng cách bật **Dark mode** trong Settings điện thoại/emulator, hoặc dùng nút **"Dark Theme"** trong panel **Preview** của Android Studio.

```kotlin
@Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun WoofDarkThemePreview() {
    WoofTheme(darkTheme = true) {
        WoofApp()
    }
}
```

---

## 10. Dynamic Color

**Dynamic Color** (Material You) tự sinh bảng màu từ hình nền điện thoại — chỉ khả dụng từ **Android 12 (API 31)** trở lên.

- Đã được xử lý sẵn trong `WoofTheme()` ở mục 6 (`dynamicLightColorScheme` / `dynamicDarkColorScheme`).
- Để **tắt** dynamic color (dùng bảng màu cố định của app), gọi:

```kotlin
WoofTheme(dynamicColor = false) {
    WoofApp()
}
```

- Để kiểm tra: chạy trên emulator Android 12+, đổi hình nền (wallpaper) → mở lại app để thấy màu thay đổi theo.

---

## 11. Top App Bar

Sử dụng `CenterAlignedTopAppBar` (Material 3) để hiển thị tên + icon app.

```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WoofTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.image_size))
                        .padding(dimensionResource(R.dimen.padding_small)),
                    painter = painterResource(R.drawable.ic_woof_logo),
                    contentDescription = null
                )
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.displayLarge
                )
            }
        },
        modifier = modifier
    )
}
```

> Nhớ thêm dependency Material3 trong `build.gradle.kts` nếu chưa có:
> `implementation("androidx.compose.material3:material3:<version>")`

---

## 12. LazyColumn + Card

Mỗi con chó được hiển thị bằng 1 `Card` chứa `Image` + `Text`, đặt trong `LazyColumn` để cuộn danh sách hiệu quả.

```kotlin
@Composable
fun DogItem(dog: Dog, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small))
        ) {
            DogIcon(dog.imageResourceId)
            DogInformation(dog.name, dog.age)
        }
    }
}

@Composable
fun DogIcon(@DrawableRes dogIcon: Int, modifier: Modifier = Modifier) {
    Image(
        modifier = modifier
            .size(dimensionResource(R.dimen.image_size))
            .padding(dimensionResource(R.dimen.padding_small))
            .clip(MaterialTheme.shapes.small),
        contentScale = ContentScale.Crop,
        painter = painterResource(dogIcon),
        contentDescription = null
    )
}

@Composable
fun DogInformation(@StringRes dogName: Int, dogAge: Int, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(dogName),
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small))
        )
        Text(
            text = stringResource(R.string.years_old, dogAge),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
```

Danh sách chính:

```kotlin
LazyColumn {
    items(DogsRepository.getDogs()) { dog ->
        DogItem(dog = dog, modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)))
    }
}
```

---

## 13. Chạy và kiểm tra app

1. **Build project**: `Build → Make Project` (hoặc `Ctrl + F9` / `Cmd + F9`).
2. **Chạy app**: chọn thiết bị/emulator (khuyến nghị API 31+ để test Dynamic Color) → nhấn ▶ **Run**.
3. **Kiểm tra checklist**:
   - [ ] App hiển thị đúng danh sách chó với ảnh, tên, tuổi.
   - [ ] Top App Bar hiển thị logo + tên app.
   - [ ] Chuyển **Light/Dark theme** → màu sắc thay đổi đúng.
   - [ ] Trên Android 12+: đổi wallpaper → màu app đổi theo (Dynamic Color).
   - [ ] Font tiêu đề (Abril Fatface) và nội dung (Montserrat) hiển thị đúng.
   - [ ] Card có bo góc theo `Shapes` đã định nghĩa.
   - [ ] Cuộn danh sách mượt (LazyColumn hoạt động đúng).
4. **Xem trước nhanh không cần chạy app**: dùng `@Preview` trong Android Studio, bật cả `WoofPreview()` (light) và `WoofDarkThemePreview()` (dark) để so sánh song song.

---

## 📚 Tham khảo

- Codelab gốc: *Material Theming with Jetpack Compose* – developer.android.com
- [Material Design 3 – Material You](https://m3.material.io/)
- [Material Theme Builder](https://m3.material.io/theme-builder)
- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)

---

*File này được tạo để làm tài liệu hướng dẫn nội bộ (README) cho project Woof – Material Theming with Jetpack Compose.*
