
package com.atze.lump

import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.net.URLEncoder

object WebSearch {
    private val client = OkHttpClient()

    fun wikiSummaryDe(query: String): String {
        return try {
            val q = URLEncoder.encode(query, "UTF-8")
            val url = "https://de.wikipedia.org/api/rest_v1/page/summary/$q"
            val req = Request.Builder().url(url).build()
            client.newCall(req).execute().use { resp ->
                val txt = resp.body?.string() ?: return "Keine Antwort."
                val js = JSONObject(txt)
                val title = js.optString("title", "")
                val extract = js.optString("extract", "")
                if (title.isNotEmpty()) "$title — $extract" else extract
            }
        } catch (e: Exception) {
            "Suche fehlgeschlagen: ${e.message}"
        }
    }
}
