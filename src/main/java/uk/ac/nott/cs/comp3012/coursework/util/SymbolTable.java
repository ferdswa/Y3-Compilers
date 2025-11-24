package uk.ac.nott.cs.comp3012.coursework.util;

import java.util.Map;

public class SymbolTable {
    private String scopeName;
    private SymbolTable parent;
    private Map<String, SymbolData> symbols;
    public SymbolTable(){}
    public SymbolTable(SymbolTable parent){
        this.parent = parent;
    }
    public void define(String symName,SymbolData data){
        symbols.put(symName, data);
    }
    public String getScopeName(){return this.scopeName;}
    public void setScopeName(String scopeName){this.scopeName = scopeName;}
    public SymbolTable getParent(){return this.parent;}
    public void setParent(SymbolTable parent){this.parent = parent;}
    public Map<String, SymbolData> getSymbols(){return this.symbols;}
    public void setSymbols(Map<String, SymbolData> symbols){this.symbols = symbols;}
}
