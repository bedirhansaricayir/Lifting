package com.lifting.app.core.network.model.response

data class NetworkRecordResponse(
    val relationshipId: String,
    val records: List<NetworkRecords>
)

data class NetworkRecords(
    val name: String,
    val data: List<NetworkRecordData>
)

data class NetworkRecordData(
    val set: Int,
    val weight: Int
)