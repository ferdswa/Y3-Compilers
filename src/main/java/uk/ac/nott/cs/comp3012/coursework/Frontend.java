package uk.ac.nott.cs.comp3012.coursework;

import uk.ac.nott.cs.comp3012.coursework.ast.Ast;
import uk.ac.nott.cs.comp3012.coursework.ast.AstBuilder;
import uk.ac.nott.cs.comp3012.coursework.util.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Frontend {
    List<List<String[]>> difference = new ArrayList<>();
    int posOfDifference = 0;


    public Ast runFrontend(String input) throws IOException {
        AstBuilder astBuilder = new AstBuilder();
        ArrayList<String> ast = astBuilder.buildAst(input);
        List<String[]> pathAsNodes = new ArrayList<>();
        for (String s : ast) {
            pathAsNodes.add( s.split(":"));
        }
        for(String[] y: pathAsNodes){
            System.out.println( Arrays.toString(y));
        }
        ArrayList<Node> astAsNodes = new ArrayList<>();
        Node prevNode = null;
        Node newNode = null;
        Node resNode = null;
        int lo =0;
        int hi = pathAsNodes.size();

        for(String[] pathToLeaves: pathAsNodes){
            int i = pathToLeaves.length-1;
            prevNode = new Node(pathToLeaves[i]);
            while(i!=0){
                newNode = new Node(pathToLeaves[i-1]);
                newNode.assignChild(prevNode);
                prevNode.setParent(newNode);
                astAsNodes.add(prevNode);
                prevNode = newNode;
                i--;
            }
        }
        Iterator listNodes = astAsNodes.iterator();

        resNode = astAsNodes.getLast().getParent();

        Stream<Node> a= astAsNodes.stream().filter(x-> x.getChildren().isEmpty());
        List<Node> b = a.toList();
        List<List<Node>> bsParents = new ArrayList<>();
        List<Node> builder = new ArrayList<>();
        for(Node x: b){
            resNode = x;
            //Values
            builder = new ArrayList<>();
            while(x!=null){
                builder.add(x);
                x=x.getParent();
            }
            bsParents.add(builder);
        }
        for(Node x: b){
                //Values
                if(!(b.indexOf(x)==b.indexOf(b.getLast()))){
                    System.out.print(x.getName()+": ");
                    areSLNeighbors(x,b.get(b.indexOf(x)+1));
                }
            System.out.print("\b\n");
        }

        String inputA = "d";
        while(inputA.equals("d") || inputA.equals("u")){
            System.out.println( "You are in: "+ resNode.getName());
            BufferedReader r = new BufferedReader(
                    new InputStreamReader(System.in));
            inputA = r.readLine();
            if(inputA.equals("d")){
                resNode = resNode.getChildren().getLast();
            } else if (inputA.equals("u")) {
                resNode = resNode.getParent();
            }
        }

        return null;
    }
    public void areSLNeighbors(Node n1, Node n2){
        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(n1);
        nodes.add(n2);
        List<List<Node>> bsParents = new ArrayList<>();
        List<Node> builder;
        for(Node x: nodes){
            //Values
            builder = new ArrayList<>();
            while(x!=null){
                builder.add(x);
                x=x.getParent();
            }
            bsParents.add(builder);
        }
        if(bsParents.get(0).size()==4 && bsParents.get(1).size()==4){
            System.out.println(false);
        }
        else{
            System.out.println("traverse");//Nodes are currently perceived as neighbors
        }
    }
}
