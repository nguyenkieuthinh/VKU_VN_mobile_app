# Lab 3: Dice Roller app

Xây dựng ứng dụng **tung xúc xắc (Dice Roller)** bằng Jetpack Compose — bấm nút Roll để đổ ngẫu nhiên từ 1 đến 6 và hiển thị hình xúc xắc tương ứng.

## Video demo

<video src="video/VIDEO_LAB_3.webm" controls preload="metadata" playsinline width="100%"></video>

## Tính năng

- Nút `Roll` tung xúc xắc ngẫu nhiên (1..6)
- Hiển thị hình ảnh xúc xắc tương ứng với kết quả
- Hiển thị thông báo (trúng số may mắn / lời động viên)
- Cập nhật giao diện ngay khi state thay đổi (recomposition)

## Kiến thức áp dụng

- State management: `remember` + `mutableIntStateOf`
- Xử lý sự kiện nhấn nút: `Button(onClick = { ... })`
- Biểu thức `when` để chọn hình ảnh / thông báo theo kết quả
- Composable stateful (`DiceRollerApp`) và stateless (`Image`, `Text`)

## Chạy dự án

```bash
cd "Lab 3. Dice Roller app"
./gradlew assembleDebug      # Build
./gradlew installDebug       # Cài lên thiết bị (qua adb)
```

## Mô phỏng console (console-simulation)

Kèm theo bộ bài mô phỏng trên console minh họa các khái niệm của Unit 2:

- `Bai1_DiceRoller.kt` — Dice Roller App
- `Bai2_DebuggerAndState.kt` — Debugger & State Recomposition
- `Bai3_LemonadePractice.kt` — Lemonade App Practice
- `Bai4_ClickBehaviorPractice.kt` — Click Behavior Practice (State hoisting, Toggle)

Xem hướng dẫn chi tiết: [`console-simulation/RUN_LAB2.md`](Lab%203.%20Dice%20Roller%20app/console-simulation/RUN_LAB2.md)

Mã nguồn chính: [`src/main/java/vku/ltm/diceroller/MainActivity.kt`](Lab%203.%20Dice%20Roller%20app/src/main/java/vku/ltm/diceroller/MainActivity.kt)
