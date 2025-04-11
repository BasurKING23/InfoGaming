package com.example.infogaming.activity.newsletter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.infogaming.databinding.FreeGameDealsBinding
import com.example.infogaming.databinding.NewsletterBinding

class NewsletterFragment : Fragment() {

    // Utiliza el nombre de la clase de binding generada (en este caso, FragmentNewsletterBinding)
    lateinit var binding: NewsletterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el layout usando el nombre correcto de la clase de binding
        binding = NewsletterBinding.inflate(inflater, container, false)

        // Aquí se usa el binding para acceder a las vistas
        binding.newsletter.text = "Texto del fragmento"

        return binding.root // Devuelve la raíz de la vista inflada
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}


