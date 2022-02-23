import java.io.IOException;

public class KevinBaconRunner {
    public static void main(String[] args) throws IOException {

       BaconDegreeMaker b =new BaconDegreeMaker("src/movie_data.txt");
       //b.printOutList();
        b.writeMap();
    }
}
