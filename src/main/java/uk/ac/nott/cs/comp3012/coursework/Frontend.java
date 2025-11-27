package uk.ac.nott.cs.comp3012.coursework;

import org.apache.commons.collections4.ListUtils;
import uk.ac.nott.cs.comp3012.coursework.ast.Ast;
import uk.ac.nott.cs.comp3012.coursework.ast.AstBuilder;
import uk.ac.nott.cs.comp3012.coursework.util.Node;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Stream;

public class Frontend {
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
        List<Node> b = getNodes(pathAsNodes);
        ArrayList<Integer> splitAt = new ArrayList<>();
        boolean areSU;
        for(Node x: b){
            //Values
            if(!(b.indexOf(x)==b.indexOf(b.getLast()))){
                areSU= areSameUnit(x,b.get(b.indexOf(x)+1));
                if(!areSU){
                    splitAt.add(b.indexOf(x)+1);
                    System.out.println("New sublist begins @"+(b.indexOf(x)+1));//Found where new unit begins
                }
            }
        }
        int splitAtX = splitAt.getFirst();
        List<List<String>> pathUnions = new ArrayList<>();
        for(String[] nodePath: pathAsNodes){
            while(pathAsNodes.indexOf(nodePath)+1<splitAtX){
                List<String> union = ListUtils.union(List.of(nodePath), List.of(pathAsNodes.get(pathAsNodes.indexOf(nodePath) + 1)));
            }
        }
        for(List<String> path: pathUnions){
            System.out.println(Arrays.toString(path.toArray()));
        }
        return null;
    }

    private static List<Node> getNodes(List<String[]> pathAsNodes) {
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

        resNode = astAsNodes.getLast().getParent();

        Stream<Node> a= astAsNodes.stream().filter(x-> x.getChildren().isEmpty());
        List<Node> b = a.toList();
        return b;
    }

    public boolean areSameUnit(Node n1, Node n2){
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
        //They are in separate program units.
        return bsParents.get(0).size() != 4 || bsParents.get(1).size() != 4;
    }
}
