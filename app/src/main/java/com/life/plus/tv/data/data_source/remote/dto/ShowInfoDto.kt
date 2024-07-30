package com.life.plus.tv.data.data_source.remote.dto


import com.google.gson.annotations.SerializedName
import com.life.plus.tv.domain.model.ShowInfo

data class ShowInfoDto(
    @SerializedName("score")
    val score: Double? = null, // 1.2034719
    @SerializedName("show")
    val show: Show? = null) {
    data class Show(
        @SerializedName("id")
        val id: Int? = null, // 6
        @SerializedName("url")
        val url: String? = null, // https://www.tvmaze.com/shows/6/the-100
        @SerializedName("name")
        val name: String? = null, // The 100
        @SerializedName("type")
        val type: String? = null, // Scripted
        @SerializedName("language")
        val language: String? = null, // English
        @SerializedName("genres")
        val genres: List<String?>? = null,
        @SerializedName("status")
        val status: String? = null, // Ended
        @SerializedName("runtime")
        val runtime: Int? = null, // 60
        @SerializedName("averageRuntime")
        val averageRuntime: Int? = null, // 60
        @SerializedName("premiered")
        val premiered: String? = null, // 2014-03-19
        @SerializedName("ended")
        val ended: String? = null, // 2020-09-30
        @SerializedName("officialSite")
        val officialSite: String? = null, // http://www.cwtv.com/shows/the-100/
        @SerializedName("schedule")
        val schedule: Schedule? = null,
        @SerializedName("rating")
        val rating: Rating? = null,
        @SerializedName("weight")
        val weight: Int? = null, // 99
        @SerializedName("network")
        val network: Network? = null,
        @SerializedName("webChannel")
        val webChannel: WebChannel? = null,
        @SerializedName("externals")
        val externals: Externals? = null,
        @SerializedName("image")
        val image: Image? = null,
        @SerializedName("summary")
        val summary: String? = null, // <p>Ninety-seven years ago, nuclear Armageddon decimated planet Earth, destroying civilization. The only survivors were the 400 inhabitants of 12 international space stations that were in orbit at the time. Three generations have been born in space, the survivors now number 4,000, and resources are running out on their dying "Ark" - the 12 stations now linked together and repurposed to keep the survivors alive. Draconian measures including capital punishment and population control are the order of the day, as the leaders of the Ark take ruthless steps to ensure their future, including secretly exiling a group of 100 juvenile prisoners to the Earth's surface to test whether it's habitable.</p>
        @SerializedName("updated")
        val updated: Int? = null, // 1704793476
        @SerializedName("_links")
        val links: Links? = null
    ) {
        data class Schedule(
            @SerializedName("time")
            val time: String? = null, // 20:00
            @SerializedName("days")
            val days: List<String?>? = null
        )

        data class Rating(
            @SerializedName("average")
            val average: Double? = null // 7.7
        )

        data class Network(
            @SerializedName("id")
            val id: Int? = null, // 5
            @SerializedName("name")
            val name: String? = null, // The CW
            @SerializedName("country")
            val country: Country? = null,
            @SerializedName("officialSite")
            val officialSite: String? = null // https://www.cwtv.com/
        ) {
            data class Country(
                @SerializedName("name")
                val name: String? = null, // United States
                @SerializedName("code")
                val code: String? = null, // US
                @SerializedName("timezone")
                val timezone: String? = null // America/New_York
            )
        }

        data class WebChannel(
            @SerializedName("id")
            val id: Int? = null, // 574
            @SerializedName("name")
            val name: String? = null, // NFL+
            @SerializedName("country")
            val country: Country? = null,
            @SerializedName("officialSite")
            val officialSite: String? = null // https://www.nfl.com/plus/games/jets-at-browns-2023-pre-0/1598747
        ) {
            data class Country(
                @SerializedName("name")
                val name: String? = null, // United States
                @SerializedName("code")
                val code: String? = null, // US
                @SerializedName("timezone")
                val timezone: String? = null // America/New_York
            )
        }

        data class Externals(
            @SerializedName("tvrage")
            val tvrage: Int? = null, // 34770
            @SerializedName("thetvdb")
            val thetvdb: Int? = null, // 268592
            @SerializedName("imdb")
            val imdb: String? = null // tt2661044
        )

        data class Image(
            @SerializedName("medium")
            val medium: String? = null, // https://static.tvmaze.com/uploads/images/medium_portrait/477/1194981.jpg
            @SerializedName("original")
            val original: String? = null // https://static.tvmaze.com/uploads/images/original_untouched/477/1194981.jpg
        )

        data class Links(
            @SerializedName("self")
            val self: Self? = null,
            @SerializedName("previousepisode")
            val previousEpisode: Episode? = null,
            @SerializedName("nextepisode")
            val nextEpisode: Episode? = null
        ) {
            data class Self(
                @SerializedName("href")
                val href: String? = null // https://api.tvmaze.com/shows/6
            )

            data class Episode(
                @SerializedName("href")
                val href: String? = null, // https://api.tvmaze.com/episodes/1913280
                @SerializedName("name")
                val name: String? = null // The Last War
            )
        }
    }

    fun toShowInfo()= ShowInfo(
        id = show?.id ?: 0,
        name = show?.name ?: "",
        score = score?.times(10) ?: 0.0,
        url = show?.url ?: "",
        language = show?.language ?: "N/A",
        country = show?.network?.country?.name ?: "N/A",
        status = show?.status ?: "",
        premiered = show?.premiered ?: "",
        mediumImage = show?.image?.medium ?: "",
        summary = show?.summary ?: "",
        rating = show?.rating?.average ?: 0.0,
        imdb = if(show?.externals?.imdb != null) "https://www.imdb.com/title/${show.externals.imdb}" else "",
        averageRuntime = show?.averageRuntime ?: 0,
        officialSite = show?.officialSite ?: "",
        genres= show?.genres?.filterNotNull() ?: listOf()
    )
}