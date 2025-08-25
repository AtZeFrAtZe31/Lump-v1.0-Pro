
package com.atze.lump

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.atze.lump.databinding.FragmentChatBinding

class ChatFragment : Fragment() {
    private var _b: FragmentChatBinding? = null
    private val b get() = _b!!
    private val adapter = ChatAdapter(mutableListOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _b = FragmentChatBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b.chatList.layoutManager = LinearLayoutManager(requireContext()).apply { stackFromEnd = true }
        b.chatList.adapter = adapter

        b.btnSendMessage.setOnClickListener {
            val txt = b.etMessage.text.toString()
            if (txt.isNotBlank()) {
                adapter.add(Message(txt, true))
                b.etMessage.setText("")
                val reply = synthReply(txt)
                adapter.add(Message(reply, false))
            }
        }
    }

    private fun synthReply(txt: String): String {
        val lower = txt.lowercase()
        return when {
            "wie spät" in lower || "uhrzeit" in lower -> java.text.SimpleDateFormat("HH:mm").format(java.util.Date()).let { "Es ist $it Uhr." }
            lower.startsWith("suche ") || lower.startsWith("was ist ") -> WebSearch.wikiSummaryDe(txt.replace("suche","").replace("was ist","").trim())
            else -> "Lump: Verstanden. $txt"
        }
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
