package ai.antigem.ui.browser

import ai.antigem.ui.browser.BrowserSessionManager.NonHttpUrlAction
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class NonHttpUrlTest {

    @Test
    fun httpAndFriendsPassThroughToWebView() {
        assertEquals(NonHttpUrlAction.LetWebViewHandle, BrowserSessionManager.resolveNonHttpUrl("https://v0.app"))
        assertEquals(NonHttpUrlAction.LetWebViewHandle, BrowserSessionManager.resolveNonHttpUrl("http://example.com/"))
        assertEquals(NonHttpUrlAction.LetWebViewHandle, BrowserSessionManager.resolveNonHttpUrl("file:///sdcard/x.html"))
        assertEquals(NonHttpUrlAction.LetWebViewHandle, BrowserSessionManager.resolveNonHttpUrl("about:blank"))
        assertEquals(NonHttpUrlAction.LetWebViewHandle, BrowserSessionManager.resolveNonHttpUrl("data:text/html,<h1>hi</h1>"))
    }

    @Test
    fun blankOrSchemelssUrlsPassThrough() {
        assertEquals(NonHttpUrlAction.LetWebViewHandle, BrowserSessionManager.resolveNonHttpUrl(""))
        assertEquals(NonHttpUrlAction.LetWebViewHandle, BrowserSessionManager.resolveNonHttpUrl("   "))
        assertEquals(NonHttpUrlAction.LetWebViewHandle, BrowserSessionManager.resolveNonHttpUrl("/relative/path"))
    }

    @Test
    fun intentUrlWithFallbackYieldsLaunchWithDecodedFallback() {
        val url = "intent://accounts.google.com/devicephoneverification/begin?hl=en&flow=browser" +
            "#Intent;package=com.google.android.gms;" +
            "S.browser_fallback_url=https%3A%2F%2Faccounts.google.com%2Fsignin;end"
        val expected = NonHttpUrlAction.LaunchExternalIntent(
            intentUri = url,
            fallbackUrl = "https://accounts.google.com/signin"
        )
        assertEquals(expected, BrowserSessionManager.resolveNonHttpUrl(url))
    }

    @Test
    fun intentUrlFallbackQueryParamsAreDecoded() {
        val url = "intent://example.com/#Intent;" +
            "S.browser_fallback_url=https%3A%2F%2Faccounts.google.com%2Fdevicephoneverification%2Fbegin%3Fhl%3Den%26flow%3Dbrowser;end"
        val action = BrowserSessionManager.resolveNonHttpUrl(url) as NonHttpUrlAction.LaunchExternalIntent
        assertEquals(
            "https://accounts.google.com/devicephoneverification/begin?hl=en&flow=browser",
            action.fallbackUrl
        )
    }

    @Test
    fun intentUrlWithoutFallbackYieldsLaunchWithNullFallback() {
        val url = "intent://accounts.google.com/devicephoneverification/begin?hl=en#Intent;package=com.google.android.gms;end"
        val action = BrowserSessionManager.resolveNonHttpUrl(url) as NonHttpUrlAction.LaunchExternalIntent
        assertEquals(url, action.intentUri)
        assertNull(action.fallbackUrl)
    }

    @Test
    fun telAndMailtoLaunchExternally() {
        val tel = BrowserSessionManager.resolveNonHttpUrl("tel:+911234567890") as NonHttpUrlAction.LaunchExternalIntent
        assertEquals("tel:+911234567890", tel.intentUri)
        assertNull(tel.fallbackUrl)
        val mail = BrowserSessionManager.resolveNonHttpUrl("mailto:someone@example.com") as NonHttpUrlAction.LaunchExternalIntent
        assertEquals("mailto:someone@example.com", mail.intentUri)
        assertNull(mail.fallbackUrl)
    }

    @Test
    fun unknownSchemeTriesExternalInsteadOfWebViewError() {
        val action = BrowserSessionManager.resolveNonHttpUrl("market://details?id=com.example.app")
        assertEquals(
            NonHttpUrlAction.LaunchExternalIntent("market://details?id=com.example.app"),
            action
        )
    }

    @Test
    fun extractBrowserFallbackUrlHandlesMissingAndEmpty() {
        assertNull(
            BrowserSessionManager.extractBrowserFallbackUrl(
                "intent://example.com/#Intent;package=com.example;end"
            )
        )
        assertNull(
            BrowserSessionManager.extractBrowserFallbackUrl(
                "intent://example.com/#Intent;S.browser_fallback_url=;end"
            )
        )
        assertNull(BrowserSessionManager.extractBrowserFallbackUrl("https://example.com/"))
    }
}
