import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class KevinBaconRunner {
    public static void main(String[] args) throws IOException {

       //BaconDegreeMaker b =new BaconDegreeMaker("src/movie_data.txt");
       //DONOTUNCOMMENTTHISPLSIDONTWANTTOSUFFER****b.writeMap();

        /**FileReader reader = new FileReader("src/KevinBaconDegreeMap");
        BufferedReader buffread = new BufferedReader(reader);
        String line = "";
        int count =0;
        while((line = buffread.readLine()) != null)
        {
            count++;
        }
        System.out.println(count);
         **/ //OMG ITS 13 LINES I CAN DO STUFF NOW
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
            while ((line = buffread.readLine()) != null) {
                lineNum++;
                alreadyRead.add(line);
                if (line.contains(choice)) break;
            }
            if (line == null) System.out.println("NOT WITHIN 6 DEGREES OR SPELT WRONG");
            else System.out.println("Bacon Number of: " + lineNum / 2);
            System.out.println(getPath(alreadyRead, choice, lineNum));
            reader.close();
            buffread.close();
        }
        scanner.close();

    }

    public static String getPath(ArrayList<String> arr, String name,int lineNum)
    {
        int reference = 0;
        ArrayList<String> print = new ArrayList<>();
        String display = "";
        print.add(name);
        String[] actors = arr.get(arr.size()-1).split("/");
        for(String s:actors)
        {
            if(s.contains(name))
            {
               if(lineNum%2==0)
               {
                   String subStr = s.substring(s.indexOf(";A")+2);
                   reference = Integer.parseInt(subStr.substring(0,subStr.indexOf(";")));
               }
               else
               {
                   String subStr = s.substring(s.indexOf(";B")+2);
                   reference = Integer.parseInt(subStr.substring(0,subStr.indexOf(";")));
               }
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
