/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import Interfaces.I_MESI_Cache;
import Interfaces.MESI_States;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Vasiliy
 */
public class CacheTableModel extends AbstractTableModel  {

    int RowCount;
    private ArrayList<Integer> MemoryStringNumbers; 
    private ArrayList<String> Strings; 
    private ArrayList<MESI_States> States;

    public CacheTableModel()
    {
        this.RowCount = 0;
    }
    
    @Override
    public int getRowCount()
    {
        return RowCount;
    }
    

    @Override
    public int getColumnCount()
    {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        switch (columnIndex)
        {
            case 0:
                return rowIndex + 1;
            case 1:
                switch (States.get(rowIndex))
                {
                    case INVALID:
                        return "I";
                    case EXCLUSIVE:
                        return "E";
                    case SHARED:
                        return "S";
                    case MODIFIED:
                        return "M";
                }
            case 2:
                return MemoryStringNumbers.get(rowIndex);
            case 3:
                return Strings.get(rowIndex);
            default:
                return null;
        }
    }
    public void ExtractCacheData(I_MESI_Cache Cache)
    {
        this.States = Cache.GetStringStates();
        this.Strings = Cache.GetStrings();
        this.MemoryStringNumbers = Cache.GetMemStringsNums();
        this.RowCount = Cache.GetSize();
    }
}
