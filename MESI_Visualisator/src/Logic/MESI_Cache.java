/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Logic;

import Interfaces.I_MESI_Cache;
import Interfaces.MESI_Operation_Descriptor;
import Interfaces.MESI_States;
import Interfaces.MESI_Transitions;
import java.util.ArrayList;

/**
 *
 * @author Vasiliy
 */
public class MESI_Cache implements I_MESI_Cache {

    private int Cache_Size;
    private int Cache_Num;
    private ArrayList<Integer> MemoryStringNumbers; 
    private ArrayList<String> Strings; 
    private ArrayList<MESI_States> States;
    private MESI_Model Parent;
    
    public MESI_Cache(int Cache_Size, MESI_Model Parent, int Cache_Num)
    {
        this.Parent = Parent;
        this.Cache_Size = Cache_Size; 
        this.Cache_Num = Cache_Num;
        Strings = new ArrayList<>(Cache_Size);
        States  = new ArrayList<>(Cache_Size);
        MemoryStringNumbers  = new ArrayList<>(Cache_Size);
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
    
    public void Read(int Mem_String_Num, ArrayList<MESI_Operation_Descriptor> Operations)
    {
        int CacheStringNum = Mem_String_Num % Cache_Size;
        if (MemoryStringNumbers.get(CacheStringNum) == Mem_String_Num)
        {
            switch (States.get(CacheStringNum))
            {
                 case EXCLUSIVE:
                     Operations.add(new MESI_Operation_Descriptor(
                     MESI_Transitions.EXCLUSIVE_TO_EXCLUSIVE_READ, Cache_Num,
                             Cache_Num,Mem_String_Num));
                     return;
                 case SHARED:
                     Operations.add(new MESI_Operation_Descriptor(
                     MESI_Transitions.SHARED_TO_SHARED_READ, Cache_Num,
                             Cache_Num,Mem_String_Num));
                     return;
                 case MODIFIED:
                     Operations.add(new MESI_Operation_Descriptor(
                     MESI_Transitions.MODIFIED_TO_MODIFIED_READ, Cache_Num,
                             Cache_Num,Mem_String_Num));
                     return;
            }
            return;
        }
        this.Drop(CacheStringNum, Operations,Cache_Num);
        Operations.add(new MESI_Operation_Descriptor(
                MESI_Transitions.READ_REQUEST, Cache_Num,
                Cache_Num, Mem_String_Num));
        boolean Shared = Parent.IsAlreadyCached(Mem_String_Num,Cache_Num);
        Operations.add(new MESI_Operation_Descriptor(
                MESI_Transitions.READING_FROM_MEMORY, Cache_Num,
                Cache_Num, Mem_String_Num));
        Strings.set(CacheStringNum, Parent.GetFromMemory(Mem_String_Num));
        MemoryStringNumbers.set(CacheStringNum, Mem_String_Num);
        if (Shared)
        {
            Operations.add(new MESI_Operation_Descriptor(
                    MESI_Transitions.INVALID_TO_SHARED, Cache_Num,
                    Cache_Num, Mem_String_Num));
            States.set(CacheStringNum, MESI_States.SHARED);
        } else
        {
            Operations.add(new MESI_Operation_Descriptor(
                    MESI_Transitions.INVALID_TO_EXCLUSIVE, Cache_Num,
                    Cache_Num, Mem_String_Num));
            States.set(CacheStringNum, MESI_States.EXCLUSIVE);
        }
    }

    public void WriteDrop(int CacheStringNum, ArrayList<MESI_Operation_Descriptor> Operations, int Primary_Cache_Num)
    {
        if (States.get(CacheStringNum) == MESI_States.MODIFIED)
        {
            Parent.SetToMemory(MemoryStringNumbers.get(CacheStringNum), Strings.get(CacheStringNum));
            int Num = MemoryStringNumbers.get(CacheStringNum);
            this.Drop(CacheStringNum, Operations, Primary_Cache_Num);            
            Operations.add(new MESI_Operation_Descriptor(
                    MESI_Transitions.WRITE_TO_MEMORY, Primary_Cache_Num,
                    Cache_Num, Num));
        }
        else 
            this.Drop(CacheStringNum, Operations, Primary_Cache_Num);
    }
    
    public void Drop(int CacheStringNum, ArrayList<MESI_Operation_Descriptor> Operations, int Primary_Cache_Num)
    {
        switch (States.get(CacheStringNum))
        {
            case EXCLUSIVE:
                Operations.add(new MESI_Operation_Descriptor(
                            MESI_Transitions.EXCLUSIVE_TO_INVALID, Primary_Cache_Num,
                            Cache_Num, MemoryStringNumbers.get(CacheStringNum)));
                break;
            case SHARED:
                 Operations.add(new MESI_Operation_Descriptor(
                            MESI_Transitions.SHARED_TO_INVALID, Primary_Cache_Num,
                            Cache_Num, MemoryStringNumbers.get(CacheStringNum)));
                break;
            case MODIFIED:
                Operations.add(new MESI_Operation_Descriptor(
                            MESI_Transitions.MODIFIED_TO_INVALID, Primary_Cache_Num,
                            Cache_Num, MemoryStringNumbers.get(CacheStringNum)));
        }
        Strings.set(CacheStringNum, "");
        States.set(CacheStringNum, MESI_States.INVALID);
        MemoryStringNumbers.set(CacheStringNum, -1);
    }
    
    public  void Write(int Mem_String_Num, String New, ArrayList<MESI_Operation_Descriptor> Operations)
    {
        int CacheStringNum = Mem_String_Num % Cache_Size;
        if (MemoryStringNumbers.get(CacheStringNum) == Mem_String_Num)
        {
            switch (States.get(CacheStringNum))
            {
                case MODIFIED:
                    Operations.add(new MESI_Operation_Descriptor(
                            MESI_Transitions.MODIFIED_TO_MODIFIED_WRITE, Cache_Num,
                            Cache_Num, Mem_String_Num));
                    Strings.set(CacheStringNum, New);
                    return;
                case EXCLUSIVE:
                    Operations.add(new MESI_Operation_Descriptor(
                            MESI_Transitions.EXCLUSIVE_TO_MODIFIED, Cache_Num,
                            Cache_Num, MemoryStringNumbers.get(CacheStringNum)));
                    break;
                case SHARED:
                    Operations.add(new MESI_Operation_Descriptor(
                            MESI_Transitions.INVALIDATE_REQUSET, Cache_Num,
                            Cache_Num, MemoryStringNumbers.get(CacheStringNum)));
                    Parent.Invalidate(Mem_String_Num,Cache_Num);
                    Operations.add(new MESI_Operation_Descriptor(
                            MESI_Transitions.SHARED_TO_MODIFIED, Cache_Num,
                            Cache_Num, MemoryStringNumbers.get(CacheStringNum)));
                    break;
            }
        } else
        {
            this.Drop(CacheStringNum, Operations, Cache_Num);
            Operations.add(new MESI_Operation_Descriptor(
                    MESI_Transitions.INVALIDATE_REQUSET, Cache_Num,
                    Cache_Num, Mem_String_Num));
            Parent.Invalidate(Mem_String_Num,Cache_Num);
            Operations.add(new MESI_Operation_Descriptor(
                    MESI_Transitions.INVALID_TO_MODIFIED, Cache_Num,
                    Cache_Num, Mem_String_Num));
        }
        States.set(CacheStringNum, MESI_States.MODIFIED);
        Strings.set(CacheStringNum, New);
        MemoryStringNumbers.set(CacheStringNum, Mem_String_Num);
    }
    
    public void Invalidate(int Mem_String_Num, ArrayList<MESI_Operation_Descriptor> Operations, int Primary_Cache_Num)
    {
         int CacheStringNum = Mem_String_Num % Cache_Size;
         if (MemoryStringNumbers.get(CacheStringNum) == Mem_String_Num )
             this.Drop(CacheStringNum,Operations,Primary_Cache_Num);
    }
    
    public boolean RequestToShare(int Mem_String_Num,
            ArrayList<MESI_Operation_Descriptor> Operations, int Primary_Cache_Num)
    {
        int CacheStringNum = Mem_String_Num % Cache_Size;
        if (MemoryStringNumbers.get(CacheStringNum) == Mem_String_Num)
        {
            switch (States.get(CacheStringNum))
            {
                
                case INVALID:
                    return false;
                case MODIFIED: {
                    Operations.add(new MESI_Operation_Descriptor(
                    MESI_Transitions.MODIFIED_TO_SHARED, Primary_Cache_Num,
                    Cache_Num, Mem_String_Num));
                    Operations.add(new MESI_Operation_Descriptor(
                    MESI_Transitions.WRITE_TO_MEMORY, Primary_Cache_Num,
                    Cache_Num, Mem_String_Num));
                    Parent.SetToMemory(MemoryStringNumbers.get(CacheStringNum), Strings.get(CacheStringNum));                   
                    States.set(CacheStringNum, MESI_States.SHARED);
                    return true;
                }
                case EXCLUSIVE:
                    Operations.add(new MESI_Operation_Descriptor(
                    MESI_Transitions.EXCLUSIVE_TO_SHARED, Primary_Cache_Num,
                    Cache_Num, Mem_String_Num));
                    States.set(CacheStringNum, MESI_States.SHARED);
                    return true;
                case SHARED:
                    Operations.add(new MESI_Operation_Descriptor(
                    MESI_Transitions.SHARED_TO_SHARED, Primary_Cache_Num,
                    Cache_Num, Mem_String_Num));
                    return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<Integer> GetMemStringsNums()
    {
        return new ArrayList<>(this.MemoryStringNumbers);
    }

    @Override
    public int GetSize()
    {
       return this.Cache_Size;
    }
}
