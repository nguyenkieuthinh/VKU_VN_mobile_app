# Lab 9 · Inventory — ghi chú tiến độ

> File này để phiên làm việc sau biết **đã làm gì / chưa làm gì** và chỉnh sửa tiếp.
> Cập nhật lần cuối: 2026-10-16.

---

## 1. Bối cảnh

| Mục | Giá trị |
|-----|---------|
| Codelab | [Persist data with Room](https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room) |
| Starter code | repo `google-developer-training/basic-android-kotlin-compose-training-inventory-app`, nhánh **`starter`** |
| Nhánh giải pháp tham khảo | nhánh **`room`** của cùng repo (dùng để đối chiếu cách viết, không copy nguyên) |
| Thư mục project | `Lab 9. Inventory app/` |
| Package | `com.example.inventory` |
| Mục tiêu | Nút **Save** ghi được item xuống SQLite qua Room (trước đó `onSaveClick = { }` nên bấm không có gì xảy ra) |

---

## 2. ĐÃ LÀM

### 2.1. Phiên bản & cấu hình

| Thành phần | Phiên bản | Ghi chú |
|-----------|-----------|---------|
| Room | **2.8.5** | bản ổn định mới nhất; khai báo ở `build.gradle.kts` (gốc) trong `buildscript { extra.apply { set("room_version", "2.8.5") } }` — starter để 2.6.0 |
| KSP | 2.1.0-1.0.29 | đã có sẵn trong starter, phải khớp Kotlin 2.1.0 |
| Kotlin / AGP / Gradle | 2.1.0 / 8.8.0 / 8.12 | giữ nguyên như starter |
| compileSdk / targetSdk | 35 / 35 | giữ nguyên |
| **minSdk** | **26** | ⚠️ starter để **24**, mình đổi theo ràng buộc đề bài — xem mục 4 để đổi lại |

### 2.2. Các file đã sửa / thêm

Đường dẫn tính từ `Lab 9. Inventory app/`:

| File | Trạng thái | Nội dung |
|------|-----------|----------|
| `build.gradle.kts` | sửa | `room_version` 2.6.0 → 2.8.5 |
| `app/build.gradle.kts` | sửa | thêm `room-runtime`, `room-ktx`, `ksp("room-compiler")`; `minSdk` 24 → 26 |

Các file Kotlin dưới đây nằm trong `app/src/main/java/com/example/inventory/`:

| File | Trạng thái | Nội dung |
|------|-----------|----------|
| `data/Item.kt` | sửa | `class` → `data class`, thêm `@Entity(tableName = "items")`, `@PrimaryKey(autoGenerate = true)` trên `id` |
| `data/ItemDao.kt` | **mới** | `@Insert(onConflict = IGNORE)`, `@Update`, `@Delete` (đều `suspend`); `getItem(id): Flow<Item>`; `getAllItems(): Flow<List<Item>>` với `ORDER BY name ASC` |
| `data/InventoryDatabase.kt` | **mới** | `@Database(entities = [Item::class], version = 1, exportSchema = false)`, `abstract fun itemDao()`, singleton `@Volatile Instance` + `synchronized(this)`, tên file `item_database` |
| `data/ItemsRepository.kt` | sửa | interface rỗng → `getAllItemsStream()`, `getItemStream(id)`, `insertItem()`, `updateItem()`, `deleteItem()` |
| `data/OfflineItemsRepository.kt` | sửa | `class OfflineItemsRepository(private val itemDao: ItemDao)` cài đặt 5 hàm trên |
| `data/AppContainer.kt` | sửa | `OfflineItemsRepository(InventoryDatabase.getDatabase(context).itemDao())` |
| `ui/item/ItemEntryViewModel.kt` | sửa | nhận `ItemsRepository` qua constructor; thêm `saveItem()` |
| `ui/AppViewModelProvider.kt` | sửa | `ItemEntryViewModel(inventoryApplication().container.itemsRepository)` |
| `ui/item/ItemEntryScreen.kt` | sửa | thêm `rememberCoroutineScope()`; `onSaveClick` gọi `saveItem()` rồi `navigateBack()` |

### 2.3. Quyết định tự đưa ra (khác codelab, đổi lại được)

1. **`saveItem()` KHÔNG phải `suspend`**, mà tự mở `viewModelScope.launch { }` bên trong.
   → Codelab để `suspend fun saveItem()` rồi `scope.launch { viewModel.saveItem(); navigateBack() }`.
   → Lý do chọn cách này: insert không bị huỷ giữa chừng nếu composable rời khỏi composition.
   → Muốn quay về đúng codelab: bỏ `viewModelScope.launch` trong `ItemEntryViewModel.kt`, thêm `suspend` vào `saveItem()`.
2. **`getItemStream` / DAO `getItem` trả `Flow<Item>`** (không nullable) — đúng như đề bài yêu cầu.
   → Codelab thứ hai sẽ đổi thành `Flow<Item?>`; nếu làm tiếp codelab 2 thì phải sửa ở **3 chỗ**: `ItemDao.getItem`, `ItemsRepository.getItemStream`, `OfflineItemsRepository.getItemStream`.
3. **`minSdk = 26`** thay vì 24 của starter.
4. Đã xoá khỏi starter: `.github/`, `CONTRIBUTING.md`, `README.md` (giữ lại `LICENSE`).
5. Đã thêm `local.properties` (`sdk.dir=/home/thinh/Android/Sdk`) — file này bị `.gitignore` bỏ qua, không lên git.

### 2.4. Build

```bash
cd "Lab 9. Inventory app"
JAVA_HOME=/usr/lib/jvm/java-21-openjdk ./gradlew assembleDebug
```

`JAVA_HOME` là **bắt buộc**: JDK mặc định của máy là 27, Gradle 8.12 không chạy được trên đó.

- Kết quả: **BUILD SUCCESSFUL in 2m 27s** (lần đầu ~8 phút vì phải tải Gradle 8.12 + Room + KSP).
- APK: `Lab 9. Inventory app/app/build/outputs/apk/debug/app-debug.apk` (~9,9 MB).
- Đã kiểm tra trong APK: `package: com.example.inventory`, `minSdkVersion: 26`, `targetSdkVersion: 35`.
- KSP sinh được `app/build/generated/ksp/debug/kotlin/com/example/inventory/data/{ItemDao_Impl,InventoryDatabase_Impl}.kt`.
- SQL Room sinh ra khớp DAO:
  - `INSERT OR IGNORE INTO items (id,name,price,quantity) VALUES (nullif(?, 0),?,?,?)`
  - `SELECT * from items ORDER BY name ASC`
  - `SELECT * from items WHERE id = ?`
  - `UPDATE OR ABORT items SET ... WHERE id = ?`
  - `DELETE FROM items WHERE id = ?`

### 2.5. Tài liệu

- `README_LAB_9.md` (gốc repo) — **đã viết**: giải thích, kiến thức, cấu trúc file, sơ đồ luồng, hướng dẫn Database Inspector, lỗi thường gặp.
- `README.md` (gốc repo) — **đã sửa**: "7 ứng dụng" → "8 ứng dụng", thêm dòng Lab 9 vào bảng, thêm mục video Lab 9, thêm ảnh `lab9_1.png`, nối chuỗi điều hướng cuối trang.

### 2.6. Lỗi đã gặp và cách sửa

| Lỗi | Nguyên nhân | Cách sửa |
|-----|-------------|----------|
| `Cannot get non-null extra property 'roomVersion' as it does not exist` | mình viết `val roomVersion: String by rootProject.extra` trong block `dependencies` của `app/build.gradle.kts` | dùng `${rootProject.extra["room_version"]}` như repo gốc của Google. **Lưu ý:** `buildscript { extra.apply { set(...) } }` ở file gốc có set được lên extra của project hay không thì cách dùng `by` không đọc ra — dùng dạng `extra["..."]` là chắc chắn chạy |

---

## 3. CHƯA LÀM

| # | Việc | Chi tiết |
|---|------|----------|
| 1 | **Chưa test trên thiết bị** | Người dùng tự cài bằng adb. Lệnh: `adb install -r "<đường dẫn APK ở mục 2.4>"` |
| 2 | **Chưa commit / push** | Xem danh sách file ở mục 5 |
| 3 | **Ảnh minh hoạ chưa có** | `README.md` đang trỏ tới `video/lab9_1.png` nhưng file **chưa tồn tại** → ảnh lỗi trên GitHub. `README_LAB_9.md` đã comment sẵn phần ảnh nên không bị lỗi. Cần chụp: màn hình chính, màn hình nhập hàng, và (nên có) bảng `items` trong Database Inspector |
| 4 | **Video demo Lab 9 chưa có** | `README_LAB_9.md` và `README.md` đang để chỗ trống chờ dán link |
| 5 | **Codelab 2 chưa làm** | Phần *Read and update data using Room*: `HomeViewModel`, `HomeUiState`, `ItemEditViewModel`, `ItemDetailsViewModel`, `HomeScreen`, `ItemDetailsScreen`, `ItemEditScreen` vẫn là bản starter rỗng → sau khi Save, màn hình chính **vẫn trống danh sách**. Đây là đúng phạm vi Lab 9, không phải bug |

---

## 4. Muốn đổi lại thì sửa ở đâu

| Muốn gì | Sửa ở |
|---------|-------|
| Quay về `minSdk = 24` như starter | `Lab 9. Inventory app/app/build.gradle.kts`, dòng `minSdk = 26` |
| Đổi phiên bản Room | `Lab 9. Inventory app/build.gradle.kts`, dòng `set("room_version", "2.8.5")` |
| `saveItem()` thành `suspend` cho giống codelab | `ui/item/ItemEntryViewModel.kt` (bỏ `viewModelScope.launch`) + `ui/item/ItemEntryScreen.kt` (giữ `scope.launch`) |
| Đổi tên bảng / tên file database | `data/Item.kt` (`@Entity(tableName = "items")`) và `data/InventoryDatabase.kt` (`"item_database"`) |
| Thêm cột mới vào Item | sửa `data/Item.kt` + tăng `version` trong `InventoryDatabase.kt`, và thêm `Migration` (nếu không sẽ phải xoá app cài lại) |

---

## 5. Trạng thái git

Tính đến lúc ghi chú này, **chưa commit**. Các đường dẫn mới/sửa:

```
?? Lab 9. Inventory app/        (mới toàn bộ)
?? README_LAB_9.md              (mới)
?? LAB9_TIEN_DO.md              (file này)
 M README.md                    (sửa: thêm Lab 9)
```

Commit gần nhất trên `main`: `17d1666 lab 8`.

Lưu ý: `Lab 9. Inventory app/.gitignore` đã bỏ qua `build/`, `.gradle/`, `.kotlin/`, `local.properties` — nên `build/` và APK sẽ không lọt vào commit.
