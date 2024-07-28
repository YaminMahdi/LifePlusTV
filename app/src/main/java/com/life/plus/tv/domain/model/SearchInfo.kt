package com.life.plus.tv.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchInfo(
    val id: Int = 0, // 48189
    val name: String = "", // Romance
    val score: Double = 0.0, // 0.8557435
    val url: String = "", // https://www.tvmaze.com/shows/48189/romance
    val language: String = "", // French
    val country: String = "", // France
    val status: String = "", // Ended
    val premiered: String = "", // 2020-06-10
    val mediumImage: String = "", // https://static.tvmaze.com/uploads/images/medium_portrait/257/644048.jpg
    val summary: String = "", // <p>This is a story about a mysterious woman, pretty as a picture. A woman in danger. A woman who refuses to love and to be loved. Of a man who travels, from Paris to Biarritz, and finds himself lost during the summer of 1960.</p>
    val rating: Double = 0.0,
    val imdb: String = "", // https://www.imdb.com/title/tt11469120
    val averageRuntime: Int = 0, // 50
    val officialSite: String = "", // https://www.francetvpro.fr/france-2/dossiers-de-presse/37605842
): Parcelable