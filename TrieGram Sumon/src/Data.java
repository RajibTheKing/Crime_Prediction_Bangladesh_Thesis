
import java.util.Vector;

public class Data 
{
    Vector<String> prev;
    Vector<String> next;
    Data()
    {
        prev = new Vector<String>();
        next = new Vector<String>();
    }
    
    
    void AddNewValue(String s, boolean flag)
    {
        if(flag) prev.add(s);
        else next.add(s);
    }
    
}
