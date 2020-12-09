package com.itunessearch.android.datasource.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "content", indices = [Index(value = ["trackId"], unique = true)])
data class EntityCacheContent(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    var trackId: Int = 0,
    var wrapperType: String = "",
    var kind: String = "",
    var collectionId: Int = 0,
    var artistName: String? = "",
    var collectionName: String? = "",
    var trackName: String? = "",
    var collectionCensoredName: String? = "",
    var trackCensoredName: String? = "",
    var collectionArtistId: Int? = -1,
    var collectionArtistViewUrl: String? = "",
    var collectionViewUrl: String? = "",
    var trackViewUrl: String? = "",
    var previewUrl: String? = "",
    var artworkUrl30: String? = "",
    var artworkUrl60: String? = "",
    var artworkUrl100: String? = "",
    var collectionPrice: Float? = -1F,
    var trackPrice: Float? = -1F,
    var trackRentalPrice: Float? = -1F,
    var collectionHdPrice: Float? = -1F,
    var trackHdPrice: Float? = -1F,
    var trackHdRentalPrice: Float? = -1F,
    var releaseDate: String? = "",
    var collectionExplicitness: String? = "",
    var trackExplicitness: String? = "",
    var discCount: Int? = -1,
    var discNumber: Int? = -1,
    var trackCount: Int? = -1,
    var trackNumber: Int? = -1,
    var trackTimeMillis: Int? = -1,
    var country: String? = "",
    var currency: String? = "",
    var primaryGenreName: String? = "",
    var contentAdvisoryRating: String? = "",
    var shortDescription: String? = "",
    var longDescription: String? = "",
    var hasITunesExtras: Boolean? = false
) : EntityModel
