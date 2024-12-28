package eric.app.pabaproject

import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {
    lateinit var indext: TextView

    //untuk recylerview jenisOlahraga
    private lateinit var _namaOlahraga : Array<String>
    private lateinit var _gambarOlahraga : Array<String>
    private var _arJenisOlahraga = arrayListOf<JenisOlahraga>()
    private lateinit var _rvJenisOlahraga : RecyclerView

    //untuk Promo dari firebase
    val db = Firebase.firestore
    var promoList = ArrayList<Promo>()
    lateinit var adapter: adapterPromo
    lateinit var rvPromo: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
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



        //lanjutan untuk hubungkan recyclerview jenis olahraga
        _rvJenisOlahraga = findViewById<RecyclerView>(R.id.rvJenisOlahraga)
        SiapkanDataJenisOlahraga()
        TambahDataJenisOlahraga()
        TampilkanDataJenisOlahraga()

        //tampilkan recyclerview nya promo dari firebase
        rvPromo = findViewById(R.id.rvPromo)
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

        //tampilkan data ketika di run awal
        readData(db)

    }



    //fungsi untuk jenisOlahraga
    fun SiapkanDataJenisOlahraga() {
        _namaOlahraga = resources.getStringArray(R.array.namaOlahraga)
        _gambarOlahraga = resources.getStringArray(R.array.gambarOlahraga)
    }
    fun TambahDataJenisOlahraga(){
        for (position in _namaOlahraga.indices){
            val data = JenisOlahraga(
                _gambarOlahraga[position],
                _namaOlahraga[position]
            )
            _arJenisOlahraga.add(data)
        }
    }
    fun TampilkanDataJenisOlahraga(){
        _rvJenisOlahraga.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        _rvJenisOlahraga.adapter = adapterJenisOlahraga(_arJenisOlahraga)

        //inisialisasi untuk adapter
        val adapterJenisOlahraga = adapterJenisOlahraga(_arJenisOlahraga)
        _rvJenisOlahraga.adapter = adapterJenisOlahraga

        //intent dari gambar icon folder resources
        adapterJenisOlahraga.setOnItemClickCallback(object : adapterJenisOlahraga.OnItemClickCallback{
            override fun onItemClicked(data: JenisOlahraga) {
//                Toast.makeText(this@MainActivity,data.nama,Toast.LENGTH_LONG).show()
                //untuk intent
                val intent = Intent (this@MainActivity, PilihanLapangan::class.java)
//                intent.putExtra("kirimData", data)
                intent.putExtra("namaOlahraga", data.nama)
                startActivity(intent)
            }
        })
    }

    //fungsi untuk baca firebase Promo
    fun readData(db: FirebaseFirestore){
        db.collection("tbPromo").get()
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