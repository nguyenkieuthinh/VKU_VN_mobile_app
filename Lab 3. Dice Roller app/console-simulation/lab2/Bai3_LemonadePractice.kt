package vku.ltm.lab2

import kotlin.random.Random

/**
 * Android Basics Compose - Unit 2 Pathway 2: Bài 3 (Thực hành: Lemonade App)
 * 
 * Đề bài thực hành: Tạo ứng dụng Lemonade tương tác qua 4 trạng thái:
 * 1. Bước 1 (SELECT): Nhấp vào cây chanh để chọn chanh.
 * 2. Bước 2 (SQUEEZE): Nhấp nhiều lần vào quả chanh để vắt (số lần ngẫu nhiên từ 2 đến 4 lần).
 * 3. Bước 3 (DRINK): Nhấp vào ly nước chanh để uống.
 * 4. Bước 4 (RESTART): Nhấp vào ly rỗng để bắt đầu lại từ Bước 1.
 */

enum class LemonadeState(val stepNumber: Int, val title: String, val instruction: String, val asciiArt: String) {
    SELECT(
        stepNumber = 1,
        title = "Cây chanh (Lemon Tree)",
        instruction = "Nhấp vào cây chanh để chọn một quả chanh",
        asciiArt = """
               ,----.
             (  /  \  )
            (  / |  \  )
           (____________)
                 ||
                 ||
        """.trimIndent()
    ),
    SQUEEZE(
        stepNumber = 2,
        title = "Quả chanh (Lemon)",
        instruction = "Nhấp liên tục để vắt chanh!",
        asciiArt = """
              .---.
             /     \
            |  (🍋) |
             \     /
              `---'
        """.trimIndent()
    ),
    DRINK(
        stepNumber = 3,
        title = "Ly nước chanh (Glass of Lemonade)",
        instruction = "Nhấp vào ly để thưởng thức nước chanh",
        asciiArt = """
             |      |
             |~~🍋~~|
             |______|
              \____/
        """.trimIndent()
    ),
    RESTART(
        stepNumber = 4,
        title = "Ly rỗng (Empty Glass)",
        instruction = "Nhấp vào ly rỗng để bắt đầu lại",
        asciiArt = """
             |      |
             |      |
             |______|
              \____/
        """.trimIndent()
    )
}

class LemonadeApp {
    var currentState: LemonadeState = LemonadeState.SELECT
        private set

    var squeezesNeeded: Int = 0
        private set

    var currentSqueezes: Int = 0
        private set

    var totalLemonsMade: Int = 0
        private set

    // Xử lý sự kiện nhấp vào hình ảnh / nút bấm
    fun onImageClick(): String {
        return when (currentState) {
            LemonadeState.SELECT -> {
                currentState = LemonadeState.SQUEEZE
                squeezesNeeded = Random.nextInt(2, 5) // 2 đến 4 lần vắt
                currentSqueezes = 0
                "Bạn đã chọn một quả chanh tươi! Cần vắt $squeezesNeeded lần."
            }
            LemonadeState.SQUEEZE -> {
                currentSqueezes++
                val remaining = squeezesNeeded - currentSqueezes
                if (remaining <= 0) {
                    currentState = LemonadeState.DRINK
                    "Vắt xong! Bạn đã tạo ra một ly nước chanh thơm ngon."
                } else {
                    "Vắt chanh... (Đã vắt $currentSqueezes/$squeezesNeeded lần, còn lại $remaining lần)"
                }
            }
            LemonadeState.DRINK -> {
                currentState = LemonadeState.RESTART
                totalLemonsMade++
                "Ưm, thật sảng khoái! Bạn đã uống hết ly nước chanh."
            }
            LemonadeState.RESTART -> {
                currentState = LemonadeState.SELECT
                squeezesNeeded = 0
                currentSqueezes = 0
                "Sẵn sàng pha ly tiếp theo!"
            }
        }
    }

    fun renderUI(): String {
        val sb = StringBuilder()
        sb.appendLine("==========================================")
        sb.appendLine("        LEMONADE APP (COMPOSE PRACTICE)   ")
        sb.appendLine("==========================================")
        sb.appendLine(" Bước ${currentState.stepNumber}: ${currentState.title}")
        sb.appendLine(" Hướng dẫn: ${currentState.instruction}")
        sb.appendLine("------------------------------------------")
        sb.appendLine(currentState.asciiArt)
        if (currentState == LemonadeState.SQUEEZE) {
            sb.appendLine(" Tiến độ vắt: $currentSqueezes / $squeezesNeeded")
        }
        sb.appendLine("------------------------------------------")
        sb.appendLine(" Tổng số ly nước chanh đã pha: $totalLemonsMade")
        sb.appendLine("==========================================")
        return sb.toString()
    }
}

fun runBai3() {
    println(">>> CHẠY BÀI 3: LEMONADE PRACTICE APP <<<")
    val app = LemonadeApp()

    println("\n--- Giao diện ban đầu ---")
    print(app.renderUI())

    println("\n--- Thực hiện quy trình pha chế nước chanh ---")
    
    // Step 1 -> Step 2
    println("\n[Action] Click 1: Chọn chanh từ cây")
    println("Thông báo: " + app.onImageClick())
    print(app.renderUI())

    // Squeeze until step 3
    var clickCount = 2
    while (app.currentState == LemonadeState.SQUEEZE) {
        println("\n[Action] Click $clickCount: Vắt chanh")
        println("Thông báo: " + app.onImageClick())
        print(app.renderUI())
        clickCount++
    }

    // Step 3 -> Step 4
    println("\n[Action] Click $clickCount: Uống nước chanh")
    println("Thông báo: " + app.onImageClick())
    print(app.renderUI())
    clickCount++

    // Step 4 -> Step 1
    println("\n[Action] Click $clickCount: Nhấp vào ly rỗng để làm lại")
    println("Thông báo: " + app.onImageClick())
    print(app.renderUI())
}

// Hàm main dùng để chạy thử độc lập Bài 3
fun main() {
    runBai3()
}

