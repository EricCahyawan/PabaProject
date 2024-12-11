package eric.app.pabaproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
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
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_jadwal_untuk_pesan_lapangan, container, false)
        _jamYgDipilih = view.findViewById(R.id.jamYgDipilih)
        _tanggalYgDipilih = view.findViewById(R.id.tanggalYgDipilih)
        val constraintLayoutTanggal: ConstraintLayout = view.findViewById(R.id.constraintLayoutTanggal)
        val constraintLayoutWaktu: ConstraintLayout = view.findViewById(R.id.constraintLayoutWaktu)
        val calendar = Calendar.getInstance()
        val tanggal = calendar.get(Calendar.DAY_OF_MONTH)
        val bulan = calendar.get(Calendar.MONTH) + 1
        val tahun = calendar.get(Calendar.YEAR)
        val jam = calendar.get(Calendar.HOUR_OF_DAY)
        val menit = calendar.get(Calendar.MINUTE)
        _tanggalYgDipilih.setText("$tanggal/$bulan/$tahun")
        _jamYgDipilih.setText("$jam:$menit")

        // Listener untuk mengganti fragment
        constraintLayoutTanggal.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerJadwal, dateSelection())
            transaction.addToBackStack(null) // Menambahkan ke backstack
            transaction.commit() // Menyelesaikan transaksi
        }

        constraintLayoutWaktu.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerJadwal, timeSelection())
            transaction.addToBackStack(null) // agar saat ditekan back pada android akan kembali ke halaman sebelumnya
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
