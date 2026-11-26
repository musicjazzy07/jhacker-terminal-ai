package ai.antigem.ui.browser

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class V0UrlTest {

    @Test
    fun v0UrlIsHttpsV0App() {
        assertEquals("https://v0.app", BrowserSessionManager.V0_URL)
    }

    @Test
    fun v0HomeAndPathsAreV0Urls() {
        assertTrue(BrowserSessionManager.isV0Url("https://v0.app"))
        assertTrue(BrowserSessionManager.isV0Url("https://v0.app/"))
        assertTrue(BrowserSessionManager.isV0Url("https://v0.app/chat/abc123"))
        assertTrue(BrowserSessionManager.isV0Url("https://v0.app?foo=bar"))
        assertTrue(BrowserSessionManager.isV0Url("  https://v0.app/chat/x  "))
    }

    @Test
    fun otherSitesAreNotV0Urls() {
        assertFalse(BrowserSessionManager.isV0Url(""))
        assertFalse(BrowserSessionManager.isV0Url("https://v0.app.evil.com/"))
        assertFalse(BrowserSessionManager.isV0Url("https://google.com/"))
        assertFalse(BrowserSessionManager.isV0Url("https://www.v0.app/"))
        assertFalse(BrowserSessionManager.isV0Url("http://v0.app/"))
    }
}
