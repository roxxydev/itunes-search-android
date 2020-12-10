package com.itunessearch.android.datasource.mapper

import com.itunessearch.android.datasource.model.EntityCacheContent
import com.itunessearch.android.datasource.model.EntityModel
import com.itunessearch.android.datasource.model.EntityNetworkContent
import com.itunessearch.android.datasource.model.EntityNetworkSearchRes
import com.itunessearch.android.domain.model.Content
import com.itunessearch.android.domain.model.Kind
import com.itunessearch.android.domain.model.WrapperType
import javax.inject.Inject

class ContentMapper
@Inject
constructor(): MapperEntity<EntityModel, Content> {

    override fun mapFromCacheEntity(entityModel: EntityModel): Content {

        val entityCache = entityModel as EntityCacheContent

        return Content(
            wrapperType = WrapperType.fromString(entityCache.wrapperType),
            kind = Kind.fromString(entityCache.kind),
            collectionId = entityCache.collectionId,
            trackId = entityCache.trackId,
            artistName = entityCache.artistName,
            collectionName = entityCache.collectionName,
            trackName = entityCache.trackName,
            collectionCensoredName = entityCache.collectionCensoredName,
            trackCensoredName = entityCache.trackCensoredName,
            collectionArtistId = entityCache.collectionArtistId,
            collectionArtistViewUrl = entityCache.collectionArtistViewUrl,
            collectionViewUrl = entityCache.collectionViewUrl,
            trackViewUrl = entityCache.trackViewUrl,
            previewUrl = entityCache.previewUrl,
            artworkUrl30 = entityCache.artworkUrl30,
            artworkUrl60 = entityCache.artworkUrl60,
            artworkUrl100 = entityCache.artworkUrl100,
            collectionPrice = entityCache.collectionPrice,
            trackPrice = entityCache.trackPrice,
            trackRentalPrice = entityCache.trackRentalPrice,
            collectionHdPrice = entityCache.collectionHdPrice,
            trackHdPrice = entityCache.trackHdPrice,
            trackHdRentalPrice = entityCache.trackHdRentalPrice,
            releaseDate = entityCache.releaseDate,
            collectionExplicitness = entityCache.collectionExplicitness,
            trackExplicitness = entityCache.trackExplicitness,
            discCount = entityCache.discCount,
            discNumber = entityCache.discNumber,
            trackCount = entityCache.trackCount,
            trackNumber = entityCache.trackNumber,
            trackTimeMillis = entityCache.trackTimeMillis,
            country = entityCache.country,
            currency = entityCache.currency,
            primaryGenreName = entityCache.primaryGenreName,
            contentAdvisoryRating = entityCache.contentAdvisoryRating,
            shortDescription = entityCache.shortDescription,
            longDescription = entityCache.longDescription,
            hasITunesExtras = entityCache.hasITunesExtras
        )
    }

    override fun mapToCacheEntity(content: Content): EntityModel {

        val trackId = content.trackId ?: 0
        val collectionId = content.collectionId ?: 0
        val kind = content.kind?.value ?: ""
        val wrapperType = content.wrapperType?.value ?: ""

        return EntityCacheContent(
            id = null,
            wrapperType = wrapperType,
            kind = kind,
            collectionId = collectionId,
            trackId = trackId,
            artistName = content.artistName,
            collectionName = content.collectionName,
            trackName = content.trackName,
            collectionCensoredName = content.collectionCensoredName,
            trackCensoredName = content.trackCensoredName,
            collectionArtistId = content.collectionArtistId,
            collectionArtistViewUrl = content.collectionArtistViewUrl,
            collectionViewUrl = content.collectionViewUrl,
            trackViewUrl = content.trackViewUrl,
            previewUrl = content.previewUrl,
            artworkUrl30 = content.artworkUrl30,
            artworkUrl60 = content.artworkUrl60,
            artworkUrl100 = content.artworkUrl100,
            collectionPrice = content.collectionPrice,
            trackPrice = content.trackPrice,
            trackRentalPrice = content.trackRentalPrice,
            collectionHdPrice = content.collectionHdPrice,
            trackHdPrice = content.trackHdPrice,
            trackHdRentalPrice = content.trackHdRentalPrice,
            releaseDate = content.releaseDate,
            collectionExplicitness = content.collectionExplicitness,
            trackExplicitness = content.trackExplicitness,
            discCount = content.discCount,
            discNumber = content.discNumber,
            trackCount = content.trackCount,
            trackNumber = content.trackNumber,
            trackTimeMillis = content.trackTimeMillis,
            country = content.country,
            currency = content.currency,
            primaryGenreName = content.primaryGenreName,
            contentAdvisoryRating = content.contentAdvisoryRating,
            shortDescription = content.shortDescription,
            longDescription = content.longDescription,
            hasITunesExtras = content.hasITunesExtras
        )
    }

    override fun mapFromNetworkEntity(entityModel: EntityModel): Content {

        val entityNetworkContent = entityModel as EntityNetworkContent

        return Content(
            wrapperType = WrapperType.fromString(entityNetworkContent.wrapperType),
            kind = Kind.fromString(entityNetworkContent.kind),
            collectionId = entityNetworkContent.collectionId,
            trackId = entityNetworkContent.trackId,
            artistName = entityNetworkContent.artistName,
            collectionName = entityNetworkContent.collectionName,
            trackName = entityNetworkContent.trackName,
            collectionCensoredName = entityNetworkContent.collectionCensoredName,
            trackCensoredName = entityNetworkContent.trackCensoredName,
            collectionArtistId = entityNetworkContent.collectionArtistId,
            collectionArtistViewUrl = entityNetworkContent.collectionArtistViewUrl,
            collectionViewUrl = entityNetworkContent.collectionViewUrl,
            trackViewUrl = entityNetworkContent.trackViewUrl,
            previewUrl = entityNetworkContent.previewUrl,
            artworkUrl30 = entityNetworkContent.artworkUrl30,
            artworkUrl60 = entityNetworkContent.artworkUrl60,
            artworkUrl100 = entityNetworkContent.artworkUrl100,
            collectionPrice = entityNetworkContent.collectionPrice,
            trackPrice = entityNetworkContent.trackPrice,
            trackRentalPrice = entityNetworkContent.trackRentalPrice,
            collectionHdPrice = entityNetworkContent.collectionHdPrice,
            trackHdPrice = entityNetworkContent.trackHdPrice,
            trackHdRentalPrice = entityNetworkContent.trackHdRentalPrice,
            releaseDate = entityNetworkContent.releaseDate,
            collectionExplicitness = entityNetworkContent.collectionExplicitness,
            trackExplicitness = entityNetworkContent.trackExplicitness,
            discCount = entityNetworkContent.discCount,
            discNumber = entityNetworkContent.discNumber,
            trackCount = entityNetworkContent.trackCount,
            trackNumber = entityNetworkContent.trackNumber,
            trackTimeMillis = entityNetworkContent.trackTimeMillis,
            country = entityNetworkContent.country,
            currency = entityNetworkContent.currency,
            primaryGenreName = entityNetworkContent.primaryGenreName,
            contentAdvisoryRating = entityNetworkContent.contentAdvisoryRating,
            shortDescription = entityNetworkContent.shortDescription,
            longDescription = entityNetworkContent.longDescription,
            hasITunesExtras = entityNetworkContent.hasITunesExtras
        )
    }

    fun mapFromNetworkEntitySearchRes(entityNetworkSearchRes: EntityNetworkSearchRes): List<Content> {

        return entityNetworkSearchRes.results.map {
            val entityNetworkContent = it
            Content(
                wrapperType = WrapperType.fromString(entityNetworkContent.wrapperType),
                kind = Kind.fromString(entityNetworkContent.kind),
                collectionId = entityNetworkContent.collectionId,
                trackId = entityNetworkContent.trackId,
                artistName = entityNetworkContent.artistName,
                collectionName = entityNetworkContent.collectionName,
                trackName = entityNetworkContent.trackName,
                collectionCensoredName = entityNetworkContent.collectionCensoredName,
                trackCensoredName = entityNetworkContent.trackCensoredName,
                collectionArtistId = entityNetworkContent.collectionArtistId,
                collectionArtistViewUrl = entityNetworkContent.collectionArtistViewUrl,
                collectionViewUrl = entityNetworkContent.collectionViewUrl,
                trackViewUrl = entityNetworkContent.trackViewUrl,
                previewUrl = entityNetworkContent.previewUrl,
                artworkUrl30 = entityNetworkContent.artworkUrl30,
                artworkUrl60 = entityNetworkContent.artworkUrl60,
                artworkUrl100 = entityNetworkContent.artworkUrl100,
                collectionPrice = entityNetworkContent.collectionPrice,
                trackPrice = entityNetworkContent.trackPrice,
                trackRentalPrice = entityNetworkContent.trackRentalPrice,
                collectionHdPrice = entityNetworkContent.collectionHdPrice,
                trackHdPrice = entityNetworkContent.trackHdPrice,
                trackHdRentalPrice = entityNetworkContent.trackHdRentalPrice,
                releaseDate = entityNetworkContent.releaseDate,
                collectionExplicitness = entityNetworkContent.collectionExplicitness,
                trackExplicitness = entityNetworkContent.trackExplicitness,
                discCount = entityNetworkContent.discCount,
                discNumber = entityNetworkContent.discNumber,
                trackCount = entityNetworkContent.trackCount,
                trackNumber = entityNetworkContent.trackNumber,
                trackTimeMillis = entityNetworkContent.trackTimeMillis,
                country = entityNetworkContent.country,
                currency = entityNetworkContent.currency,
                primaryGenreName = entityNetworkContent.primaryGenreName,
                contentAdvisoryRating = entityNetworkContent.contentAdvisoryRating,
                shortDescription = entityNetworkContent.shortDescription,
                longDescription = entityNetworkContent.longDescription,
                hasITunesExtras = entityNetworkContent.hasITunesExtras
            )
        }
    }

    fun mapFromCacheEntityList(entities: List<EntityCacheContent>): List<Content> {

        return entities.map{ mapFromCacheEntity(it) }
    }

    fun mapToCacheEntityList(contents: List<Content>): List<EntityModel> {

        return contents.map{ mapToCacheEntity(it) }
    }

    override fun mapToNetworkEntity(domainModel: Content): EntityModel {

        return object: EntityModel {}
    }
}
