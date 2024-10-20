import model.Model;
import model.ModelProvider;
import model.ModelSpec;
import model.Profile;
import model.ProfileProvider;
import model.Provider;
import model.Session;
import model.Spec;

public class Main {


    private static Session session = null;

  Main(Session session) {
        this.session = session;
    }

    public static void main(String[] args) {


        Model model = new Model();
        model.setName("ggg");

        ModelSpec dd = new Spec();
        dd.setName("dd");



        ProfileProvider pprovider= session.getProvider(ProfileProvider.class);

        Profile provider = pprovider.create(model);




        System.out.println(model.getName());
    }
}