import java.util.Calendar;
import java.util.GregorianCalendar;

public class Bean {
    private static int beanNumber = 0;

    private int ID;
    private String name;
    private String category;
    private String main_category;
    private String currency;
    private Calendar deadline;
    private double goal;
    private Calendar launched;
    private double pledged;
    private String state;
    private int backers;
    private String country;
    private double usd_pledged;
    private double usd_pledged_real;
    private double usd_goal_real;


    private StringBuilder sb;
    private String line;

    public Bean(String str) throws BeanException {

        if (str.isEmpty()){throw new BeanException("Class Bean recieved empty string");}
        else {
            sb = new StringBuilder();
            line = str;
            parseValues();
        }
        beanNumber++;
    }


    private void parseValues(){
        int counter = 0;
        //ID
        while (Character.isDigit(line.charAt(counter))){
            sb.append(line.charAt(counter));
            counter++;
        }
        ID = Integer.parseInt(sb.toString());
        sb.setLength(0);
        counter++; //skip comma

        //name
        int quotesCounter = 0;
        if (line.charAt(counter) != ','){
            do{
                if (line.charAt(counter) == '\"') quotesCounter++;
                sb.append(line.charAt(counter));
                counter++;
            }
            while (line.charAt(counter) != ',' || (quotesCounter == 1 || quotesCounter % 2 == 1));

        }
        name = sb.toString();
        sb.setLength(0);
        counter++;

        //cut ID, name
        line = line.substring(counter);

        String[] values = line.split(",");
        category = values[0];
        main_category = values[1];
        currency = values[2];

        //deadline
        deadline = new GregorianCalendar();
        deadline.set(Calendar.YEAR,Integer.parseInt(values[3].substring(0,4)));
        deadline.set(Calendar.MONTH, Integer.parseInt(values[3].substring(5,6)));
        deadline.set(Calendar.DAY_OF_MONTH, Integer.parseInt(values[3].substring(8,9)));

        goal = Double.parseDouble(values[4]);

        launched = new GregorianCalendar();
        launched.set(Calendar.YEAR,Integer.parseInt(values[3].substring(0,4)));
        launched.set(Calendar.MONTH, Integer.parseInt(values[3].substring(5,6)));
        launched.set(Calendar.DAY_OF_MONTH, Integer.parseInt(values[3].substring(8,9)));


        pledged = Double.parseDouble(values[6]);
        state = values[7];
        backers = Integer.parseInt(values[8]);
        country = values[9];
        try {
            usd_pledged = Double.parseDouble(values[10]);
        }catch (NumberFormatException e){
            usd_pledged = -1;
        }
        try {
            usd_pledged_real = Double.parseDouble(values[11]);
        }catch (NumberFormatException e){
            usd_pledged_real = -1;
        }
        try {
            usd_goal_real = Double.parseDouble(values[12]);
        }catch (NumberFormatException e){
            usd_goal_real = -1;
        }

    }
    public static int getBeanNumber() {
        return beanNumber;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getMain_category() {
        return main_category;
    }

    public String getCurrency() {
        return currency;
    }

    public Calendar getDeadline() {
        return deadline;
    }

    public double getGoal() {
        return goal;
    }

    public Calendar getLaunched() {
        return launched;
    }

    public double getPledged() {
        return pledged;
    }

    public String getState() {
        return state;
    }

    public int getBackers() {
        return backers;
    }

    public String getCountry() {
        return country;
    }

    public double getUsd_pledged() {
        return usd_pledged;
    }

    public double getUsd_pledged_real() {
        return usd_pledged_real;
    }

    public double getUsd_goal_real() {
        return usd_goal_real;
    }
}
