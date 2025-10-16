package com.example.kmmecomproject

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform