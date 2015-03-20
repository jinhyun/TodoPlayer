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

    @Ignore @Test
    public void playControl_play(){
        TodoPlayer todoPlayer = new TodoPlayer();
        todoPlayer.playTodo(1);
    }

    @Ignore @Test
    public void playControl_stop(){
        TodoPlayer todoPlayer = new TodoPlayer();
        todoPlayer.stopTodo();
    }

    @Test
    public void viewPlayingTodo() {
        TodoPlayer todoPlayer = new TodoPlayer();
        todoPlayer.viewPlayingTodo();
    }

    @Test
    public void testViewTodoList() {
        TodoPlayer todoPlayer = new TodoPlayer();
        todoPlayer.viewTodoList();
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