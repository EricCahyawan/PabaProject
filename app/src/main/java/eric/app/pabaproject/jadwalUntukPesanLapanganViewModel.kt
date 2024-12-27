package eric.app.pabaproject

import androidx.lifecycle.ViewModel

class jadwalUntukPesanLapanganViewModel: ViewModel() {
    var selectedDate: String? = null
    var selectedTime: String? = null
    var duration: String? = null
    var namaLapangan: String? = null
    var namaPemesan: String? = null
}