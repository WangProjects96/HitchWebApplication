package bottomnav.hitherejoe.com.bottomnavigationsample.algo;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Couleur {

    GREEN(Color.GREEN,Arrays.asList(Color.GREEN,Color.YELLOW,Color.RED)),
    RED(Color.RED,Arrays.asList(Color.RED,Color.YELLOW,Color.WHITE)),
    BLUE(Color.BLUE,Arrays.asList(Color.BLUE,Color.GRAY,Color.BLACK,Color.WHITE)),
    BLACK(Color.BLACK,Arrays.asList(Color.BLACK,Color.WHITE,Color.GRAY,Color.BLUE)),
    GRAY(Color.GRAY,Arrays.asList(Color.GRAY,Color.WHITE,Color.BLACK,Color.BLUE)),
    YELLOW(Color.YELLOW,Arrays.asList(Color.YELLOW,Color.RED,Color.WHITE)),
    WHITE(Color.WHITE,Arrays.asList(Color.WHITE,Color.RED,Color.YELLOW,Color.BLACK));



    int c;
    ArrayList<Integer> CAssos= new ArrayList<Integer>();
    Couleur(int c,List<Integer> L) {
        CAssos.addAll(L);
        this.c=c;
    }

    public boolean isCOkay(int c){

        for(int i=0;i<CAssos.size();i++){
            if ( c==CAssos.get(i)) return true;
        }
        return false;
    }

    public static int transfoColor(String s){
        int i=Integer.parseInt(s);

        /*if(i==Color.BLACK) return Color.BLACK;
        if(i==Color.WHITE) return Color.WHITE;
        if(i==Color.GREEN) return Color.GREEN;
        if(i==Color.RED) return Color.RED;
        if(i==Color.BLUE) return Color.BLUE;
        if(i==Color.GRAY) return Color.GRAY;
        if(i==Color.YELLOW) return Color.YELLOW;
        return 0;*/

        return Color.parseColor(s);


    }
    public Couleur getC(){
        return this;
    }

    public static Couleur getCouleur(String st){
        String s=st.toUpperCase();
        if(s.equals(Couleur.BLACK.name())) return Couleur.BLACK;
        if(s.equals(Couleur.WHITE.name())) return Couleur.WHITE;
        if(s.equals(Couleur.GREEN.name())) return Couleur.GREEN;
        if(s.equals(Couleur.RED.name())) return Couleur.RED;
        if(s.equals(Couleur.BLUE.name())) return Couleur.BLUE;
        if(s.equals(Couleur.GRAY.name())) return Couleur.GRAY;
        if(s.equals(Couleur.YELLOW.name())) return Couleur.YELLOW;
        return null;
    }
    public int yolo(){
        return 2;
    }

}