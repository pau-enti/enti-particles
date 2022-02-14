package com.example.particles.exercicieInterfaces

import java.lang.Thread.sleep
import java.util.*

abstract class Program(val name: String) {
    abstract fun compile(): Executable
}

interface Executable {
    fun execute(args: ArrayList<Any>)
}

class KernelProgram : Program("Kernel") {
    companion object {
        var iteration = 0
    }

    override fun compile(): Executable {
        val thread = Thread(0) {
            println("Executing kernel iteration ${iteration++}")
            sleep(1000L)
            CPU.run(KernelProgram().compile() as ThreadsPool)
        }

        return ThreadsPool(arrayListOf(thread))
    }
}

class KotlinProgram(name: String, val instructions: ArrayList<Int> /* sec */) : Program(name) {
    override fun compile(): Executable {
        val threads = instructions.map {
            Thread((0..10).random()) {
                println("Executing thread during $it ms")
                sleep(it * 1000L)
            }
        } as ArrayList

        return ThreadsPool(threads)
    }
}

class ThreadsPool(val threads: ArrayList<Thread>) : Executable {

    override fun execute(args: ArrayList<Any>) {
        threads.first().execute(args)
    }
}

class Thread(val priority: Int, val assemblerCode: () -> Unit) : Executable, Comparable<Thread> {

    override fun execute(args: ArrayList<Any>) {
        return assemblerCode.invoke()
    }

    override fun compareTo(other: Thread): Int {
        return priority - other.priority
    }
}


object CPU {
    private const val TOTAL_RAM_SIZE = 100
    private const val THREAD_SIZE = 10

    private var occupiedRAM = 0
    private val queue = LinkedList<Thread>()

    fun start() {
        schedulerLoop()
    }

    fun run(pool: ThreadsPool) {
        queue.addAll(pool.threads)
    }

    fun run(thread: Thread) {
        queue.add(thread)
    }

    @Throws(OutOfMemoryError::class)
    private fun schedulerLoop() {
        val thread = queue.poll()

        if (occupiedRAM + THREAD_SIZE >= TOTAL_RAM_SIZE) {
            throw OutOfMemoryError("RAM is full")
        }

        occupiedRAM += THREAD_SIZE

        println("RAM $occupiedRAM")
        thread?.execute(arrayListOf())

        if (queue.isNotEmpty())
            schedulerLoop()
        else
            println("CPU without tasks")
    }
}

fun main() {
    val program = KotlinProgram("Android", arrayListOf(1, 2, 3))
    val program2 = KernelProgram()

    CPU.run(program.compile() as ThreadsPool)
    CPU.start()
}



































