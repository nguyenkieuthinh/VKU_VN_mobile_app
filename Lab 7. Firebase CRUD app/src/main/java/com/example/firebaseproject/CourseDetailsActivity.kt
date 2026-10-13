package com.example.firebaseproject

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firebaseproject.ui.theme.FirebaseprojectTheme
import com.example.firebaseproject.ui.theme.greenColor
import com.google.firebase.firestore.FirebaseFirestore

class CourseDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseprojectTheme {
                Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AppScaffold("Danh sach khoa hoc") { pad ->
                        val ctx = LocalContext.current
                        val courses = remember { mutableStateListOf<Course>() }

                        // addSnapshotListener: danh sach TU CAP NHAT moi khi du lieu
                        // tren Firestore thay doi (them/sua/xoa) -> khong can mo lai man hinh.
                        // DisposableEffect de go listener khi man hinh bi huy (tranh ro ri).
                        DisposableEffect(Unit) {
                            val registration =
                                    FirebaseFirestore.getInstance()
                                            .collection("Courses")
                                            .addSnapshotListener { snap, err ->
                                                if (err != null) {
                                                    Toast.makeText(
                                                                    ctx,
                                                                    "Fail to get data",
                                                                    Toast.LENGTH_SHORT
                                                            )
                                                            .show()
                                                    return@addSnapshotListener
                                                }
                                                courses.clear()
                                                snap?.documents?.forEach { d ->
                                                    d.toObject(Course::class.java)?.let {
                                                        // Gan lai id that cua document
                                                        it.courseID = d.id
                                                        courses.add(it)
                                                    }
                                                }
                                                if (snap != null && snap.isEmpty) {
                                                    Toast.makeText(
                                                                    ctx,
                                                                    "No data found",
                                                                    Toast.LENGTH_SHORT
                                                            )
                                                            .show()
                                                }
                                            }
                            onDispose { registration.remove() }
                        }

                        CourseList(courses, pad)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseList(courses: List<Course>, pad: PaddingValues) {
    val ctx = LocalContext.current
    LazyColumn(Modifier.fillMaxSize().padding(pad)) {
        items(courses) { c ->
            Card(
                    onClick = { // bam vao item -> sang man hinh Sua, truyen du lieu qua Intent extra
                        ctx.startActivity(
                                Intent(ctx, UpdateCourse::class.java).apply {
                                    putExtra("courseID", c.courseID)
                                    putExtra("courseName", c.courseName)
                                    putExtra("courseDuration", c.courseDuration)
                                    putExtra("courseDescription", c.courseDescription)
                                }
                        )
                    },
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(Modifier.padding(8.dp).fillMaxWidth()) {
                    Text(
                            c.courseName ?: "",
                            Modifier.padding(4.dp),
                            color = greenColor,
                            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    )
                    Text(
                            c.courseDuration ?: "",
                            Modifier.padding(4.dp),
                            color = Color.Black,
                            fontSize = 15.sp
                    )
                    Text(
                            c.courseDescription ?: "",
                            Modifier.padding(4.dp),
                            color = Color.Black,
                            fontSize = 15.sp
                    )
                }
            }
        }
    }
}
