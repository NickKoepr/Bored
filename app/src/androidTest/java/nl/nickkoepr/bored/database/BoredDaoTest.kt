package nl.nickkoepr.bored.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import nl.nickkoepr.bored.data.database.BoredDao
import nl.nickkoepr.bored.data.database.BoredDatabase
import nl.nickkoepr.bored.dummy.DummyActivity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class BoredDaoTest {

    private lateinit var boredDao: BoredDao
    private lateinit var db: BoredDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, BoredDatabase::class.java).build()
        boredDao = db.boredDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @Throws(IOException::class)
    fun addFavoriteActivityToDb() = runTest {
        // Changing the primaryKey to one to assert that the object are equal.
        val activity = DummyActivity.activity1.copy(primaryKey = 1)
        boredDao.addFavorite(activity)
        val favoriteList = boredDao.getFavorites().first()
        assertFalse(favoriteList.isEmpty())
        assertEquals(activity, favoriteList[0])
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @Throws(IOException::class)
    fun removeFavoriteActivityFromDb() = runTest {
        // Changing the primaryKey to one to assert that the object are equal.
        val activity = DummyActivity.activity1.copy(primaryKey = 1)
        boredDao.addFavorite(activity)
        boredDao.removeFavorite(activity)
        val favoriteList = boredDao.getFavorites().first()
        assertTrue(favoriteList.isEmpty())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @Throws(IOException::class)
    fun getActivityFromDbByKey() = runTest {
        boredDao.addFavorite(DummyActivity.activity1)
        val result = boredDao.getActivityByKey(DummyActivity.activity1.key)
        assertNotNull(result.first())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @Throws(IOException::class)
    fun getUnknownActivityFromDbByKey() = runTest {
        boredDao.addFavorite(DummyActivity.activity1)
        val result = boredDao.getActivityByKey(DummyActivity.activity2.key)
        assertNull(result.first())
    }
}