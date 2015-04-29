
/**
 * Write a description of class Test here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Test
{

    public static long currentTimeMillis(){
        for(int i = 0; i < 1000001; i++)
        {
            System.out.println(i);
        }
        return System.currentTimeMillis();
    }
    public long time(){
        return currentTimeMillis() / 1000;
    }
}
