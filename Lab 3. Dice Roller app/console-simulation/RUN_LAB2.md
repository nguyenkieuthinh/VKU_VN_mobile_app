# HƯỚNG DẪN CHẠY BÀI THỰC HÀNH LAB 2 (GRADLE CLI & INTERACTIVE MENU)

Dự án **Lab 2** đã được cấu hình độc lập hoàn chỉnh với Gradle wrapper (`./gradlew`).

---

## 📌 CÁCH 1: Chạy Menu tổng hợp Tương tác (Khuyên dùng)

Từ thư mục dự án `lab2`, chạy lệnh:

```bash
./gradlew run
```

Sau khi menu xuất hiện, bạn chọn số tương ứng từ `1` đến `5` để chạy bài thực hành mong muốn hoặc chế độ tương tác.

---

## 📌 CÁCH 2: Chạy trực tiếp từng bài học qua Gradle CLI

Bạn có thể chạy riêng từng bài bằng cách truyền tham số `-PmainClass`:

### 🎲 Chạy Bài 1 (Dice Roller App):
```bash
./gradlew run -PmainClass=vku.ltm.lab2.Bai1_DiceRollerKt
```

### 🔍 Chạy Bài 2 (Debugger & State Recomposition):
```bash
./gradlew run -PmainClass=vku.ltm.lab2.Bai2_DebuggerAndStateKt
```

### 🍋 Chạy Bài 3 (Lemonade Practice App):
```bash
./gradlew run -PmainClass=vku.ltm.lab2.Bai3_LemonadePracticeKt
```

### 🔘 Chạy Bài 4 (Advanced Click Behavior Practice):
```bash
./gradlew run -PmainClass=vku.ltm.lab2.Bai4_ClickBehaviorPracticeKt
```

---

## 🧪 CÁCH 3: Chạy Unit Tests để kiểm tra mã nguồn

Để chạy toàn bộ các bài kiểm thử tự động của Lab 2 (Dice Roller range, Lemonade state transitions, Counter bounds, Toggle state):

```bash
./gradlew test
```

Báo cáo kết quả kiểm thử sẽ nằm tại `build/reports/tests/test/index.html`.
