package vku.ltm.lab2

import kotlin.random.Random

/**
 * Android Basics Compose - Unit 2 Pathway 2: Bài 1 (Dice Roller App)
 * 
 * Khái niệm chính:
 * 1. Quản lý trạng thái (State Management): `remember` và `mutableStateOf`
 * 2. Phản hồi sự kiện nhấp nút (`Button(onClick = { ... })`)
 * 3. Hiển thị hình ảnh con xúc xắc tương ứng với kết quả ngẫu nhiên (1..6)
 * 4. Tách biệt Composable có trạng thái (Stateful) và không có trạng thái (Stateless)
 */

// Mô phỏng đối tượng Modifier trong Compose
data class ComposeModifier(val padding: Int = 0, val centered: Boolean = true) {
    fun fillMaxSize() = copy()
    fun wrapContentSize() = copy()
}

// Lớp lưu trữ trạng thái của ứng dụng Dice Roller
data class DiceState(
    var currentRoll: Int = 1,
    var totalRolls: Int = 0,
    val rollHistory: MutableList<Int> = mutableListOf()
) {
    val averageRoll: Double
        get() = if (totalRolls == 0) 0.0 else rollHistory.sum().toDouble() / totalRolls

    // Đường dẫn tới hình ảnh xúc xắc thật (PNG) trong thư mục resources
    fun getDiceImageResource(): String = "dice_images/dice_$currentRoll.png"

    // Vẽ hình ảnh xúc xắc bằng ART/ASCII
    fun getDiceAsciiArt(): String {
        return when (currentRoll) {
            1 -> """
                +-------+
                |       |
                |   o   |
                |       |
                +-------+
            """.trimIndent()
            2 -> """
                +-------+
                | o     |
                |       |
                |     o |
                +-------+
            """.trimIndent()
            3 -> """
                +-------+
                | o     |
                |   o   |
                |     o |
                +-------+
            """.trimIndent()
            4 -> """
                +-------+
                | o   o |
                |       |
                | o   o |
                +-------+
            """.trimIndent()
            5 -> """
                +-------+
                | o   o |
                |   o   |
                | o   o |
                +-------+
            """.trimIndent()
            6 -> """
                +-------+
                | o   o |
                | o   o |
                | o   o |
                +-------+
            """.trimIndent()
            else -> "Invalid dice"
        }
    }
}

/**
 * Hàm Stateful Composable (Mô phỏng): DiceRollerApp
 * Nơi khởi tạo và giữ state của ứng dụng.
 */
class DiceRollerApp {
    private val state = DiceState()

    // Hàm xử lý sự kiện khi nhấn nút "Roll" (Tung xúc xắc)
    fun rollDice(): Int {
        val result = Random.nextInt(1, 7)
        state.currentRoll = result
        state.totalRolls++
        state.rollHistory.add(result)
        return result
    }

    fun getState(): DiceState = state

    // Mô phỏng UI được vẽ bởi Jetpack Compose
    fun renderUI(): String {
        val sb = StringBuilder()
        sb.appendLine("==========================================")
        sb.appendLine("      DICE ROLLER APP (JETPACK COMPOSE)   ")
        sb.appendLine("==========================================")
        sb.appendLine(" [Mô phỏng Image Component]")
        sb.appendLine(" Ảnh PNG: ${state.getDiceImageResource()}")
        sb.appendLine(state.getDiceAsciiArt())
        sb.appendLine(" Kết quả hiện tại: [ ${state.currentRoll} ]")
        sb.appendLine("------------------------------------------")
        sb.appendLine(" [Mô phỏng Button Component: 'ROLL']")
        sb.appendLine(" Thống kê: Tổng số lần lắc = ${state.totalRolls} | Điểm trung bình = %.2f".format(state.averageRoll))
        if (state.rollHistory.isNotEmpty()) {
            sb.appendLine(" Lịch sử các lần lắc: ${state.rollHistory.takeLast(10).joinToString(", ")}")
        }
        sb.appendLine("==========================================")
        return sb.toString()
    }
}

fun runBai1() {
    println(">>> CHẠY BÀI 1: DICE ROLLER APP <<<")
    val app = DiceRollerApp()

    println("\n--- Giao diện ban đầu khi mở App ---")
    print(app.renderUI())

    println("\n--- Thao tác: Nhấn nút [ROLL] 5 lần ---")
    for (i in 1..5) {
        val rolled = app.rollDice()
        println("\nLần $i: Đổ được $rolled")
        print(app.renderUI())
    }
}

// Hàm main dùng để chạy thử độc lập Bài 1
fun main() {
    runBai1()
}

