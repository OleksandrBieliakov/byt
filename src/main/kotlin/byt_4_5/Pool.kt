package byt_4_5

import kotlinx.coroutines.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// Example of object pool design pattern

fun main() {
    testObjectPool()
}

// tasks submitted by clients are executed concurrently
fun testObjectPool() = runBlocking {
    val client0 = Client(0)
    val client1 = Client(1)
    // clients submit tasks periodically
    for (i in 0..3 step 2) {
        launch { client0.submitTask(i) }
        delay(1000)
        launch { client1.submitTask(i + 1) }
        delay(1000)
    }
}

fun time(): String {
    val currentTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ISO_LOCAL_TIME
    return currentTime.format(formatter)
}

class Worker(private val id: Int) {
    suspend fun performTask(clientId: Int, taskId: Int) {
        println("${time()} Task-$taskId STARTED by byt_4_5.Worker-$id for byt_4_5.Client-$clientId")
        // wait for some byt_4_5.time to imitate some work
        delay(2000)
        println("${time()} Task-$taskId FINISHED by byt_4_5.Worker-$id for byt_4_5.Client-$clientId")
    }
}

object WorkersPool {
    private const val MAX_WORKERS = 2
    private val availableWorkers: MutableList<Worker> = MutableList(MAX_WORKERS) { Worker(it) }

    fun requestWorker(): Worker? {
        return availableWorkers.removeLastOrNull()
    }

    fun releaseWorker(worker: Worker) {
        availableWorkers.add(worker)
    }
}

class Client(private val id: Int) {
    suspend fun submitTask(taskId: Int) {
        println("${time()} Task-$taskId SUBMITTED by byt_4_5.Client-$id")
        var worker: Worker?
        do {
            worker = WorkersPool.requestWorker()
            // wait for some byt_4_5.time before requesting a worker again
            if (worker == null)
                delay(500)
        } while (worker == null)
        worker.performTask(id, taskId)
        WorkersPool.releaseWorker(worker)
    }
}