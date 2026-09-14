package vku.ltm.diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiceRollerTheme {
                Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                ) { DiceRollerApp() }
            }
        }
    }
}

@Composable
fun DiceRollerTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = lightColorScheme(), content = content)
}

@Composable
fun DiceRollerApp(modifier: Modifier = Modifier) {
    var result by remember { mutableIntStateOf(1) }
    val imageResource =
            when (result) {
                1 -> R.drawable.dice_1
                2 -> R.drawable.dice_2
                3 -> R.drawable.dice_3
                4 -> R.drawable.dice_4
                5 -> R.drawable.dice_5
                else -> R.drawable.dice_6
            }

    val luckyNumber = 4
    val resultMessage =
            when (result) {
                luckyNumber -> "You won!"
                1 -> "So sorry! You rolled a 1. Try again!"
                2 -> "Sadly, you rolled a 2. Try again!"
                3 -> "Unfortunately, you rolled a 3. Try again!"
                5 -> "Don't cry! You rolled a 5. Try again!"
                6 -> "Apologies! You rolled a 6. Try again!"
                else -> "Rolled $result"
            }

    Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
    ) {
        Image(
                painter = painterResource(id = imageResource),
                contentDescription = "Kết quả đổ xúc xắc: $result",
                modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = "Kết quả: $result", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = resultMessage, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = { result = (1..6).random() }) { Text(text = "Roll", fontSize = 24.sp) }
    }
}
