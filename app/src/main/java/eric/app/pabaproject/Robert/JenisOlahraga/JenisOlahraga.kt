package eric.app.pabaproject.Robert.JenisOlahraga


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class JenisOlahraga(
    var gambar : String,
    var nama : String
) : Parcelable
