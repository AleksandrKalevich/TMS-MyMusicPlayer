package com.github.krottv.tmstemp

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun `addition_isCorrect`() = runTest {
        var a = 1
        var b : Int = a
        println(a)
        println(b)
        a = 2
        println(a)
        println(b)
        assertEquals(a,b)
    }
}