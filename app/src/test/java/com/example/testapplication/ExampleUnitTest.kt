package com.example.testapplication

import android.util.Log
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
    fun testString() {
//测试字符串
        val str =
            "%5B%7B%22lastUpdateTime%22%3A%222011-10-28+9%3A39%3A41%22%2C%22smsList%22%3A%5B%7B%22liveState%22%3A%221"
        println("原长度：" + str.length)
        println("压缩后：" + ZipUtil2.compress(str).length)
        println("解压缩：" + ZipUtil2.uncompress(ZipUtil2.compress(str)))
    }
}
