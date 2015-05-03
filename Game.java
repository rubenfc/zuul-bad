import java.util.Stack;
import java.util.Random;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Player player;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        // Por ahora el peso se crea con un random
        Random rand = new Random();
        player = new Player(((rand.nextFloat()*20F) +20F));
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room entrada, pasillo, caverna, bifurcacion, habitacionTesoro, guarida, camaraOculta, salidaObstruida;

        // create the rooms
        entrada = new Room("la entrada de una mazmorra");
        pasillo = new Room("un pasillo de la mazmorra");
        caverna = new Room("una caverna rocosa");
        bifurcacion = new Room("el camino se divide en dos");
        habitacionTesoro = new Room("una habitacion del tesoro");
        guarida = new Room("la guarida del monstruo");
        camaraOculta = new Room ("en una sala peque�a, a la que entras por un peque�o boquete");
        salidaObstruida = new Room ("un pasillo que termina en una salida de la mazmorra, obstruida por un derrumbamiento");

        // A�adimos objetos a las localizaciones
        entrada.addItem(new Item("piedra", "una piedra enorme", 50F, true, false, false));
        entrada.addItem(new Item("antorcha", "una antorcha", 0.5F, true, false, false));
        caverna.addItem(new Item("cubo", "un cubo", 1.0F, true, false, false));
        caverna.addItem(new Item("transparente", "bebida transaparente", 0.5F, false, true, true));
        caverna.addItem(new Item("negra", "negra", 0.6F, false, true, false));
        caverna.addItem(new Item("verde", "verde", 0.4F, false, true, true));
        caverna.addItem(new Item("amarilla", "amarilla", 1.0F, false, true, false));
        caverna.addItem(new Item("azul", "azul", 0.8F, false, true, false));
        bifurcacion.addItem(new Item("piedra", "una piedra", 10.0F, false, false, false));
        habitacionTesoro.addItem(new Item("monedas", "unas monedas de oro", 1.0F, true, false, false));
        habitacionTesoro.addItem(new Item("pocion", "una poci�n", 0.5F, true, false, false));
        guarida.addItem(new Item("espada", "una espada", 2.0F, true, false, false));

        // initialise room exits (norte, este, sur, oeste, sureste, noroeste)
        entrada.setExit("este", pasillo);
        pasillo.setExit("este", bifurcacion);
        pasillo.setExit("sur", caverna);
        pasillo.setExit("oeste", entrada);
        caverna.setExit("este", pasillo);
        caverna.setExit("sureste", camaraOculta);
        bifurcacion.setExit("norte", habitacionTesoro);
        bifurcacion.setExit("este", guarida);
        bifurcacion.setExit("oeste", pasillo);
        habitacionTesoro.setExit("sur", bifurcacion);
        guarida.setExit("oeste", bifurcacion);
        camaraOculta.setExit("suroeste", salidaObstruida);
        camaraOculta.setExit("noroeste", caverna);
        salidaObstruida.setExit("noroeste", camaraOculta);

        player.setRoom(entrada);  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Gracias por jugar, adios");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Bienvenido a World of Zuul!");
        System.out.println("World of Zuul es un nuevo y muy aburrido juego de aventuras");
        System.out.println("Escribe '" + Option.AYUDA.getCommand() +"' para ver la ayuda");
        System.out.println();
        player.look();
        System.out.println();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        Option commandWord = command.getCommandWord();
        switch(commandWord){
            case AYUDA:
            printHelp();
            break;
            case IR:
            goRoom(command);
            break;
            case TERMINAR:
            wantToQuit = quit(command);
            break;
            case EXAMINAR:
            player.look();
            break;
            case COMER:
            player.eat();
            break;
            case VOLVER:
            player.goBack();
            break;
            case COGER:
            take(command);
            break;
            case SOLTAR:
            drop(command);
            break;        
            case OBJETOS:
            player.showInventory();
            break;
            case BEBER:
            beber (command);
            break;          
            case DESCONOCIDO:  
            System.out.println("No entiendo las instrucciones");
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        parser.printValidCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(gameOver()){
            System.out.println("lo siento has perdido");
            return;
        }
        else if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("�A donde quieres ir?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        player.goRoom(direction);
    }

    /** 
     * Try to take an item.
     */
    private void take(Command command) 
    {
        if(gameOver()){
            System.out.println("lo siento has perdido");
            return;
        }
        else if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to take...
            System.out.println("�Que quieres coger?");
            return;
        }

        String objeto = command.getSecondWord();

        // Intenta coger el objeto
        player.addItem(objeto);
    }

    /** 
     * Try to drop an item.
     */
    private void drop(Command command) 
    {
        if(gameOver()){
            System.out.println("lo siento has perdido");
            return;
        }
        else if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to drop...
            System.out.println("�Que quieres soltar");
            return;
        }

        String objeto = command.getSecondWord();

        // Intenta soltar un objeto
        player.dropItem(objeto);
    }
    
    private void beber(Command command){
        if(gameOver()){
            System.out.println("lo siento has perdido");
            return;
        }
        else if(!command.hasSecondWord()){
         System.out.println("que quieres beber");
         return;
        }
        String objeto = command.getSecondWord();
        
        player.beber(objeto);
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("�Salir?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    private boolean gameOver(){
        return player.haPerdido();
    }
}