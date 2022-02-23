import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class KevinBaconRunner {
    public static void main(String[] args) throws IOException {

       //BaconDegreeMaker b =new BaconDegreeMaker("src/movie_data.txt");
       //b.writeMap();

        FileReader reader = new FileReader("src/KevinBaconDegreeMap");
        BufferedReader buffread = new BufferedReader(reader);
        String line = "";
        int count =0;
        while((line = buffread.readLine()) != null)
        {
            count++;
        }
        System.out.println(count);
    }
}
