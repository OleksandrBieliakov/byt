package byt_4_5

fun main() {
    testState()
}

fun testState() {
    var boss = GameBoss()
    println("Current state is ${boss.state!!.javaClass.simpleName} current health is ${boss.health}")
    while (boss.health >= 0) {
        boss.primaryAttack()
        boss.secondaryAttack()
        boss.move()
        boss.takeDamage(25)
    }

    boss = GameBoss()
    println("\nCurrent state is ${boss.state!!.javaClass.simpleName} current health is ${boss.health}")
    boss.takeDamage(100)
}


class GameBoss(var health: Int = 100, var state: State? = null) {
    init {
        state = Healthy(this)
    }

    fun primaryAttack() {
        state?.primaryAttack()
    }

    fun secondaryAttack() {
        state?.secondaryAttack()
    }

    fun move() {
        state?.move()
    }

    fun takeDamage(damage: Int) {
        health -= damage
        println("$damage damage taken, current health is $health")
        state?.takeDamage()
    }
}

abstract class State(protected val context: GameBoss, private val healthCap: Int) {
    abstract fun primaryAttack()
    abstract fun secondaryAttack()
    abstract fun move()
    abstract fun takeDamage()
    protected fun reachedHealthCap(): Boolean {
        return context.health < healthCap
    }
}

class Healthy(context: GameBoss) : State(context, 71) {
    override fun primaryAttack() {
        println("Healthy punch")
    }

    override fun secondaryAttack() {
        println("Healthy kick")
    }

    override fun move() {
        println("Healthy walk")
    }

    override fun takeDamage() {
        if (reachedHealthCap()) {
            println("State changes to Damaged")
            context.state = Damaged(context)
            (context.state as Damaged).takeDamage()
        }
    }
}

class Damaged(context: GameBoss) : State(context, 31) {
    override fun primaryAttack() {
        println("Damaged heavy punch")
    }

    override fun secondaryAttack() {
        println("Damaged heavy kick")
    }

    override fun move() {
        println("Damaged run")
    }

    override fun takeDamage() {
        if (reachedHealthCap()) {
            println("State changes to Critical")
            context.state = Critical(context)
            (context.state as Critical).takeDamage()
        }
    }
}

class Critical(context: GameBoss) : State(context, 1) {
    override fun primaryAttack() {
        println("Critical berserk punch")
    }

    override fun secondaryAttack() {
        println("Critical berserk kick")
    }

    override fun move() {
        println("Critical dash")
    }

    override fun takeDamage() {
        if (reachedHealthCap()) {
            println("State changes to Dead")
            context.state = Dead(context)
        }
    }
}

class Dead(context: GameBoss) : State(context, 0) {
    override fun primaryAttack() {
        println("Too late to punch...")
    }

    override fun secondaryAttack() {
        println("Too late to kick...")
    }

    override fun move() {
        println("Too late to run...")
    }

    override fun takeDamage() {
        println("Don't be a psycho, it's a dead body")
    }
}