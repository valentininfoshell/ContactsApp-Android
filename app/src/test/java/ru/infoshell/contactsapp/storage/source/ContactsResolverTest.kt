package ru.infoshell.contactsapp.storage.source

import android.content.ContentResolver
import android.provider.ContactsContract
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.same
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.robolectric.fakes.RoboCursor

class ContactsResolverTest : TestCase() {
//class ContactsResolverTest {

    private lateinit var contentResolver: ContentResolver
    private lateinit var contactsRoboCursor: RoboCursor

    @Before
    override fun setUp() {
        reopenCursor()
    }

    fun testFetchContacts() {
        testCorrectGrouping()
        testCorrectFiltrateDuplicatePhones()
    }

    fun testFormatNumber() {
        val contactsResolver = ContactsResolver(contentResolver)

        assertEquals(contactsResolver.formatNumber(PHONE_1_CLEAN), PHONE_1_CLEAN)
        assertEquals(contactsResolver.formatNumber(PHONE_1_FORMATED_1), PHONE_1_CLEAN)
        assertEquals(contactsResolver.formatNumber(PHONE_1_FORMATED_2), PHONE_1_CLEAN)
    }

    @Test
    fun testCorrectGrouping() {
        if (contactsRoboCursor.isClosed) reopenCursor()
        contactsRoboCursor.setResults(
            arrayOf(
                CONTACT_1_UNIQUE,
                CONTACT_2_PHONE_1,
                CONTACT_2_PHONE_2,
                CONTACT_3_PHONE_1,
                CONTACT_3_PHONE_1_DUPLICATE,
                CONTACT_3_PHONE_1_DUPLICATE_FORMATTED_1,
                CONTACT_3_PHONE_1_DUPLICATE_FORMATTED_2,
                CONTACT_3_PHONE_2,
                CONTACT_4_UNIQUE
            )
        )

        val contacts = ContactsResolver(contentResolver).fetchContacts()
        contactsRoboCursor.close()
        assertEquals(contacts.size, 4)
    }

    @Test
    fun testCorrectFiltrateDuplicatePhones() {
        if (contactsRoboCursor.isClosed) reopenCursor()
        contactsRoboCursor.setResults(
            arrayOf(
                CONTACT_2_PHONE_1,
                CONTACT_2_PHONE_2,
                CONTACT_3_PHONE_1,
                CONTACT_3_PHONE_1_DUPLICATE,
                CONTACT_3_PHONE_1_DUPLICATE_FORMATTED_1,
                CONTACT_3_PHONE_1_DUPLICATE_FORMATTED_2,
                CONTACT_3_PHONE_2,
            )
        )

        val contacts = ContactsResolver(contentResolver).fetchContacts()
        contactsRoboCursor.close()
        assertEquals(contacts[0].allPhones.size, 2)
        assertEquals(contacts[1].allPhones.size, 2)
    }

    private fun reopenCursor() {
        contactsRoboCursor = RoboCursor()

        contentResolver = mock {
            on {
                query(
                    same(ContactsContract.Data.CONTENT_URI),
                    anyOrNull(),
                    anyOrNull(),
                    anyOrNull(),
                    anyOrNull()
                )
            } doReturn contactsRoboCursor
        }
        contactsRoboCursor.setColumnNames(CONTACTS_COLUMNS)
    }

    companion object MockData {
        private val CONTACTS_COLUMNS = listOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.PHOTO_URI,
            ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI,
            ContactsContract.CommonDataKinds.Phone.TYPE
        )

        private val PHONES_COLUMNS = listOf(
            ContactsContract.Data.RAW_CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )


        private val PHONE_1_CLEAN = "+79990000001"
        private val PHONE_1_FORMATED_1 = "+7 999 000-00-01"
        private val PHONE_1_FORMATED_2 = "+7(999) 000 00 01"

        private val CONTACT_1_UNIQUE = arrayOf(
            "Name1 Surname1",
            "+71234567890",
            null,
            null,
            1
        )

        private val CONTACT_2_PHONE_1 = arrayOf(
            "Name2 Surname2",
            "+79999999991",
            null,
            null,
            1
        )

        private val CONTACT_2_PHONE_2 = arrayOf(
            "Name2 Surname2",
            "+79999999992",
            null,
            null,
            1
        )

        private val CONTACT_3_PHONE_1 = arrayOf(
            "Name3 Surname3",
            PHONE_1_CLEAN,
            null,
            null,
            1
        )

        private val CONTACT_3_PHONE_1_DUPLICATE = arrayOf(
            "Name3 Surname3",
            PHONE_1_CLEAN,
            null,
            null,
            1
        )

        private val CONTACT_3_PHONE_1_DUPLICATE_FORMATTED_1 = arrayOf(
            "Name3 Surname3",
            PHONE_1_FORMATED_1,
            null,
            null,
            1
        )

        private val CONTACT_3_PHONE_1_DUPLICATE_FORMATTED_2 = arrayOf(
            "Name3 Surname3",
            PHONE_1_FORMATED_2,
            null,
            null,
            1
        )

        private val CONTACT_3_PHONE_2 = arrayOf(
            "Name3 Surname3",
            "+79990000002",
            null,
            null,
            1
        )

        private val CONTACT_4_UNIQUE = arrayOf(
            "Name4 Surname4",
            "+79991111111",
            null,
            null,
            1
        )
    }
}