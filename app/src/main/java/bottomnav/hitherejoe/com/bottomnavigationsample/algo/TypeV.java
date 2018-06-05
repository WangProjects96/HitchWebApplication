package bottomnav.hitherejoe.com.bottomnavigationsample.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public enum TypeV {



    MANCHECOURTE(Arrays.asList(Temperature.CHAUD, Temperature.DOUX), Categorie.HAUT1),
    MANCHELONGUE(Arrays.asList(Temperature.DOUX, Temperature.FRAIS, Temperature.FROID, Temperature.TRESFROID), Categorie.HAUT1),
    CHEMISE(Arrays.asList(Temperature.FRAIS, Temperature.DOUX, Temperature.CHAUD, Temperature.FROID), Categorie.HAUT1),

    COSTUME(Arrays.asList(Temperature.DOUX, Temperature.FRAIS), Categorie.HAUT2),
    PULL(Arrays.asList(Temperature.FROID, Temperature.TRESFROID, Temperature.FRAIS), Categorie.HAUT2),
    SWEAT(Arrays.asList(Temperature.DOUX, Temperature.FRAIS, Temperature.FROID, Temperature.TRESFROID), Categorie.HAUT2),
    VESTE(Arrays.asList(Temperature.FRAIS, Temperature.DOUX, Temperature.CHAUD), Categorie.HAUT2),
    MANTEAU(Arrays.asList(Temperature.FROID, Temperature.FRAIS, Temperature.DOUX, Temperature.TRESFROID), Categorie.MANTEAU),
    PANTALONCOSTUME(Arrays.asList(Temperature.DOUX, Temperature.FRAIS), Categorie.BAS),
    PANTALON(Arrays.asList(Temperature.DOUX, Temperature.FRAIS, Temperature.FROID, Temperature.TRESFROID),
            Categorie.BAS),
    LEGGING(Arrays.asList(Temperature.FROID, Temperature.FRAIS, Temperature.DOUX, Temperature.CHAUD), Categorie.BAS),
    JUPE(Arrays.asList(Temperature.CHAUD, Temperature.DOUX), Categorie.BAS),
    BASKET(Arrays.asList(Temperature.DOUX, Temperature.FRAIS, Temperature.FROID, Temperature.TRESFROID, Temperature.CHAUD), Categorie.CHAUSSURES),
    CHAUSSUREVILLE(	Arrays.asList(Temperature.DOUX, Temperature.FRAIS, Temperature.FROID, Temperature.TRESFROID, Temperature.CHAUD),	Categorie.CHAUSSURES),
    TONG(Arrays.asList(Temperature.CHAUD), Categorie.CHAUSSURES),
    BOTTE(Arrays.asList(Temperature.DOUX, Temperature.FRAIS, Temperature.FROID, Temperature.TRESFROID, Temperature.CHAUD), Categorie.CHAUSSURES);

    Categorie cat;
    ArrayList<Temperature> temp= new ArrayList<Temperature>();
    //ArrayList<Temperature> arra= new ArrayList<Temperature>();

    TypeV(List<Temperature> T, Categorie c) {
        cat = c; // si ya un probleme c'est maybe dans la duplication de
        // l'array

        temp.addAll(T);
    }

    public Categorie getCate() {
        return cat;
    }

    public boolean isOkay(double t) {
        for (int i = 0; i < temp.size(); i++) {
            if (t <= temp.get(i).getMax())
                if (t >= temp.get(i).getMin()) {
                    return true;
                }

        }
        return false;
    }

    public static String getCat(String s){

        for(TypeV t : TypeV.values()){

            if(t.name().equalsIgnoreCase(s)){
                return t.cat.name();
            }
        }

        return null;

    }
    public static TypeV type(String s){
        TypeV t;
        t= TypeV.valueOf(s.toUpperCase());
        return t;
    }

}
