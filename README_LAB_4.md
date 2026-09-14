# Lab 4: Tip Calculator app (Tip Time)

Xây dựng ứng dụng **tính tiền boa (Tip Time)** bằng Jetpack Compose — nhập số tiền hóa đơn và phần trăm tip để tự động tính ra số tiền boa.

## Video demo

<video src="video/VIDEO_LAB_4.webm" controls preload="metadata" playsinline width="100%"></video>

## Tính năng

- **Bill Amount** — nhập số tiền hóa đơn
- **Tip Percentage (%)** — nhập phần trăm tiền boa (có hậu tố `%`)
- **Round up tip?** — bật/tắt làm tròn lên số tiền boa
- Tự động tính & hiển thị **Tip Amount** ngay khi dữ liệu thay đổi

## Kiến thức áp dụng

- State trong Compose: `mutableStateOf` + `remember`
- **State hoisting** — đưa state lên composable cha, composable con trở thành stateless
- `TextField` nhận input (`label`, `suffix`, `keyboardOptions`)
- `Switch` cho lựa chọn bật/tắt
- Parse an toàn: `toDoubleOrNull() ?: 0.0`
- Tính toán & định dạng tiền tệ (`NumberFormat.getCurrencyInstance()`, `kotlin.math.ceil`)

## Chạy dự án

```bash
cd "Lab 4. Tip Calculator app"
./gradlew assembleDebug      # Build
./gradlew installDebug       # Cài lên thiết bị (qua adb)
```

Mã nguồn chính: [`src/main/java/vku/ltm/tipcalculator/MainActivity.kt`](Lab%204.%20Tip%20Calculator%20app/src/main/java/vku/ltm/tipcalculator/MainActivity.kt)

Tài liệu tóm tắt: [`docs/Lab4.md`](docs/Lab4.md)
