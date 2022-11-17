/**Assignment 3
 * Author: Mohammadreza Moradijam - Arshdeep Kaur CST8221-302
 * 
 * 
 */

package a3.piccross.server;

import a3.piccross.helper.Logger;

import javax.swing.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class GameServer {

    public static List<String> SCORES = Collections.synchronizedList(new ArrayList<>());
    public static AtomicBoolean isRunning = new AtomicBoolean(false);
    private final Logger logger = new Logger();
    private AtomicInteger clientId = new AtomicInteger(0);
    public static AtomicInteger activeClientNumber = new AtomicInteger(0);

    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServerMainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        GameServer gameServer = new GameServer();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ServerMainForm serverMainForm = new ServerMainForm(gameServer);
                serverMainForm.setVisible(true);
            }
        });
    }
/**This method prints out the score and the message(score) dialog 
 * 
 */
    public static void showScores() {
        StringBuilder sb = new StringBuilder();
        sb.append("Game Results:\n");
        Iterator<String> iterator = SCORES.iterator();
        int i = 1;
        while (iterator.hasNext()) {
            String next = iterator.next();
            sb.append("Player[").append(i++).append("]: ").append(next).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Message", JOptionPane.INFORMATION_MESSAGE);

    }
    
  /**Getter for the boolean isrunning
   * 
   * @return true or false
   */  
    public static boolean isRunning() {
        return isRunning.get();
    }

    public void execute(int port) {
        isRunning.set(true);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(port);
                    while (GameServer.isRunning()) {
                        try {
                            Socket client = serverSocket.accept();
                            if (!GameServer.isRunning()) {
                                DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
                                dataOutputStream.writeUTF("X");
                                logger.addLog("Server Closed!");
                                return;
                            } else {
                                GameServer.activeClientNumber.incrementAndGet();
                                Thread thread = new Thread(new ServerThread(client, logger, clientId.incrementAndGet()));
                                thread.start();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            logger.addLog("Connection Error!");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.addLog("Server Error!");
                }
            }
        });
        thread.start();
    }
/**This method stops the server
 * 
 */
    public void end() {
        if (activeClientNumber.get() == 0) {
            System.exit(0);
        } else {
            isRunning.set(false);
            logger.addLog("Server is shutting down.....");
        }
    }
/** Getter for the logger
 * 
 * @return logger 
 */
    public Logger getLogger() {
        return logger;
    }


}
