package me.sonique.common.collision
import me.sonique.common.core.CGDKObject
import org.openrndr.math.Vector2
import kotlin.test.Test
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

class CollisionHelperTest {


    @BeforeTest
    fun setup() {

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
            size = Vector2(100.0, 100.0),
            position = Vector2(50.0, 50.0)
        )

        val objectB = CGDKObject(
            size = Vector2(100.0, 100.0),
            position = Vector2(100.0, 100.0)
        )

        val objectList = listOf<CGDKObject>(objectA, objectB)
        val collisions = CollisionHelper.detectCollisions(objectList)

        assertEquals(1, collisions.size, "Collision count invalid")

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

        val objectList = listOf(objectA, objectB)
        val collisions = CollisionHelper.detectCollisions(objectList)

        assertEquals(0, collisions.size, "No collision should be found")
    }


    @Test
    fun detectCollisionDoesNotExistsWithOverlapingALittleObjectsTest() {
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
            position = Vector2(130.0, 130.0)
        )

        val objectList = listOf(objectA, objectB)
        val collisions = CollisionHelper.detectCollisions(objectList)

        assertEquals(0, collisions.size, "No collision should be found")
    }
}