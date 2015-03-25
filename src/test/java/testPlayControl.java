import org.junit.*;

import static org.junit.Assert.*;

/**
 * Created by Jinhyun on 2015. 3. 16..
 */
public class testPlayControl {
    @Test
    public void playControl() throws InterruptedException {
        TodoPlayer todoPlayer = new TodoPlayer();
        todoPlayer.playTodo(3);
        Thread.sleep(2000);
        todoPlayer.stopTodo();

        todoPlayer.viewPlayingTodo();
        todoPlayer.viewTodoList();
    }

    @Test
    public void testUpdateTodoListJson() throws InterruptedException {
        TodoPlayer todoPlayer = new TodoPlayer();
        todoPlayer.playTodo(3);
        Thread.sleep(2000); //계좌이체 항목, 2000 추가
        todoPlayer.stopTodo();
        todoPlayer.updateTodoListJson();
    }

    @Test
    public void playControl_play(){
        TodoPlayer todoPlayer = new TodoPlayer();
        todoPlayer.playTodo(1);
    }

    @Test
    public void playControl_stop() throws InterruptedException {
        TodoPlayer todoPlayer = new TodoPlayer();
        todoPlayer.playTodo(2);
        Thread.sleep(1000);
        todoPlayer.stopTodo();
    }

    @Test
    public void viewPlayingTodo() {
        TodoPlayer todoPlayer = new TodoPlayer();
        todoPlayer.playTodo(2);
        todoPlayer.viewPlayingTodo();
    }

    @Test
    public void testViewTodoList() {
        TodoPlayer todoPlayer = new TodoPlayer();
        todoPlayer.viewTodoList();
    }

    @Test
    public void testViewMenu() {
        TodoPlayer todoPlayer = new TodoPlayer();
        todoPlayer.viewMenu();
    }

    @Test
    public void testControlMenu(){
        TodoPlayer todoPlayer = new TodoPlayer();
        todoPlayer.viewTodoPlayer();
        todoPlayer.selectMenu("2");
        todoPlayer.viewTodoPlayer();
    }

    @Test
    public void testConvertSecondsFormat() {
        TodoPlayer todoPlayer = new TodoPlayer();
        assertEquals("2h30m5s", todoPlayer.convertSecondsFormat(9005000.000));
        assertEquals("5s", todoPlayer.convertSecondsFormat(5000.000));
        //
    }

    @Test
    public void testTodoJsonFileRead() {
        TodoPlayer todoPlayer1 = new TodoPlayer();
    }
}