package eric.app.pabaproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class adapterJenisOlahraga (private val listJenisOlahraga: ArrayList<JenisOlahraga>) : RecyclerView
    .Adapter<adapterJenisOlahraga.ListViewHolder> (){

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var _namaOlahraga = itemView.findViewById<TextView>(R.id.namaOlahraga)
        var _gambarOlahraga = itemView.findViewById<ImageView>(R.id.gambarOlahraga)
      }

    //bawaan
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_olahraga,parent , false)
        return ListViewHolder(view)
    }

    //bawaan
    override fun getItemCount(): Int {
        return listJenisOlahraga.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var JenisOlahraga = listJenisOlahraga[position]

        //penampilan nama olahraga
        holder._namaOlahraga.setText(JenisOlahraga.nama)
        //menampilkan gambar icon olahraga
        Picasso.get()
            .load(JenisOlahraga.gambar)
            .into(holder._gambarOlahraga)

        //supaya gambar bisa di click
        holder._gambarOlahraga.setOnClickListener {
        Toast.makeText(holder.itemView.context,JenisOlahraga.nama,Toast.LENGTH_LONG).show()
        }
    }

    //penambahan intent ketika click sesuatu
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data:JenisOlahraga)
    }
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }


}