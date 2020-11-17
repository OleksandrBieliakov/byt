package byt_4_5

import java.util.*
import kotlin.collections.HashMap

// Example of memento design pattern

fun main() {
    testMemento()
}

fun testMemento() {
    val glossary = Glossary()
    val caretaker = Caretaker(glossary)

    caretaker.makeBackup()
    println("initial state: $glossary")

    glossary.addWord("CLI", "command line interface")
    println("word \"CLI\" added: $glossary")

    caretaker.undo()
    println("undo: $glossary")

    glossary.addWord("CLI", "command line interface")
    caretaker.makeBackup()
    println("word \"CLI\"added: $glossary")

    glossary.addWord("UML", "unified modeling language")
    caretaker.makeBackup()
    println("word \"UML\"added: $glossary")

    glossary.removeWord("CLI")
    println("word \"CLI\" removed: $glossary")

    caretaker.undo()
    println("undo1: $glossary")

    caretaker.undo()
    println("undo2: $glossary")

    glossary.addWord("UML", "unified modeling language")
    caretaker.makeBackup()
    println("word \"UML\"added: $glossary")

    glossary.clear()
    println("cleared: $glossary")

    caretaker.undo()
    println("undo1: $glossary")
}

class Glossary {
    var words: HashMap<String, String> = HashMap()

    fun addWord(word: String, definition: String) {
        words[word] = definition
    }

    fun removeWord(word: String) {
        words.remove(word)
    }

    fun clear() {
        words = HashMap()
    }

    fun createSnapshot(): Snapshot {
        return Snapshot(this)
    }

    override fun toString(): String {
        return words.toString()
    }
}

class Snapshot(private val glossary: Glossary) {
    private val words: HashMap<String, String> = HashMap(glossary.words)

    fun restore() {
        glossary.words = words
    }
}

class Caretaker(private val glossary: Glossary) {
    private var snapshots: Stack<Snapshot> = Stack()

    fun makeBackup() {
        snapshots.push(glossary.createSnapshot())
    }

    fun undo() {
        snapshots.pop().restore()
    }
}
