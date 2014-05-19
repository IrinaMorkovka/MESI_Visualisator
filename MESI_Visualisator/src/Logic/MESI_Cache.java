/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Logic;

import Interfaces.I_MESI_Cache;
import Interfaces.MESI_States;
import java.util.ArrayList;

/**
 *
 * @author Vasiliy
 */
public class MESI_Cache implements I_MESI_Cache {

    private int Cache_Size;
    private ArrayList<String> Strings; 
    private ArrayList<MESI_States> States;
    
    public MESI_Cache(int Cache_Size)
    {
        this.Cache_Size = Cache_Size; 
        Strings = new ArrayList<>(Cache_Size);
        States  = new ArrayList<>(Cache_Size);
        for (int i=0; i < Cache_Size;i++)
         {
             Strings.add("");
             States.add(MESI_States.INVALID);
         }
    }
    
    @Override
    public ArrayList<MESI_States> GetStringStates()
    {
        return new ArrayList<>(States);
    }

    @Override
    public ArrayList<String> GetStrings()
    {
        return new ArrayList<>(Strings);
    }
    
}
