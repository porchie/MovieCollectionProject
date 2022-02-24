import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class KevinBaconRunner {
    public static void main(String[] args) throws IOException {

      // BaconDegreeMaker b =new BaconDegreeMaker("src/movie_data.txt");
      // b.writeMap();

        /**FileReader reader = new FileReader("src/KevinBaconDegreeMap");
        BufferedReader buffread = new BufferedReader(reader);
        String line = "";
        int count =0;
        while((line = buffread.readLine()) != null)
        {
            count++;
        }
        System.out.println(count);
          //OMG ITS 13 LINES I CAN DO STUFF NOW
**/


       FileReader reader2 = new FileReader("src/actorsList");
        BufferedReader bufferedReader = new BufferedReader(reader2);
        String actors = bufferedReader.readLine();
        reader2.close();
        bufferedReader.close();
        String[] actorsArr = actors.split(",");
       // System.out.println(actorsArr.length);

        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("Enter an actor's name or (q) to quit");
            FileReader reader = new FileReader("src/KevinBaconDegreeMap");
            BufferedReader buffread = new BufferedReader(reader);
            String line = "";
            ArrayList<String> alreadyRead = new ArrayList<>();
            int lineNum = 0;

            String choice = scanner.nextLine();
            if (choice.equals("q")) break;

            ArrayList<String> matches = new ArrayList<>();
            for(String str:actorsArr)
            {
                if(str.toLowerCase().contains(choice.toLowerCase())) matches.add(str);
            }

            for (int i = 0; i < matches.size(); i++)
            {

                String name = matches.get(i);

                // this will print index 0 as choice 1 in the results list; better for user!
                int choiceNum = i + 1;

                System.out.println("" + choiceNum + ". " + name);
            }
            if(matches.size()==0)
            {
                System.out.println("No results found");
            }
            else {
                System.out.println("Which actor do you want to pick");
                System.out.println("Enter a number");

                int num = scanner.nextInt();
                scanner.nextLine();

                choice = matches.get(num - 1);

                while ((line = buffread.readLine()) != null) {
                    lineNum++;
                    alreadyRead.add(line);
                    if (line.contains(choice)) break;
                }
                System.out.println("Actor chosen: "+ choice);
                if (line == null) System.out.println("NOT WITHIN 6 DEGREES OR SPELT WRONG");
                else {
                    String display = getPath(alreadyRead, choice, lineNum);

                    System.out.println(display);
                    System.out.println("Bacon Number of: " + lineNum / 2);
                }
            }
            reader.close();
            buffread.close();
        }
        scanner.close();

    }

    public static String getPath(ArrayList<String> arr, String name,int lineNum)
    {
        if(name.equals("Kevin Bacon")) return name;
        int reference = 0;
        ArrayList<String> print = new ArrayList<>();
        String display = "";
        print.add(name);
        String[] actors = arr.get(arr.size()-1).split("/");
        for(String s:actors)
        {
            String[] strArr = s.split(";");

            if(s.contains(name)&&strArr[2].equals(name))
            {
               reference = Integer.parseInt(strArr[1].substring(1));
               break;
            }
        }
        int j = 1;
        for(int i = arr.size()-2;i>0;i--)
        {

            int currentLine = lineNum-j;
            String[] castArr = arr.get(i).split("/");
            String[] actor = castArr[reference].split(";");

            print.add(actor[2]);

            //System.out.println(castArr[reference]);
            reference = Integer.parseInt(actor[1].substring(1));
            j++;
        }

        for(int k = 0;k<print.size();k++)
        {
            display += print.get(k) + " -> ";
        }
        return display + "Kevin Bacon";
    }
}
