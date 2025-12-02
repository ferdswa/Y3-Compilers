package uk.ac.nott.cs.comp3012.coursework.util;

public class SymbolData{
    public SymbolData(){
    }
    public String name;
    public String value;
    public String type;
    public String scope;
    public String notes;

    public SymbolData(String name,String type,String value,String scope,String notes){
        this.name=name;
        this.type=type;
        this.value=value;
        this.scope=scope;
        this.notes=notes;
    }

    public SymbolData(SymbolData symbolData){
        this.name=symbolData.name;
        this.value=symbolData.value;
        this.type=symbolData.type;
        this.scope=symbolData.scope;
        this.notes=symbolData.notes;
    }

    public SymbolData(String name){
        this.name=name;

    }
    public SymbolData(String name,String type){
        this.name=name;
        this.type=type;
    }
    public SymbolData(String name,String type,String value){
        this.name=name;
        this.type=type;
        this.value=value;
    }

    public SymbolData(String name,String type,String value,String scope){
        this.name=name;
        this.type=type;
        this.value=value;
        this.scope=scope;
    }

    public String toString(){
        String str="";
        str+=this.name+" ";
        str+=this.type+" ";
        str+=this.value+" ";
        str+=this.scope+" ";
        str+=this.notes;
        return str;
    }
}
