package com.itunessearch.android.datasource.model

data class EntityNetworkSearchRes(
    var resultCount: Int,
    var results: List<EntityNetworkContent>
): EntityModel