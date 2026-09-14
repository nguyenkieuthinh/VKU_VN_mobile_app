<div align="center">

# Business Card

**Lab 2 · Android với Jetpack Compose**

[![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com)

</div>

---

> Ứng dụng **danh thiếp số (Business Card)** — làm quen với cách xây dựng giao diện bằng các composable layout cơ bản trong Jetpack Compose.

---

## Video demo

<video src="video/VIDEO_LAB_2.webm" controls preload="metadata" playsinline width="100%"></video>

## Ảnh minh họa

<div align="center">
  <img src="video/lab2_1.jpg" alt="Lab 2 - Business Card" width="300">
</div>

## Tính năng nổi bật

| Tính năng | Mô tả |
|-----------|--------|
| Ảnh đại diện | Hiển thị logo Android ở trung tâm thẻ |
| Thông tin cá nhân | Họ tên và chức danh được làm nổi bật |
| Thông tin liên hệ | Số điện thoại, Share, Email kèm icon Material |
| Bố cục rõ ràng | Chia 2 khối trên/dưới bằng đường kẻ `Divider` |

## Kiến thức áp dụng

| Khái niệm | Cách dùng |
|-----------|-----------|
| `Column` / `Row` | Sắp xếp thành phần theo chiều dọc / ngang |
| `Image` / `Icon` | Hiển thị hình ảnh và icon vector |
| `Modifier` | `padding`, `size`, `fillMaxWidth`, `weight`, `background` |
| `FontWeight` / `sp` | Tùy chỉnh độ đậm và cỡ chữ |
| Composable tái sử dụng | `ContactRow` dùng chung cho 3 loại liên hệ |
| `@Preview` | Xem trước giao diện ngay trong Android Studio |

## Chạy dự án

```bash
cd "Lab 2. Business Card app"
./gradlew assembleDebug      # Build APK
./gradlew installDebug       # Cài lên thiết bị qua adb
```

**Mã nguồn chính:** [`src/main/java/vku/ltm/businesscard/MainActivity.kt`](Lab%202.%20Business%20Card%20app/src/main/java/vku/ltm/businesscard/MainActivity.kt)

<div align="center">

[← Về trang chủ](README.md) · [Lab 3 · Dice Roller →](README_LAB_3.md)

</div>
