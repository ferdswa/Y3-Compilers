package uk.ac.nott.cs.comp3012.coursework.util;

import java.util.*;

public class SymbolTable {
    private String scopeName;
    private SymbolTable parent;
    private List<SymbolTable> children;
    private LinkedHashMap<String, SymbolData> symbols;
    public SymbolTable(){
        this.children = new ArrayList<SymbolTable>();
        this.symbols = new LinkedHashMap<>();
    }
    public SymbolTable(SymbolTable parent){
        this.parent = parent;
        this.children = new ArrayList<>();
        this.symbols = new LinkedHashMap<>();
        this.parent.addChild(this);
    }
    public SymbolTable(SymbolTable existingValues, boolean copyFlag){
        this.parent = existingValues.getParent();
        this.children = existingValues.getChildren();
        this.symbols = (LinkedHashMap<String, SymbolData>) existingValues.getSymbols();
        this.scopeName = existingValues.getScopeName();
    }
    public Optional<SymbolData> lookup(String symbolName){
        return Optional.ofNullable(this.getSymbols().get(symbolName));
    }
    public void define(String symName,SymbolData data){
        this.symbols.put(symName, data);
    }
    public String getScopeName(){return this.scopeName;}
    public void setScopeName(String scopeName){this.scopeName = scopeName;}
    public SymbolTable getParent(){return this.parent;}
    public void setParent(SymbolTable parent){
        if(this.parent != null){
            this.parent.removeChild(this);
        }
        this.parent = parent;
        this.parent.addChild(this);
    }
    public Map<String, SymbolData> getSymbols(){return this.symbols;}
    public void setSymbols(Map<String, SymbolData> symbols){this.symbols = (LinkedHashMap<String, SymbolData>) symbols;}
    public List<SymbolTable> getChildren(){return this.children;}
    public void addChild(SymbolTable child){this.children.add(child);}
    public void removeChild(SymbolTable child){
        this.children.remove(child);
    }
    public void clearChildren(){
        this.children.clear();
    }
}
