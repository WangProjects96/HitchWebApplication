package bottomnav.hitherejoe.com.bottomnavigationsample.fil;

/**
 * Created by mohamed on 27/03/2017.
 */

public class Post {


    private String titre;
    private String commentaire;
    private String image;
    private String user;
    private  long post_time;

    public Post(String titre, String commentaire, String image, String user, long post_time) {
        this.titre = titre;
        this.commentaire = commentaire;
        this.image = image;
        this.user = user;
        this.post_time = post_time;
    }

    public Post() {
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPost_time() {
        return post_time;
    }

    public void setPost_time(long post_time) {
        this.post_time = post_time;
    }


}
