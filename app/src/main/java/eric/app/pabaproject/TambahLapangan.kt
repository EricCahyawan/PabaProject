package eric.app.pabaproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TambahLapangan : AppCompatActivity() {
    // Untuk Firebase
    val db = Firebase.firestore
    lateinit var etkategoriLapangan: EditText
    lateinit var etNamaLapangan: EditText
    lateinit var etLinkGambar: EditText
    lateinit var etHargaLapangan : EditText
    lateinit var etLokasi : EditText
    lateinit var etDeskripsi : EditText
    lateinit var etJamTersedia : EditText
    lateinit var rvLapangan: RecyclerView
    var lapanganList = ArrayList<Lapangan>()
    lateinit var adapter: adapterLapangan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tambah_lapangan)
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

        // Intent balik ke halaman sebelumnya
        val _btnKembali = findViewById<ImageView>(R.id.btnKembali)
        _btnKembali.setOnClickListener {
            startActivity(Intent(this, HalamanAdmin::class.java))
        }

        // Inisialisasi
        etkategoriLapangan = findViewById(R.id.kategoriLapangan)
        etNamaLapangan = findViewById(R.id.etNamaLapangan)
        etLinkGambar = findViewById(R.id.etLinkGambarLapangan)
        etHargaLapangan = findViewById(R.id.etHargaLapangan)
        etLokasi = findViewById(R.id.etLokasi)
        etDeskripsi = findViewById(R.id.etDeskripsi)
        etJamTersedia = findViewById(R.id.etJamTersedia)
        rvLapangan = findViewById<RecyclerView>(R.id.rvLapangan)

        // Atur RecyclerView
        rvLapangan.layoutManager = LinearLayoutManager(this)
        adapter = adapterLapangan(
            listLapangan = lapanganList,
        )
        rvLapangan.adapter = adapter

        //tampilkan data ketika di run awal
        readData(db)

        // Tambah Data
        val btnTambah = findViewById<CardView>(R.id.btnTambahLapangan)
        btnTambah.setOnClickListener {
            TambahData(db, etkategoriLapangan.text.toString(),etNamaLapangan.text.toString(), etLinkGambar.text.toString(), etHargaLapangan.text.toString(), etLokasi.text.toString(), etDeskripsi.text.toString(), etJamTersedia.text.toString())
        }


    }

    // Fungsi Firebase
    fun TambahData(db: FirebaseFirestore, kategori: String, nama: String, gambar: String, harga: String, lokasi: String, deskripsi: String, jamTersedia: String) {
        if (nama.isEmpty() || gambar.isEmpty() || harga.isEmpty()) {
            Toast.makeText(this, "Nama, harga, dan link gambar tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        }
        else {
            val dataBaru = Lapangan(kategori, nama, gambar, harga, lokasi, deskripsi, jamTersedia)
            db.collection("tbLapangan")
                .document(dataBaru.nama)
                .set(dataBaru)
                .addOnSuccessListener {
                    etkategoriLapangan.setText("")
                    etNamaLapangan.setText("")
                    etLinkGambar.setText("")
                    etHargaLapangan.setText("")
                    etLokasi.setText("")
                    etDeskripsi.setText("")
                    etJamTersedia.setText("")

                    Log.d("Firebase", "Data Berhasil Disimpan")
                    lapanganList.add(dataBaru)
                    adapter.notifyDataSetChanged()
                    readData(db)
                }
                .addOnFailureListener {
                    Log.d("Firebase", it.message.toString())
                }
        }
    }
    fun readData(db: FirebaseFirestore){
        db.collection("tbLapangan").get()
            .addOnSuccessListener {
                    result ->
                lapanganList.clear()
                for(document in result){
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
            .addOnFailureListener{
                Log.d("Firebase",it.message.toString())
            }
    }
}