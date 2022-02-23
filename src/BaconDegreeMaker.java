import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileWriter;


public class BaconDegreeMaker {
    private ArrayList<SimpleMovie> movies;


    public BaconDegreeMaker(String fileName)
    {
        movies = new ArrayList<SimpleMovie>();
        importJSONMovieList(fileName);
    }
    public void printOutList()
    {
        movies.forEach((n) -> System.out.println(n.getTitle() + " " + n.getCast()));
    }

    public ArrayList<SimpleMovie> kevinBaconMovies()
    {
        ArrayList<SimpleMovie> kevBacMov = new ArrayList<>();
        for(SimpleMovie m: movies)
        {
            if(m.getCast().contains("Kevin Bacon")) {
                kevBacMov.add(m);
            }
        }
        return kevBacMov;
    }





    private void importJSONMovieList(String fileName)
    {
        try {
            FileReader fileReader = new FileReader(fileName);
            Scanner scanner = new Scanner(fileReader);
            String line = "";
            while(scanner.hasNextLine())
            {
                line = scanner.nextLine();
                ArrayList<String> cast = new ArrayList<>();
                String title ="";
                String castStr = "";

                line = line.substring(1);
                line = line.substring(0,line.length()-1);

                try {
                    String temp = line.substring(line.indexOf(":")+2);
                    title = temp.substring(0,temp.indexOf(":[")-8);
                    temp = temp.substring(temp.indexOf(":["));
                    castStr = temp.substring(1, temp.contains("directors")? temp.indexOf("directors") - 3:temp.length());
                }
                catch(IndexOutOfBoundsException i)
                {
                    System.out.println(line);
                    System.out.println(i.getMessage());
                    System.exit(0);
                }

                castStr = castStr.replaceAll("\"","");
                castStr = castStr.replaceAll("\\[","");
                castStr = castStr.replaceAll("]","");
                movies.add(new SimpleMovie(title,castStr));
            }


        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
