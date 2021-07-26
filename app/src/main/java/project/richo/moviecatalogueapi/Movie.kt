package project.richo.moviecatalogueapi

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    var id: Int = 0,
    var name: String? = null,
    var description: String? = null,
    var release: String? = null,
    var rating: Int = 0,
    var photo: String? = null
) : Parcelable