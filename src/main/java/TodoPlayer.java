import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jinhyun on 2015. 3. 20..
 */
public class TodoPlayer {
    Logger logger = LoggerFactory.getLogger("");

    List<Todo> todoList = new ArrayList<>();
    Todo playingTodo = new Todo();
    String fileName = "src/main/resources/todoList.json";
    StopWatch stopWatch = new StopWatch();

    TodoPlayer(){
        todoList = todoJsonFileRead();
    }

    public List<Todo> getTodoList() {
        return todoList;
    }

    private List <Todo> todoJsonFileRead() {
        TodoListJson todoListJson = new TodoListJson();
        try {
            Gson gson = new Gson();
            todoListJson =
                    gson.fromJson(new FileReader(fileName),TodoListJson.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return todoListJson.getTodoList();
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

    public void viewMenu() {
        StringBuffer sb = new StringBuffer();
        sb.append("정지:S, 완료:D, 번호입력시 자동시작합니다\n");
        sb.append(">>");
        System.out.println(sb);
    }

    public void selectMenu(String userInput) {
        if (userInput.equals("S") || userInput.equals("s")){
            stopTodo();
        } else if (userInput.equals("D") || userInput.equals("d")){
            // TODO: feature doneTodo()
        } else {
            // TODO: validation
            playTodo(Integer.parseInt(userInput));
        }
    }

    public void viewTodoPlayer(){
        if (playingTodo != null) {
            viewPlayingTodo();
        }
        viewTodoList();
        viewMenu();
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

    public void updateTodoListJson() {
        Gson gson = new Gson();
        TodoListJson todoListJson = new TodoListJson();
        todoListJson.setTodoList(todoList);
        String json = gson.toJson(todoListJson, TodoListJson.class);
        System.out.println(json);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            bw.write(json);
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPlayingTodo(int no) {
        for (Todo todo : todoList){
            if (todo.getNo() == no) {
                this.playingTodo = todo.clone();
                break;
            }
        }
    }

    private class TodoListJson {
        List <Todo> todoList = new ArrayList<>();

        public List <Todo> getTodoList() {
            return todoList;
        }

        public void setTodoList(List<Todo> todoList) {
            this.todoList = todoList;
        }
    }

    private class Todo implements Cloneable {
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

        public Todo clone() {
            Todo todo = null;
            try {
                todo = (Todo)super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return todo;
        }
    }
}
