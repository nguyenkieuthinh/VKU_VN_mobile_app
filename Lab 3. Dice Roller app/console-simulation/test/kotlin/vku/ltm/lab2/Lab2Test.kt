package vku.ltm.lab2

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class Lab2Test {

    @Test
    fun testDiceRollerRange() {
        val diceApp = DiceRollerApp()
        repeat(100) {
            val roll = diceApp.rollDice()
            assertTrue(roll in 1..6, "Kết quả xúc xắc phải nằm trong khoảng 1 đến 6")
        }
        assertEquals(100, diceApp.getState().totalRolls)
    }

    @Test
    fun testLemonadeStateTransitions() {
        val lemonApp = LemonadeApp()
        assertEquals(LemonadeState.SELECT, lemonApp.currentState)

        // Step 1 -> Step 2
        lemonApp.onImageClick()
        assertEquals(LemonadeState.SQUEEZE, lemonApp.currentState)
        assertTrue(lemonApp.squeezesNeeded in 2..4)

        // Step 2 -> Squeeze until drink
        while (lemonApp.currentState == LemonadeState.SQUEEZE) {
            lemonApp.onImageClick()
        }
        assertEquals(LemonadeState.DRINK, lemonApp.currentState)

        // Step 3 -> Step 4
        lemonApp.onImageClick()
        assertEquals(LemonadeState.RESTART, lemonApp.currentState)
        assertEquals(1, lemonApp.totalLemonsMade)

        // Step 4 -> Step 1
        lemonApp.onImageClick()
        assertEquals(LemonadeState.SELECT, lemonApp.currentState)
    }

    @Test
    fun testCounterBounds() {
        val counterApp = CounterApp()
        assertEquals(0, counterApp.getState().count)

        // Increment 10 times to max limit
        repeat(10) {
            counterApp.increment()
        }
        assertEquals(10, counterApp.getState().count)

        // Increment 11th time should fail
        val canIncrement = counterApp.increment()
        assertFalse(canIncrement)
        assertEquals(10, counterApp.getState().count)

        // Decrement down to 0
        repeat(10) {
            counterApp.decrement()
        }
        assertEquals(0, counterApp.getState().count)

        // Decrement 11th time should fail (min limit 0)
        val canDecrement = counterApp.decrement()
        assertFalse(canDecrement)
        assertEquals(0, counterApp.getState().count)
    }

    @Test
    fun testToggleButtonState() {
        val toggleApp = ToggleButtonApp()
        assertFalse(toggleApp.isOn)

        val newState = toggleApp.toggle()
        assertTrue(newState)
        assertTrue(toggleApp.isOn)

        val resetState = toggleApp.toggle()
        assertFalse(resetState)
        assertFalse(toggleApp.isOn)
    }
}
