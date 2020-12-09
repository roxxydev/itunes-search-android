package com.itunessearch.android.domain.model

enum class WrapperType(val value: String) {

    TRACK("track"),
    COLLECTION("collection"),
    ARTIST_FOR("artistFor"),
    UNKNOWN("");

    companion object {
        private val map = values().associateBy(WrapperType::value)
        fun fromString(type: String?) = type?.let { map[type] } ?: UNKNOWN
    }
}