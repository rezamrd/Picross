/**Assignment 3
 * Author: Mohammadreza Moradijam - Arshdeep Kaur CST8221-302
 * 
 */
package a3.piccross.helper;

public class Protocol {

    public static String seperator = "#";
    
    /**This is a separator method
     * 
     * @param input given string
     * @return separated(#) string 
     */
    public static String[] parseInput(String input){
        if (input == null)
                return new String[0];
        return input.split(seperator);
    }

 /**This method uses stringBuilder to format the given string(separator#)
 * 
 * @param inputs given string
 * @return string
 */
    public static String generate(String ...inputs){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < inputs.length; i++) {
            sb.append(inputs[i]);
            if (inputs.length-1 != i){
                sb.append(seperator);
            }
        }

        return sb.toString();
    }


}
