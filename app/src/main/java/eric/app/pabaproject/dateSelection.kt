package eric.app.pabaproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView

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

        _calendarView.setOnDateChangeListener {
            view, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth/${month + 1}/$year"

            _tanggalYgDipilih.setText(selectedDate)

        }

        _backBtn.setOnClickListener {
            val currentFragment = parentFragmentManager.findFragmentById(R.id.fragmentContainerJadwal) // yang dimaskud di fragment di android studio yaitu frame layout
            if (currentFragment != null) {
                val transaction = parentFragmentManager.beginTransaction()
                transaction.remove(currentFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }

        _saveBtn.setOnClickListener {
            val sendDate = selectedDate
            val targetFragment = JadwalUntukPesanLapangan().apply {
                arguments = Bundle().apply {
                    putString("selectedDate", sendDate)
                }
            }

            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, targetFragment)
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