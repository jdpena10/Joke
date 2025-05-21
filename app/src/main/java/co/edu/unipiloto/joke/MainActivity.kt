package co.edu.unipiloto.joke

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var btnStartService: Button
    private lateinit var btnReservarPaseo: Button
    private lateinit var btnPaseoCumplido: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Solicitar permiso de notificación en Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1
                )
            }
        }

        // Referenciar botones
        btnStartService = findViewById(R.id.btnStartService)
        btnReservarPaseo = findViewById(R.id.btnReservarPaseo)
        btnPaseoCumplido = findViewById(R.id.btnPaseoCumplido)

        // Notificación simple
        btnStartService.setOnClickListener {
            val intent = Intent(this, DelayedMessageService::class.java).apply {
                putExtra(DelayedMessageService.EXTRA_MESSAGE, "Hola desde Kotlin!")
            }
            startService(intent)
        }

        // Notificación por reserva de paseo
        btnReservarPaseo.setOnClickListener {
            val intent = Intent(this, DelayedMessageService::class.java).apply {
                putExtra(DelayedMessageService.EXTRA_MESSAGE, "🐾 Nueva reserva de paseo para Rocky")
            }
            startService(intent)
        }

        // Notificación por paseo cumplido
        btnPaseoCumplido.setOnClickListener {
            val intent = Intent(this, DelayedMessageService::class.java).apply {
                putExtra(DelayedMessageService.EXTRA_MESSAGE, "✅ El paseo con Rocky ha sido completado")
            }
            startService(intent)
        }
    }

    // Manejo de respuesta del permiso
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido
            } else {
                // Permiso denegado
            }
        }
    }
}