package vku.ltm.lab2

/**
 * Android Basics Compose - Unit 2 Pathway 2: Bài 2 (Trình gỡ lỗi & Quản lý trạng thái)
 * 
 * Khái niệm chính:
 * 1. Hiểu cơ chế Recomposition (Tái kết hợp) trong Compose: Khi state thay đổi, các Composable đọc state sẽ được vẽ lại.
 * 2. `mutableStateOf(value)` và `remember { }`: Lưu trữ giá trị qua các lần Recomposition.
 * 3. Kỹ thuật Debugging: Đặt Breakpoints, theo dõi Variable values, Step Over/Into trong Android Studio.
 * 4. State Hoisting (Nâng trạng thái): Chuyển state lên hàm cha để biến hàm con thành Stateless Composable.
 */

// Lớp mô phỏng `MutableState<T>` của Jetpack Compose
class ComposeMutableState<T>(initialValue: T) {
    private var _value: T = initialValue
    private val recompositionListeners = mutableListOf<(T) -> Unit>()

    var value: T
        get() = _value
        set(newValue) {
            if (_value != newValue) {
                val oldValue = _value
                _value = newValue
                notifyRecomposition(oldValue, newValue)
            }
        }

    fun addRecompositionListener(listener: (T) -> Unit) {
        recompositionListeners.add(listener)
    }

    private fun notifyRecomposition(oldVal: T, newVal: T) {
        println("[RECOMPOSITION TRIGGERED] Trạng thái thay đổi: '$oldVal' -> '$newVal'")
        recompositionListeners.forEach { it(newVal) }
    }
}

// Hàm trợ giúp mô phỏng `mutableStateOf`
fun <T> mutableStateOfSim(value: T): ComposeMutableState<T> = ComposeMutableState(value)

/**
 * Lớp mô phỏng Debugger & Recomposition Tracker
 */
class DebuggerAndStateDemo {
    // Trạng thái bước thực thi hiện tại trong Debugger
    val currentStep = mutableStateOfSim("START")
    val counterState = mutableStateOfSim(0)

    // Đếm số lần Recomposition xảy ra
    var recompositionCount = 0
        private set

    init {
        counterState.addRecompositionListener {
            recompositionCount++
            println(" -> UI Component đã được vẽ lại (Recomposed) lần thứ: $recompositionCount")
        }
        currentStep.addRecompositionListener { step ->
            println(" -> Debugger Step chuyển sang: $step")
        }
    }

    fun incrementCounter() {
        println("\n[EVENT: User click Button '+1']")
        counterState.value += 1
    }

    fun resetCounter() {
        println("\n[EVENT: User click Button 'Reset']")
        counterState.value = 0
    }

    fun simulateDebuggerSession() {
        println("=== MÔ PHỎNG PHIÊN GỠ LỖI (DEBUGGER SESSION) ===")
        currentStep.value = "SET_BREAKPOINT"
        println("1. Đặt Breakpoint tại dòng cập nhật State.")
        
        currentStep.value = "STEP_OVER"
        println("2. Step Over: Kiểm tra biến `counterState.value` = ${counterState.value}")
        
        incrementCounter()
        
        currentStep.value = "INSPECT_VARIABLES"
        println("3. Kiểm tra cửa sổ Variables trong Android Studio Debugger:")
        println("   - counterState.value = ${counterState.value}")
        println("   - recompositionCount = $recompositionCount")

        incrementCounter()

        resetCounter()
        println("=================================================");
    }
}

fun runBai2() {
    println(">>> CHẠY BÀI 2: DEBUGGER & STATE RECOMPOSITION DEMO <<<")
    val demo = DebuggerAndStateDemo()
    demo.simulateDebuggerSession()
}

// Hàm main dùng để chạy thử độc lập Bài 2
fun main() {
    runBai2()
}

