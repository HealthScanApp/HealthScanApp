import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import com.capstone.healthscanapp.R
import com.capstone.healthscanapp.databinding.FragmentProfileBinding
import com.capstone.healthscanapp.local.pref.PrefsManager
import com.capstone.healthscanapp.ui.app.login.LoginActivity
import com.capstone.healthscanapp.ui.app.ui.home_main.NotifikasiActivity
import com.capstone.healthscanapp.ui.app.ui.home_profile.PrivacyActivity

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var prefsManager: PrefsManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        prefsManager = PrefsManager(requireContext())

        val language = binding.root.findViewById<LinearLayout>(R.id.language)
        val notif = binding.root.findViewById<LinearLayout>(R.id.notifikasi)
        val privasi = binding.root.findViewById<LinearLayout>(R.id.ketentuan_privasi)
        val keluarButton = binding.root.findViewById<Button>(R.id.riwayatKonseling)

        language.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }

        notif.setOnClickListener {
            val intent = Intent(requireContext(), NotifikasiActivity::class.java)
            startActivity(intent)
        }

        privasi.setOnClickListener {
            val intent = Intent(requireContext(), PrivacyActivity::class.java)
            startActivity(intent)
        }

        keluarButton.setOnClickListener {
            prefsManager.token = ""
            prefsManager.userEmail = ""
            prefsManager.isExampleLogin = false
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish() // Menutup activity saat ini agar tidak dapat kembali lagi
        }

        return binding.root
    }
}
