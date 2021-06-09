package fr.gems.lejos

import java.io.*
import java.net.Socket
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

class WifiControl {
    var socket: Socket? = null

    private lateinit var listenThread: Future<*>
    private lateinit  var sendThread: Future<*>
    private val listenExecService: ExecutorService = Executors.newSingleThreadExecutor()
    private val sendExecService: ExecutorService = Executors.newSingleThreadExecutor()

    var `in`: BufferedReader? = null
    var out: BufferedWriter? = null
    final val ip = "10.0.1.1"
    fun ouvrir() {
        listenThread = listenExecService.submit {
            val port = 80
            try {
                socket = Socket(ip, port)
                `in` = BufferedReader(InputStreamReader(socket!!.getInputStream()))
                out = BufferedWriter(OutputStreamWriter(socket!!.getOutputStream()))
                println(String.format("Connecté sur %s, port %d", ip, port))
                while (!socket!!.isClosed) {
                    var s: String? = ""
                    println("Attente d'un message...")
                    if (`in`!!.readLine().also { s = it } != null) println(String.format("Message reçu : %s", s)) else socket!!.close()
                }
                println("Socket fermé")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun fermer() {
        try {
            if (!socket!!.isClosed) socket!!.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun envoyerMessage(message: String?) {
        sendThread = sendExecService.submit {
            try {
                println(String.format("Message envoyé : %s", message))
                out!!.write(String.format("%s\n", message))
                out!!.flush()
            } catch (e: Exception) {
                println(String.format("Impossible d'envoyer un message : %s", e.message))
            }
        }
    }
}