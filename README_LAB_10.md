# Lab 10 · Mars Photos

Ứng dụng Jetpack Compose lấy danh sách ảnh sao Hỏa qua REST API và tải ảnh từ URL bằng Coil.

## Tính năng

- Gọi Mars Photos API bằng Retrofit và Gson.
- Hiển thị tối đa 20 ảnh sao Hỏa trong lưới thích ứng `LazyVerticalGrid`.
- Có tab tải ảnh từ các URL trong file `Lab 10. Mars Photos app/img/image-links.txt` trên GitHub.
- Tab ảnh Internet đọc lại danh sách khi mở, tự hiển thị link mới được thêm và giới hạn tối đa 20 ảnh.
- Hiển thị trạng thái đang tải, lỗi tải ảnh, lỗi mạng và nút thử lại.
- Giao diện và trạng thái được viết bằng Kotlin/Jetpack Compose, không dùng XML layout.

## Ảnh mẫu

Mỗi dòng trong `img/image-links.txt` là một URL ảnh HTTPS. Để thêm ảnh, thêm URL vào file rồi push lên GitHub; khi mở lại tab **Ảnh Internet**, app tải danh sách mới.

| Hồ núi lúc hoàng hôn | Đường núi | Hồ Wanaka |
|:---:|:---:|:---:|
| ![Hồ núi lúc hoàng hôn](Lab%2010.%20Mars%20Photos%20app/img/ddg_1775238082972349461.jpg) | ![Đường núi](Lab%2010.%20Mars%20Photos%20app/img/ddg_1774780397475447773.jpg) | ![Hồ Wanaka](Lab%2010.%20Mars%20Photos%20app/img/ken-cheung-KonWFWUaAuk-unsplash.jpg) |

## Chạy ứng dụng

Mở thư mục `Lab 10. Mars Photos app` trong Android Studio, đồng bộ Gradle và chạy trên máy ảo hoặc thiết bị có Internet.

Ứng dụng chỉ cần quyền Internet. Lab này không dùng Firebase hoặc cấu hình dịch vụ Firebase.

## Tài liệu tham khảo

- [Android Basics with Compose: Load and display images from the internet](https://developer.android.com/codelabs/basic-android-kotlin-compose-load-images?hl=vi)
- [Coil](https://coil-kt.github.io/coil/)
