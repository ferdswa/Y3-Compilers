package uk.ac.nott.cs.comp3012.coursework.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolTable {
    private String scopeName;
    private SymbolTable parent;
    private List<SymbolTable> children;
    private Map<String, SymbolData> symbols;
    public SymbolTable(){
        this.children = new ArrayList<SymbolTable>();
        this.symbols = new HashMap<String, SymbolData>();
    }
    public SymbolTable(SymbolTable parent){
        this.parent = parent;
        this.children = new ArrayList<SymbolTable>();
        this.symbols = new HashMap<String, SymbolData>();
        this.parent.addChild(this);
    }
    public void define(String symName,SymbolData data){
        this.symbols.put(symName, data);
    }
    public String getScopeName(){return this.scopeName;}
    public void setScopeName(String scopeName){this.scopeName = scopeName;}
    public SymbolTable getParent(){return this.parent;}
    public void setParent(SymbolTable parent){this.parent = parent;}
    public Map<String, SymbolData> getSymbols(){return this.symbols;}
    public void setSymbols(Map<String, SymbolData> symbols){this.symbols = symbols;}
    public List<SymbolTable> getChildren(){return this.children;}
    public void addChild(SymbolTable child){this.children.add(child);
    }
}
