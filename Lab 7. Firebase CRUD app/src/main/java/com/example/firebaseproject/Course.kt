package com.example.firebaseproject

import com.google.firebase.firestore.Exclude

// Model khop voi document trong collection "Courses" tren Firestore.
// Moi field deu phai co gia tri mac dinh: Firestore dung reflection de tao
// doi tuong tu document (toObject), nen can constructor rong.
data class Course(
        // @Exclude: courseID KHONG duoc luu thanh field trong document.
        // ID that cua document duoc gan lai bang d.id sau khi toObject().
        //
        // BAT BUOC phai co ca @get: va @field: (khong viet tran @Exclude).
        // Kotlin mac dinh chi gan annotation len FIELD, nhung Firestore quet
        // GETTER truoc: getCourseID() khong co annotation -> property van duoc
        // them vao danh sach va bi ghi xuong Firestore. Phai chan ca 2 dau.
        @get:Exclude @field:Exclude var courseID: String? = "",
        var courseName: String? = "",
        var courseDuration: String? = "",
        var courseDescription: String? = ""
)
