package eric.app.pabaproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class TambahPromo : AppCompatActivity() {

    // Untuk Firebase
    val db = Firebase.firestore
    lateinit var etNamaPromo: EditText
    lateinit var etLinkGambar: EditText
    lateinit var rvPromo: RecyclerView
    var promoList = ArrayList<Promo>()
    lateinit var adapter: adapterPromo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tambah_promo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Intent balik ke halaman utama
        val _btnKembali = findViewById<ImageView>(R.id.btnKembali)
        _btnKembali.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        // Inisialisasi View
        etNamaPromo = findViewById(R.id.etNamaPromo)
        etLinkGambar = findViewById(R.id.etLinkGambar)
        rvPromo = findViewById<RecyclerView>(R.id.rvPromo)

        // Atur RecyclerView
        rvPromo.layoutManager = LinearLayoutManager(this)
        adapter = adapterPromo(promoList)
        rvPromo.adapter = adapter

        // Tambah Data
        val btnTambah = findViewById<ImageView>(R.id.btnTambah)
        btnTambah.setOnClickListener {
            TambahData(db, etNamaPromo.text.toString(), etLinkGambar.text.toString())
        }
    }

    // Fungsi Firebase
    fun TambahData(db: FirebaseFirestore, nama: String, gambar: String) {
        if (nama.isEmpty() || gambar.isEmpty()) {
            Toast.makeText(this, "Nama dan link gambar tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        }
        else {

            val dataBaru = Promo(nama, gambar)
            db.collection("tbPromo")
                .add(dataBaru)
                .addOnSuccessListener {
                    etNamaPromo.setText("")
                    etLinkGambar.setText("")
                    Log.d("Firebase", "Data Berhasil Disimpan")
                    promoList.add(dataBaru)
                    adapter.notifyDataSetChanged()
                }
                .addOnFailureListener {
                    Log.d("Firebase", it.message.toString())
                }
        }
    }
}
