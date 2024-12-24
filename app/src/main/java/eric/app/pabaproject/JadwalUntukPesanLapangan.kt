package eric.app.pabaproject

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import java.util.Calendar
import java.util.Date

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
        _jamYgDipilih = view.findViewById(R.id.jamYgDipilih)
        _tanggalYgDipilih = view.findViewById(R.id.tanggalYgDipilih)
        _totalDurasi = view.findViewById(R.id.totalDurasi)
        _tarifTotal = view.findViewById(R.id.tarifTotal)
        _biayaPerJam = view.findViewById(R.id.biayaPerJam)
        val constraintLayoutTanggal: ConstraintLayout = view.findViewById(R.id.constraintLayoutTanggal)
        val constraintLayoutWaktu: ConstraintLayout = view.findViewById(R.id.constraintLayoutWaktu)

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

        constraintLayoutWaktu.setOnClickListener {
                val transaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, timeSelection())
                transaction.addToBackStack(null)
                transaction.commit()
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
