package com.countries.weather

import java.io.InputStreamReader

class MockFileReader(path: String) {

    val body: String
    init {
        val reader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(path))
        body = reader.readText()
        reader.close()
    }
}