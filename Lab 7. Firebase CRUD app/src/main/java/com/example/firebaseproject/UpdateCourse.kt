package com.example.firebaseproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.firebaseproject.ui.theme.FirebaseprojectTheme
import com.google.firebase.firestore.FirebaseFirestore

class UpdateCourse : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Nhan du lieu tu Intent (do CourseDetailsActivity truyen sang)
        val init =
                Course(
                        intent.getStringExtra("courseID"),
                        intent.getStringExtra("courseName"),
                        intent.getStringExtra("courseDuration"),
                        intent.getStringExtra("courseDescription")
                )
        setContent {
            FirebaseprojectTheme {
                Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AppScaffold("Cap nhat du lieu") { pad ->
                        val ctx = LocalContext.current
                        CourseForm(
                                pad,
                                init,
                                "Update Data",
                                extra = { // === XOA (bo sung so voi ban docx) ===
                                    Button(
                                            onClick = { deleteCourse(init.courseID, ctx) },
                                            colors =
                                                    ButtonDefaults.buttonColors(
                                                            containerColor =
                                                                    MaterialTheme.colorScheme.error
                                                    ),
                                            modifier = Modifier.fillMaxWidth().padding(16.dp)
                                    ) { Text("Delete Data", Modifier.padding(8.dp)) }
                                }
                        ) { updateDataToFirebase(it, ctx) }
                    }
                }
            }
        }
    }
}

private fun backToList(ctx: Context) {
    // CLEAR_TOP + SINGLE_TOP: quay ve dung man hinh danh sach dang co san trong stack,
    // khong tao them man hinh moi. Man hinh do dang nghe snapshotListener nen
    // tu dong cap nhat du lieu vua sua/xoa.
    ctx.startActivity(
            Intent(ctx, CourseDetailsActivity::class.java)
                    .addFlags(
                            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    )
    )
}

fun updateDataToFirebase(course: Course, ctx: Context) {
    FirebaseFirestore.getInstance()
            .collection("Courses")
            // set = ghi de toan bo document; muon sua 1 phan: .update("courseName", v)
            .document(course.courseID.toString())
            .set(course)
            .addOnSuccessListener {
                Toast.makeText(ctx, "Course Updated", Toast.LENGTH_SHORT).show()
                (ctx as? ComponentActivity)?.finish()
                backToList(ctx)
            }
            .addOnFailureListener {
                Toast.makeText(ctx, "Fail to update: ${it.message}", Toast.LENGTH_SHORT).show()
            }
}

fun deleteCourse(id: String?, ctx: Context) {
    if (id.isNullOrEmpty()) return
    FirebaseFirestore.getInstance()
            .collection("Courses")
            .document(id)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(ctx, "Course Deleted", Toast.LENGTH_SHORT).show()
                (ctx as? ComponentActivity)?.finish()
                backToList(ctx)
            }
            .addOnFailureListener {
                Toast.makeText(ctx, "Fail to delete: ${it.message}", Toast.LENGTH_SHORT).show()
            }
}
