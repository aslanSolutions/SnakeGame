package group13;

import group13.backend.Field;
import group13.frontend.GameLoop;
import group13.frontend.Painter;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import java.io.IOException;

public class SnakeGameMain extends Application {
    private GraphicsContext context;
    private GameLoop loop;
    private Field field;
    @Override
    public void start(Stage primaryStage) throws IOException {
        Group root = new Group();
        Canvas canvas = new Canvas(700, 700);
        context = canvas.getGraphicsContext2D();

        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case UP -> field.up();
                case DOWN -> field.down();
                case LEFT -> field.left();
                case RIGHT -> field.right();
                case ENTER -> {
                    if (loop.isPaused()) {
                        reset();
                        (new Thread(loop)).start();
                    }
                }
            }
        });

        reset();

        root.getChildren().add(canvas);

        Scene scene = new Scene(root);

        primaryStage.setTitle("SnakeGame");
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        primaryStage.setScene(scene);
        primaryStage.show();

        (new Thread(loop)).start();
    }

    private void reset() {
        field = new Field();
        loop = new GameLoop(field, context);
        Painter.paint(field, context);
    }

    public static void main(String[] args) {
        launch();
    }
}