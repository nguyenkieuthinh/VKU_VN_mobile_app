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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseprojectTheme {
                Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AppScaffold("Them du lieu") { pad ->
                        val ctx = LocalContext.current
                        CourseForm(
                                pad,
                                Course(),
                                "Add Data",
                                extra = { // nut sang man hinh danh sach
                                    Button(
                                            onClick = {
                                                ctx.startActivity(
                                                        Intent(
                                                                ctx,
                                                                CourseDetailsActivity::class.java
                                                        )
                                                )
                                            },
                                            modifier = Modifier.fillMaxWidth().padding(16.dp)
                                    ) { Text("View Courses", Modifier.padding(8.dp)) }
                                }
                        ) { addDataToFirebase(it, ctx) }
                    }
                }
            }
        }
    }
}

// .add() de Firestore tu sinh document id.
// Muon id rieng: .document(id).set(course)
fun addDataToFirebase(course: Course, context: Context) {
    FirebaseFirestore.getInstance()
            .collection("Courses")
            .add(course)
            .addOnSuccessListener {
                Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Fail to add: $it", Toast.LENGTH_SHORT).show()
            }
}
