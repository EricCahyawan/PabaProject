package eric.app.pabaproject

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"

/**
 * A simple [Fragment] subclass.
 * Use the [barcode.newInstance] factory method to
 * create an instance of this fragment.
 */
class barcode : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            param3 = it.getString(ARG_PARAM3)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_barcode, container, false)

        if (param1 != null && param2 != null && param3 != null) {
            val qrCodeBitmap = generateQRCode("Pembayaran")
            view.findViewById<ImageView>(R.id.barcodeImageView).setImageBitmap(qrCodeBitmap)
        }

        return view
    }

    private fun generateQRCode(content: String): Bitmap? {
        val qrCodeWriter = QRCodeWriter()
        return try {
            val bitMatrix: BitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 200, 200)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val pixels = IntArray(width * height)

            for (x in 0 until width) {
                for (y in 0 until height) {
                    pixels[y * width + x] = if (bitMatrix[x, y]) Color.BLACK else Color.WHITE
                }
            }
            Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ARGB_8888)
        } catch (e: WriterException) {
            null
        }
    }

    companion object {
        const val ARG_PARAM1 = "param1"
        const val ARG_PARAM2 = "param2"
        const val ARG_PARAM3 = "param3"

        @JvmStatic
        fun newInstance(param1: String, param2: String, param3: String) =
            barcode().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putString(ARG_PARAM3, param3)
                }
            }
    }
}