package eric.app.pabaproject

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class JadwalUntukPesanLapanganContainer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_jadwal_untuk_pesan_lapangan_container)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if (savedInstanceState == null) {
            val namaLapangan = intent.getStringExtra("namaLapangan") // Ambil data dari Intent
            val fragment = JadwalUntukPesanLapangan()

            // Kirim data ke fragment melalui Bundle
            val bundle = Bundle()
            bundle.putString("namaLapangan", namaLapangan)
            fragment.arguments = bundle

            val transaction= supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
}