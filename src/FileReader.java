import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.scene.layout.BorderPane;
import java.io.BufferedReader;
import java.io.File;

public class FileReader extends BorderPane{
    public static String FileChooser(){
        FileChooser fileChoose = new FileChooser();
        fileChoose.setTitle("Choose an image file");
        File data = fileChoose.showOpenDialog(null);
        if(data != null){
            String imagePath = data.toURI().toString();
            return imagePath;
        }//end of if
        else{
            return null;
        }
    }
}//end of FileReader
