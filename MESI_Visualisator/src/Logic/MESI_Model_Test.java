/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Logic;

import Interfaces.I_MESI_Cache;
import Interfaces.I_MESI_Model;
import java.util.ArrayList;

/**
 *
 * @author Vasiliy
 */
public class MESI_Model_Test implements I_MESI_Model  {

    @Override
    public void Initialize(int Cache_Num, int Memory_Size, int Cache_Size, int String_Size)
    {
        System.out.println("Создана модель с " + String.valueOf(Cache_Num) +
                " кэшами, размером памяти "+ String.valueOf(Memory_Size) + 
                " строк, размером кэша "+ String.valueOf(Cache_Size) +
                " строк и длиной строки "+ String.valueOf(String_Size) );
    }

    @Override
    public ArrayList<String> GetMemory()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<I_MESI_Cache> GetCaches()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ReadToCache(int Cache_Num, int Mem_String_Num)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void WriteToCache(int Cache_Num, int Mem_String_Num, String New)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void DropFromCache(int Cache_Num, int Cache_String_Num)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
