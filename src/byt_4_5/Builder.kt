package byt_4_5

import kotlin.random.Random

// Example of builder design pattern

fun main() {
    testBuilder()
}

fun testBuilder() {
    val mage = Mage()
    val rouge = Rouge()
    val hybridBuilder = HybridBuilder(mage, rouge)
    val characterEditor = CharacterEditor(hybridBuilder)
    val hybrid = characterEditor.createCharacter()
    println("$mage\n")
    println("$rouge\n")
    println(hybrid)
}

interface CharacterBuilder {
    fun setPrimaryAbility()
    fun setSecondaryAbility()
    fun setPassiveAbility()
    fun getCharacter(): CharacterClass
}

class HybridBuilder(var primaryClass: CharacterClass, var secondaryClass: CharacterClass) : CharacterBuilder {

    val hybrid = Hybrid()

    override fun setPrimaryAbility() {
        hybrid.primaryAbility = primaryClass.primaryAbility
    }

    override fun setSecondaryAbility() {
        hybrid.secondaryAbility = secondaryClass.secondaryAbility
    }

    // Randomly selects the passive ability of primary or secondary class
    override fun setPassiveAbility() {
        hybrid.passiveAbility =
            if (Random.nextInt(2) == 0) primaryClass.passiveAbility else secondaryClass.passiveAbility
    }

    override fun getCharacter(): CharacterClass {
        return hybrid
    }
}

abstract class CharacterClass(
    var primaryAbility: String? = null,
    var secondaryAbility: String? = null,
    var passiveAbility: String? = null
) {
    override fun toString(): String {
        return this.javaClass.simpleName +
                "\nprimary ability: $primaryAbility" +
                "\nsecondary ability: $secondaryAbility" +
                "\npassive ability: $passiveAbility"
    }
}

class Mage :
    CharacterClass(
        primaryAbility = "Fireball",
        secondaryAbility = "Teleportation",
        passiveAbility = "Magic resistance"
    )

class Rouge :
    CharacterClass(
        primaryAbility = "Piercing strike",
        secondaryAbility = "Invisibility",
        passiveAbility = "Quiet movement"
    )

class Hybrid : CharacterClass()

// This class represents the Director of the builder pattern
class CharacterEditor(var characterBuilder: CharacterBuilder) {
    fun createCharacter(): CharacterClass {
        characterBuilder.setPrimaryAbility()
        characterBuilder.setSecondaryAbility()
        characterBuilder.setPassiveAbility()
        return characterBuilder.getCharacter()
    }
}
