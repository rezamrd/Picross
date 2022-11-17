/**Assignment 3
 * Author: Mohammadreza Moradijam - Arshdeep Kaur CST8221-302
 * 
 * This is the controller class which interacts with the user interaction
 */

package a3.piccross.client;

import javax.swing.*;

public class GameController {

    private GameClient gameClient; //Object of the gameClinet class
    private GameModel gameModel;//Object of the gameModel class

    /**This is the arg-constructor
     * 
     * @param gameClient Object of the gameClinet class
     */
    public GameController(GameClient gameClient) {
        this.gameClient = gameClient;//Setting gameClient
        this.gameModel = new GameModel();//Setting gameModel
    }

    /** This method checks/assembles the connection
     * 
     * @param user user id
     * @param server server
     * @param port port number
     * @return true if connected and false if not
     */
    public boolean connect(String user, String server, int port) {
        if (!gameClient.isRunning()) {
            gameClient.connect(user, server, port);
            return true;
        }
        return false;
    }

    /**This method updates/writes the text area
     * 
     * @param textArea 
     */
    public void setLoggerTA(JTextArea textArea) {
        gameClient.getLogger().setTextArea(textArea);
    }

/** This method checks/destroys the connection
 * 
 * @return true if unconnected and false if connected
 */
    public boolean end() {
        if (gameClient.isRunning()) {
            gameClient.end();
            return true;
        }
        return false;
    }

    /**This method calls the gameModel newGame method and it sends the game string to gameTostring in the gameModel class
     * 
     */
    public void newGame() {
        gameModel.newGame();
        gameClient.getLogger().addLog("New Game: " + gameModel.gameToString());
    }
    
/**This method sends the game string from Model class to client
 * 
 */
    public void send() {
        gameClient.sendGameToServer(gameModel.gameToString());
    }
    
/**This method runs the game when the play button is clicked
 * 
 */
    public void play() {
        GameController gameController = this;

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ClientGameForm clientGameForm = new ClientGameForm(gameController, gameModel.getGameBoard());
                clientGameForm.setVisible(true);
            }
        });

    }
    
/**This method sends the score to the server when the game is won
 * 
 * @param point the points
 * @param time the time
 */
    public void win(String point, String time) {
        gameClient.sendScoreToServer(point, time);
    }


}
