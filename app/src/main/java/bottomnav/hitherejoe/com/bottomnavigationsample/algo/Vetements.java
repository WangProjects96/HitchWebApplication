package bottomnav.hitherejoe.com.bottomnavigationsample.algo;
import java.util.Date;
import java.util.GregorianCalendar;


public class Vetements {
    private String nom;
    private Couleur color;
    private TypeV type;
    private GregorianCalendar date;
    // private Date d;

    public Vetements(String nom, Couleur color, TypeV type) {
        super();
        this.nom = nom;
        this.color = color;
        this.type = type;
        this.date= new GregorianCalendar();


    }


    public String getNom(){
        return nom;
    }


    public Couleur getCouleur(){
        return this.color;
    }

    public TypeV getTypeV(){
        return type;
    }

    public int getPointDate(GregorianCalendar d){

        Date d1 = this.date.getTime();
        Date d2= d.getTime();
        long diff = d2.getTime() - d1.getTime();
        diff = diff / (1000 * 60 * 60 * 24);

        return (int) diff;
    }


    public void setDate(GregorianCalendar date2) {
        // TODO Auto-generated method stub
        this.date=date2;
        //mettre a jour la BD
    }

    @Override
    public String toString() {
        return  this.getNom() + " " + this.type;
    }
}