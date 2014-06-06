/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Logic;

import Interfaces.I_MESI_Cache;
import Interfaces.I_MESI_Model;
import Interfaces.MESI_Operation_Descriptor;
import de.svenjacobs.loremipsum.LoremIpsum;
import java.util.ArrayList;

/**
 *
 * @author Irina
 */
public class MESI_Model implements I_MESI_Model  {


    private int Cache_Size;
    private int String_Size;
    private ArrayList<MESI_Cache> Caches;
    private ArrayList<String> Memory;
    private ArrayList<MESI_Operation_Descriptor> Operations;
            
    public MESI_Model()
    {
        this.Initialize(0, 0, 0, 0);
    }
    
    public MESI_Model(int Cache_Num, int Memory_Size, int Cache_Size, int String_Size)
    {
        this.Initialize(Cache_Num, Memory_Size, Cache_Size, String_Size);
    }
    
    @Override
    public void Initialize(int Cache_Num, int Memory_Size, int Cache_Size, int String_Size)
    {
        this.Cache_Size = Cache_Size;
        this.String_Size = String_Size;
        Caches =  new ArrayList<>(Cache_Num);
        for (int i=0; i < Cache_Num;i++)
            Caches.add(new MESI_Cache(Cache_Size, this,i));
        Memory =  new ArrayList<>(Memory_Size);
        LoremIpsum Generator = new LoremIpsum();
        int Ind = 0;
        for (int i=0; i < Memory_Size;i++)
        {
            String  Temp = "";
            int j=0;
            while (Temp.length() < this.String_Size)
            {
                j++;
                Temp = Generator.getWords(j, Ind);
            }
            Ind+=j;
            if (Ind>50)
                Ind = 0;
            
            Memory.add(Temp.substring(0, String_Size));
        }
        Operations = new ArrayList<>();
    }        

    @Override
    public ArrayList<String> GetMemory()
    {
        return new ArrayList<>(Memory);
    }

    @Override
    public ArrayList<I_MESI_Cache> GetCaches()
    {
         ArrayList<I_MESI_Cache> Caches_Out = new  ArrayList<>();
         for (int i=0; i < Caches.size();i++)
            Caches_Out.add((I_MESI_Cache)Caches.get(i));
         return Caches_Out;
    }

    @Override
    public void ReadToCache(int Cache_Num, int Mem_String_Num)
    {
        Operations.clear();
        Caches.get(Cache_Num).Read(Mem_String_Num,this.Operations);
    }

    @Override
    public void WriteToCache(int Cache_Num, int Mem_String_Num, String New)
    {
        Operations.clear();
        String New_trimmed;
        if (New.length() < this.String_Size)
            New_trimmed = New;
        else  New_trimmed = New.substring(0, String_Size);
        Caches.get(Cache_Num).Write(Mem_String_Num, New_trimmed,this.Operations);
    }

    @Override
    public void DropFromCache(int Cache_Num, int Cache_String_Num)
    {
         Operations.clear();
         Caches.get(Cache_Num).WriteDrop(Cache_String_Num,this.Operations, -1);
    }
    
    public String GetFromMemory(int Mem_String_Num)
    {
        return this.Memory.get(Mem_String_Num);
    }
    public String SetToMemory(int Mem_String_Num, String CachedString)
    {
        return this.Memory.set(Mem_String_Num, CachedString);
    }
    
    public boolean IsAlreadyCached(int Mem_String_Num, int Cache_Num){
        boolean Res = false;
        for(int i=0; i<this.Caches.size(); i++)
            Res = Res || Caches.get(i).RequestToShare(Mem_String_Num,this.Operations,Cache_Num);
        return Res;
    }
    
    public void Invalidate(int Mem_String_Num, int Cache_Num){
         for(int i=0; i<this.Caches.size(); i++)
            Caches.get(i).Drop(Mem_String_Num,this.Operations,Cache_Num);
    }

    @Override
    public int GetCacheNum()
    {
        return this.Caches.size();
    }

    @Override
    public int GetMemSize()
    {
        return this.Memory.size();
    }

    @Override
    public int GetCahceSize()
    {
        return this.Cache_Size;
    }

    @Override
    public int GetStringSize()
    {
        return this.String_Size;
    }

    @Override
    public ArrayList<MESI_Operation_Descriptor> GetOperations()
    {
        ArrayList<MESI_Operation_Descriptor> OperationsOut = new ArrayList<>();
        for (MESI_Operation_Descriptor D: this.Operations)
            OperationsOut.add(D);
        return OperationsOut;
    }
}
