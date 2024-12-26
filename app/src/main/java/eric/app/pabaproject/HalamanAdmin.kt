package eric.app.pabaproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HalamanAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_halaman_admin)
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

        //intent ke halaman Tambah Promo untuk admin
        val _btnTambahPromo = findViewById<CardView>(R.id.btnTambahPromo)
        _btnTambahPromo.setOnClickListener{
            startActivity(Intent(this, TambahPromo::class.java))
        }

        //intent ke halaman Tambah Lapangan untuk admin
        val _btnTambahLapangan = findViewById<CardView>(R.id.btnTambahLapangan)
        _btnTambahLapangan.setOnClickListener{
            startActivity(Intent(this, TambahPromo::class.java))
        }

        // Intent balik ke halaman sebelumnya
        val _btnKembali = findViewById<ImageView>(R.id.btnKembali)
        _btnKembali.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}