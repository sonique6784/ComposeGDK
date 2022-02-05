package me.sonique.common.collision
import me.sonique.common.core.CGDKObject
import org.openrndr.math.Vector2
import kotlin.test.Test
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class CollisionHelperTest {


    @BeforeTest
    fun setup() {

    }

    @Test
    fun detectCollisionsExistsTest() {
        /*
        ╔═══╗
        ║ ┌───┐
        ╚═══╝ │
          └───┘
         */

        val objectA = CGDKObject(
            size = Vector2(90.0, 110.0),
            position = Vector2(50.0, 50.0)
        )

        val objectB = CGDKObject(
            size = Vector2(120.0, 70.0),
            position = Vector2(100.0, 100.0)
        )

        val collision = CollisionHelper.isColliding(objectA, objectB)
        assertTrue(collision, "Collision should be detected")

    }

    @Test
    fun detectCollisionExistsTest() {
        /*
        ╔═══╗
        ║ ┌───┐
        ╚═══╝ │
          └───┘
         */

        val objectA = CGDKObject(
            size = Vector2(110.0, 100.0),
            position = Vector2(50.0, 50.0)
        )

        val objectB = CGDKObject(
            size = Vector2(100.0, 80.0),
            position = Vector2(100.0, 100.0)
        )

        val collision = CollisionHelper.isColliding(objectA, objectB)
        assertTrue(collision, "Collision should be detected")
    }

    @Test
    fun detectCollisionExists2Test() {
        /*
        ╔═══╗
        ║ ┌────┐
        ║ └────┘
        ╚═══╝ 
          
         */

        val objectA = CGDKObject(
            size = Vector2(50.0, 100.0),
            position = Vector2(50.0, 50.0)
        )

        val objectB = CGDKObject(
            size = Vector2(100.0, 50.0),
            position = Vector2(60.0, 60.0)
        )

        val collision = CollisionHelper.isColliding(objectA, objectB)
        assertTrue(collision, "Collision should be detected")
    }

    @Test
    fun detectCollisionExistsWhenObjectOverlapExactlyTest() {
        /*
        ╔═══╗
        ║   ║
        ╚═══╝
          
         */

        val objectA = CGDKObject(
            size = Vector2(100.0, 100.0),
            position = Vector2(100.0, 100.0)
        )

        val objectB = CGDKObject(
            size = Vector2(100.0, 100.0),
            position = Vector2(100.0, 100.0)
        )

        val collision = CollisionHelper.isColliding(objectA, objectB)
        assertTrue(collision, "Collision should be detected")
    }

    @Test
    fun detectCollisionDoesNotExistsWithFarAppartObjectsTest() {

        /*
        ╔═══╗
        ║   ║
        ╚═══╝
              ┌───┐
              │   │
              └───┘
         */

        val objectA = CGDKObject(
            size = Vector2(100.0, 100.0),
            position = Vector2(50.0, 50.0)
        )

        val objectB = CGDKObject(
            size = Vector2(100.0, 100.0),
            position = Vector2(200.0, 200.0)
        )

        val collision = CollisionHelper.isColliding(objectA, objectB)
        assertFalse(collision, "Collision should not be detected")
    }


    @Test
    fun detectCollisionExistsWithOverlapingBottomRightTest() {
        /*
        ╔═══╗
        ║   ║
        ╚═══┌───┐
            │   │
            └───┘
         */

        val objectA = CGDKObject(
            size = Vector2(100.0, 100.0),
            position = Vector2(50.0, 50.0)
        )

        val objectB = CGDKObject(
            size = Vector2(100.0, 100.0),
            position = Vector2(150.0, 150.0)
        )

        val collision = CollisionHelper.isColliding(objectA, objectB)
        assertFalse(collision, "Collision should not be detected")
    }

    @Test
    fun detectCollisionExistsWithOverlapingBottomLeftTest() {
        /*
            ╔═══╗
            ║   ║ 
        ┌───╚═══╝
        │   │
        └───┘
         */

        val objectA = CGDKObject(
            size = Vector2(100.0, 100.0),
            position = Vector2(50.0, 50.0)
        )

        val objectB = CGDKObject(
            size = Vector2(100.0, 100.0),
            position = Vector2(-50.0, 150.0)
        )

        val collision = CollisionHelper.isColliding(objectA, objectB)
        assertFalse(collision, "Collision should not be detected")
    }

    @Test
    fun detectCollisionExistsWithOverlapingRightEdge() {
        /*
            ╔═══╗───┐
            ║   ║   │
            ╚═══╝───┘
         */

        val objectA = CGDKObject(
            size = Vector2(100.0, 100.0),
            position = Vector2(50.0, 50.0)
        )

        val objectB = CGDKObject(
            size = Vector2(100.0, 100.0),
            position = Vector2(150.0, 50.0)
        )

        val collision = CollisionHelper.isColliding(objectA, objectB)
        assertFalse(collision, "Collision should not be detected")
    }

    @Test
    fun detectCollisionExistsWhenIsInsideTheOther() {
        /*
            ╔══════╗
            ║ ┌──┐ ║ 
            ║ └──┘ ║
            ╚══════╝
         */

        val objectA = CGDKObject(
            size = Vector2(100.0, 100.0),
            position = Vector2(50.0, 50.0)
        )

        val objectB = CGDKObject(
            size = Vector2(25.0, 25.0),
            position = Vector2(60.0, 60.0)
        )

        val collision = CollisionHelper.isColliding(objectA, objectB)
        assertTrue(collision, "Collision should not be detected")
    }
}