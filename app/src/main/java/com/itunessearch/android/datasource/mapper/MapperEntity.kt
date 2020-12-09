package com.itunessearch.android.datasource.mapper

interface MapperEntity<Entity, DomainModel> {

    fun mapFromCacheEntity(entity: Entity): DomainModel

    fun mapToCacheEntity(domainModel: DomainModel): Entity

    fun mapFromNetworkEntity(entity: Entity): DomainModel

    fun mapToNetworkEntity(domainModel: DomainModel): Entity
}