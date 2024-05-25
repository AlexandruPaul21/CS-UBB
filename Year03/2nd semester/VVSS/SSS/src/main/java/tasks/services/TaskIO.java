package tasks.services;

import javafx.collections.ObservableList;
import org.apache.log4j.Logger;
import tasks.model.LinkedTaskList;
import tasks.model.Task;
import tasks.model.TaskList;
import tasks.view.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TaskIO {
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS]");
    private static final String[] TIME_ENTITY = {" day"," hour", " minute"," second"};
    private static final int secondsInDay = 86400;
    private static final int secondsInHour = 3600;
    private static final int secondsInMin = 60;

    private static final Logger log = Logger.getLogger(TaskIO.class.getName());
    public static void write(TaskList tasks, OutputStream out) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(out);
        try {
            dataOutputStream.writeInt(tasks.size());
            for (Task t : tasks){
                dataOutputStream.writeInt(t.getTitle().length());
                dataOutputStream.writeUTF(t.getTitle());
                dataOutputStream.writeBoolean(t.isActive());
                dataOutputStream.writeInt(t.getRepeatInterval());
                if (t.isRepeated()){
                    dataOutputStream.writeLong(t.getStartTime().getTime());
                    dataOutputStream.writeLong(t.getEndTime().getTime());
                }
                else {
                    dataOutputStream.writeLong(t.getTime().getTime());
                }
            }
        }
        finally {
            dataOutputStream.close();
        }
    }
    public static void read(TaskList tasks, InputStream in)throws IOException {
        DataInputStream dataInputStream = new DataInputStream(in);
        try {
            int listLength = dataInputStream.readInt();
            for (int i = 0; i < listLength; i++){
                int titleLength = dataInputStream.readInt();
                String title = dataInputStream.readUTF();
                boolean isActive = dataInputStream.readBoolean();
                int interval = dataInputStream.readInt();
                Date startTime = new Date(dataInputStream.readLong());
                Task taskToAdd;
                if (interval > 0){
                    Date endTime = new Date(dataInputStream.readLong());
                    taskToAdd = new Task(title, startTime, endTime, interval);
                }
                else {
                    taskToAdd = new Task(title, startTime);
                }
                taskToAdd.setActive(isActive);
                tasks.add(taskToAdd);
            }
        }
        finally {
            dataInputStream.close();
        }
    }
    public static void writeBinary(TaskList tasks, File file)throws IOException{
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            write(tasks,fos);
        }
        catch (IOException e){
            log.error("IO exception reading or writing file");
        }
        finally {
            fos.close();
        }
    }

    public static void readBinary(TaskList tasks, File file) throws IOException{
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            read(tasks, fis);
        }
        catch (IOException e){
            log.error("IO exception reading or writing file");
        }
        finally {
            fis.close();
        }
    }
    public static void write(TaskList tasks, Writer out) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(out);
        Task lastTask = tasks.getTask(tasks.size()-1);
        for (Task t : tasks){
            bufferedWriter.write(getFormattedTask(t));
            bufferedWriter.write(t.equals(lastTask) ? ';' : '.');
            bufferedWriter.newLine();
        }
        bufferedWriter.close();

    }

    public static void read(TaskList tasks, Reader in)  throws IOException {
        BufferedReader reader = new BufferedReader(in);
        String line;
        Task t;
        while ((line = reader.readLine()) != null){
            t = getTaskFromString(line);
            tasks.add(t);
        }
        reader.close();

    }
    public static void writeText(TaskList tasks, File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        try {
            write(tasks, fileWriter);
        }
        catch (IOException e ){
            log.error("IO exception reading or writing file");
        }
        finally {
            fileWriter.close();
        }

    }
    public static void readText(TaskList tasks, File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        try {
            read(tasks, fileReader);
        }
        finally {
            fileReader.close();
        }
    }
    //// service methods for reading
    private static Task getTaskFromString (String line){
        boolean isRepeated = line.contains("from");//if contains - means repeated
        boolean isActive = !line.contains("inactive");//if isn't inactive - means active
        //Task(String title, Date time)   Task(String title, Date start, Date end, int interval)
        Task result;
        String title = getTitleFromText(line);
        if (isRepeated){
            Date startTime = getDateFromText(line, true);
            Date endTime = getDateFromText(line, false);
            int interval = getIntervalFromText(line);
            result = new Task(title, startTime, endTime, interval);
        }
        else {
            Date startTime = getDateFromText(line, true);
            result = new Task(title, startTime);
        }
        result.setActive(isActive);
        return result;
    }
    //
    private static int getIntervalFromText(String line){
        int days, hours, minutes, seconds;
        //[1 day 2 hours 46 minutes 40 seconds].
        //[46 minutes 40 seconds].
        //[46 minutes].
        int start = line.lastIndexOf("[");
        int end = line.lastIndexOf("]");
        String trimmed = line.substring(start+1, end);//returns interval without brackets -> 2 hours 46 minutes
        days = trimmed.contains("day") ? 1 : 0;
        hours = trimmed.contains("hour") ? 1 : 0;
        minutes = trimmed.contains("minute") ? 1 : 0;
        seconds = trimmed.contains("second") ? 1 : 0;

        int[] timeEntities = new int[]{days, hours, minutes, seconds};
        int i = 0, j = timeEntities.length-1;// positions of timeEntities available
        while (i != 1 && j != 1) {
            if (timeEntities[i] == 0) i++;
            if (timeEntities[j] == 0) j--;
        }

        String[] numAndTextValues = trimmed.split(" "); //{"46", "minutes", "40", "seconds"};
        for (int k = 0 ; k < numAndTextValues.length; k+=2){
            timeEntities[i] = Integer.parseInt(numAndTextValues[k]);
            i++;
        }

        int result = 0;
        for (int p = 0; p < timeEntities.length; p++){
            if (timeEntities[p] != 0 && p == 0){
                result+=secondsInDay*timeEntities[p];
            }
            if (timeEntities[p] != 0 && p == 1){
                result+=secondsInHour*timeEntities[p];
            }
            if (timeEntities[p] != 0 && p == 2){
                result+=secondsInMin*timeEntities[p];
            }
            if (timeEntities[p] != 0 && p == 3){
                result+=timeEntities[p];
            }
        }
        return result;
    }

    private static Date getDateFromText (String line, boolean isStartTime) {
        Date date = null;
        String trimmedDate; //date trimmed from whole string
        int start, end;

        if (isStartTime){
            start = line.indexOf("[");
            end = line.indexOf("]");
        }
        else {
            int firstRightBracket = line.indexOf("]");
            start = line.indexOf("[", firstRightBracket+1);
            end = line.indexOf("]", firstRightBracket+1);
        }
        trimmedDate = line.substring(start, end+1);
        try {
            date = simpleDateFormat.parse(trimmedDate);
        }
        catch (ParseException e){
            log.error("date parse exception");
        }
        return date;

    }
    private static String getTitleFromText(String line){
        int start = 1;
        int end = line.lastIndexOf("\"");
        String result = line.substring(start, end);
        result = result.replace("\"\"", "\"");
        return result;
    }


    ////service methods for writing
    private static String getFormattedTask(Task task){
        StringBuilder result = new StringBuilder();
        String title = task.getTitle();
        if (title.contains("\"")) title = title.replace("\"","\"\"");
        result.append("\"").append(title).append("\"");

        if (task.isRepeated()){
            result.append(" from ");
            result.append(simpleDateFormat.format(task.getStartTime()));
            result.append(" to ");
            result.append(simpleDateFormat.format(task.getEndTime()));
            result.append(" every ").append("[");
            result.append(getFormattedInterval(task.getRepeatInterval()));
            result.append("]");
        }
        else {
            result.append(" at ");
            result.append(simpleDateFormat.format(task.getStartTime()));
        }
        if (!task.isActive()) result.append(" inactive");
        return result.toString().trim();
    }

    public static String getFormattedInterval(int interval){
        if (interval <= 0) throw new IllegalArgumentException("Interval <= 0");
        StringBuilder sb = new StringBuilder();

        int days = interval/secondsInDay;
        int hours = (interval - secondsInDay*days) / secondsInHour;
        int minutes = (interval - (secondsInDay*days + secondsInHour*hours)) / secondsInMin;
        int seconds = (interval - (secondsInDay*days + secondsInHour*hours + secondsInMin*minutes));

        int[] time = new int[]{days, hours, minutes, seconds};
        int i = 0, j = time.length-1;
        while (time[i] == 0 || time[j] == 0){
            if (time[i] == 0) i++;
            if (time[j] == 0) j--;
        }

        for (int k = i; k <= j; k++){
            sb.append(time[k]);
            sb.append(time[k] > 1 ? TIME_ENTITY[k]+ "s" : TIME_ENTITY[k]);
            sb.append(" ");
        }
        return sb.toString();
    }


    public static void rewriteFile(ObservableList<Task> tasksList) {
        LinkedTaskList taskList = new LinkedTaskList();
        for (Task t : tasksList){
            taskList.add(t);
        }
        try {
            TaskIO.writeBinary(taskList, Main.savedTasksFile);
        }
        catch (IOException e){
            log.error("IO exception reading or writing file");
        }
    }
}
