package com.itunessearch.android.domain.model

enum class Kind(val value: String) {

    BOOK("book"),
    ALBUM("album"),
    COACHED_AUDIO("coached-audio"),
    FEATURE_MOVIE("feature-movie"),
    INTERACTIVE_BOOKLET("interactive-booklet"),
    MUSIC_VIDEO("music-video"),
    PDF("pdf"),
    PODCAST("podcast"),
    PODCAST_EPISODE("podcast-episode"),
    SOFTWARE_PACKAGE("software-package"),
    SONG("song"),
    TV_EPISODE("tv-episode"),
    ARTIST_FOR("artistFor"),
    UNKNOWN("");

    companion object {
        private val map = values().associateBy(Kind::value)
        fun fromString(type: String?) = type?.let { map[type] } ?: UNKNOWN
    }
}
