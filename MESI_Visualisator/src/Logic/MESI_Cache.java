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
    private ArrayList<Integer> MemoryStringNumbers; 
    private ArrayList<String> Strings; 
    private ArrayList<MESI_States> States;
    private MESI_Model Parent;
    
    public MESI_Cache(int Cache_Size, MESI_Model Parent)
    {
        this.Parent = Parent;
        this.Cache_Size = Cache_Size; 
        Strings = new ArrayList<>(Cache_Size);
        States  = new ArrayList<>(Cache_Size);
        for (int i=0; i < Cache_Size;i++)
         {
             Strings.add("");
             States.add(MESI_States.INVALID);
             MemoryStringNumbers.add(-1);
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
    
    void Read(int Mem_String_Num)
    {
        int CacheStringNum = Mem_String_Num % Cache_Size;
        if ((States.get(CacheStringNum) != MESI_States.INVALID)
                && (MemoryStringNumbers.get(CacheStringNum) == Mem_String_Num))
        {
            return;
        }
        switch (States.get(CacheStringNum))
        {
            case MODIFIED:
            {
                this.Drop(CacheStringNum);
            }
            case INVALID:
            case EXCLUSIVE:
            case SHARED:
            {

                boolean Shared = Parent.IsAlreadyCached(Mem_String_Num);
                Strings.set(CacheStringNum, Parent.GetFromMemory(Mem_String_Num));
                MemoryStringNumbers.set(CacheStringNum, Mem_String_Num);
                if (Shared)
                {
                    States.set(CacheStringNum, MESI_States.SHARED);
                } else
                {
                    States.set(CacheStringNum, MESI_States.EXCLUSIVE);
                }
            }
        }
    }
    
    void Drop(int CacheStringNum)
    {
        if (States.get(CacheStringNum) == MESI_States.MODIFIED)
            Parent.SetToMemory(MemoryStringNumbers.get(CacheStringNum), Strings.get(CacheStringNum));
        Strings.set(CacheStringNum, "");
        States.set(CacheStringNum, MESI_States.INVALID);
        MemoryStringNumbers.set(CacheStringNum, -1);
    }
    
    void Write(int Mem_String_Num, String New)
    {
        int CacheStringNum = Mem_String_Num % Cache_Size;
        switch (States.get(CacheStringNum))
        {
            case MODIFIED:
            {
                 if ( (MemoryStringNumbers.get(CacheStringNum) == Mem_String_Num))
                 {
                     Strings.set(CacheStringNum, New);
                     return;
                 }
                 this.Drop(CacheStringNum);                 
            }
            case INVALID:
            case SHARED: 
            case EXCLUSIVE:            
            {
                if ((States.get(CacheStringNum) != MESI_States.EXCLUSIVE) 
                        ||(MemoryStringNumbers.get(CacheStringNum) != Mem_String_Num ))
                    Parent.Invalidate(Mem_String_Num);
                States.set(CacheStringNum, MESI_States.MODIFIED);
                Strings.set(CacheStringNum, Parent.GetFromMemory(Mem_String_Num));
                MemoryStringNumbers.set(CacheStringNum, Mem_String_Num);
            }
        }
    }
    
    void Invalidate(int Mem_String_Num)
    {
         int CacheStringNum = Mem_String_Num % Cache_Size;
         if (MemoryStringNumbers.get(CacheStringNum) == Mem_String_Num )
             Drop(CacheStringNum);
    }
    
    boolean RequestToShare(int Mem_String_Num)
    {
        int CacheStringNum = Mem_String_Num % Cache_Size;
        if (MemoryStringNumbers.get(CacheStringNum) == Mem_String_Num)
        {
            switch (States.get(CacheStringNum))
            {
                
                case INVALID:
                    return false;
                case MODIFIED: {
                    Parent.SetToMemory(MemoryStringNumbers.get(CacheStringNum), Strings.get(CacheStringNum));
                }
                case EXCLUSIVE:
                    States.set(CacheStringNum, MESI_States.SHARED);
                case SHARED:
                    return true;
            }
        }
        return false;
    }
}
