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
 * @author Nadja
 */
public class CacheTableModel extends AbstractTableModel  {

    private int RowCount;
    private int CacheNumber;
    private ArrayList<Integer> MemoryStringNumbers; 
    private ArrayList<String> Strings; 
    private ArrayList<MESI_States> States;
    private final String[] tableHeaders = {"Номер","Состояние","Тэг","Содержимое"};

    public CacheTableModel(int CacheNumber)
    {
        this.RowCount = 0;
        this.CacheNumber = CacheNumber;
    }
    
    public int GetCacheNumber()
    {
        return this.CacheNumber;
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
    public String getColumnName(int columnIndex) {
        return tableHeaders[columnIndex];
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
                        return "Invalid";
                    case EXCLUSIVE:
                        return "Exclusive";
                    case SHARED:
                        return "Shared";
                    case MODIFIED:
                        return "Modified";
                }
            case 2:
                if (MemoryStringNumbers.get(rowIndex)==-1)
                    return "";
                else return MemoryStringNumbers.get(rowIndex)+1;
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
