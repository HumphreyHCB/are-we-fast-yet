import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * ProcessAsyncFile
 */
public class ProcessAsyncFile {

    public static void main(String[] args) {
        getTop5HottestMethods(args[0]);
    }
    
    private static String[][] getTop5HottestMethods(String Filename) {
        String[][] top5 = new String[5][2];
        try {
            File myObj = new File(Filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                if (myReader.nextLine().equals("  ----------  -------  -------  ---")) {
                    for (int i = 0; i < 5; i++) {
                        String[] split = myReader.nextLine().split("\\s+");
                        top5[i][0] = split[4];
                        top5[i][1] = split[2];
                    }
                    break;
                }
             
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
        return top5;
    }




}