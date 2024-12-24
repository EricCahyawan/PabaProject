package eric.app.pabaproject

import android.annotation.SuppressLint
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
private const val ARG_PARAM3 = "param3"

class JadwalUntukPesanLapangan : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null
    lateinit var _jamYgDipilih: TextView
    lateinit var _tanggalYgDipilih: TextView
    lateinit var _totalDurasi: TextView
    val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString("selectedDate")
            param2 = it.getString("selectedTime")
            param3 = it.getString("duration")
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_jadwal_untuk_pesan_lapangan, container, false)

        // inisialisasi layout
        _jamYgDipilih = view.findViewById(R.id.jamYgDipilih)
        _tanggalYgDipilih = view.findViewById(R.id.tanggalYgDipilih)
        _totalDurasi = view.findViewById(R.id.totalDurasi)
        val constraintLayoutTanggal: ConstraintLayout = view.findViewById(R.id.constraintLayoutTanggal)
        val constraintLayoutWaktu: ConstraintLayout = view.findViewById(R.id.constraintLayoutWaktu)

        //pengambilan param
        if (param1 != null) {
            val receivedDate = param1.toString()
            _tanggalYgDipilih.text = receivedDate
        }

        val maxLength = 25
        if (param2 != null) {
            val receivedTime = param2.toString()
            val truncatedText = if (receivedTime.length > maxLength) {
                receivedTime.substring(0, maxLength) + "..."
            } else {
                receivedTime
            }

            _jamYgDipilih.text = truncatedText
        }

        if ( param3 != null ){
            val duration = param3.toString()
            _totalDurasi.text = "$duration jam"
        }

        // constraintLayout setOnClickListener
        constraintLayoutTanggal.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, dateSelection())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        constraintLayoutWaktu.setOnClickListener {
            if (param2 != null){
                val targetFragment = timeSelection().apply {
                    arguments = Bundle().apply {
                        putString("selectedTime", param2)
                    }
                }
                val transaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, targetFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
            else{
                val transaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, timeSelection())
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
        return view
    }



    companion object {
        fun newInstance(param1: String, param2: String, param3: String) =
            JadwalUntukPesanLapangan().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putString(ARG_PARAM3, param3)
                }
            }
    }
}
