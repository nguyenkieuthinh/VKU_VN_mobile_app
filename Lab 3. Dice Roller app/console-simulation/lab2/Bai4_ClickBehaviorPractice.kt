package vku.ltm.lab2

/**
 * Android Basics Compose - Unit 2 Pathway 2: Bài 4 (Thực hành: Hành vi nhấp chuột nâng cao)
 * 
 * Khái niệm chính:
 * 1. State Hoisting (Nâng trạng thái): Chuyển State từ composable con lên composable cha.
 * 2. Biến composable thành Stateless (Không giữ trạng thái) để dễ tái sử dụng và kiểm thử.
 * 3. Xử lý các sự kiện click phức tạp (Thêm/bớt với giới hạn min/max, chuyển đổi trạng thái On/Off).
 */

// 1. STATEFUL COMPONENT vs STATELESS COMPONENT DEMO

// Lớp chứa dữ liệu State của Counter
data class CounterState(
    val count: Int = 0,
    val minLimit: Int = 0,
    val maxLimit: Int = 10,
    val step: Int = 1
)

// Stateless Component: Chỉ nhận dữ liệu và callback, KHÔNG tự thay đổi state
class StatelessCounterComponent {
    fun render(state: CounterState): String {
        return "Counter Status: [ ${state.count} ] (Limits: ${state.minLimit}..${state.maxLimit}, Step: ${state.step})"
    }
}

// Stateful Component (State Hoister): Quản lý state và cung cấp logic xử lý sự kiện
class CounterApp {
    private var state = CounterState()
    private val view = StatelessCounterComponent()

    fun increment(): Boolean {
        if (state.count + state.step <= state.maxLimit) {
            state = state.copy(count = state.count + state.step)
            return true
        }
        return false // Đạt giới hạn Max
    }

    fun decrement(): Boolean {
        if (state.count - state.step >= state.minLimit) {
            state = state.copy(count = state.count - state.step)
            return true
        }
        return false // Đạt giới hạn Min
    }

    fun reset() {
        state = state.copy(count = 0)
    }

    fun render(): String = view.render(state)
    fun getState(): CounterState = state
}

// 2. TOGGLE BUTTON STATE PRACTICE (Trạng thái On/Off)
class ToggleButtonApp {
    var isOn: Boolean = false
        private set

    fun toggle(): Boolean {
        isOn = !isOn
        return isOn
    }

    fun render(): String {
        val statusText = if (isOn) "[ BẬT (ON) 🟢 ]" else "[ TẮT (OFF) 🔴 ]"
        return "Trạng thái công tắc: $statusText"
    }
}

fun runBai4() {
    println(">>> CHẠY BÀI 4: HÀNH VI NHẤP CHUỘT NÂNG CAO (CLICK BEHAVIOR PRACTICE) <<<\n")

    println("=== 1. KIỂM THỬ COUNTER APP WITH BOUNDS ===")
    val counter = CounterApp()
    println(counter.render())

    println("\n-> Tăng counter 3 lần:")
    repeat(3) {
        counter.increment()
        println(counter.render())
    }

    println("\n-> Thử tăng vượt quá maxLimit (10):")
    repeat(9) {
        val success = counter.increment()
        if (!success) {
            println(" cảnh báo: Đã đạt giới hạn tối đa (${counter.getState().maxLimit})! Không thể tăng thêm.")
        }
    }
    println(counter.render())

    println("\n-> Reset Counter:")
    counter.reset()
    println(counter.render())

    println("\n=== 2. KIỂM THỬ TOGGLE BUTTON APP ===")
    val toggleApp = ToggleButtonApp()
    println(toggleApp.render())

    println("-> Nhấp công tắc lần 1:")
    toggleApp.toggle()
    println(toggleApp.render())

    println("-> Nhấp công tắc lần 2:")
    toggleApp.toggle()
    println(toggleApp.render())
}

// Hàm main dùng để chạy thử độc lập Bài 4
fun main() {
    runBai4()
}

