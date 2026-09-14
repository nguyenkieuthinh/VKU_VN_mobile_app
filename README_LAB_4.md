<div align="center">

# Tip Calculator

**Lab 4 · Android với Jetpack Compose**

[![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com)

</div>

---

> Ứng dụng **tính tiền boa (Tip Time)** — nhập số tiền hóa đơn và phần trăm tip để tự động tính số tiền boa, áp dụng kỹ thuật **State Hoisting**.

---

## Video demo

<video src="video/VIDEO_LAB_4.webm" controls preload="metadata" playsinline width="100%"></video>

## Ảnh minh họa

<div align="center">
  <img src="video/lab4_1.jpg" alt="Lab 4 - Tip Calculator" width="300">
</div>

## Tính năng nổi bật

| Tính năng | Mô tả |
|-----------|--------|
| Bill Amount | Nhập số tiền hóa đơn |
| Tip Percentage | Nhập phần trăm tiền boa, kèm hậu tố `%` |
| Round up tip? | Công tắc bật/tắt làm tròn lên số tiền boa |
| Tip Amount | Tự động tính và hiển thị kết quả tức thì |

## Kiến thức áp dụng

| Khái niệm | Cách dùng |
|-----------|-----------|
| State trong Compose | `mutableStateOf` + `remember` |
| State Hoisting | Đưa state lên composable cha, composable con trở thành stateless |
| `TextField` | Nhận input với `label`, `suffix`, `keyboardOptions` |
| `Switch` | Lựa chọn bật/tắt |
| Parse an toàn | `toDoubleOrNull() ?: 0.0` |
| Định dạng tiền tệ | `NumberFormat.getCurrencyInstance()`, `kotlin.math.ceil` |

## Chạy dự án

```bash
cd "Lab 4. Tip Calculator app"
./gradlew assembleDebug      # Build APK
./gradlew installDebug       # Cài lên thiết bị qua adb
```

**Mã nguồn chính:** [`src/main/java/vku/ltm/tipcalculator/MainActivity.kt`](Lab%204.%20Tip%20Calculator%20app/src/main/java/vku/ltm/tipcalculator/MainActivity.kt)

**Tài liệu tóm tắt:** [`docs/Lab4.md`](docs/Lab4.md)

<div align="center">

[← Lab 3 · Dice Roller](README_LAB_3.md) · [Về trang chủ](README.md)

</div>
