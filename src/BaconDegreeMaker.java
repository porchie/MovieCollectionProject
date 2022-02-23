import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.io.FileWriter;


public class BaconDegreeMaker {
    private ArrayList<SimpleMovie> movies;
    private static Set<String> addedMovies = new HashSet<>();
    private static Set<String> addedActors = new HashSet<>();
    private FileWriter writer = new FileWriter("src/KevinBaconDegreeMap");


    public BaconDegreeMaker(String fileName) throws IOException {
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

    public void writeMap()
    {
        try
        {
            ArrayList<String> literallyJustKevinBacon = new ArrayList<>();
            literallyJustKevinBacon.add("Kevin Bacon");
            FileWriter writer = new FileWriter("src/KevinBaconDegreeMap");

            writer.write(literallyJustKevinBacon.get(0)+";A0;");
            writer.write(System.lineSeparator());

            ArrayList<SimpleMovie> arr1 = actorToMovie(literallyJustKevinBacon,"A","B");
            ArrayList<String> prevLayerAct =  movieToActor(arr1,"B","A");

            for(int i = 0;i<5;i++) //mfw cook egg on my scorching laptop cuz jvm is crying
            {
                String top = (i%2==0) ?"A":"B";
                String bot = (i%2==0) ?"B":"A";
                arr1 = actorToMovie(prevLayerAct,top,bot);
                prevLayerAct = movieToActor(arr1,top,bot);
            }


            writer.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private ArrayList<SimpleMovie> actorToMovie(ArrayList<String> actors, String topPointer, String botPointer)
    {
        try {

            ArrayList<SimpleMovie> added = new ArrayList<>();
            int i = 0;
            int j = 0;
            for (String s : actors) {

                for (SimpleMovie m : movies) {
                   if(m.getCast().contains(s)) {
                        if(addedMovies.add(m.getTitle()))
                        {
                            added.add(m);
                            writer.write(";"+topPointer+i+";"+m.getTitle()+";"+botPointer+j+";/");
                            j++;
                        }
                   }
                }
                i++;
            }
            writer.write(System.lineSeparator());
            removeFromMovies(added);
            return added;
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return new ArrayList<SimpleMovie>();
        }
    }

    private ArrayList<String> movieToActor(ArrayList<SimpleMovie> mov,String topPointer, String botPointer)
    {
        try
        {
            int i=0;
            int j=0;

            ArrayList<String> actors = new ArrayList<>();
            for(SimpleMovie m:mov)
            {
                String[] cast = m.getCast().split(",");
                for(String s:cast)
                {
                    if(addedActors.add(s))
                    {
                        actors.add(s);
                        writer.write(";"+topPointer+i+";"+s+";"+botPointer+j+";/");
                        j++;
                    }
                }
                i++;
            }
            writer.write(System.lineSeparator());


            return actors;
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return new ArrayList<String>();
        }
    }

    private void removeFromMovies(ArrayList<SimpleMovie> mov)
    {
        for(SimpleMovie m: mov)
        {
            for(int i = 0;i<movies.size();i++)
            {
                if(m.isCopy(movies.get(i)))
                {
                    movies.remove(i);
                    break;
                }
            }
        }
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
