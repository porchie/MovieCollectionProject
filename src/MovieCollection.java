import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MovieCollection
{
  private ArrayList<Movie> movies;
  private Scanner scanner;

  public MovieCollection(String fileName)
  {
    importMovieList(fileName);
    scanner = new Scanner(System.in);
  }

  public ArrayList<Movie> getMovies()
  {
    return movies;
  }
  
  public void menu()
  {
    String menuOption = "";
    
    System.out.println("Welcome to the movie collection!");
    System.out.println("Total: " + movies.size() + " movies");
    
    while (!menuOption.equals("q"))
    {
      System.out.println("------------ Main Menu ----------");
      System.out.println("- search (t)itles");
      System.out.println("- search (k)eywords");
      System.out.println("- search (c)ast");
      System.out.println("- see all movies of a (g)enre");
      System.out.println("- list top 50 (r)ated movies");
      System.out.println("- list top 50 (h)igest revenue movies");
      System.out.println("- (q)uit");
      System.out.print("Enter choice: ");
      menuOption = scanner.nextLine();
      
      if (!menuOption.equals("q"))
      {
        processOption(menuOption);
      }
    }
  }
  
  private void processOption(String option)
  {
    if (option.equals("t"))
    {
      searchTitles();
    }
    else if (option.equals("c"))
    {
      searchCast();
    }
    else if (option.equals("k"))
    {
      searchKeywords();
    }
    else if (option.equals("g"))
    {
      listGenres();
    }
    else if (option.equals("r"))
    {
      listHighestRated();
    }
    else if (option.equals("h"))
    {
      listHighestRevenue();
    }
    else
    {
      System.out.println("Invalid choice!");
    }
  }
  
  private void searchTitles()
  {
    System.out.print("Enter a title search term: ");
    String searchTerm = scanner.nextLine();
    
    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();
    
    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();
    
    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++)
    {
      String movieTitle = movies.get(i).getTitle();
      movieTitle = movieTitle.toLowerCase();
      
      if (movieTitle.indexOf(searchTerm) != -1)
      {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }
    
    // sort the results by title
    sortResults(results);
    
    // now, display them all to the user    
    for (int i = 0; i < results.size(); i++)
    {
      String title = results.get(i).getTitle();
      
      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;
      
      System.out.println("" + choiceNum + ". " + title);
    }
    
    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");
    
    int choice = scanner.nextInt();
    scanner.nextLine();
    
    Movie selectedMovie = results.get(choice - 1);
    
    displayMovieInfo(selectedMovie);
    
    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }
  
  private void sortResults(ArrayList<Movie> listToSort)
  {
    for (int j = 1; j < listToSort.size(); j++)
    {
      Movie temp = listToSort.get(j);
      String tempTitle = temp.getTitle();
      
      int possibleIndex = j;
      while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
      {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }
  
  private void displayMovieInfo(Movie movie)
  {
    System.out.println();
    System.out.println("Title: " + movie.getTitle());
    System.out.println("Tagline: " + movie.getTagline());
    System.out.println("Runtime: " + movie.getRuntime() + " minutes");
    System.out.println("Year: " + movie.getYear());
    System.out.println("Directed by: " + movie.getDirector());
    System.out.println("Cast: " + movie.getCast());
    System.out.println("Overview: " + movie.getOverview());
    System.out.println("User rating: " + movie.getUserRating());
    System.out.println("Box office revenue: " + movie.getRevenue());
  }

  public static void insertionSortWordList(ArrayList<String> words)
  {

    for (int j = 1; j < words.size(); j++)
    {
      String temp = words.get(j);
      int possibleIndex = j;
      while (possibleIndex > 0 && temp.compareTo(words.get(possibleIndex-1)) < 0)
      {
        words.set(possibleIndex,words.get(possibleIndex-1));
        possibleIndex--;
      }
      words.set(possibleIndex,temp);
    }
  }
  
  private void searchCast()
  {
    System.out.print("Enter an cast search term: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results

    Set<String> casts = new HashSet<String>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++)
    {
      String movieCast = movies.get(i).getCast();
      String movieCastLow = movieCast.toLowerCase();

      if (movieCastLow.indexOf(searchTerm) != -1) {
        //add the Cast to the set
        /**
        while (movieCast.indexOf(searchTerm) != -1) {
          casts.add(movieCast.substring(movieCast.indexOf(searchTerm), movieCast.substring(movieCast.indexOf(searchTerm)).indexOf("|")));
          movieCast = movieCast.substring(movieCast.substring(movieCast.indexOf(searchTerm)).indexOf("|") + 1);
        }**/

        String[] arr = movieCast.split("\\|");
        String[] arrLow = movieCastLow.split("\\|");
        for(int j = 0; j<arr.length;j++)
        {
          if(arrLow[j].contains(searchTerm)) casts.add(arr[j]);
        }
      }
    }
    // CONVERT SET TO ARRAYLIST
    ArrayList<String> results = new ArrayList<String>();
    results.addAll(casts);
    // sort the names
    insertionSortWordList(results);

    // now, display them all to the user
    for (int i = 0; i < results.size(); i++)
    {
      String name = results.get(i);

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + name);
    }

    System.out.println("Which actor would you want to see their movies in");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    String selectedCast = results.get(choice - 1);

    // arraylist to hold search results
    ArrayList<Movie> rez = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++)
    {
      String movieTitle = movies.get(i).getCast();
      movieTitle = movieTitle.toLowerCase();

      if (movieTitle.indexOf(selectedCast.toLowerCase()) != -1)
      {
        //add the Movie objest to the results list
        rez.add(movies.get(i));
      }
    }

    // sort the results by title
    sortResults(rez);
    for (int i = 0; i < rez.size(); i++)
    {
      String title = rez.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = rez.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void searchKeywords()
  {
    System.out.print("Enter a keyword: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++)
    {
      String movieTitle = movies.get(i).getKeywords();
      movieTitle = movieTitle.toLowerCase();

      if (movieTitle.indexOf(searchTerm) != -1)
      {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }

    // sort the results by Keyword
    sortResults(results);

    // now, display them all to the user
    for (int i = 0; i < results.size(); i++)
    {
      String title = results.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = results.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }
  
  private void listGenres()
  {
    try
    {
      FileReader fileReader = new FileReader("src/genres");
      BufferedReader bufferedReader = new BufferedReader(fileReader);

      String line;


      System.out.println("List of genres");

      int i = 0;
      while ((line = bufferedReader.readLine()) != null)
      {
        System.out.println("" + (i+1)+ ". " + line);
        i++;
      }

      System.out.println("What genre will you pick?");
      System.out.print("Enter number: ");

      int choice = scanner.nextInt();
      scanner.nextLine();

      bufferedReader.close();
      fileReader = new FileReader("src/genres");
      bufferedReader = new BufferedReader(fileReader);

      for(int k = 0;k<choice-1;k++)
      {
        bufferedReader.readLine();
      }
      String genre = bufferedReader.readLine();
      System.out.println(genre);//TEST STATMENTPRINT STUFF

      ArrayList<Movie> results = new ArrayList<>();

      for(Movie m: movies)
      {
        if(m.getGenres().contains(genre)) results.add(m);
      }


      sortResults(results);

      // now, display them all to the user
      for (int j = 0; j < results.size(); j++)
      {
        String title = results.get(j).getTitle();

        // this will print index 0 as choice 1 in the results list; better for user!
        int choiceNum = j + 1;

        System.out.println("" + choiceNum + ". " + title);
      }

      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");

      choice = scanner.nextInt();
      scanner.nextLine();

      Movie selectedMovie = results.get(choice - 1);

      displayMovieInfo(selectedMovie);

      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();



      bufferedReader.close();
    }
    catch(IOException exception)
    {
      // Print out the exception that occurred
      System.out.println("Stuff happened: " + exception.getMessage());
    }
  }

  public ArrayList<String> getAllGenre()
  {
      Set<String> genres = new HashSet<String>();
      for(Movie m:movies)
      {
        String[] gen = m.getGenres().split("\\|");
        for(String s:gen)
        {
          genres.add(s);
        }
      }
      ArrayList<String> results = new ArrayList<>();
      results.addAll(genres);
      insertionSortWordList(results);
      return results;
  }

  public static void writeArrListToFile(ArrayList<String> arr, String fileName) {
    try
    {
      FileWriter writer = new FileWriter(fileName);
      for(String str:arr) {
      writer.write(str + System.lineSeparator());
      }
      writer.close();
    }
    catch (IOException e) {
      System.out.println("Stuff happened: " + e.getMessage());
    }
  }


  
  private void listHighestRated()
  {

    ArrayList<Movie> results = new ArrayList<>();
    ArrayList<Movie> moviesCopy = makeCopy();
    insertionSortRate(moviesCopy);
    for(int i = moviesCopy.size() - 1; i>= moviesCopy.size()-50;i--)
    {
      results.add(moviesCopy.get(i));
    }


    // now, display them all to the user
    for (int j = 0; j < results.size(); j++)
    {
      String title = results.get(j).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = j + 1;

      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = results.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();


  }


  public static void insertionSortRate(ArrayList<Movie> m)
  {

    for (int j = 1; j < m.size(); j++)
    {
      double temp = m.get(j).getUserRating();
      int possibleIndex = j;
      while (possibleIndex > 0 && temp < m.get(possibleIndex - 1).getUserRating())
      {

        // elements[possibleIndex] = elements[possibleIndex - 1];
        Movie t = m.get(possibleIndex);
        m.set(possibleIndex,m.get(possibleIndex-1));
        m.set(possibleIndex-1,t);

        possibleIndex--;
      }

    }

  }
  public static void insertionSortRev(ArrayList<Movie> m) {

    for (int j = 1; j < m.size(); j++) {
      double temp = m.get(j).getRevenue();
      int possibleIndex = j;
      while (possibleIndex > 0 && temp < m.get(possibleIndex - 1).getRevenue()) {

        // elements[possibleIndex] = elements[possibleIndex - 1];
        Movie t = m.get(possibleIndex);
        m.set(possibleIndex, m.get(possibleIndex - 1));
        m.set(possibleIndex - 1, t);

        possibleIndex--;
      }

    }
  }

  private ArrayList<Movie> makeCopy()
  {
    ArrayList<Movie> arr = new ArrayList<>();
    for(Movie m:movies)
    {
      arr.add(m);
    }
    return arr;
  }
  
  private void listHighestRevenue()
  {
    ArrayList<Movie> results = new ArrayList<>();
    ArrayList<Movie> moviesCopy = makeCopy();
    insertionSortRev(moviesCopy);
    for(int i = moviesCopy.size() - 1; i>= moviesCopy.size()-50;i--)
    {
      results.add(moviesCopy.get(i));
    }


    // now, display them all to the user
    for (int j = 0; j < results.size(); j++)
    {
      String title = results.get(j).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = j + 1;

      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = results.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();

  }
  
  private void importMovieList(String fileName)
  {
    try
    {
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line = bufferedReader.readLine();
      
      movies = new ArrayList<Movie>();
      
      while ((line = bufferedReader.readLine()) != null) 
      {
        String[] movieFromCSV = line.split(",");
     
        String title = movieFromCSV[0];
        String cast = movieFromCSV[1];
        String director = movieFromCSV[2];
        String tagline = movieFromCSV[3];
        String keywords = movieFromCSV[4];
        String overview = movieFromCSV[5];
        int runtime = Integer.parseInt(movieFromCSV[6]);
        String genres = movieFromCSV[7];
        double userRating = Double.parseDouble(movieFromCSV[8]);
        int year = Integer.parseInt(movieFromCSV[9]);
        int revenue = Integer.parseInt(movieFromCSV[10]);
        
        Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
        movies.add(nextMovie);  
      }
      bufferedReader.close();
    }
    catch(IOException exception)
    {
      // Print out the exception that occurred
      System.out.println("Unable to access " + exception.getMessage());              
    }
  }
}