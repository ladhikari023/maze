import generators.MazeGenerator;
import gui.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import maze.Settings;
import solvers.MazeSolver;

import java.io.File;
import java.io.FileNotFoundException;


public class Main extends Application {
    private static Settings settings;


    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please give path to configuration file.");
            System.exit(1);
        } else {
            try {
                settings = new Settings(new File(args[0]));
                // = new Settings(new File("src/ex.txt"));
            } catch (FileNotFoundException e) {
                System.out.println("File not found.");
                System.exit(1);
            }
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        int windowSize = settings.getWindowSize();
        int cellSize = settings.getCellSize();

        FXMLLoader loader = new FXMLLoader(
                this.getClass().getResource("main.fxml")
        );

        Scene s = new Scene(loader.load(), windowSize, windowSize);
        Controller controller = loader.getController();
        controller.setSettings(settings);

        primaryStage.setScene(s);
        primaryStage.show();

        MazeGenerator gen = settings.getGenerationAlgo();
        MazeSolver solver = settings.getSolverAlgo();
        gen.setDisplay(controller);
        solver.setDisplay(controller);

        gen.setOnGenerationComplete(maze -> {
            solver.solve(maze);
        });

        new Thread(() -> gen.generate(windowSize/cellSize)).start();
    }
}
