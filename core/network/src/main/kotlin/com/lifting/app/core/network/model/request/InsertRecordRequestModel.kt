package com.lifting.app.core.network.model.request


data class InsertRecordRequestModel(
    val relationshipId: String,
    val records: List<InsertRecord>,
)

data class InsertRecord(
    val name: String,
    val data: List<InsertRecordData>
)

data class InsertRecordData(
    val set: Int,
    val weight: Int
)