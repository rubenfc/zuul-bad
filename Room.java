import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */
public class Room 
{
    private String description;
    private HashMap<String, Room> salidas;
    private ArrayList<Item> objetos;
    private boolean cerrada;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description, boolean cerrada) 
    {
        this.description = description;
        salidas = new HashMap<String, Room>();
        objetos = new ArrayList<Item>();
        this.cerrada = cerrada;
    }

    /**
     * A�ade una salida a la habitacion. Cada salida debe componerse de una salida y una habitacion.
     * @param dir Nombre de la salida.
     * @param room Habitaci�n que se encuentra tras esa salida.
     */
    public void setExit(String dir, Room room)
    {
        salidas.put(dir, room);
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Devuelve la habitaci�n que se encuentra en la direccion indicada como parametro
     * @param dir Direcci�n por la que quiere salir
     * @return la habitaci�n que se encuentra en esa direcci�n, o null si no hay ninguna
     */
    public Room getExit(String dir)
    {
        Room salida = null;
        salida = salidas.get(dir);
        return salida;
    }

    /**
     * Return a description of the room's exits.
     * For example: "Exits: north east west"
     *
     * @ return A description of the available exits.
     */
    public String getExitString()
    {
        String descripcion = "";
        // Itera sobre el hashMap, si la habitacion no es null
        // guarda la key de la direcci�n
        Iterator it = salidas.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry<String, Room> pair = (Map.Entry)it.next();
            Room habitacion = pair.getValue();
            if(habitacion != null)
            {
                descripcion = descripcion + pair.getKey() + " ";
            }
        }
        return descripcion;
    }

    /**
     * Return a long description of this room, of the form:
     *     You are in the 'name of room'
     *     Exits: north west southwest
     * @return A description of the room, including exits.
     */
    public String getLongDescription()
    {
        String descr = "";
        descr = "\nEstas en " + description + "\nSalidas: " + getExitString();
        // Si hay algun objeto en la habitaci�n, lo incluye en la descripci�n
        if (objetos.size() > 0)
        {
            descr += "\nVes los siguientes objetos:";
            for(int i = 0; i < objetos.size(); i++)
            {
                descr += "\n- " + objetos.get(i).getLongDescription();
            }
        }
        else
        {
            descr += "\nNo ves nada aqui";
        }
        return descr;
    }

    /**
     * A�ade un objeto a la localizaci�n
     * @param objeto Objeto a a�adir a la localizaci�n
     */
    public void addItem(Item objeto)
    {
        objetos.add(objeto);
    }
    
    /**
     * Busca un objeto en la localizaci�n. Si existe lo devuelve,
     * sino devuelve null.
     * @return El objeto si contiene, null sino.
     */
    public Item search(String nombre)
    {
        boolean find = false;
        int index = 0;
        Item objeto = null;
        // Busca el objeto en la localizaci�n
        while((index < objetos.size()) & (!find))
        {
            if(nombre.equals(objetos.get(index).getNombreObj()))
            {
                objeto = objetos.get(index);
                find = true;
            }
            index++;
        }
        return objeto;
    }
    
    /**
     * Elimina un objeto de la localizaci�n
     */
    public void remove(Item objeto)
    {
        objetos.remove(objeto);
    }
    
    public boolean cerrada(){
        return cerrada;
    }
 
}