package com.itunessearch.android.domain.model

import timber.log.Timber

enum class Media(val value: String) {

    MOVIE("movie"),
    PODCAST("podcast"),
    MUSIC("music"),
    MUSIC_VIDEO("musicVideo"),
    AUDIOBOOK("audiobook"),
    SHORT_FILM("shortFilm"),
    TV_SHOW("tvShow"),
    SOFTWARE("software"),
    EBOOK("ebook"),
    ALL("all");

    override fun toString(): String {
        return value
    }

    companion object {
        fun fromString(text: String): Media {
            val textToMedia = values().find {
                it.value.compareTo(text, true) == 0
            }
            return textToMedia ?: ALL
        }
    }
}