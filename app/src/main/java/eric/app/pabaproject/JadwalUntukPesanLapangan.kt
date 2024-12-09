package eric.app.pabaproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class JadwalUntukPesanLapangan : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

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

        val constraintLayoutTanggal: ConstraintLayout = view.findViewById(R.id.constraintLayout)

        // Listener untuk mengganti fragment tanpa commit ekstensi
        constraintLayoutTanggal.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, dateSelection())
            transaction.addToBackStack(null) // Menambahkan ke backstack
            transaction.commit() // Menyelesaikan transaksi
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
