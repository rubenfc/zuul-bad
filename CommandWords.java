import java.util.HashMap;
/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class CommandWords
{
    // a constant array that holds all valid command words
    //     private static final String[] validCommands = {
    //             "ir", "terminar", "ayuda", "examinar", "comer", "volver", "coger", "soltar", "objetos"
    //         };
    private HashMap<String,Option> comandos;
    private Option option;
    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        // nothing to do at the moment...
        comandos = new HashMap<>();
        comandos.put("go",Option.GO);
        comandos.put("quit",Option.QUIT);
        comandos.put("help",Option.HELP);
        comandos.put("look",Option.LOOK);
        comandos.put("back",Option.BACK);
        comandos.put("take",Option.TAKE);
        comandos.put("drop",Option.DROP);
        comandos.put("item",Option.ITEM);
        comandos.put("unknown",Option.UNKNOWN);
    }

    /**
     * Check whether a given String is a valid command word. 
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public boolean isCommand(String aString)
    {
        //         for(int i = 0; i < validCommands.length; i++) {
        //             if(validCommands[i].equals(aString))
        //                 return true;
        //         }
        //         // if we get here, the string was not found in the commands
        //         return false;
        return comandos.containsKey(aString);
    }

    /**
     * Print all valid commands to System.out
     */
    public void showAll()
    {
        String commands = "Los comandos son: \n";
        //         for(int i = 0; i < validCommands.length; i++){
        //             commands = commands + validCommands[i] + " ";
        //         }
        //         System.out.println(commands);
        for( String comands : comandos.keySet())
        {
            System.out.println(commands + " ");
        }
    }

    /**
     * Return the object Option associated with a word.
     * @param commandWord The word to look up (as a string).
     * @return the object Option correspondng to the paramater commandWord, or the object Option.UNKNOWN
     *         if it is not a valid command word
     */
    public Option getCommandWord(String commandWord)
    {
        Option option = null;
        if( comandos.get(commandWord) == null)
        {
            option = Option.UNKNOWN;
        }
        else
        {
            option = comandos.get(commandWord);
        }
        return option;
    }
}
