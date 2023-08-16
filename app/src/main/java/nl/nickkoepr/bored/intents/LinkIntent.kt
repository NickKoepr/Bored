package nl.nickkoepr.bored.intents

import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * Open a given link in the browser of a user.
 * @param context [Context]
 * @param link The link that has to be opened
 */
fun openLink(context: Context, link: String) {
    val urlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
    context.startActivity(urlIntent)
}