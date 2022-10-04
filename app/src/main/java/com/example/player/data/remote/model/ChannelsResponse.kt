package com.example.player.data.remote.model


import com.example.player.domain.data.Channel
import com.google.gson.annotations.SerializedName

data class ChannelsResponse(
    @SerializedName("channels")
    val channels: List<ChannelResponse>,
    @SerializedName("ckey")
    val ckey: String,
    @SerializedName("valid")
    val valid: Int
) {
    data class ChannelResponse(
        @SerializedName("address")
        val address: String,
        @SerializedName("cdn")
        val cdn: String,
        @SerializedName("current")
        val current: Current,
        @SerializedName("drm_status")
        val drmStatus: Int,
        @SerializedName("epg_id")
        val epgId: Int,
        @SerializedName("foreign_player")
        val foreignPlayer: ForeignPlayer?,
        @SerializedName("foreign_player_key")
        val foreignPlayerKey: Boolean,
        @SerializedName("hasEpg")
        val hasEpg: Boolean,
        @SerializedName("id")
        val id: Int,
        @SerializedName("image")
        val image: String,
        @SerializedName("is_federal")
        val isFederal: Boolean,
        @SerializedName("is_foreign")
        val isForeign: Boolean,
        @SerializedName("name_en")
        val nameEn: String,
        @SerializedName("name_ru")
        val nameRu: String,
        @SerializedName("number")
        val number: Int,
        @SerializedName("owner")
        val owner: String,
        @SerializedName("region_code")
        val regionCode: Int,
        @SerializedName("tz")
        val tz: Int,
        @SerializedName("url")
        val url: String,
        @SerializedName("url_sound")
        val urlSound: String,
        @SerializedName("vitrina_events_url")
        val vitrinaEventsUrl: String
    ) {
        data class Current(
            @SerializedName("cdnvideo")
            val cdnvideo: Int,
            @SerializedName("desc")
            val desc: String,
            @SerializedName("rating")
            val rating: Int,
            @SerializedName("timestart")
            val timestart: Int,
            @SerializedName("timestop")
            val timestop: Int,
            @SerializedName("title")
            val title: String
        )

        data class ForeignPlayer(
            @SerializedName("sdk")
            val sdk: String,
            @SerializedName("url")
            val url: String?,
            @SerializedName("valid_from")
            val validFrom: Int
        )

        fun toDomain(): Channel =
            Channel(
                id = id,
                name = nameRu,
                descShort = current.title,
                descFull = current.desc,
                image = image,
                url = url
            )
    }

    fun toDomainModel(): List<Channel> = channels.map { it.toDomain() }
}