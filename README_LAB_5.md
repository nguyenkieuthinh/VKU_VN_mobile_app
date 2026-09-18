<div align="center">

# Woof

**Lab 5 · Android với Jetpack Compose**

[![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Material 3](https://img.shields.io/badge/Material_3-757575?style=for-the-badge&logo=materialdesign&logoColor=white)](https://m3.material.io)
[![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com)

</div>

---

> Ứng dụng **Woof** — danh sách chó cưng được xây dựng với **Material Design 3**, áp dụng **Color Scheme, Typography, Shape, Dark Theme và Dynamic Color** để tùy biến giao diện theo hệ thống.

---

## Video demo

<video src="video/VIDEO_LAB_5.webm" controls width="100%"></video>

## Ảnh minh họa

<div align="center">
  <img src="video/lab5_1.png" alt="Lab 5 - Woof" width="300">
</div>

## Tính năng nổi bật

| Tính năng | Mô tả |
|-----------|--------|
| Danh sách chó cưng | Hiển thị 6 chú chó (Bella, Rocky, Tinkerbell, Fido, Lulu, Rex) với ảnh, tên, tuổi và sở thích |
| Top App Bar | `CenterAlignedTopAppBar` hiển thị logo + tên app |
| Custom Font | 3 font tùy chỉnh: Abril Fatface, Love Days, Ariana Violeta |
| Dark Theme | Tự chuyển đổi sáng/tối theo cài đặt hệ thống |
| Dynamic Color | Hỗ trợ Material You trên Android 12+ (có thể bật/tắt) |
| Edge-to-edge | Giao diện tràn viền với status/navigation bar trong suốt |

## Kiến thức áp dụng

| Khái niệm | Cách dùng |
|-----------|-----------|
| Material 3 Theme | `MaterialTheme(colorScheme, typography, shapes)` |
| Color Scheme | `lightColorScheme` / `darkColorScheme` / `dynamicLightColorScheme` / `dynamicDarkColorScheme` |
| Typography | Font tùy chỉnh `.ttf` trong `res/font/` + `FontFamily` |
| Shape | `RoundedCornerShape` bo góc (có góc bất đối xứng) |
| Dark Theme | `isSystemInDarkTheme()` |
| Dynamic Color | `Build.VERSION.SDK_INT >= S` + `LocalContext` |
| Danh sách | `LazyColumn` + `items()` |
| Card | `Card` chứa `Row` → `Image` + `Column` |
| Data class | `Dog(imageResourceId, name, age, hobbies)` |

## Cấu trúc mã nguồn

| File | Vai trò |
|------|---------|
| `MainActivity.kt` | `WoofApp`, `WoofTopAppBar`, `DogItem`, `DogIcon`, `DogInformation` |
| `Dog.kt` | Data class mô tả một chú chó |
| `DogsRepository.kt` | Danh sách dữ liệu mẫu |
| `ui/theme/Color.kt` | Bảng màu light/dark |
| `ui/theme/Theme.kt` | Hàm `WoofTheme` (color scheme + edge-to-edge) |
| `ui/theme/Type.kt` | Font chữ & `Typography` |
| `ui/theme/Shape.kt` | Bo góc `Shapes` |

## Chạy dự án

```bash
cd "Lab 5. Woof app"
./gradlew assembleDebug      # Build APK
./gradlew installDebug       # Cài lên thiết bị qua adb
```

**Mã nguồn chính:** [`src/main/java/com/example/woof/MainActivity.kt`](Lab%205.%20Woof%20app/src/main/java/com/example/woof/MainActivity.kt)

**Tài liệu tóm tắt:** [`docs/lab5.md`](docs/lab5.md)

<div align="center">

[← Lab 4 · Tip Calculator](README_LAB_4.md) · [Về trang chủ](README.md)

</div>
