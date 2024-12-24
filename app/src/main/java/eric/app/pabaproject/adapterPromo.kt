package eric.app.pabaproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class adapterPromo(private val listPromo: ArrayList<Promo>) : RecyclerView
.Adapter<adapterPromo.ListViewHolder> () {

    //inisialisasi
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var _namaPromo = itemView.findViewById<TextView>(R.id.namaPromo)
        var _gambarPromo = itemView.findViewById<ImageView>(R.id.gambarPromo)

    }

    //bawaan
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_promo,parent , false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listPromo.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var Promo = listPromo[position]

        //penampilan nama Promo
        holder._namaPromo.setText(Promo.nama)
    }
}