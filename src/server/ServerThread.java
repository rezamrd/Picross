/**Assignment 3
 * Author: Mohammadreza Moradijam - Arshdeep Kaur CST8221-302
 * 
 */
package a3.piccross.server;

import a3.piccross.helper.Logger;
import a3.piccross.helper.Protocol;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerThread implements Runnable {

    private final Socket socket;
    private final Logger logger;
    private int id;

    /**This is arg- constructor which gets/sets the socket and the logger
     * 
     * @param socket the socket
     * @param logger the logger
     * @param id the id
     */
    public ServerThread(Socket socket, Logger logger, int id) {
        this.socket = socket;
        this.logger = logger;
        this.id = id;
    }

    @Override
    public void run() {
        DataInputStream dataInputStream = null;
        DataOutputStream dataOutputStream = null;
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            return;
        }
        try {
            logger.addLog(id + " is connected");
            String currentGame = "";
            String line;
            dataOutputStream.writeUTF(id + ""); //first send client id
            while (true) {
                line = dataInputStream.readUTF();
                logger.addLog(line);
                String[] parseInput = Protocol.parseInput(line);

                if (parseInput.length == 0 || "P0".equals(parseInput[1])) {
                    GameServer.activeClientNumber.decrementAndGet();
                    logger.addLog(id + " leave!");
                    socket.close();
                    return;
                } else {
                    if ("P1".equals(parseInput[1])) {//Protocol 1
                        currentGame = parseInput[2];
                    } else if ("P2".equals(parseInput[1])) { //protocol 2
                        dataOutputStream.writeUTF(Protocol.generate(id + "", currentGame));
                        currentGame = "";
                    } else if ("P3".equals(parseInput[1])) {
                        GameServer.SCORES.add(parseInput[2] + ", points:" + parseInput[3] + ", time:" + parseInput[4]);
                        GameServer.showScores();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }


}
