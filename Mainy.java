import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Mainy {
    public static void main(String[] args) throws IOException {
        System.out.println("Вас приветствует CSV Analyser v0.1");
        System.out.println("Ищем количество успешных проектов на кикстартере в зависимости от дня недели старта");
        System.out.println("Укажите путь до ks-projects-201801.csv или жмите ввод:");
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        File file;

        if (!fileName.isEmpty()) file = new File(fileName);
        else file = new File("ks-projects-201801.csv");

        if (file.exists() && file.isFile()) System.out.println("файл найден");
        else {
            System.out.println("файл не найден");
            System.exit(101010);
        }

        scanner = new Scanner(file);

        List<List<String>> list = new ArrayList<>();

        int linesCounter = 0;
        while (scanner.hasNextLine()) {
            scanner.nextLine();
            linesCounter++;
        }
        scanner.close();
        //парсим с ошибками изза сепаратора внутри кавычек
        scanner = new Scanner(file);
        while (scanner.hasNextLine()) list.add(new ArrayList<>(Arrays.asList(scanner.nextLine().split(","))));
        scanner.close();

        //исправляем ошибки
        boolean modified = false;
        for (int i = 1; i < list.size(); i++) {
            for (int j = 1; j < list.get(i).size(); j++) {
                if (modified) {
                    modified = false;
                    break;
                }
                //если в текущей ячейке есть кавычка
                if (list.get(i).get(j).contains("\"")) {
                    //ищем последнюю ячейку с кавычками, конкатенируем все, что между включительно и чистим
                    //-6 изза артефакта вида "N,0""" в исх. файле
                    for (int k = list.get(i).size()-6; k > j; k--) {
                        if (list.get(i).get(k).contains("\"")){
                            for (int l = 0; l < k-j; l++) {
                                list.get(i).set(j, list.get(i).get(j)+","+list.get(i).get(j+1));
                                list.get(i).remove(j+1);
                            }
                            modified = true; //проброс на следующую i
                            break;
                        }
                    }
                    if (list.get(i).get(11).equals("\"N")) {
                        list.get(i).set(11,list.get(i).get(11)+","+list.get(i).get(12));
                        list.get(i).remove(12);
                    }
                }
            }
        }


        Calendar calendar = new GregorianCalendar();
        int[] arr = new int[7];
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).get(9).equals("successful")){
                calendar.set(Calendar.YEAR,Integer.parseInt(list.get(i).get(7).substring(0,4)));
                calendar.set(Calendar.MONTH, Integer.parseInt(list.get(i).get(7).substring(5,6)));
                calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(list.get(i).get(7).substring(8,9)));
                arr[calendar.get(Calendar.DAY_OF_WEEK)-1]++;
            }
        }
        System.out.println(Arrays.toString(arr));


        FileWriter fileWriter = new FileWriter("result.txt");
        fileWriter.write("пн: "+arr[1]+"  вт: "+arr[2]+"  ср: "+arr[3]+"  чт: "+arr[4]+"  пт: "+arr[5]+"  сб: "+arr[6]+"  вс: "+arr[0]);
        fileWriter.flush();
        fileWriter.close();
        System.out.println("запись в файл result.txt произведена");


//        //отладочный
//        int headersCounter = list.get(0).size();
//        int counter = 0;
//        for (int i = 1; i < list.size(); i++ ) {
//            counter++;
//            if (list.get(i).size() != headersCounter){
//
//                System.out.println("ожидаемое: "+headersCounter+"   фактическое:"+list.get(i).size());
//                System.out.println("строка: "+counter);
//                System.out.println();
//                for (int j = 0; j < list.get(i).size(); j++) {
//                    System.out.print(j+list.get(i).get(j)+"; ");
//
//                }
//                System.out.println();
//
//            }
//        }
//        System.out.println("посчитано строк: "+linesCounter+"\n"+
//                            "считано в лист: "+list.size());
//        for (int i = 0; i < 10; i++) {
//            System.out.println(Arrays.toString(list.get(i).toArray()));
//
//        }
    }
}
