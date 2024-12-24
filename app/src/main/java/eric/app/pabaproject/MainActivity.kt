package eric.app.pabaproject

import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
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

class MainActivity : AppCompatActivity() {
    lateinit var indext: TextView

    //untuk recylerview jenisOlahraga
    private lateinit var _namaOlahraga : Array<String>
    private lateinit var _gambarOlahraga : Array<String>
    private var _arJenisOlahraga = arrayListOf<JenisOlahraga>()
    private lateinit var _rvJenisOlahraga : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState == null) {
            val fragment = JadwalUntukPesanLapangan()
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }

        //intent ke admin
        val _btnTambah = findViewById<ImageView>(R.id.btnTambah)
        _btnTambah.setOnClickListener{
            startActivity(Intent(this, TambahPromo::class.java))
        }

        //lanjutan untuk hubungkan recyclerview jenis olahraga
        _rvJenisOlahraga = findViewById<RecyclerView>(R.id.rvJenisOlahraga)
        SiapkanDataJenisOlahraga()
        TambahDataJenisOlahraga()
        TampilkanDataJenisOlahraga()

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

        adapterJenisOlahraga.setOnItemClickCallback(object : adapterJenisOlahraga.OnItemClickCallback{
            override fun onItemClicked(data: JenisOlahraga) {
                Toast.makeText(this@MainActivity,data.nama,Toast.LENGTH_LONG).show()

                //untuk intent
                val intent = Intent (this@MainActivity,roberttest::class.java)
                intent.putExtra("kirimData", data)
                startActivity(intent)
            }
        })


    }


}