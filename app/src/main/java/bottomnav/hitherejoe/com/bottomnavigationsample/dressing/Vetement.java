package bottomnav.hitherejoe.com.bottomnavigationsample.dressing;

/**
 * Created by pauli on 26/03/2017.
 */

public class Vetement {

    private String COLOR;
    private String Categorie;
    private String idUser;
    private String type;
    private String url;

    public Vetement(){

    }

    public Vetement(String couleur, String catego, String user, String type, String url){
        this.COLOR = couleur;
        this.Categorie = catego;
        this.idUser = user;
        this.type = type;
        this.url = url;
    }

    public String getCOLOR() {
        return COLOR;
    }

    public void setCOLOR(String COLOR) {
        this.COLOR = COLOR;
    }

    public String getCategorie() {
        return Categorie;
    }

    public void setCategorie(String categorie) {
        Categorie = categorie;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
