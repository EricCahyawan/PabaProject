package eric.app.pabaproject


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class JenisOlahraga(
    var gambar : String,
    var nama : String
) : Parcelable
