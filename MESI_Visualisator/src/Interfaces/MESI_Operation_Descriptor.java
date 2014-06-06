/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interfaces;

/**
 *
 * @author Irina
 */
public class MESI_Operation_Descriptor {
    public MESI_Transitions Operation;
    public int  Primary_Cache_Num;
    public int  Current_Cache_Num; 
    public int  Current_Memory_String_Num; 
     
    public MESI_Operation_Descriptor (MESI_Transitions Operation, int  Primary_Cache_Num,
            int  Current_Cache_Num, int  Current_Memory_String_Num ) {
        this.Operation = Operation;
        this.Current_Cache_Num = Current_Cache_Num;
        this.Primary_Cache_Num = Primary_Cache_Num;
        this.Current_Memory_String_Num = Current_Memory_String_Num;
    }
    
    
    public MESI_Operation_Descriptor (MESI_Operation_Descriptor Descript) {
        this.Operation = Descript.Operation;
        this.Current_Cache_Num = Descript.Current_Cache_Num;
        this.Primary_Cache_Num = Descript.Primary_Cache_Num;
        this.Current_Memory_String_Num  = Descript.Primary_Cache_Num;
    }
}
