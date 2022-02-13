package com.example.particles.exercicieInterfaces

import java.lang.Thread.sleep
import java.util.*

abstract class Program {
    abstract fun compile(): Executable
}

class KotlinProgram(val instructions: ArrayList<Long>) : Program() {
    override fun compile(): Executable {
        val threads = instructions.map {
            Thread((0..10).random()) {
                println("Executing thread during $it ms")
                sleep(it)
                arrayListOf()
            }
        } as ArrayList

        return ExecutableProgram(threads)
    }
}

class ExecutableProgram(val threads: ArrayList<Thread>) : Executable {
    @Throws(IllegalThreadStateException::class)
    override fun execute(): ArrayList<Executable> {
        val main = threads.maxByOrNull { it.priority }
        return main?.execute() ?: throw IllegalThreadStateException("No main thread found")
    }

    override fun compareTo(other: Thread): Int {
        return 1 // always proritaire
    }
}

open class Thread(val priority: Int, val assemblerCode: () -> ArrayList<Executable>) : Executable {
    override fun execute(): ArrayList<Executable> {
        return assemblerCode.invoke()
    }

    override fun compareTo(other: Thread): Int {
        return priority - other.priority
    }
}


interface Executable : Comparable<Thread> {
    fun execute(): ArrayList<Executable>
}


object CPU {
    private const val TOTAL_RAM_SIZE = 100
    private const val THREAD_SIZE = 10

    private var occupiedRAM = 0
    private val queue = PriorityQueue<Executable>()

    @Throws(OutOfMemoryError::class)
    fun run(executable: Executable) {
        if (occupiedRAM + THREAD_SIZE >= TOTAL_RAM_SIZE) {
            throw OutOfMemoryError("RAM is full")
        }

        occupiedRAM += THREAD_SIZE

        queue.add(executable)
    }

    private fun schedulerLoop() {
        val executable = queue.poll()

        print("Thread $executable.")
        executable?.execute()

        if (queue.isNotEmpty())
            schedulerLoop()
    }
}



































