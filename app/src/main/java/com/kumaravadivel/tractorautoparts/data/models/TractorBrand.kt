package com.kumaravadivel.tractorautoparts.data.models

data class TractorBrand(
    val id: String = "",
    val name: String = "",
    val models: List<TractorModel> = emptyList()
)

data class TractorModel(
    val id: String = "",
    val name: String = "",
    val brandId: String = ""
)
