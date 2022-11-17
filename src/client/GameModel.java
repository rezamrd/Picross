/**Assignment 3
 * Author: Mohammadreza Moradijam - Arshdeep Kaur CST8221-302
 * 
 * This is the model class which has the game logic.
 */

package a3.piccross.client;

public class GameModel {

    public static final int BOARD_SIZE = 5; //Dimension
    private int[][] gameBoard = new int[BOARD_SIZE][BOARD_SIZE];//2-d array for the game board

/** This method generates a new game using random
 * 
 */
    public void newGame() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                gameBoard[i][j] = (int) Math.round(Math.random());
            }
        }
    }

/**This method generates using string builder the game solution(string) and returns it 
 * 
 * @return sb.toString game solution
 */
    public String gameToString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                sb.append(gameBoard[i][j]);
            }
            if (i + 1 != BOARD_SIZE) {
                sb.append(",");

            }
        }

        return sb.toString();
    }

/**Getter for the game board
 * 
 * @return game board
 */
    public int[][] getGameBoard() {
        return gameBoard;
    }
}
