/**Assignment 3
 * Author: Mohammadreza Moradijam - Arshdeep Kaur CST8221-302
 * 
 */

package a3.piccross.client;

import a3.piccross.helper.Logger; //importing the logger class
import a3.piccross.helper.Protocol; //importing the protocol class

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class GameClient {

    private final Logger logger = new Logger();
    private boolean isRunning = false;

    private Socket clientSocket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;

    private String user;
    private String id;

 /** The main method
 * 
 * @param args 
 */
    public static void main(String[] args) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientMainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        GameClient gameClient = new GameClient();
        GameController gameController = new GameController(gameClient);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ClientMainForm mainForm = new ClientMainForm(gameController);
                mainForm.setVisible(true);
            }
        });
    }

    /** This method assembles the connection
     * 
     * @param user user id
     * @param server server
     * @param port port number
     */
    public void connect(String user, String server, int port) {
        try {
            this.user = user;
            clientSocket = new Socket(server, port);
            dataInputStream = new DataInputStream(clientSocket.getInputStream());
            id = dataInputStream.readUTF();

            if ("X".equals(id)) {
                JOptionPane.showMessageDialog(null, "Server Closed!", "Error", JOptionPane.ERROR_MESSAGE);
                clientSocket.close();
                dataInputStream.close();
            } else {
                dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                isRunning = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
/**This method ends the connection
 * 
 */
    public void end() {
        try {
            dataOutputStream.writeUTF(Protocol.generate(id, "P0"));
            dataOutputStream.flush();
            clientSocket.close();
            dataOutputStream.close();
            dataInputStream.close();
            isRunning = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/**This method sends the game and the user id
 * 
 * @param game 
 */
    public void sendGameToServer(String game) {
        String p1 = Protocol.generate(id, "P1", game);
        logger.addLog("Sending: " + p1);
        try {
            dataOutputStream.writeUTF(p1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**This method sends the game to the server
     * 
     * @return string
     */
    public String getGameOnServer() {
        try {
            String p2 = Protocol.generate(id, "P2");
            logger.addLog("Sending: " + p2);
            dataOutputStream.writeUTF(p2);
            String input = dataInputStream.readUTF();
            String[] parseInput = Protocol.parseInput(input);
            return parseInput[1];
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
/**This method sends the points and the time to the server
 * 
 * @param point game points
 * @param time game timer
 */
    public void sendScoreToServer(String point, String time) {
        try {
            String p3 = Protocol.generate(id, "P3", user, point, time);
            logger.addLog("Sending: " + p3);
            dataOutputStream.writeUTF(p3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/**Getter for the logger
 * 
 * @return logger
 */
    public Logger getLogger() {
        return logger;
    }
  
  /**Getter for the boolean isrunning
   * 
   * @return true or false
   */  
    public boolean isRunning() {
        return isRunning;
    }
}

