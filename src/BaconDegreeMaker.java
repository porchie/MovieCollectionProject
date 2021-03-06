import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.io.FileWriter;
import java.util.regex.*


public class BaconDegreeMaker {
    private ArrayList<SimpleMovie> movies;
    private Set<String> addedMovies = new HashSet<>();
    private Set<String> addedActors = new HashSet<>();
    //private FileWriter writer = new FileWriter("src/KevinBaconDegreeMap");//dont uncomment this


    public BaconDegreeMaker(String fileName) throws IOException {
        movies = new ArrayList<SimpleMovie>();
        importJSONMovieList(fileName);
    }
    public void printOutList()
    {
        movies.forEach((n) -> System.out.println(n.getTitle() + " " + n.getCast()));
    }
    /**public void actorsList()
    {
        try {
            FileWriter writer2 = new FileWriter("src/actorsList");
            Set<String> actors = new HashSet<>();
            for (SimpleMovie m : movies) {
                String[] cast = m.getCast().split(",");
                for (String s : cast) actors.add(s);
            }
            ArrayList<String> result = new ArrayList<>();
            result.addAll(actors);
            MovieCollection.insertionSortWordList(result);

            for(String str:result)
            {
                writer2.write(str + ",");
            }
            writer2.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
**/
/**
    public void writeMap() //dont call this lol it will kill your computer
    {
        try
        {
            ArrayList<String> literallyJustKevinBacon = new ArrayList<>();
            literallyJustKevinBacon.add("Kevin Bacon");
            writer = new FileWriter("src/KevinBaconDegreeMap");

            writer.write(literallyJustKevinBacon.get(0)+";A0;");
            writer.write(System.lineSeparator());

            ArrayList<SimpleMovie> arr1 = actorToMovie(literallyJustKevinBacon,"A","B");
            ArrayList<String> prevLayerAct =  movieToActor(arr1,"B","A");



            for(int i = 0;i<5;i++) //mfw cook egg on my scorching laptop cuz jvm is crying
            {
                String top = (i%2==0) ?"A":"B";
                String bot = (i%2==0) ?"B":"A";
                arr1 = actorToMovie(prevLayerAct,top,bot);
                prevLayerAct = movieToActor(arr1,bot,top);
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
                for (SimpleMovie m : movies) { //150000 everytime very bad
                   if(movieHasActor(s,m)) { //so basically a bunch of actors have their name written out as Name , Jr. and there is an actor named Jr. so All of the movies with Name, Jr. have Jr. in them cuz it splits by comma.
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
            removeFromMovies(added); //try to remove from the 150000 possibly even worse
            return added;
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return new ArrayList<SimpleMovie>();
        }
    }

    private boolean movieHasActor(String actor, SimpleMovie mov)
    {
        String[] arr = mov.getCast().split(",");
        for(String s:arr)
        {
            if(s.equals(actor)) return true;
        }
        return false;
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
                    if(s.isEmpty())
                    {

                    }
                    else if(addedActors.add(s))
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
**/
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
    
    /**
    
    private void importJSONMovieList(String fileName)
    {
        Pattern patt = Pattern.compile("\"([^\"]*)\"");
        BufferedReader r = new BufferedReader(new FileReader(fileName);

        String line;

        while ((line = r.readLine()) != null) {

            Matcher m = patt.matcher(line);
            ArrayList<String> lineRead = new ArrayList<>();
            while (m.find()) {
                lineRead.add(m.group(0));
            }
            String title = lineRead.get(1);
            for(int i = 2;i<lineRead.size();i++)
            {
                if(lineRead.get(i).contains("directors") // do the str that directors and stuffs
                {
                    
                }
            }
        }
        
        
         
        
        
    }
    
    **/
}
