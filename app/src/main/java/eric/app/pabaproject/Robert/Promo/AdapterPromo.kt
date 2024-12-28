package eric.app.pabaproject.Robert.Promo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import eric.app.pabaproject.R

class adapterPromo(private val listPromo: ArrayList<Promo>,
                   private var onItemDelete: (Promo) -> Unit,
                   private val isAdmin: Boolean) : RecyclerView
.Adapter<adapterPromo.ListViewHolder> () {

    //inisialisasi
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var _namaPromo = itemView.findViewById<TextView>(R.id.namaPromo)
        var _gambarPromo = itemView.findViewById<ImageView>(R.id.gambarPromo)
        var _btnHapus = itemView.findViewById<ImageView>(R.id.btnHapus)
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
        //menampilkan gambar icon olahraga
        Picasso.get()
            .load(Promo.gambar)
            .into(holder._gambarPromo)

        // Atur visibilitas tombol hapus
        if (isAdmin) {
            holder._btnHapus.visibility = View.VISIBLE
            holder._btnHapus.setOnClickListener {
                // Callback untuk hapus
                onItemDelete(Promo)
            }
        } else {
            holder._btnHapus.visibility = View.GONE
        }
    }
}