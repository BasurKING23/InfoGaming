package com.example.infogaming.activity.gameDeals

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.ui.semantics.text
import androidx.fragment.app.Fragment
import com.example.infogaming.R

import com.example.infogaming.databinding.FreeGameDealsBinding
import com.example.infogaming.databinding.NavHeaderMainBinding

class FreeGamesDealsFragment : Fragment() {

    lateinit var binding: FreeGameDealsBinding

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FreeGameDealsBinding.inflate(inflater, container, false)

        val sharedPrefs = requireContext().getSharedPreferences("prefs", 0)

        // Mostrar el nombre guardado si existe
        val nombreGuardado = sharedPrefs.getString("usuario_guardado", "")
        binding.textfreegamesdeal.text = if (nombreGuardado.isNullOrEmpty()) {
            "Bienvenido"
        } else {
            "Hola, $nombreGuardado"
        }

        binding.saveNoteBtn.setOnClickListener {
            val texto = binding.editTextGameNote.text.toString()
            sharedPrefs.edit().putString("usuario_guardado", texto).apply()

            binding.textfreegamesdeal.text = "Hola, $texto"
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}


