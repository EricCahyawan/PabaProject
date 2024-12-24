package eric.app.pabaproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class adapterJenisOlahraga (private val listJenisOlahraga: ArrayList<JenisOlahraga>) : RecyclerView
    .Adapter<adapterJenisOlahraga.ListViewHolder> (){

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var _namaOlahraga = itemView.findViewById<TextView>(R.id.namaOlahraga)
        var _gambarOlahraga = itemView.findViewById<ImageView>(R.id.gambarOlahraga)
      }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_olahraga,parent , false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listJenisOlahraga.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var JenisOlahraga = listJenisOlahraga[position]

        holder._namaOlahraga.setText(JenisOlahraga.nama)
        Picasso.get()
            .load(JenisOlahraga.gambar)
            .into(holder._gambarOlahraga)
    }
}