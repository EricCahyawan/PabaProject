package eric.app.pabaproject

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso

class roberttest : AppCompatActivity() {

    private lateinit var _ivGambar: ImageView
    private lateinit var _tvNama: TextView

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

        val dataIntent = intent.getParcelableExtra<JenisOlahraga>("kirimData", JenisOlahraga::class.java)
        if (dataIntent != null) {
            Picasso.get()
                .load(dataIntent.gambar)
                .into(_ivGambar)
            _tvNama.setText(dataIntent.nama)
        }
    }
}