
/**
 * Esta clase representa los objetos que se pueden encontrar en el juego.
 * Todos los objetos estan definidos por una descripcion y un peso.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Item
{
    // El nombre del objeto;
    private String nombreObj;
    // Descripcion del objeto
    private String descripcionObj;
    // Peso del objeto
    private float peso;
    // Indica si el objeto puede o no ser cogido por el jugador
    private boolean puedeCogerse;
    //indica si puede beberse
    private boolean puedeBeberse;
    //indica en caso de que pueda beberse, si es venenosos
    private boolean esVenenoso;

    /**
     * Constructor de items. Crea un objeto item con una descripción y un peso dados.
     */
    public Item( String nombreObj, String desc, float peso, boolean puedeCogerse, boolean puedeBeberse, boolean esVenenoso)
    {
        this.nombreObj = nombreObj;
        this.descripcionObj = desc;
        this.peso = peso;
        this.puedeCogerse = puedeCogerse;
        this.puedeBeberse = puedeBeberse;
        this.esVenenoso = esVenenoso;

    }

    /**
     * Devuelve la descripción del objeto.
     * @return La descripción del objeto.
     */
    public String getDescripcionObj()
    {
        return descripcionObj;
    }

    /**
     * Devuelve el peso del objeto.
     * @return El peso del objeto.
     */
    public float getPeso()
    {
        return peso;
    }
    
    /**
     * Devuelve el nombre del objeto.
     * @return El nombre del objeto.
     */
    public String getNombreObj()
    {
        return nombreObj;
    }
    
    /**
     * Devuelve si el objeto puede o no cogerse
     * @return Si el objeto puede o no cogerse
     */
    public boolean getPuedeCogerse()
    {
        return puedeCogerse;
    }

    /**
     * Devuelve una descripción con toda la información del item.
     * @return La información del objeto.
     */
    public String getLongDescription()
    {
        String info = descripcionObj + " que pesa " + peso;
        return info;
    }
    
    public boolean puedeBeberse(){
        return puedeBeberse;
    }
    
    public boolean esVenenoso(){
        return esVenenoso;
    }
}