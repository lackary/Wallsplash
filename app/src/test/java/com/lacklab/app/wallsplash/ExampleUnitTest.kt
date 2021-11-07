package com.lacklab.app.wallsplash

import com.google.common.truth.Truth
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun addTest() {
        val actual = 3
        val expected = 3
        Truth.assertThat(actual).isEqualTo(expected)
    }
}