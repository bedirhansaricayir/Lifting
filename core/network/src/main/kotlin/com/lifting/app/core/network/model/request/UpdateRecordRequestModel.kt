package com.lifting.app.core.network.model.request

data class UpdateRecordRequestModel(
    val relationshipId: String,
    val records: List<UpdateRecord>,
)

data class UpdateRecord(
    val name: String,
    val data: List<UpdateRecordData>
)

data class UpdateRecordData(
    val set: Int,
    val weight: Int
)