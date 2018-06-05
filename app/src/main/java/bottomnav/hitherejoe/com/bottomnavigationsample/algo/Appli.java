package bottomnav.hitherejoe.com.bottomnavigationsample.algo;

import android.graphics.Color;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.GregorianCalendar;
//import java.util.Arrays;



public class Appli {

    private FirebaseAuth authentificationFireBase;


    static ArrayList<Vetements> dressing;
    static ArrayList<Vetements> DressingTemp;

    static ArrayList<Vetements> categorieH1;
    static ArrayList<Vetements> categorieH2;
    static ArrayList<Vetements> categorieM;
    static ArrayList<Vetements> categorieBas;
    static ArrayList<Vetements> categorieCh;
    static ArrayList<Vetements> tenue;

    static String nom = "";
    static int CFav = Color.BLACK;



    private static int compteurHabits = 0;
    private static int compteurHabitsLoaded = 0;



    //temperature dans le weather activity        ArrayList<Vetements>
    public static void main(double temperature,ArrayList<Vetements> d) {
        dressing = d;
        DressingTemp = new ArrayList<Vetements>();
        categorieH1 = new ArrayList<Vetements>();
        categorieH2 = new ArrayList<Vetements>();
        categorieM = new ArrayList<Vetements>();
        categorieBas = new ArrayList<Vetements>();
        categorieCh = new ArrayList<Vetements>();
        tenue = new ArrayList<Vetements>();




        Appli.initTemps(temperature);
        Log.wtf("probleme","temperature");
        Appli.initCategorie();
        Log.d("unsuff","p21 Categ");

        Appli.Generation();
        Log.d("unsuff","p22 Gen ");
        Log.d("logVetF","________________");
        logList(tenue);
//        Log.wtf("probleme","generation");
//          Appli.afficherTenue(tenue);
//        Log.wtf("probleme","afficher tenue");
        //System.out.println(Appli.afficherTenue(tenue));

//        for(Vetements v : dressing){
//            Log.i("tenue", v.getNom());
//        }

        //return tenue;
    }





    private static void initTemps(double temperature) {
        for (int i = 0; i < dressing.size(); i++) {
            if (dressing.get(i).getTypeV().isOkay(temperature)) {
                DressingTemp.add(dressing.get(i));
            }
            ;
        }

        // System.out.println("j: "+ j);

    }

    private static void initCategorie() {

        for (int i = 0; i < DressingTemp.size(); i++) {
            if (DressingTemp.get(i).getTypeV().cat == Categorie.CHAUSSURES)
                categorieCh.add(DressingTemp.get(i));
            else if (DressingTemp.get(i).getTypeV().cat == Categorie.BAS)
                categorieBas.add(DressingTemp.get(i));
            else if (DressingTemp.get(i).getTypeV().cat == Categorie.HAUT1)
                categorieH1.add(DressingTemp.get(i));
            else if (DressingTemp.get(i).getTypeV().cat == Categorie.HAUT2)
                categorieH2.add(DressingTemp.get(i));
            else if (DressingTemp.get(i).getTypeV().cat == Categorie.MANTEAU)
                categorieM.add(DressingTemp.get(i));
        }

    }

    private static void Generation() {
//         int random = 0;
//         random = (int) (Math.random() * (categorieH1.size() - 1));
//         tenue.add(categorieH1.get(random));
//         random = (int) (Math.random() * (categorieH2.size() - 1));
//         tenue.add(categorieH2.get(random));
//         random = (int) (Math.random() * (categorieBas.size() - 1));
//         tenue.add(categorieBas.get(random));
//         random = (int) (Math.random() * (categorieCh.size() - 1));
//         tenue.add(categorieCh.get(random));
//         random = (int) (Math.random() * (categorieM.size() - 1));
//         tenue.add(categorieM.get(random));

        ArrayList<Vetements> Lpivot = new ArrayList<Vetements>();

//
        Appli.bla2(categorieH1);
        Appli.bla2(categorieH2);
        Appli.bla2(categorieBas);
        Appli.bla2(categorieCh);
        Appli.bla2(categorieM);



    }

    private static void bla(ArrayList<Vetements> L) {
        ArrayList<Vetements> Pivot = new ArrayList<Vetements>();
        int p = 0;
        Vetements Vpivot = null;
        // int random = 0;
        GregorianCalendar date = new GregorianCalendar();
        Log.wtf("bla", "aaaaaa");
        if (!(L.isEmpty())) {
            for (int i = 0; i < L.size(); i++) {
                if (L.get(i).getCouleur().isCOkay(CFav)) {
                    Pivot.add(L.get(i));
                }

            }
            if (Pivot.isEmpty()) {
                p = L.get(0).getPointDate(date);
                Vpivot = L.get(0);
                for (int i = 0; i < L.size(); i++) {
                    if (L.get(i).getPointDate(date) > p) {
                        p = L.get(i).getPointDate(date);
                        Vpivot = L.get(i);
                    }

                }
                // random = (int) (Math.random() * (L.size() - 1));
                Vpivot.setDate(date);
                tenue.add(Vpivot);
            } else {
                //Log.wtf("fatNinja",L.get(0).getTypeV().name());
                p = Pivot.get(0).getPointDate(date);
                //  Log.wtf("yolo","a");
                Vpivot = Pivot.get(0);
                //Log.wtf("yolo","b");
                for (int i = 0; i < L.size(); i++) {
                    if (L.get(i).getPointDate(date) > p) {
                        p = Pivot.get(i).getPointDate(date);
                        Vpivot = Pivot.get(i);
                    }
                    //Log.wtf("yolo","c");
                    // random = (int) (Math.random() * (Pivot.size() - 1));
                }
                Vpivot.setDate(date);
                // Log.wtf("yolo",Vpivot.getTypeV().name());
                tenue.add(Vpivot);
            }
        } else

            tenue.add(null);



    }
    private static void bla2(ArrayList<Vetements> L) {
        ArrayList<Vetements> Pivot = new ArrayList<Vetements>();
        //int p = 0;
        Vetements Vpivot = null;
        int random = 0;
        //GregorianCalendar date = new GregorianCalendar();
        if (!(L.isEmpty())) {
            for (int i = 0; i < L.size(); i++) {
                if (L.get(i).getCouleur().isCOkay(CFav)) {
                    Pivot.add(L.get(i));
                }

            }

            if (!(Pivot.isEmpty())){
                random = (int) (Math.random() * (Pivot.size() - 1));

                tenue.add(Pivot.get(random));}
            else{
                random = (int) (Math.random() * (L.size() - 1));

                tenue.add(L.get(random));}


        } else
            tenue.add(null);

        Pivot.clear();

    }


    public static void afficherTenue(ArrayList<Vetements> L){
        String s="";
        s+= "La tenue générée est la suivante : \n";
        if (L.get(0) != null)
            s+="Haut1 : " + L.get(0).getNom() + " " + L.get(0).getCouleur().name()+"\n";
        else
            s+="Haut1 : pas de L pour la temperature extérieur \n";

        if (L.get(1) != null)
            s+="Haut2 : " + L.get(1).getNom() + " " + L.get(1).getCouleur().name()+"\n";
        else
            s+="Haut2 : pas de L pour la temperature extérieur \n";

        if (L.get(2) != null)
            s+="Bas : " + L.get(2).getNom() + " " + L.get(2).getCouleur().name()+"\n";
        else
            s+="Bas : pas de L pour la temperature extérieur \n";

        if (L.get(3) != null)
            s+="Chassure : " + L.get(3).getNom() + " " + L.get(3).getCouleur().name()+"\n";
        else
            s+="Chaussure : pas de L pour la temperature extérieur \n";

        if (L.get(4) != null)
            s+="Manteau : " + L.get(4).getNom() + " " + L.get(4).getCouleur().name()+"\n";
        else
            s+="Manteau : pas de L pour la temperature extérieur \n";

        Log.wtf("complete dress",s);

    }

    public static void logList( ArrayList<Vetements> vs){
        for(Vetements v:vs){
            Log.d("logVetF",v.toString());
        }
    }

    public static ArrayList<Vetements> getTenue(){
        return tenue;
    }

    public static  void setColor(String couleur){
        CFav= Couleur.transfoColor(couleur);
    }

}
