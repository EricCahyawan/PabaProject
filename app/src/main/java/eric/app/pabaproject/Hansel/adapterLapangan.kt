package eric.app.pabaproject.Hansel

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.squareup.picasso.Picasso
import eric.app.pabaproject.William.DetailLapangan
import eric.app.pabaproject.R


class adapterLapangan (private val listLapangan: ArrayList<Lapangan>, private var onItemDelete: (Lapangan) -> Unit,
                       private val isAdmin: Boolean) : RecyclerView
.Adapter<adapterLapangan.ListViewHolder> () {

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: Lapangan)

    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback

    }


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

        // Atur visibilitas tombol hapus
        if (isAdmin) {
            holder._btnHapus.visibility = View.VISIBLE
            holder._btnHapus.setOnClickListener {
                onItemDelete(Lapangan)
            }
        } else {
            holder._btnHapus.visibility = View.GONE
        }

        val qrCodeBitmap = generateQRCode(Lapangan)
        holder.qrCode.setImageBitmap(qrCodeBitmap)

       holder.itemView.setOnClickListener {
           val intent = Intent(holder.itemView.context, DetailLapangan::class.java).apply {
               putExtra("nama_lapangan", Lapangan.nama)
               putExtra("harga_lapangan", Lapangan.harga)
               putExtra("gambar_lapangan", Lapangan.gambar)
               putExtra("deskripsi_lapangan",Lapangan.deskripsi)
               putExtra("lokasi_lapangan",Lapangan.lokasi)
               putExtra("jam_tersedia",Lapangan.jamTersedia)
           }
           holder.itemView.context.startActivity(intent)
       }
    }

    private fun generateQRCode(Lapangan: Lapangan): Bitmap {
        val size = 512
        val qrCodeWriter = QRCodeWriter()
        val text = formatTicketDetails(Lapangan)
        return try {
            val bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, size, size)
            val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565)
            for (x in 0 until size) {
                for (y in 0 until size) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
            bitmap
        } catch (e: WriterException) {
            Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565)
        }
    }
    private fun formatTicketDetails(Lapangan: Lapangan): String {
        return """
        Nama Lapangan: ${Lapangan.nama}
        Harga: ${Lapangan.harga}
        Jam Operasional: ${Lapangan.jamTersedia}
        Lokasi: ${Lapangan.lokasi}
        Deskripsi: ${Lapangan.deskripsi}
        Kategori: ${Lapangan.kategori}
    """.trimIndent()
    }



    override fun getItemCount(): Int {
        return listLapangan.size
    }

    //inisialisasi
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var _namaLapangan = itemView.findViewById<TextView>(R.id.namaLapangan)
        var _gambarLapangan = itemView.findViewById<ImageView>(R.id.gambarLapangan)
        var _hargaLapangan = itemView.findViewById<TextView>(R.id.hargaLapangan)
        val qrCode = itemView.findViewById<ImageView>(R.id.qrCode)
        var _btnHapus = itemView.findViewById<ImageView>(R.id.btnHapus)
    }


}