package com.example.firebaseproject

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.firebaseproject.ui.theme.greenColor

// Khung chung: TopAppBar xanh + tieu de can giua, dung cho ca 3 man hinh
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(title: String, content: @Composable (PaddingValues) -> Unit) =
        Scaffold(
                topBar = {
                    TopAppBar(
                            title = {
                                Text(
                                        title,
                                        Modifier.fillMaxWidth(),
                                        color = Color.White,
                                        textAlign = TextAlign.Center
                                )
                            },
                            colors =
                                    TopAppBarDefaults.topAppBarColors(
                                            containerColor = greenColor
                                    )
                    )
                },
                content = content
        )

// Form 3 o nhap + nut submit. Dung cho ca THEM va SUA
// (khac nhau o init, btnText, onSubmit).
// extra: slot them nut phu (vd nut Xoa) ben duoi.
@Composable
fun CourseForm(
        pad: PaddingValues,
        init: Course,
        btnText: String,
        extra: @Composable ColumnScope.() -> Unit = {},
        onSubmit: (Course) -> Unit
) {
    val ctx = LocalContext.current
    var name by remember { mutableStateOf(init.courseName ?: "") }
    var dur by remember { mutableStateOf(init.courseDuration ?: "") }
    var desc by remember { mutableStateOf(init.courseDescription ?: "") }

    @Composable
    fun Field(v: String, hint: String, set: (String) -> Unit) =
            TextField(
                    value = v,
                    onValueChange = set,
                    placeholder = { Text(hint) },
                    singleLine = true,
                    modifier = Modifier.padding(16.dp).fillMaxWidth()
            )

    Column(
            Modifier.fillMaxSize().padding(pad),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Field(name, "Enter your course name") { name = it }
        Field(dur, "Enter your course duration") { dur = it }
        Field(desc, "Enter your course description") { desc = it }
        Button(
                onClick = {
                    when {
                        name.isBlank() ->
                                Toast.makeText(ctx, "Please enter course name", Toast.LENGTH_SHORT)
                                        .show()
                        dur.isBlank() ->
                                Toast.makeText(
                                                ctx,
                                                "Please enter course duration",
                                                Toast.LENGTH_SHORT
                                        )
                                        .show()
                        desc.isBlank() ->
                                Toast.makeText(
                                                ctx,
                                                "Please enter course description",
                                                Toast.LENGTH_SHORT
                                        )
                                        .show()
                        // Giu nguyen courseID cu (khi SUA) de ghi de dung document
                        else -> onSubmit(Course(init.courseID, name, dur, desc))
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) { Text(btnText, Modifier.padding(8.dp)) }

        extra()
    }
}
