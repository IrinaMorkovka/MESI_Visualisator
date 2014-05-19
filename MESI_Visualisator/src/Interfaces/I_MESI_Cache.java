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
public interface I_MESI_Cache {
    public int GetSize();
     public ArrayList<MESI_States> GetStringStates();      
     public ArrayList<String> GetStrings();
     public ArrayList<Integer> GetMemStringsNums();
}
