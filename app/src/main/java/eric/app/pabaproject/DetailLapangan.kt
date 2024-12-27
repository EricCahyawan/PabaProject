package eric.app.pabaproject

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso

class DetailLapangan : AppCompatActivity() {

//    lateinit var _namaLapangan: TextView
//    lateinit var _hargaLapangan: TextView
//    lateinit var _gambarLapangan: TextView
//    lateinit var _deskripsiLapangan: TextView
//    lateinit var _lokasiLapangan: TextView
//    lateinit var _jamOperasionalLapangan    : TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_lapangan)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //ambil data
        val _namaLapangan = intent.getStringExtra("nama_lapangan")
        val _hargaLapangan = intent.getStringExtra("harga_lapangan")
        val _gambarLapangan = intent.getStringExtra("gambar_lapangan")
        val _deskripsiLapangan = intent.getStringExtra("deskripsi_lapangan")
        val _lokasiLapangan = intent.getStringExtra("lokasi_lapangan")
        val _jamOperasionalLapangan = intent.getStringExtra("jam_tersedia")


        val imageLapangann = findViewById<ImageView>(R.id.gambarLapangan)



        findViewById<TextView>(R.id.namaLapangan).text = _namaLapangan
        findViewById<TextView>(R.id.hargaLapangan).text = _hargaLapangan
        findViewById<TextView>(R.id.jamOperasional).text = _jamOperasionalLapangan
        findViewById<TextView>(R.id.lokasiLapangan).text = _lokasiLapangan
        findViewById<TextView>(R.id.deskripsiLapangan).text = _deskripsiLapangan
        Picasso.get()
            .load(_gambarLapangan)
            .into(imageLapangann)

        //hansel -> _namaLapangan.text = data yang dikirimkan dari hansel
        val _pindah = findViewById<Button>(R.id.pindah)
        _pindah.setOnClickListener {
            val intent = Intent(this, JadwalUntukPesanLapanganContainer::class.java)
            intent.putExtra("namaLapangan", _namaLapangan.toString())
            startActivity(intent)
        }

        val _balik = findViewById<ImageView>(R.id.backButton)

        _balik.setOnClickListener {
            val intent = Intent(this, adapterLapangan::class.java)
            startActivity(intent)

        }
    }
}