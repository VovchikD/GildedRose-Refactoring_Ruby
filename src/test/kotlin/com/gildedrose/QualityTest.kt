package com.gildedrose

import com.gildedrose.core.*
import com.gildedrose.core.TestUtils.generateRandomNumber
import com.gildedrose.core.TestUtils.pickRandomItem
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Before
import org.junit.Test

class QualityTest {

    private lateinit var store: List<Item>

    @Before
    fun setUp() {
        store = TestUtils.fixture.toList()
    }

    @Test
    fun givenSulfuras_afterAnyNumberOfDays_shouldHaveQualityEighty() {
        val days = generateRandomNumber()
        val item = Item("Sulfuras, Hand of Ragnaros", generateRandomNumber(), 80)
        val app = GildedRose(arrayOf(item))

        app.advanceTimeBy(days)

        assertThat(item.quality).isEqualTo(80)
    }

    @Test
    fun givenKnownItemsExceptSulfuras_afterAnyNumberOfDays_shouldHaveQualityGreaterThanOrEqualToZero() {
        store = store.excludeSulfuras()
        val days = generateRandomNumber()
        val app = GildedRose(store.toTypedArray())

        app.advanceTimeBy(days)

        store.forEach { item ->
            assertThat(item.quality).isGreaterThanOrEqualTo(0)
        }
    }

    @Test
    fun givenKnownItemsExceptSulfuras_afterAnyNumberOfDays_shouldHaveQualityLessThanOrEqualToFifty() {
        store = store.excludeSulfuras()
        val days = generateRandomNumber()
        val app = GildedRose(store.toTypedArray())

        app.advanceTimeBy(days)

        store.forEach { item ->
            assertThat(item.quality).isLessThanOrEqualTo(50)
        }
    }

    @Test
    fun givenKnownItemWithDegradingQuality_whenSellInIsGreaterThanOrEqualToZero_shouldDegradeQualityByNumberOfDays() {
        store = store.excludeSulfuras()
                .excludeBackstagePasses()
                .excludeAgedBrie()
        val days = generateRandomNumber().coerceAtLeast(0)
        val item = pickRandomItem(store)
        val initialItemQuality = item.quality
        val app = GildedRose(arrayOf(item))

        app.advanceTimeBy(days)

        val expectedQuality = (initialItemQuality - days).coerceAtLeast(0)
        assertThat(item.quality).isEqualTo(expectedQuality)
    }

    @Test
    fun givenKnownItemWithDegradingQuality_whenSellInIsLessThanZero_shouldDegradeQualityByTwiceTheNumberOfDays() {
        store = store.excludeSulfuras()
                .excludeBackstagePasses()
                .excludeAgedBrie()
        val sellIn = (-generateRandomNumber() until 0).shuffled().last()
        val days = (0 until 100).shuffled().last()
        val item = pickRandomItem(store).copy(sellIn = sellIn)
        val initialItemQuality = item.quality
        val app = GildedRose(arrayOf(item))

        app.advanceTimeBy(days)

        val expectedQuality = (initialItemQuality - days * 2).coerceAtLeast(0)
        assertThat(item.quality).isEqualTo(expectedQuality)
    }

    @Test
    fun givenAgedBrie_afterAnyNumberOfDays_shouldIncreaseQualityByNumberOfDays() {
        val days = generateRandomNumber()
        val initialItemQuality = (0..50).shuffled().last()
        val item = Item("Aged Brie", generateRandomNumber(), initialItemQuality)
        val app = GildedRose(arrayOf(item))

        app.advanceTimeBy(days)

        val expectedQuality = (initialItemQuality + days).coerceAtMost(50)
        assertThat(item.quality).isEqualTo(expectedQuality)
    }

    @Test
    fun givenBackstagePass_whenSellInNegativeOrZero_shouldSetQualityToZero() {
        val days = generateRandomNumber()
        val sellIn = (-generateRandomNumber()..0).shuffled().last()
        val item = Item("Backstage passes to a TAFKAL80ETC concert", sellIn, (0..50).shuffled().last())
        val app = GildedRose(arrayOf(item))

        app.advanceTimeBy(days)

        assertThat(item.quality).isZero()
    }

    @Test
    fun givenBackstagePass_whenSellInMoreThanZeroAndLessThanSix_shouldIncreaseQualityByNumberOfDaysMultipliedByThree() {
        val sellIn = (1 until 6).shuffled().last()
        val days = sellIn - 1
        val initialItemQuality = (0..50).shuffled().last()
        val item = Item("Backstage passes to a TAFKAL80ETC concert", sellIn, initialItemQuality)
        val app = GildedRose(arrayOf(item))

        app.advanceTimeBy(days)

        val expectedQuality = (initialItemQuality + days * 3).coerceAtMost(50)
        assertThat(item.quality).isEqualTo(expectedQuality)
    }

    @Test
    fun givenBackstagePass_whenSellInMoreThanFiveAndLessThanEleven_shouldIncreaseQualityByNumberOfDaysMultipliedByTwo() {
        (6 until 11).shuffled().forEach {
            val sellIn = it
            val days = sellIn - 6
            val initialItemQuality = (0..50).shuffled().last()
            val item = Item("Backstage passes to a TAFKAL80ETC concert", sellIn, initialItemQuality)
            val app = GildedRose(arrayOf(item))

            app.advanceTimeBy(days)

            val expectedQuality = (initialItemQuality + days * 2).coerceAtMost(50)
            assertThat(item.quality).isEqualTo(expectedQuality)
        }
    }

    @Test
    fun givenBackstagePass_whenSellInMoreThanEleven_shouldIncreaseQualityByNumberOfDays() {
        (11..999).shuffled().forEach {
            val sellIn = it
            val days = sellIn - 11
            val initialItemQuality = (0..50).shuffled().last()
            val item = Item("Backstage passes to a TAFKAL80ETC concert", sellIn, initialItemQuality)
            val app = GildedRose(arrayOf(item))

            app.advanceTimeBy(days)

            val expectedQuality = (initialItemQuality + days).coerceAtMost(50)
            assertThat(item.quality).isEqualTo(expectedQuality)
        }
    }
}