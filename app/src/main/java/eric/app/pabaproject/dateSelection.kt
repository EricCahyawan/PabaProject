package eric.app.pabaproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [dateSelection.newInstance] factory method to
 * create an instance of this fragment.
 */
class dateSelection : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var _calendarView: CalendarView
    lateinit var _backBtn: Button
    lateinit var _saveBtn: Button
    lateinit var _tanggalYgDipilih: TextView
    var selectedDate: String = ""
    private lateinit var viewModel: jadwalUntukPesanLapanganViewModel

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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_date_selection, container, false)

        _tanggalYgDipilih = view.findViewById(R.id.tanggalYgDipilih)
        _calendarView = view.findViewById(R.id.calendarView)
        _backBtn = view.findViewById(R.id.backBtn)
        _saveBtn = view.findViewById(R.id.saveBtn)
        viewModel = ViewModelProvider(requireActivity())[jadwalUntukPesanLapanganViewModel::class.java]

        if (viewModel.selectedDate != null) {
            selectedDate = viewModel.selectedDate!!
            _tanggalYgDipilih.text = selectedDate

            // Split the date into components
            val dateParts = selectedDate.split("/")
            if (dateParts.size == 3) {
                val day = dateParts[0].toInt()
                val month = dateParts[1].toInt() - 1 // Month is zero-based in Calendar
                val year = dateParts[2].toInt()
                val calendar = Calendar.getInstance()
                calendar.set(year, month, day)
                _calendarView.date = calendar.timeInMillis
            }
        }

        _calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth/${month + 1}/$year"
            _tanggalYgDipilih.text = selectedDate
        }

        _backBtn.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, JadwalUntukPesanLapangan())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        _saveBtn.setOnClickListener {
            viewModel.selectedTime = null
            viewModel.selectedDate = selectedDate
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, JadwalUntukPesanLapangan())
            transaction.addToBackStack(null)
            transaction.commit()
        }


        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment dateSelection.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            dateSelection().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}