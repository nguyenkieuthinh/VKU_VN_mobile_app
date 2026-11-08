package com.example.marsphotos.data

data class RemoteImage(val url: String) {
    val id: String get() = url
    val title: String
        get() = url.substringAfterLast('/').substringBefore('?')
            .substringBeforeLast('.')
            .replace('-', ' ')
            .replace('_', ' ')
}
