package com.chesire.nekome.flow

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chesire.nekome.R
import com.chesire.nekome.helpers.launchActivity
import com.chesire.nekome.helpers.login
import com.chesire.nekome.injection.DatabaseModule
import com.chesire.nekome.kitsu.AuthProvider
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotExist
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaDrawerInteractions.openDrawer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(DatabaseModule::class)
@RunWith(AndroidJUnit4::class)
class ActivityTests {
    @get:Rule
    val hilt = HiltAndroidRule(this)

    @Inject
    lateinit var authProvider: AuthProvider

    @Before
    fun setUp() {
        hilt.inject()
        authProvider.login()
    }

    @Test
    fun overviewStartsInAnimeView() {
        launchActivity()
        assertDisplayed(R.string.nav_anime)
    }

    @Test
    fun overviewCanNavigateToAnimeView() {
        launchActivity()
        assertDisplayed(R.string.nav_anime)
        openDrawer()
        clickOn(R.string.nav_manga)
        assertDisplayed(R.string.nav_manga)
        openDrawer()
        clickOn(R.string.nav_anime)
        assertDisplayed(R.string.nav_anime)
    }

    @Test
    fun overviewCanNavigateToMangaView() {
        launchActivity()
        openDrawer()
        clickOn(R.string.nav_manga)
        assertDisplayed(R.string.nav_manga)
    }

    @Test
    fun overviewCanNavigateToSettingsView() {
        launchActivity()
        openDrawer()
        clickOn(R.string.nav_settings)
        assertDisplayed(R.string.settings_version)
    }

    @Test
    fun acceptingLogoutExits() {
        launchActivity()
        openDrawer()

        clickOn(R.string.menu_logout)
        clickOn(R.string.menu_logout_prompt_confirm)

        assertDisplayed(R.id.detailsLayout)
    }

    @Test
    fun decliningLogoutRemains() {
        launchActivity()
        openDrawer()

        clickOn(R.string.menu_logout)
        clickOn(R.string.menu_logout_prompt_cancel)

        assertNotExist(R.id.detailsLayout)
    }
}
