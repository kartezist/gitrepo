import java.io.*;
import java.util.*;

public class Mainy {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to CSV Analyser v0.1");
        System.out.println("Enter full path to ks-projects-201801.csv:");
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        File file;

        if (!fileName.isEmpty()) file = new File(fileName);
        else file = new File("ks-projects-201801.csv");

        if (file.exists() && file.isFile()) System.out.println("file found");
        else {
            System.out.println("file not found");
            System.exit(101010);
        }

        List<Bean> beanList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String str = reader.readLine();
        while ((str = reader.readLine()) != null) {
            try {
                beanList.add(new Bean(str));
            } catch (BeanException e) {
                e.getMessage();
                e.printStackTrace();
            }
        }

        int[] arr = new int[7];
        for (int i = 1; i < beanList.size(); i++) {
            if (beanList.get(i).getState().equals("successful")){
                arr[beanList.get(i).getLaunched().get(Calendar.DAY_OF_WEEK)-1]++;
            }
        }
        System.out.println(Arrays.toString(arr));


        FileWriter fileWriter = new FileWriter("result.txt");
        fileWriter.write("пн: "+arr[1]+"  вт: "+arr[2]+"  ср: "+arr[3]+"  чт: "+arr[4]+"  пт: "+arr[5]+"  сб: "+arr[6]+"  вс: "+arr[0]);
        fileWriter.flush();
        fileWriter.close();
        System.out.println("the result is written to a <dir from which term is started>result.txt");
    }
}
