package eric.app.pabaproject

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
import com.squareup.picasso.Picasso

class roberttest : AppCompatActivity() {

    private lateinit var _ivGambar: ImageView
    private lateinit var _tvNama: TextView

    //untuk Promo dari firebase
    val db = Firebase.firestore
    var promoList = ArrayList<Promo>()
    lateinit var adapter: adapterPromo
    lateinit var rvPromo: RecyclerView

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_roberttest)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        _ivGambar = findViewById(R.id.ivGambarOlahraga)
        _tvNama = findViewById(R.id.tvNamaOlahraga)

        //intent balik ke halaman utama
        val _btnKembali = findViewById<ImageView>(R.id.btnKembali)
        _btnKembali.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

        //intent dari mainactivity
        val dataIntent = intent.getParcelableExtra<JenisOlahraga>("namaOlahraga", JenisOlahraga::class.java)
        if (dataIntent != null) {
            Picasso.get()
                .load(dataIntent.gambar)
                .into(_ivGambar)
            _tvNama.setText(dataIntent.nama)
        }

        // Ambil data dari Intent
        val namaOlahraga = intent.getStringExtra("namaOlahraga")
        if (namaOlahraga != null) {
            _tvNama.text = namaOlahraga
            readData(db, namaOlahraga)
        }

        //tampilkan recyclerview nya promo dari firebase
        rvPromo = findViewById(R.id.rvpromo)
        rvPromo.layoutManager = LinearLayoutManager(this)
        adapter = adapterPromo(
            listPromo = promoList,
            onItemDelete = { promo -> // Callback untuk menghapus promo
                db.collection("tbPromo").document(promo.nama)
                    .delete()
                    .addOnSuccessListener {
                        Log.d("Firebase", "Promo berhasil dihapus.")
                        promoList.remove(promo) // Hapus dari daftar lokal
                        adapter.notifyDataSetChanged()
                    }
                    .addOnFailureListener { e ->
                        Log.w("Firebase", "Gagal menghapus promo: ${e.message}")
                    }
            },
            isAdmin = false
        )
        rvPromo.adapter = adapter


    }

    //fungsi untuk baca firebase Promo
    fun readData(db: FirebaseFirestore, filterOlahraga: String){
        db.collection("tbPromo").
        whereEqualTo("nama", filterOlahraga).
        get()
            .addOnSuccessListener {
                    result ->
                promoList.clear()
                for(document in result){
                    val readData = Promo(
                        document.data.get("nama").toString(),
                        document.data.get("gambar").toString()
                    )
                    promoList.add(readData)
                    adapter.notifyDataSetChanged()
                }

            }
            .addOnFailureListener{
                Log.d("Firebase",it.message.toString())
            }
    }
}