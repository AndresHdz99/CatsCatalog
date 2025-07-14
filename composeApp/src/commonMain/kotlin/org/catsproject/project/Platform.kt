package org.catsproject.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform