/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaces;

import java.util.ArrayList;

/**
 *
 * @author Irina
 */

public interface I_MESI_Model {
    
    public void Initialize(int Cache_Num, int Memory_Size, int Cache_Size, int String_Size);
    public ArrayList<String> GetMemory();
    public ArrayList<I_MESI_Cache> GetCaches();
    public void ReadToCache(int Cache_Num, int Mem_String_Num);
    public void WriteToCache(int Cache_Num, int Mem_String_Num, String New);
    public void DropFromCache(int Cache_Num, int Mem_String_Num);
    public int  GetCacheNum();
    public int  GetMemSize();
    public int  GetCahceSize();
    public int  GetStringSize();
    public ArrayList<MESI_Operation_Descriptor> GetOperations();
}


