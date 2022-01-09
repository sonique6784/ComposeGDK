package me.sonique.common.collision

import kotlinx.datetime.Clock
import me.sonique.common.core.CGDKObject


class CollisionHelper {

    companion object {

        const val LIMIT = 100

        private val collisionsHash = hashMapOf<Pair<CGDKObject, CGDKObject>, Long>()

        fun detectCollisions(objectList: List<CGDKObject>): List<Pair<CGDKObject, CGDKObject>> {

            val time = Clock.System.now().toEpochMilliseconds()

            objectList.forEach { goA ->
                objectList.forEach { goB ->
                    if (goA != goB) {
                        // Overlap means the center of the game objects are closer together than the sum of their radiuses
                        if (goA.mutablePosition.value.distanceTo(goB.mutablePosition.value) < (goA.mutableSize.value.y / 2 + goB.mutableSize.value.y / 2)) {
                            val collisionPair = Pair(goA, goB)
                            val collisionPair2 = Pair(goB, goA)
                            if (!collisionsHash.containsKey(collisionPair)
                                && !collisionsHash.containsKey(collisionPair2)) {
                                //if(!collisions.contains(collisionPair) && !collisions.contains(collisionPair2)) {
                                collisionsHash.put(Pair(goA, goB), time)
                                if (goA is OnCollision) {
                                    goA.onCollision(goB)
                                    //goA.onCollision(goB, goA)
                                }
                            }
//                            if(goB is OnCollision) {
//                                goB.onCollision(goA)
//                                //goB.onCollision(goA, goB)
//                            }
                        }
                    }
                }
            }

            // purge old collision
            val collisionIterator = collisionsHash.iterator()
            while (collisionIterator.hasNext()) {
                val collision = collisionIterator.next()
                // we keep collision only for LIMIT, this is to prevent double count of the same collision.
                if (time - collision.value > LIMIT) {
                    collisionIterator.remove()
                }
            }

            return collisionsHash.keys.toMutableList()
        }
    }
}