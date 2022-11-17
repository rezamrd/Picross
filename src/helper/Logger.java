/**Assignment 3
 * Author: Mohammadreza Moradijam - Arshdeep Kaur CST8221-302
 * 
 */

package a3.piccross.helper;

import javax.swing.*;

public class Logger {

    private JTextArea textArea;
    /**non-arg constructor
     * 
     */
    public Logger() {

    }
/**This method sets the text are
 * 
 * @param textArea 
 */
    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }
/**This method updates the text area(log)
 * 
 * @param log 
 */
    public void addLog(String log){
        textArea.append(log);
        textArea.append("\n");
    }

}
