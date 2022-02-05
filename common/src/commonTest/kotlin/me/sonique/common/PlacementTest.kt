package me.sonique.common

import me.sonique.common.Placement
import me.sonique.common.core.CGDKObject
import me.sonique.common.core.Vector2
import kotlin.test.Test
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

class PlacementTest {

    lateinit var placement: Placement

    @BeforeTest
    fun setup() {
        placement = Placement(Vector2(320.0, 480.0))
    }

    @Test
    fun testTopLeft() {
        val vector = placement.toTopLeft()

        assertEquals(0.0, vector.x)
        assertEquals(0.0, vector.y)
    }

    @Test
    fun testCenterLeft() {
        val objectA = CGDKObject(
            size = Vector2(40.0, 30.0)
        )

        val vector = placement.toCenterLeft(objectA)

        assertEquals(0.0, vector.x)
        assertEquals(225.0, vector.y)
    }

    @Test
    fun testCenter() {
        val objectA = CGDKObject(
            size = Vector2(40.0, 30.0)
        )

        val vector = placement.toCenter(objectA)

        assertEquals(140.0, vector.x)
        assertEquals(225.0, vector.y)
    }

    @Test
    fun testBottomLeft() {
        val objectA = CGDKObject(
            size = Vector2(40.0, 30.0)
        )

        val vector = placement.toBottomLeft(objectA)

        assertEquals(0.0, vector.x)
        assertEquals(450.0, vector.y)
    }

    @Test
    fun testTopRight() {
        val objectA = CGDKObject(
            size = Vector2(40.0, 30.0)
        )

        val vector = placement.toTopRight(objectA)

        assertEquals(280.0, vector.x)
        assertEquals(0.0, vector.y)
    }

    @Test
    fun testCenterRight() {
        val objectA = CGDKObject(
            size = Vector2(40.0, 30.0)
        )

        val vector = placement.toCenterRight(objectA)

        assertEquals(280.0, vector.x)
        assertEquals(225.0, vector.y)
    }

    @Test
    fun testTopCenter() {
        val objectA = CGDKObject(
            size = Vector2(40.0, 30.0)
        )

        val vector = placement.toTopCenter(objectA)

        assertEquals(140.0, vector.x)
        assertEquals(0.0, vector.y)
    }

    @Test
    fun testBottomCenter() {
        val objectA = CGDKObject(
            size = Vector2(40.0, 30.0)
        )

        val vector = placement.toBottomCenter(objectA)

        assertEquals(140.0, vector.x)
        assertEquals(450.0, vector.y)
    }

    @Test
    fun testBottomRight() {
        val objectA = CGDKObject(
            size = Vector2(40.0, 30.0)
        )

        val vector = placement.toBottomRight(objectA)

        assertEquals(280.0, vector.x)
        assertEquals(450.0, vector.y)
    }

    @Test
    fun putObjectAOnTopB() {
        val objectA = CGDKObject(
            size = Vector2(40.0, 30.0),
            position = Vector2(23.0, 12.0)
        )

        val objectB = CGDKObject(
            size = Vector2(40.0, 30.0),
            position = placement.toBottomCenter(objectA)
        )

        val vector = placement.putObjectAOnTopB(objectA, objectB)

        assertEquals(23.0, vector.x)
        assertEquals(420.0, vector.y)
    }
}