package eric.app.pabaproject

import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

class TimeSelectionAdapter(
    context: android.content.Context,
    private val times: List<String>,
    private val unavailableTimes: List<String>
) : ArrayAdapter<String>(context, android.R.layout.simple_list_item_multiple_choice, times) {

    override fun isEnabled(position: Int): Boolean {
        // Nonaktifkan item jika termasuk dalam unavailableTimes
        return !unavailableTimes.contains(times[position])
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        if (!isEnabled(position)) {
            view.alpha = 0.5f // Efek visual untuk item yang tidak aktif
        }
        return view
    }
}
