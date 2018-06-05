package userPackage;

/**
 * Created by mohamed on 10/02/2017.
 */

public class User {

    private String firstName;
    private String  lastName;
    private String pseudo;
    private String dateNaissance;
    private String ville;
    private String sexe;

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }





    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String firstName, String lastName,String pseudo,String dateNaissance,String ville,String sexe) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pseudo = pseudo;
        this.dateNaissance = dateNaissance;
        this.ville = ville;
        this.sexe = sexe;
    }

}
