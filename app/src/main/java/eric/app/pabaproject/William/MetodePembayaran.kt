package eric.app.pabaproject.William

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import eric.app.pabaproject.R
import eric.app.pabaproject.Robert.MainActivity

class MetodePembayaran : AppCompatActivity() {

    private lateinit var radioGroup: RadioGroup
    private lateinit var bayarButton: Button
    private lateinit var btnUploadBukti: Button
    private var imageUri: Uri? = null
    private lateinit var imageViewBuktiTransfer: ImageView
    val db = Firebase.firestore


    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_metode_pembayaran)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        radioGroup = findViewById(R.id.radioGroup)

        bayarButton = findViewById(R.id.bayarButton)
        val infoTransferBankContainer = findViewById<LinearLayout>(R.id.infoTransferBankContainer)
        val btnUploadBukti = findViewById<Button>(R.id.btnUploadBukti)
        imageViewBuktiTransfer = findViewById(R.id.imageViewBuktiTransfer)


        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_transfer -> {
                    // Tampilkan informasi Transfer Bank
                    infoTransferBankContainer.visibility = View.VISIBLE
                }
                else -> {
                    // Sembunyikan informasi Transfer Bank untuk opsi lain
                    infoTransferBankContainer.visibility = View.GONE
                }
            }
        }
        // Listener untuk tombol Upload Bukti Transfer
        btnUploadBukti.setOnClickListener {
            openGallery()
        }



        // Ambil data pesanan dari intent
        val namaPemesan = intent.getStringExtra("nama_pemesan")
        val namaLapangan = intent.getStringExtra("nama_lapangan")
        val tanggalPesan = intent.getStringExtra("tanggal_pesan")
        val waktuPesan = intent.getStringExtra("waktu_pesan")
        val durasi = intent.getStringExtra("durasi")
        val tarifTotal = intent.getIntExtra("tarif_total", 0)

        val namaPemesanTextView = findViewById<TextView>(R.id.tvNamaPemesan)
        namaPemesanTextView.text = "Nama Pemesan: $namaPemesan"

        val namaLapanganTextView = findViewById<TextView>(R.id.tvNamaLapangan)
        namaLapanganTextView.text = "Nama Lapangan: $namaLapangan"

        val tanggalPesanTextView = findViewById<TextView>(R.id.tvTanggalPesan)
        tanggalPesanTextView.text = "Tanggal Pesan: $tanggalPesan"

        val waktuPesanTextView = findViewById<TextView>(R.id.tvWaktuPesan)
        waktuPesanTextView.text = "Waktu Pesan: $waktuPesan"

        val durasiTextView = findViewById<TextView>(R.id.tvDurasi)
        durasiTextView.text = "Durasi: $durasi Jam"

        val tarifTotalTextView = findViewById<TextView>(R.id.tvTarifTotal)
        tarifTotalTextView.text = "Total Tarif: Rp $tarifTotal"




        bayarButton.setOnClickListener {
            val selectedRadioButtonId = radioGroup.checkedRadioButtonId

            if (selectedRadioButtonId != -1) {

                val selectedPaymentMethod = findViewById<RadioButton>(selectedRadioButtonId).text.toString()

                val paymentStatus = if (selectedPaymentMethod == "Transfer Bank") {
                    "Bukti Transfer: $imageUri"
                } else {
                    "Bayar Tunai"
                }
                // Data yang akan disimpan ke Firestore
                val orderData = hashMapOf(
                    "namaPemesan" to namaPemesan,
                    "namaLapangan" to namaLapangan,
                    "tanggalPesan" to tanggalPesan,
                    "waktuPesan" to waktuPesan,
                    "durasi" to durasi,
                    "tarifTotal" to tarifTotal,
                    "metodePembayaran" to selectedPaymentMethod,
                    "statusPembayaran" to paymentStatus
                )
                // Simpan data ke Firestore
                db.collection("orders")
                    .add(orderData)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Pembayaran Berhasil dan Pesanan berhasil disimpan!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        this.finish()
                    }
                    .addOnFailureListener { e ->
                        e.printStackTrace() // Menampilkan stack trace di logcat
                        Log.e("FirestoreError", "Error: ${e.message}")
                        Toast.makeText(this, "Gagal menyimpan pesanan: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Silakan pilih metode pembayaran terlebih dahulu!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    // Membuka galeri untuk memilih gambar
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }
    // Menangani hasil pemilihan gambar dari galeri
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            Toast.makeText(this, "Foto berhasil dipilih: $imageUri", Toast.LENGTH_SHORT).show()
            imageViewBuktiTransfer.visibility = View.VISIBLE
            imageViewBuktiTransfer.setImageURI(imageUri)

        }
    }
}
