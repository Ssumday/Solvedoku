package application;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.AbstractAction;
 
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class IconDemoApp extends JFrame{
	
	private JLabel photographLabel = new JLabel();
    private JToolBar buttonBar = new JToolBar();
     
    private String imagedir = "/Users/Nikita/Downloads/";
     
    private MissingIcon placeholderIcon = new MissingIcon();
     
    private String[] imageCaptions = { "Sudoku 1", "Sudoku 2"};
     
    
    private String[] imageFileNames = {"puzzle4.jpg", "puzzle5.png"};
     

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                IconDemoApp app = new IconDemoApp();
                app.setVisible(true);
            }
        });
    }
     
    
    public IconDemoApp() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Icon Demo: Please Select an Image");
         
        // A label for displaying the pictures
        photographLabel.setVerticalTextPosition(JLabel.BOTTOM);
        photographLabel.setHorizontalTextPosition(JLabel.CENTER);
        photographLabel.setHorizontalAlignment(JLabel.CENTER);
        photographLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
         
        // We add two glue components. Later in process() we will add thumbnail buttons
        // to the toolbar inbetween thease glue compoents. This will center the
        // buttons in the toolbar.
        buttonBar.add(Box.createGlue());
        buttonBar.add(Box.createGlue());
         
        add(buttonBar, BorderLayout.SOUTH);
        add(photographLabel, BorderLayout.CENTER);
         
        setSize(1000, 1000);
         
        // this centers the frame on the screen
        setLocationRelativeTo(null);
         
        // start the image loading SwingWorker in a background thread
        loadimages.execute();
    }
     
   
    private SwingWorker<Void, ThumbnailAction> loadimages = new SwingWorker<Void, ThumbnailAction>() {
         
        
        @Override
        protected Void doInBackground() throws Exception {
            for (int i = 0; i < imageCaptions.length; i++) {
                ImageIcon icon = new ImageIcon(imagedir + imageFileNames[i], imageCaptions[i]);
                //icon = createImageIcon("/Users/Nikita/Downloads/puzzle1.jpg", imageCaptions[i]);
                //icon = createImageIcon(imagedir + imageFileNames[i], imageCaptions[i]);
                 
                ThumbnailAction thumbAction;
                if(icon != null){
                     
                    ImageIcon thumbnailIcon = new ImageIcon(getScaledImage(icon.getImage(), 100, 100));
                     
                    thumbAction = new ThumbnailAction(icon, thumbnailIcon, imageCaptions[i]);
                     
                }else{
                    // the image failed to load for some reason
                    // so load a placeholder instead
                    thumbAction = new ThumbnailAction(placeholderIcon, placeholderIcon, imageCaptions[i]);
                }
                publish(thumbAction);
            }
            // unfortunately we must return something, and only null is valid to
            // return when the return type is void.
            return null;
        }
         
        /**
         * Process all loaded images.
         */
        @Override
        protected void process(List<ThumbnailAction> chunks) {
            for (ThumbnailAction thumbAction : chunks) {
                JButton thumbButton = new JButton(thumbAction);
                // add the new button BEFORE the last glue
                // this centers the buttons in the toolbar
                buttonBar.add(thumbButton, buttonBar.getComponentCount() - 1);
            }
        }
    };

    protected ImageIcon createImageIcon(String path,
            String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
     
  
    private Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }
     
  
    private class ThumbnailAction extends AbstractAction{

        private Icon displayPhoto;

        public ThumbnailAction(Icon photo, Icon thumb, String desc){
            displayPhoto = photo;
             
            // The short description becomes the tooltip of a button.
            putValue(SHORT_DESCRIPTION, desc);
             
            // The LARGE_ICON_KEY is the key for setting the
            // icon when an Action is applied to a button.
            putValue(LARGE_ICON_KEY, thumb);
        }
         
        /**
         * Shows the full image in the main area and sets the application title.
         */
        public void actionPerformed(ActionEvent e) {
            photographLabel.setIcon(displayPhoto);
            setTitle("Icon Demo: " + getValue(SHORT_DESCRIPTION).toString());
        }
    }

}
