package eric.app.pabaproject.Hansel

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import eric.app.pabaproject.R
import eric.app.pabaproject.Robert.HalamanAdmin
import eric.app.pabaproject.Robert.MainActivity

class PilihanLapangan : AppCompatActivity() {
    private lateinit var _tvNamaLapangan1: TextView
    private lateinit var _tvNamaLapangan2: TextView

    //untuk Lapangan dari firebase
    val db = Firebase.firestore
    var lapanganList = ArrayList<Lapangan>()
    lateinit var adapter: adapterLapangan
    lateinit var rvPilihanLapangan: RecyclerView

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pilihan_lapangan)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //utk NAVBAR
        //intent ke admin
        val _btnAdmin = findViewById<ImageView>(R.id.btnAdmin)
        _btnAdmin.setOnClickListener{
            startActivity(Intent(this, HalamanAdmin::class.java))
        }
        //intent ke home
        val _btnHome = findViewById<ImageView>(R.id.btnHome)
        _btnHome.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

        _tvNamaLapangan1 = findViewById(R.id.tvNamaLapangan1)
        _tvNamaLapangan2 = findViewById(R.id.tvNamaLapangan2)

        //intent balik ke halaman utama
        val _btnKembali = findViewById<ImageView>(R.id.btnKembali)
        _btnKembali.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

        // Ambil data dari Intent
        val kategoriOlahraga = intent.getStringExtra("namaOlahraga")
        if (kategoriOlahraga != null) {
            _tvNamaLapangan1.text = kategoriOlahraga
            _tvNamaLapangan2.text = kategoriOlahraga
            readData(db, kategoriOlahraga)
        }

        //tampilkan recyclerview nya promo dari firebase
        rvPilihanLapangan = findViewById(R.id.rvPilihanLapangan)
        rvPilihanLapangan.layoutManager = LinearLayoutManager(this)
        adapter = adapterLapangan(
            listLapangan = lapanganList,
        )
        rvPilihanLapangan.adapter = adapter


    }
    //fungsi untuk baca firebase Lapangan
    fun readData(db: FirebaseFirestore,filterLapangan: String) {
        db.collection("tbLapangan").whereEqualTo("kategori", filterLapangan).get()
            .addOnSuccessListener { result ->
                lapanganList.clear()
                for (document in result) {
                    val readData = Lapangan(
                        document.data.get("kategori").toString(),
                        document.data.get("nama").toString(),
                        document.data.get("gambar").toString(),
                        document.data.get("harga").toString(),
                        document.data.get("lokasi").toString(),
                        document.data.get("deskripsi").toString(),
                        document.data.get("jamTersedia").toString(),
                    )
                    lapanganList.add(readData)
                    adapter.notifyDataSetChanged()
                }

            }
            .addOnFailureListener {
                Log.d("Firebase", it.message.toString())
            }

    }
}