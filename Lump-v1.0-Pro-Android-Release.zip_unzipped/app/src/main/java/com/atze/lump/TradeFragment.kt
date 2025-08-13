
package com.atze.lump

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.atze.lump.databinding.FragmentTradeBinding

class TradeFragment : Fragment() {
    private var _b: FragmentTradeBinding? = null
    private val b get() = _b!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _b = FragmentTradeBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fun amount(): Double = b.etAmount.text.toString().toDoubleOrNull() ?: 0.0
        b.btnTradeBuy.setOnClickListener { Toast.makeText(requireContext(), "Buy ${amount()} (Demo)", Toast.LENGTH_SHORT).show() }
        b.btnTradeSell.setOnClickListener { Toast.makeText(requireContext(), "Sell ${amount()} (Demo)", Toast.LENGTH_SHORT).show() }
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
