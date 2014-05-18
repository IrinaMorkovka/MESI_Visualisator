/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaces;

import java.util.ArrayList;

/**
 *
 * @author Vasiliy
 */

public interface I_MESI_Model {
    
    public void Initialize(int Cache_Num, int Memory_Size, int Cache_Size, int String_Size);
    public ArrayList<String> GetMemory();
    public ArrayList<I_MESI_Cache> GetCaches();
}


