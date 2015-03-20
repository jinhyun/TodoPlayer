import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jinhyun on 2015. 3. 20..
 */
public class TodoPlayer {
    Logger logger = LoggerFactory.getLogger("");

    List<Todo> todoList = new ArrayList<>();
    Todo playingTodo;
    String fileName = "src/main/resources/todoList.json";
    StopWatch stopWatch = new StopWatch();

    TodoPlayer(){
        todoList = todoJsonFileRead();
    }

    public List<Todo> getTodoList() {
        return todoList;
    }

    private List <Todo> todoJsonFileRead() {
        try {
            JsonReader jsonReader = new JsonReader(new FileReader(fileName));
            JsonElement jsonElement = new JsonParser().parse(jsonReader);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonArray jsonArray = jsonObject.getAsJsonArray("todoList");

            for (int i=0; i<jsonArray.size(); i++){
                JsonObject todoObj = (JsonObject) jsonArray.get(i);
                System.out.println(todoObj.get("contents").getAsString());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return todoList;
    }

    public void viewTodoList() {
        List <Todo> todoList = getTodoList();

        StringBuffer sb = new StringBuffer();
        String comma = ", ";
        String jum = ". ";

        String expectTime;
        String totalTime;

        for (Todo todo : todoList){
            sb.append(todo.isDone() ? "[v]" : "[ ]");
            sb.append(todo.getNo());
            sb.append(jum);
            sb.append(todo.getContents());
            sb.append(comma);

            totalTime = convertSecondsFormat(todo.getTotalTimeSeconds());
            expectTime = convertSecondsFormat(todo.getExpectTimeSeconds());

            sb.append(totalTime + "(" + expectTime + ")");
            sb.append("\n");
//            sb.append(todo.getExpectDate());
        }
        System.out.println(sb.toString());
    }

    public void viewPlayingTodo() {
        if (playingTodo == null) {
            throw new NullPointerException();
        }

        StringBuffer sb = new StringBuffer();
        String comma = ", ";
        String jum = ". ";
        String line = "===================================";
        String nextLine = "\n";

        String expectTime;
        String totalTime;

        sb.append(line);
        sb.append(nextLine);
        sb.append(playingTodo.getNo());
        sb.append(jum);
        sb.append(playingTodo.getContents());
        sb.append(comma);

        totalTime = convertSecondsFormat(playingTodo.getTotalTimeSeconds());
        expectTime = convertSecondsFormat(playingTodo.getExpectTimeSeconds());

        sb.append(totalTime + "(" + expectTime + ")");
        sb.append(nextLine);
        sb.append(line);
        sb.append(nextLine);
        System.out.println(sb.toString());
    }

    public String convertSecondsFormat(double time) { // 9005 000
        // 3600 + 3600 + 1800 + 5 = 2h30m5s
        double HOUR_SECONDS = 3600 * 1000;
        double MINUTE_SECONDS = 60 * 1000;
        double SECONDS = 1000;

        int hour = (int) (time / HOUR_SECONDS);
        double hourMils = hour * HOUR_SECONDS;
        double remainTime = time - hourMils;

        int minute = (int) (remainTime / MINUTE_SECONDS);
        double minuteMils = minute * MINUTE_SECONDS;
        remainTime = remainTime - minuteMils;

        int seconds = (int) (remainTime / SECONDS);

//            Object[] obj = {time, hour, minute, seconds};
//            logger.debug("time: {}, hour: {}, minute: {}, seconds: {}", obj);

        String hourStr = (hour == 0) ? "" : hour + "h";
        String minuteStr = (minute == 0) ? "" : minute + "m";
        String secondsStr = (seconds == 0) ? "" : seconds + "s";

        String result;

        result = (hourStr.equals("") && minuteStr.equals("") &&
                secondsStr.equals("")) ?
                "0m0s" : hourStr + minuteStr + secondsStr;

        return result;
    }

    public void playTodo(int no) {
        setPlayingTodo(no);
        stopWatch.start();
    }

    public void stopTodo() {
        stopWatch.stop();
        playingTodo.setTotalTimeSeconds(stopWatch.getTotalTimeMillis());
    }
    public void setPlayingTodo(int no) {
        for (Todo todo : todoList){
            if (todo.getNo() == no) {
                this.playingTodo = todo;
                break;
            }
        }
    }

    private class Todo {
        private int no;
        private String contents;
        private String expectDate;
        private double expectTimeSeconds;
        private double totalTimeSeconds;
        private boolean isDone;
        private String doneDate;

        public void setNo(int no) {
            this.no = no;
        }

        public void setContents(String contents) {
            this.contents = contents;
        }

        public void setExpectDate(String expectDate) {
            this.expectDate = expectDate;
        }


        public void setExpectTimeSeconds(double expectTimeSeconds) {
            this.expectTimeSeconds = expectTimeSeconds;
        }

        public void setTotalTimeSeconds(double totalTimeSeconds) {
            this.totalTimeSeconds = totalTimeSeconds;
        }


        public void setIsDone(boolean isDone) {
            this.isDone = isDone;
        }

        public void setDoneDate(String doneDate) {
            this.doneDate = doneDate;
        }

        public int getNo() {
            return no;
        }

        public String getContents() {
            return contents;
        }

        public String getExpectDate() {
            return expectDate;
        }

        public double getExpectTimeSeconds() {
            return expectTimeSeconds;
        }

        public double getTotalTimeSeconds() {
            return totalTimeSeconds;
        }

        public boolean isDone() {
            return isDone;
        }

        public String getDoneDate() {
            return doneDate;
        }

        public void setInitData() {
            // 테스트 json 파일 초기화
        }
    }
}
