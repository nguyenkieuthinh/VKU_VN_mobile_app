<div align="center">

# Dice Roller

**Lab 3 · Android với Jetpack Compose**

[![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com)

</div>

---

> Ứng dụng **tung xúc xắc (Dice Roller)** — bấm nút Roll để đổ ngẫu nhiên 1–6, hiển thị hình xúc xắc tương ứng và làm quen với **quản lý trạng thái (state)**.

---

## Video demo
<video src="https://github.com/user-attachments/assets/b9ac1e9a-b44b-49f4-92cc-eb7eca6780f7" controls width="100%"></video>

## Ảnh minh họa

<div align="center">
  <img src="video/lab3_1.jpg" alt="Lab 3 - Dice Roller" width="300">
</div>

## Tính năng nổi bật

| Tính năng | Mô tả |
|-----------|--------|
| Nút `Roll` | Tung xúc xắc ngẫu nhiên trong khoảng 1–6 |
| Hình xúc xắc | Ảnh PNG tương ứng với kết quả đổ được |
| Thông báo thông minh | Chúc mừng khi trúng số may mắn, động viên khi chưa |
| Cập nhật tức thì | Giao diện tự vẽ lại khi state thay đổi (recomposition) |

## Kiến thức áp dụng

| Khái niệm | Cách dùng |
|-----------|-----------|
| State management | `remember` + `mutableIntStateOf` |
| Xử lý sự kiện | `Button(onClick = { ... })` |
| Biểu thức `when` | Chọn hình ảnh / thông báo theo kết quả |
| Stateful vs Stateless | `DiceRollerApp` giữ state, con hiển thị nhận dữ liệu |

## Chạy dự án

```bash
cd "Lab 3. Dice Roller app"
./gradlew assembleDebug      # Build APK
./gradlew installDebug       # Cài lên thiết bị qua adb
```

## Mô phỏng console (console-simulation)

Kèm bộ bài mô phỏng trên console minh họa các khái niệm Unit 2:

| File | Nội dung |
|------|----------|
| `Bai1_DiceRoller.kt` | Dice Roller App |
| `Bai2_DebuggerAndState.kt` | Debugger & State Recomposition |
| `Bai3_LemonadePractice.kt` | Lemonade App Practice |
| `Bai4_ClickBehaviorPractice.kt` | Click Behavior Practice (State hoisting, Toggle) |

Hướng dẫn: [`console-simulation/RUN_LAB2.md`](Lab%203.%20Dice%20Roller%20app/console-simulation/RUN_LAB2.md)

**Mã nguồn chính:** [`src/main/java/vku/ltm/diceroller/MainActivity.kt`](Lab%203.%20Dice%20Roller%20app/src/main/java/vku/ltm/diceroller/MainActivity.kt)

<div align="center">

[← Lab 2 · Business Card](README_LAB_2.md) · [Về trang chủ](README.md) · [Lab 4 · Tip Calculator →](README_LAB_4.md)

</div>
