package me.sonique.common.collision

import kotlinx.datetime.Clock
import me.sonique.common.core.CGDKObject


class CollisionHelper {

    companion object {

        private fun isEstablishedColliding(mainObject: CGDKObject, goA: CGDKObject): Boolean {
            return goA.mutablePosition.value.distanceTo(mainObject.mutablePosition.value) < (goA.mutableSize.value.y / 2 + mainObject.mutableSize.value.y / 2)
        }

        fun isColliding(mainObject: CGDKObject, goA: CGDKObject):Boolean {
            // 227.5 < 178.5 + 50 = 228.5 -> True
            // 227.5 + 17 = 244  > 178.5 -> True
            return (mainObject.mutablePosition.value.x < goA.mutablePosition.value.x + goA.mutableSize.value.x &&
            mainObject.mutablePosition.value.x + mainObject.mutableSize.value.x > goA.mutablePosition.value.x &&
            mainObject.mutablePosition.value.y < goA.mutablePosition.value.y + goA.mutableSize.value.y &&
            mainObject.mutableSize.value.y + mainObject.mutablePosition.value.y > goA.mutablePosition.value.y)
        }

        fun detectCollision(mainObject: CGDKObject, objectList: List<CGDKObject>): List<CGDKObject> {
            val collisions = mutableListOf<CGDKObject>()
            objectList.forEach { goA ->
                if (isColliding(mainObject, goA)) {
                    collisions.add(goA)
                    if (mainObject is OnCollision) {
                        mainObject.onCollision(goA)
                    }
                }
            }
            return collisions
        }

        fun detectCollisionsWithList(objectList: List<CGDKObject>): List<Pair<CGDKObject, CGDKObject>> {

            val collisionsHash = hashSetOf<Pair<CGDKObject, CGDKObject>>()

            objectList.forEach { goA ->
                objectList.forEach { goB ->
                    if (goA != goB) {
                        // Overlap means the center of the game objects are closer together than the sum of their radiuses
                        if (isColliding(goA, goB)) {
                            val collisionPair = Pair(goA, goB)
                            val collisionPair2 = Pair(goB, goA)
                            if (!collisionsHash.contains(collisionPair)
                                && !collisionsHash.contains(collisionPair2)) {
                                collisionsHash.add(Pair(goA, goB))
                            }
                        }
                    }
                }

                // TODO: add iterator and remove goA once processed
            }

            return collisionsHash.toMutableList()
        }
    }
}