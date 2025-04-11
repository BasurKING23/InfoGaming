package com.example.infogaming.activity.gameDeals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.infogaming.databinding.FreeGameDealsBinding

class FreeGamesDealsFragment : Fragment() {

    // Usa el nombre de binding correcto generado por Android para el archivo XML
    lateinit var binding: FreeGameDealsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el layout y asigna el binding correctamente
        binding = FreeGameDealsBinding.inflate(inflater, container, false)

        // Modifica el texto del TextView en el layout
        binding.textHome.text = "Prueba Free Game Deals"

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}

