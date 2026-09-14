package vku.ltm.lab2

import java.util.Scanner

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    println("==========================================================")
    println("   VKU - BÀI THỰC HÀNH LAB 2 (ANDROID BASICS WITH COMPOSE) ")
    println("   Lộ trình: Unit 2 Pathway 2 - Thêm một nút vào ứng dụng ")
    println("==========================================================")

    while (true) {
        println("\n----- DANH SÁCH BÀI HỌC LAB 2 -----")
        println("1. Bài 1: Dice Roller App (Tung xúc xắc & State management)")
        println("2. Bài 2: Debugger & State Recomposition (Trình gỡ lỗi)")
        println("3. Bài 3: Lemonade App Practice (Thực hành bài toán vắt chanh)")
        println("4. Bài 4: Advanced Click Behavior Practice (Thực hành nút bấm)")
        println("5. Chạy Chế độ Tương tác (Interactive Simulation for Dice & Lemonade)")
        println("0. Thất thoát / Thoát (Exit)")
        print("Mời bạn chọn bài học (0-5): ")

        if (!scanner.hasNext()) break

        if (!scanner.hasNextInt()) {
            println(" Lựa chọn không hợp lệ! Vui lòng nhập số từ 0 đến 5.")
            scanner.next() // Clear invalid token
            continue
        }

        when (scanner.nextInt()) {
            1 -> {
                println("\n>>> DANG CHẠY BÀI 1 <<<")
                runBai1()
            }
            2 -> {
                println("\n>>> DANG CHẠY BÀI 2 <<<")
                runBai2()
            }
            3 -> {
                println("\n>>> DANG CHẠY BÀI 3 <<<")
                runBai3()
            }
            4 -> {
                println("\n>>> DANG CHẠY BÀI 4 <<<")
                runBai4()
            }
            5 -> {
                runInteractiveSimulation(scanner)
            }
            0 -> {
                println("\n Cảm ơn bạn đã tham gia bài học Lab 2! Tạm biệt.")
                break
            }
            else -> println(" Lựa chọn không hợp lệ. Vui lòng nhập từ 0 đến 5.")
        }
    }

}

fun runInteractiveSimulation(scanner: Scanner) {
    println("\n=== CHẾ ĐỘ THỰC HÀNH TƯƠNG TÁC LẮC XÚC XẮC & PHA LEMONADE ===")
    println("1. Lắc xúc xắc tương tác")
    println("2. Pha nước chanh Lemonade tương tác")
    print("Chọn tính năng (1 hoặc 2): ")

    if (!scanner.hasNextInt()) {
        scanner.next()
        return
    }

    when (scanner.nextInt()) {
        1 -> {
            val diceApp = DiceRollerApp()
            var action = ""
            println("\nNhấn Enter hoặc gõ 'r' để lắc xúc xắc (Gõ 'q' để thoát):")
            scanner.nextLine() // Clear newline
            while (true) {
                print(diceApp.renderUI())
                print("Nhấn [Enter] để LẮC XÚC XẮC (hoặc 'q' để dừng): ")
                action = scanner.nextLine().trim()
                if (action.equals("q", ignoreCase = true)) break
                val res = diceApp.rollDice()
                println("\n Kết quả vừa lắc được: [ $res ]")
            }
        }
        2 -> {
            val lemonApp = LemonadeApp()
            var action = ""
            println("\nNhấn Enter để thực hiện hành động trên Lemonade App (Gõ 'q' để thoát):")
            scanner.nextLine() // Clear newline
            while (true) {
                print(lemonApp.renderUI())
                print("Nhấn [Enter] để CLICK VÀO HÌNH (hoặc 'q' để dừng): ")
                action = scanner.nextLine().trim()
                if (action.equals("q", ignoreCase = true)) break
                val msg = lemonApp.onImageClick()
                println("\n=> Thông báo: $msg")
            }
        }
        else -> println("Lựa chọn không hợp lệ.")
    }
}
