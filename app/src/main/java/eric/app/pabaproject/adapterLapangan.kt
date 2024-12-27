package eric.app.pabaproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class adapterLapangan (private val listLapangan: ArrayList<Lapangan>) : RecyclerView
.Adapter<adapterLapangan.ListViewHolder> () {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_lapangan,parent , false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ListViewHolder,
        position: Int
    ) {
        var Lapangan = listLapangan[position]

        //penampilan nama Lapangan dan harga Lapangan
        holder._namaLapangan.setText(Lapangan.nama)
        holder._hargaLapangan.setText(Lapangan.harga)
        //menampilkan gambar icon olahraga
        Picasso.get()
            .load(Lapangan.gambar)
            .into(holder._gambarLapangan)
    }

    override fun getItemCount(): Int {
        return listLapangan.size
    }

    //inisialisasi
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var _namaLapangan = itemView.findViewById<TextView>(R.id.namaLapangan)
        var _gambarLapangan = itemView.findViewById<ImageView>(R.id.gambarLapangan)
        var _hargaLapangan = itemView.findViewById<TextView>(R.id.hargaLapangan)
    }


}