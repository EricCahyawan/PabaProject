package eric.app.pabaproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider

class DetailLapangan : AppCompatActivity() {

    lateinit var _namaLapangan: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_lapangan)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        _namaLapangan = findViewById(R.id.namaLapangan)
        //hansel -> _namaLapangan.text = data yang dikirimkan dari hansel
        val _pindah = findViewById<Button>(R.id.pindah)
        _pindah.setOnClickListener {
            val intent = Intent(this, JadwalUntukPesanLapanganContainer::class.java)
            intent.putExtra("namaLapangan", _namaLapangan.text.toString())
            startActivity(intent)
        }
    }
}