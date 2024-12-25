package eric.app.pabaproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [timeSelection.newInstance] factory method to
 * create an instance of this fragment.
 */
class timeSelection : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var _lvTime: ListView
    lateinit var _saveTimeBtn: Button
    var data: MutableList<String> = mutableListOf()
    private lateinit var viewModel: jadwalUntukPesanLapanganViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString("selectedTime")
            param2 = it.getString(ARG_PARAM2)
        }
        viewModel = ViewModelProvider(requireActivity())[jadwalUntukPesanLapanganViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_time_selection, container, false)
        val insertData = listOf("06:00-07:59" , "08:00-09:59", "10:00-11:59", "12:00-13:59", "14:00-15:59", "16:00-17:59", "18:00-19:59", "20:00-21:59")
        data.addAll(insertData)

        val lvAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_multiple_choice, data)
        _lvTime = view.findViewById(R.id.lvTime)
        _lvTime.adapter = lvAdapter
        _lvTime.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        viewModel.selectedTime?.let { selectedTimes ->
            val previouslySelected = selectedTimes.split(", ").map { it.trim() }
            for (i in data.indices) {
                if (previouslySelected.contains(data[i])) {
                    _lvTime.setItemChecked(i, true)
                }
            }
        }

        _saveTimeBtn = view.findViewById(R.id.saveTimeBtn)
        _saveTimeBtn.setOnClickListener {
            val selectedItems = mutableListOf<String>()
            for (i in 0 until _lvTime.count) {
                if (_lvTime.isItemChecked(i)) {
                    selectedItems.add(data[i])
                }
            }

            val selectedTime = selectedItems.joinToString(", ")
            val duration = selectedItems.size

            viewModel.selectedTime = selectedTime
            viewModel.duration = duration.toString()

            Toast.makeText(requireContext(), "Waktu berhasil dipilih", Toast.LENGTH_SHORT).show()
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
         * @return A new instance of fragment timeSelection.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            timeSelection().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}