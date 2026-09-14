# State trong Jetpack Compose — Tóm tắt (Tip Time app)

Nguồn: [Giới thiệu về trạng thái trong Compose](https://developer.android.com/codelabs/basic-android-kotlin-compose-using-state?hl=vi)

## Video demo

<video src="../video/VIDEO_LAB_4.webm" controls width="100%"></video>

Tải về: [VIDEO_LAB_4.webm](../video/VIDEO_LAB_4.webm)

## Mục tiêu

Xây ứng dụng tính tiền boa **Tip Time**: nhập số tiền hóa đơn → tự động hiện số tiền boa (mặc định 15%).

**Kiến thức chính:**
- Cách nghĩ về state trong UI
- Compose dùng state để render dữ liệu
- Thêm `TextField` để nhận input
- Cách "hoist" (chuyển lên) state

## Mã khởi đầu

```bash
git clone https://github.com/google-developer-training/basic-android-kotlin-compose-training-tip-calculator.git
cd basic-android-kotlin-compose-training-tip-calculator
git checkout starter
```

Mã giải pháp (sau khi hoàn thành): checkout branch `state`.

---

## 1. State là gì?

> State = bất kỳ giá trị nào có thể thay đổi theo thời gian (biến, dữ liệu form, trạng thái nút bấm...).

Trong app này, state = **số tiền hóa đơn người dùng nhập**.

## 2. Thêm `TextField` nhận input

```kotlin
@Composable
fun EditNumberField(modifier: Modifier = Modifier) {
   TextField(
      value = "",
      onValueChange = {},
      modifier = modifier
   )
}
```

- `value`: giá trị hiển thị trong ô nhập.
- `onValueChange`: callback chạy mỗi khi người dùng gõ.

Gọi trong `TipTimeLayout()`:

```kotlin
EditNumberField(modifier = Modifier.padding(bottom = 32.dp).fillMaxWidth())
```

## 3. `mutableStateOf()` — biến quan sát được

Biến thường (`val amountInput = "0"`) **không** khiến Compose recompose khi thay đổi. Cần dùng `State`/`MutableState`:

```kotlin
import androidx.compose.runtime.mutableStateOf

var amountInput: MutableState<String> = mutableStateOf("0")
// hoặc ngắn gọn (type inference):
var amountInput = mutableStateOf("0")
```

⚠️ Cảnh báo: *"Creating a state object during composition without using remember"* — sẽ fix ở bước tiếp theo.

Dùng trong `TextField`:

```kotlin
TextField(
   value = amountInput.value,
   onValueChange = { amountInput.value = it },
)
```

**Vấn đề còn lại:** mỗi lần recomposition, `EditNumberField()` chạy lại → `amountInput` bị reset về `"0"`.

## 4. `remember` — giữ state qua recomposition

```kotlin
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

var amountInput by remember { mutableStateOf("") }
```

- `remember`: lưu giá trị qua các lần recomposition (chỉ mất khi rời khỏi Composition).
- `by`: Kotlin property delegate → dùng trực tiếp `amountInput` thay vì `amountInput.value`.
- Cần import thêm `getValue`, `setValue` (Android Studio có thể báo lỗi nếu thiếu).

```kotlin
@Composable
fun EditNumberField(modifier: Modifier = Modifier) {
   var amountInput by remember { mutableStateOf("") }
   TextField(
       value = amountInput,
       onValueChange = { amountInput = it },
       modifier = modifier
   )
}
```

## 5. Composition & Recomposition (khái niệm)

| Thuật ngữ | Ý nghĩa |
|---|---|
| **Composition** | Bản mô tả UI mà Compose tạo ra khi chạy các composable |
| **Initial composition** | Lần đầu Compose chạy composable để tạo UI |
| **Recomposition** | Chạy lại composable bị ảnh hưởng khi state đổi, cập nhật Composition |

Compose chỉ theo dõi và recompose khi state được khai báo bằng `State`/`MutableState` (qua `mutableStateOf`).

## 6. Hoàn thiện UI cho TextField

```kotlin
TextField(
    value = amountInput,
    onValueChange = { amountInput = it },
    singleLine = true,
    label = { Text(stringResource(R.string.bill_amount)) },
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    modifier = modifier
)
```

- `singleLine = true`: ô nhập 1 dòng, cuộn ngang.
- `label`: nhãn cho ô nhập.
- `keyboardOptions`: đặt loại bàn phím (số, email...) — xem [KeyboardType](https://developer.android.com/reference/kotlin/androidx/compose/ui/text/input/KeyboardType?hl=vi).

## 7. Tính và hiển thị tiền boa

Hàm có sẵn trong mã khởi đầu:

```kotlin
private fun calculateTip(amount: Double, tipPercent: Double = 15.0): String {
    val tip = tipPercent / 100 * amount
    return NumberFormat.getCurrencyInstance().format(tip)
}
```

Chuyển `String` → `Double`, xử lý null bằng toán tử Elvis `?:`:

```kotlin
val amount = amountInput.toDoubleOrNull() ?: 0.0
val tip = calculateTip(amount)
```

## 8. State Hoisting — chuyển state lên trên ⭐ (phần quan trọng nhất)

**Vấn đề:** `amountInput` là state riêng của `EditNumberField()`, nhưng `TipTimeLayout()` cần dùng nó để tính `tip` và hiển thị → phải "hoist" state lên composable cha.

**Khi nào nên hoist state:**
- Cần chia sẻ state cho nhiều composable
- Muốn composable có thể tái sử dụng (stateless)

**Stateless vs Stateful:**
- *Stateless*: không tự giữ state, chỉ hiển thị state được truyền vào qua tham số.
- *Stateful*: tự quản lý state có thể đổi theo thời gian.

**Pattern chuẩn khi hoist:**
```kotlin
value: T                    // giá trị hiện tại để hiển thị
onValueChange: (T) -> Unit  // callback khi giá trị đổi
```

### Áp dụng:

```kotlin
@Composable
fun EditNumberField(
   value: String,
   onValueChange: (String) -> Unit,
   modifier: Modifier = Modifier
) {
   TextField(
       value = value,
       onValueChange = onValueChange,
       singleLine = true,
       label = { Text(stringResource(R.string.bill_amount)) },
       keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
       modifier = modifier
   )
}

@Composable
fun TipTimeLayout() {
   var amountInput by remember { mutableStateOf("") }
   val amount = amountInput.toDoubleOrNull() ?: 0.0
   val tip = calculateTip(amount)

   Column(
       modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
       horizontalAlignment = Alignment.CenterHorizontally,
       verticalArrangement = Arrangement.Center
   ) {
       Text(
           text = stringResource(R.string.calculate_tip),
           modifier = Modifier
               .padding(bottom = 16.dp, top = 40.dp)
               .align(alignment = Alignment.Start)
       )
       EditNumberField(
           value = amountInput,
           onValueChange = { amountInput = it },
           modifier = Modifier
               .padding(bottom = 32.dp)
               .fillMaxWidth()
       )
       Text(
           text = stringResource(R.string.tip_amount, tip),
           style = MaterialTheme.typography.displaySmall
       )
       Spacer(modifier = Modifier.height(150.dp))
   }
}
```

→ `TipTimeLayout()` giờ là nơi giữ state (`amountInput`), còn `EditNumberField()` trở thành **stateless**, tái sử dụng được.

### Định dạng vị trí (string placeholder)

`strings.xml`:
```xml
<string name="tip_amount">Tip Amount: %s</string>
```

Dùng: `stringResource(R.string.tip_amount, tip)`

---

## Tóm tắt nhanh (cheat sheet)

| Khái niệm | Công dụng |
|---|---|
| `mutableStateOf(x)` | Tạo state quan sát được, Compose theo dõi thay đổi |
| `remember { }` | Giữ giá trị qua các lần recomposition |
| `by` (delegate) | Truy cập trực tiếp `.value` mà không cần gõ `.value` |
| `onValueChange` | Callback nhận giá trị mới từ input |
| State hoisting | Đưa state lên composable cha để chia sẻ / làm composable con stateless |
| `toDoubleOrNull() ?: 0.0` | Parse String → Double an toàn, tránh crash khi null |

## Checklist khi code lại từ đầu

- [ ] Tạo `EditNumberField()` với `TextField` cơ bản
- [ ] Thêm `mutableStateOf` cho `amountInput`
- [ ] Bọc bằng `remember { }` để không mất state khi recompose
- [ ] Thêm `label`, `singleLine`, `keyboardOptions` cho UX tốt hơn
- [ ] Viết `calculateTip()` + convert String → Double an toàn
- [ ] Hoist state: thêm tham số `value` + `onValueChange` vào `EditNumberField()`
- [ ] Chuyển `remember { mutableStateOf("") }` lên `TipTimeLayout()`
- [ ] Hiển thị `tip` bằng string placeholder `%s`

## Tài liệu tham khảo thêm

- [State và Jetpack Compose](https://developer.android.com/jetpack/compose/state?hl=vi)
- [Tư duy trong Compose](https://developer.android.com/jetpack/compose/mental-model?hl=vi)
- [Vị trí để chuyển state lên trên](https://developer.android.com/jetpack/compose/state-hoisting?hl=vi)
