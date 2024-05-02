package com.example.imagecollector.data


import com.google.gson.annotations.SerializedName
import java.util.Date


data class ImageSearch(val response: ImageResponse)
//이름을 다르게할경우 @Serializedname을 사용.
data class ImageResponse(
    @SerializedName("documents")
    val documents: List<ImageInfo>,
    @SerializedName("meta")
    val meta: Meta
)

data class ImageInfo(
    @SerializedName("collection")
    val collection: String,
    @SerializedName("thumbnail_url")
    val thumbnail_url: String,
    @SerializedName("image_url")
    val image_url: String,
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("display_sitename")
    val display_sitename: String,
    @SerializedName("doc_url")
    val doc_url: String,
    @SerializedName("datetime")
    val datetime: Date
)

data class Meta(
    @SerializedName("total_count")
    val total_count: Int,
    @SerializedName("pageable_count")
    val pageable_count: Int,
    @SerializedName("is_end")
    val is_end: Boolean
)