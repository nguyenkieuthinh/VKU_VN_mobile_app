# Lab 2: Business Card app

Xây dựng ứng dụng **danh thiếp (Business Card)** bằng Jetpack Compose — hiển thị ảnh đại diện, họ tên, chức danh và thông tin liên hệ.

## Video demo

<video src="video/VIDEO_LAB_2.webm" controls preload="metadata" playsinline width="100%"></video>

## Tính năng

- Hiển thị ảnh đại diện (Android logo) ở giữa màn hình
- Hiển thị họ tên, chức danh
- Hiển thị thông tin liên hệ kèm icon: Số điện thoại, Share, Email
- Giao diện được chia bằng đường kẻ (Divider), bố cục 2 khối trên/dưới

## Kiến thức áp dụng

- Các composable layout: `Column`, `Row`, `Spacer`, `Image`, `Icon`, `Text`, `Divider`
- `Modifier`: `padding`, `size`, `fillMaxWidth`, `weight`, `background`
- Tùy chỉnh màu sắc, cỡ chữ (`sp`), độ đậm (`FontWeight`)
- Material Icons (`Icons.Filled.Phone`, `Email`, `Share`)
- Composable tái sử dụng (`ContactRow`), `@Preview`

## Chạy dự án

```bash
cd "Lab 2. Business Card app"
./gradlew assembleDebug      # Build
./gradlew installDebug       # Cài lên thiết bị (qua adb)
```

Mã nguồn chính: [`src/main/java/vku/ltm/businesscard/MainActivity.kt`](Lab%202.%20Business%20Card%20app/src/main/java/vku/ltm/businesscard/MainActivity.kt)
