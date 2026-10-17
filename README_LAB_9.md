<div align="center">

# Inventory

**Lab 9 · Android với Jetpack Compose**

[![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Room](https://img.shields.io/badge/Room-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com/training/data-storage/room)
[![SQLite](https://img.shields.io/badge/SQLite-003B57?style=for-the-badge&logo=sqlite&logoColor=white)](https://www.sqlite.org)

</div>

---

> Ứng dụng **Inventory** — lưu danh sách hàng hoá xuống **SQLite** bằng **Room**, viết theo codelab
> *[Persist data with Room](https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room)*.
> Trước Lab 9, nút **Save** không làm gì cả: dữ liệu chỉ nằm trong `ViewModel` và mất ngay khi thoát app.
> Lab này thêm tầng database để item được ghi thật xuống đĩa.

---

## Video demo

<!-- Dán link video demo Lab 9 vào đây, cùng dạng với các lab trước:
<video src="LINK_VIDEO_DEMO" controls width="100%"></video>
-->

> *Video demo sẽ được cập nhật.*

## Ảnh minh họa

<!--
| Màn hình chính | Nhập hàng hoá | Sau khi lưu |
|:--------------:|:-------------:|:-----------:|
| <img src="video/lab9_1.png" alt="Màn hình chính" width="240"> | <img src="video/lab9_2.png" alt="Màn hình nhập hàng" width="240"> | <img src="video/lab9_3.png" alt="Danh sách sau khi lưu" width="240"> |
-->

> *Ảnh minh họa sẽ được cập nhật.*

## Vấn đề & cách giải quyết

| Trước Lab 9 | Sau Lab 9 |
|-------------|-----------|
| Bấm **Save** không có phản ứng gì (`onSaveClick = { }`) | `saveItem()` ghi item xuống Room rồi `navigateBack()` về màn hình chính |
| `Item` là class thường, không gắn với bảng nào | `Item` là `@Entity(tableName = "items")`, `id` là `@PrimaryKey(autoGenerate = true)` |
| `ItemsRepository` là interface rỗng | Khai báo đủ `insert` / `update` / `delete` / `getItemStream` / `getAllItemsStream` |
| Không có nơi lưu dữ liệu | `InventoryDatabase` (SQLite) + `ItemDao` do Room sinh code lúc build |

## Kiến thức áp dụng

| Khái niệm | Cách dùng |
|-----------|-----------|
| Entity | `@Entity(tableName = "items")`, `@PrimaryKey(autoGenerate = true)`, mặc định `id = 0` |
| DAO | `@Dao` + `@Insert(onConflict = OnConflictStrategy.IGNORE)`, `@Update`, `@Delete`, `@Query` |
| Hàm suspend | `insert` / `update` / `delete` là `suspend` để Room chạy trên luồng IO, không chặn UI |
| `Flow` | `getItem()` / `getAllItems()` trả `Flow` — bảng đổi là UI tự cập nhật |
| Database | `@Database(entities = [Item::class], version = 1, exportSchema = false)` |
| Singleton | `@Volatile var Instance` + `synchronized(this)` để chỉ có một kết nối tới database |
| KSP | `ksp("androidx.room:room-compiler")` sinh code của DAO lúc build (nhanh hơn KAPT) |
| Repository | `ItemsRepository` (interface) + `OfflineItemsRepository(itemDao)` — tách UI khỏi nguồn dữ liệu |
| Dependency injection | `AppContainer` giữ `itemsRepository`, `AppViewModelProvider` truyền vào ViewModel |
| Coroutine trong Compose | `rememberCoroutineScope()` + `scope.launch { viewModel.saveItem(); navigateBack() }` |

## Cấu trúc mã nguồn

| File | Vai trò |
|------|---------|
| `data/Item.kt` | `@Entity` — một dòng trong bảng `items` |
| `data/ItemDao.kt` | Các câu truy vấn: `insert`, `update`, `delete`, `getItem`, `getAllItems` |
| `data/InventoryDatabase.kt` | `@Database` + singleton `getDatabase(context)`, tên file `item_database` |
| `data/ItemsRepository.kt` | Interface mô tả các thao tác với item |
| `data/OfflineItemsRepository.kt` | Cài đặt interface trên bằng `ItemDao` |
| `data/AppContainer.kt` | Tạo `InventoryDatabase` → `itemDao()` → `OfflineItemsRepository` |
| `ui/item/ItemEntryViewModel.kt` | Thêm `saveItem()`, nhận `ItemsRepository` qua constructor |
| `ui/AppViewModelProvider.kt` | Truyền `itemsRepository` vào `ItemEntryViewModel` |
| `ui/item/ItemEntryScreen.kt` | `onSaveClick` gọi `saveItem()` rồi `navigateBack()` |

## Luồng hoạt động

```
ItemEntryScreen            ItemEntryViewModel          OfflineItemsRepository        Room / SQLite
      │                            │                            │                        │
      │  người dùng bấm Save       │                            │                        │
      ├───────────────────────────►│  saveItem()                │                        │
      │                            │  (validateInput() == true) │                        │
      │                            ├───────────────────────────►│  insertItem(item)      │
      │                            │                            ├───────────────────────►│
      │                            │                            │                        │ INSERT INTO items
      │                            │◄───────────────────────────┤                        │
      │  scope.launch ─────────────┤                            │                        │
      │  navigateBack()            │                            │                        │
      ▼                            │                            │                        │
  HomeScreen  ◄──────────────────── getAllItems(): Flow<List<Item>> ────────────────────┤
```

## Kiểm tra bằng Database Inspector

Cách nhanh nhất để chứng minh dữ liệu đã được lưu thật:

1. Chạy app trên thiết bị/emulator (**View → Tool Windows → App Inspection** trong Android Studio).
2. Chọn tab **Database Inspector** → chọn tiến trình `com.example.inventory`.
3. Mở `item_database` → bảng `items` → bấm **Live updates** để bảng tự refresh.
4. Nhập một item ở màn hình *Add Item* rồi bấm **Save**: bảng `items` hiện thêm một dòng mới
   (`id` tự tăng, `name` / `price` / `quantity` đúng như đã nhập) và app quay về màn hình chính.
5. Xoá app rồi cài lại, hoặc tắt hẳn app rồi mở lại — dữ liệu vẫn còn, vì đã ghi xuống đĩa.

Nếu không dùng Android Studio, có thể kiểm tra bằng adb:

```bash
adb shell "run-as com.example.inventory ls databases"
# -> item_database  item_database-shm  item_database-wal
```

> 💡 Bảng chỉ có dữ liệu **sau khi bấm Save**. Đóng app giữa chừng thì item đang nhập bị mất —
> Lab 9 dừng ở bước ghi dữ liệu, phần đọc dữ liệu ra màn hình chính nằm ở codelab
> *Read and update data using Room*.

## Chạy dự án

```bash
cd "Lab 9. Inventory app"
./gradlew assembleDebug      # Build APK
./gradlew installDebug       # Cài lên thiết bị qua adb
```

**Mã nguồn chính:** [`app/src/main/java/com/example/inventory/`](Lab%209.%20Inventory%20app/app/src/main/java/com/example/inventory/)

## Lỗi thường gặp

| Lỗi | Nguyên nhân & cách xử lý |
|-----|--------------------------|
| `Cannot access database on the main thread` | Gọi hàm DAO ngoài coroutine — `insert` / `update` / `delete` phải là `suspend` và chạy trong `viewModelScope` |
| `Item has no primary key` khi build | Thiếu `@PrimaryKey` trên `id`, hoặc quên `@Entity` |
| `Schema export directory is not provided` | Thêm `exportSchema = false` vào `@Database`, hoặc khai báo `room.schemaLocation` |
| `Unresolved reference: ItemDao_Impl` | Chưa apply plugin KSP hoặc thiếu `ksp("androidx.room:room-compiler")` |
| `Cannot figure out how to save this field into database` | Room không hỗ trợ kiểu dữ liệu đó — thêm `@TypeConverter` hoặc đổi sang kiểu Room hiểu |
| Bấm **Save** không phản ứng | Nút chỉ bật khi `isEntryValid` — cả 3 ô *name* / *price* / *quantity* phải được nhập |
| Bảng `items` trống dù đã bấm Save | Room chưa ghi xong là do `insert` còn đang chạy; kiểm tra lại `AppViewModelProvider` đã truyền đúng `itemsRepository` chưa |

<div align="center">

[← Lab 8 · Pizzeria](README_LAB_8.md) · [Về trang chủ](README.md)

</div>
