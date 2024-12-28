package eric.app.pabaproject

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import eric.app.pabaproject.Robert.MainActivity
import eric.app.pabaproject.William.MetodePembayaran

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class JadwalUntukPesanLapangan : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    lateinit var _jamYgDipilih: TextView
    lateinit var _tanggalYgDipilih: TextView
    lateinit var _totalDurasi: TextView
    lateinit var _tarifTotal: TextView
    lateinit var _biayaPerJam: TextView
    lateinit var _namaLapangan: TextView
    lateinit var _inputNama: EditText
    lateinit var _pesanBtn: Button
    private lateinit var viewModel: jadwalUntukPesanLapanganViewModel
    val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        viewModel = ViewModelProvider(requireActivity())[jadwalUntukPesanLapanganViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_jadwal_untuk_pesan_lapangan, container, false)

        // inisialisasi layout
        _namaLapangan = view.findViewById(R.id.namaLapangan)
        _jamYgDipilih = view.findViewById(R.id.jamYgDipilih)
        _tanggalYgDipilih = view.findViewById(R.id.tanggalYgDipilih)
        _totalDurasi = view.findViewById(R.id.totalDurasi)
        _tarifTotal = view.findViewById(R.id.tarifTotal)
        _biayaPerJam = view.findViewById(R.id.biayaPerJam)
        _pesanBtn = view.findViewById(R.id.pesanBtn)
        _inputNama = view.findViewById(R.id.inputNama)
        val constraintLayoutTanggal: ConstraintLayout = view.findViewById(R.id.constraintLayoutTanggal)
        val constraintLayoutWaktu: ConstraintLayout = view.findViewById(R.id.constraintLayoutWaktu)

        if (arguments?.getString("namaLapangan") != null){
            val namaLapangan = arguments?.getString("namaLapangan")
            viewModel.namaLapangan = namaLapangan
        }
        _namaLapangan.text = viewModel.namaLapangan.toString()


        _tanggalYgDipilih.text = viewModel.selectedDate ?: "-"
        _totalDurasi.text = viewModel.duration ?: "-"
        if (viewModel.duration != null) {
            val tarifTotal = (viewModel.duration)?.toInt()?.times(50)
            _tarifTotal.text = "Rp ${tarifTotal}.000,-"
        }
        else{
            _tarifTotal.text = "-"
        }

        val maxLength = 25  // Atur panjang maksimum
        if (viewModel.selectedTime != null) {
            val selectedTime = viewModel.selectedTime
            val truncatedText = if (selectedTime?.length!! > maxLength) {
                selectedTime.substring(0, maxLength) + "..."
            } else {
                selectedTime
            }
            _jamYgDipilih.text = truncatedText
        } else {
            _jamYgDipilih.text = "-"
        }


        // constraintLayout setOnClickListener
        constraintLayoutTanggal.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, dateSelection())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        if ( viewModel.selectedDate!=null ){
            constraintLayoutWaktu.setOnClickListener {
                val transaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, timeSelection())
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
        else{
            Toast.makeText(requireContext(), "Pilih tanggal terlebih dahulu", Toast.LENGTH_SHORT).show()
        }

        _pesanBtn.setOnClickListener {
            viewModel.namaLapangan = _namaLapangan.text.toString()
            viewModel.namaPemesan = _inputNama.text.toString()
            val namaPemesan = viewModel.namaPemesan
            val namaLapangan = viewModel.namaLapangan
            val tanggalPesan = viewModel.selectedDate
            val waktuPesan = viewModel.selectedTime
            val durasi = viewModel.duration

            if (tanggalPesan.isNullOrEmpty() || waktuPesan.isNullOrEmpty() || durasi.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Pastikan semua data terisi dengan benar!", Toast.LENGTH_SHORT).show()
            } else {
                val tarifTotal = durasi.toInt() * 50_000
                val pesananData = mapOf(
                    "nama_pemesan" to namaPemesan,
                    "nama_lapangan" to namaLapangan,
                    "tanggal_pesan" to tanggalPesan,
                    "waktu_pesan" to waktuPesan,
                    "durasi" to durasi
                )

                db.collection("pesanan")
                    .add(pesananData)
                    .addOnSuccessListener {
                        val intent = Intent(requireContext(), MetodePembayaran::class.java)
                        intent.putExtra("nama_pemesan", namaPemesan)
                        intent.putExtra("nama_lapangan", namaLapangan)
                        intent.putExtra("tanggal_pesan", tanggalPesan)
                        intent.putExtra("waktu_pesan", waktuPesan)
                        intent.putExtra("durasi", durasi)
                        intent.putExtra("tarif_total", tarifTotal)
//                        Toast.makeText(requireContext(), "Pesanan berhasil disimpan!", Toast.LENGTH_SHORT).show()
//                        val intent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(requireContext(), "Gagal menyimpan pesanan: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }
        return view
    }



    companion object {
        fun newInstance(param1: String, param2: String) =
            JadwalUntukPesanLapangan().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
