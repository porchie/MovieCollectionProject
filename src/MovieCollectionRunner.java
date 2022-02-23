import java.io.IOException;
import java.util.ArrayList;

public class MovieCollectionRunner
{
    public static void main(String arg[]) throws IOException {
        MovieCollection myCollection = new MovieCollection("src/movies_data.csv");
        //MovieCollection.writeArrListToFile(myCollection.getAllGenre(),"src/genres"); //builds genre file with the genres
        myCollection.menu();


    }
}