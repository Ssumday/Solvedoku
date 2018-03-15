import javafx.stage.FileChooser;
import javafx.scene.layout.BorderPane;
import java.io.BufferedReader;
import java.io.File;

public class FileReader extends BorderPane{
    public static void FileChooser(){
        FileChooser fileChoose = new FileChooser();
        fileChoose.setTitle("Choose a text file");

    }
}//end of FileReader
