# Jhacker Terminal — rebrand notes

Jhacker Terminal 1.0 is a fork of **AntiGem** by Santosh Kurmi
(<https://github.com/Santoshkurmi/antigem>), licensed under the
**GNU General Public License v3.0**. The original `LICENSE` file and all
copyright notices are kept intact in this repository.

## Base

- Upstream: `Santoshkurmi/antigem`, default branch `standalone`
- Base commit: `57a71e413e145397bb291e24b3910d7a60aba5cd`
  ("Add home screen widgets", 2026-11-24), upstream version 2.0.4
  (version_code 137)
- This fork: version **1.0.0** (version_code **139**); the upstream beta
  block was removed (its code 138 was already taken).

## Changes made (name + icon only)

1. **Display name**: `AntiGem` → `Jhacker Terminal` everywhere the user
   can see it — launcher label (`app_name`), terminal label, accessibility
   service name/description, Space Manager label, beta/debug labels,
   crash-report subjects, ADB/Shizuku/root prompts, agent context strings,
   browser-extension host name, and the splash screen (the old
   "ANTIGEM" wordmark was removed; the splash now shows the skull only).
   The package name is unchanged: `ai.antigem`.
2. **Launcher icon**: the upstream gem mark was replaced with a fresh
   neon-green (`#00FF41`) skull on black, drawn as a vector
   (`ic_launcher_foreground.xml`, `ic_launcher_background.xml`,
   `splash_prism_animated.xml`). The terminal's launcher icon uses the
   same skull. No upstream logo or trademark is reused.
3. **Nothing else changed**: theme, colours, features, agents, permissions
   and behaviour are exactly as upstream. Internal identifiers that are
   not user-visible were left alone on purpose: the Kotlin/Java package
   and class names (`ai.antigem`, `AntiGemApplication`, …), internal file
   paths (`~/.config/antigem`, `.antigem` files), the `window.AntiGem`
   mini-app SDK API, and upstream URLs. The GPL credit/license section in
   the app still names the original project, as the license requires.

## Building

Same as upstream: the release is built by GitHub Actions
(`.github/workflows/release.yml`) from a `version.json` change; it needs
the `RELEASE_KEYSTORE_BASE64` repository secret. This fork is signed
with its own fresh keystore — never the upstream one.
