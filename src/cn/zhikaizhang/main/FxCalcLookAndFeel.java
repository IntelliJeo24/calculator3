package cn.zhikaizhang.main;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Map;

/**
 Control the appearance
 */
public class FxCalcLookAndFeel {

    public static String[] name = new String[]{"POINT", "EE", "ADD", "MINUS", "MULTIPLY", "DIVIDE", "SIN", "COS", "TAN", "LOG", "LN", "LEFT_PAREN", "RIGHT_PAREN",
            "NEGATIVE", "CLEAR", "CLEAR_ALL", "BACK", "EQUAL", "PI", "E", "POWER", "FACTORIAL", "SQUARE", "SQRT", "RECIPROCAL", "EXPONENT"};

    public static Map<String[], String[]> touchEffect = new HashMap<>();

    public static Map<String, String[]> touchEffect2 = new HashMap<>();

    public static Map<String, String> buttonImg = new HashMap<>();
// Ορισμός των χρωμάτων των κουμπιών
    static{
        touchEffect.put(new String[]{"#BUTTON_POINT", "#BUTTON_EE", "#BUTTON0", "#BUTTON1", "#BUTTON2", "#BUTTON3", "#BUTTON4", "#BUTTON5", "#BUTTON6", "#BUTTON7", "#BUTTON8", "#BUTTON9"},
                new String[]{"BFD5C9", "ede6b9"});
        touchEffect.put(new String[]{"#BUTTON_SIN", "#BUTTON_COS", "#BUTTON_TAN", "#BUTTON_POWER", "#BUTTON_FACTORIAL", "#BUTTON_LOG", "#BUTTON_LN", "#BUTTON_PI", "#BUTTON_E", "#BUTTON_SQUARE", "#BUTTON_SQRT", "#BUTTON_EXPONENT", "#BUTTON_RECIPROCAL"},
                new String[]{"81B9BF", "ede6b9"});
        touchEffect.put(new String[]{"#BUTTON_LEFT_PAREN", "#BUTTON_RIGHT_PAREN", "#BUTTON_NEGATIVE"}, new String[]{"F8F3E2", "ede6b9"});
        touchEffect.put(new String[]{"#BUTTON_ADD", "#BUTTON_MINUS", "#BUTTON_MULTIPLY", "#BUTTON_DIVIDE"}, new String[]{"05A3A4", "ede6b9"});
        touchEffect.put(new String[]{"#BUTTON_CLEAR_ALL", "#BUTTON_CLEAR", "#BUTTON_BACK"}, new String[]{ "B35A20", "ede6b9"});
        touchEffect.put(new String[]{"#BUTTON_EQUAL"}, new String[]{"E8891D", "ede6b9"});
// ορισμός κουμπιών ελαχιστοποίησης και close
        touchEffect2.put("#iconify", new String[]{"/images/iconify1.png", "/images/iconify2.png"});
        touchEffect2.put("#close", new String[]{"/images/close1.png", "/images/close2.png"});
// εισαγωγή της ανάλογης εικόνας στο ανάλογο κουμπί
        for(int i = 0; i <= 9; i++){
            buttonImg.put("#BUTTON" + i, "/images/" + i + ".png");
        }
        for(int i = 0; i < name.length; i++){
            buttonImg.put("#BUTTON_" + name[i], "/images/" + name[i] + ".png");
        }
    }

    public static void attach(Scene scene){

        attachImage2Buttons(scene);
        addTouchEffect2Buttons(scene);
        addTouchEffect2Labels(scene);
    }

    private static void attachImage2Buttons(Scene scene){
        Map<String, String> buttonImg = FxCalcLookAndFeel.buttonImg;
        for(String id : buttonImg.keySet()){
            Button button = (Button)scene.lookup(id);
            attachImage2Button(button, buttonImg.get(id), id.equals("#BUTTON_EQUAL")?48:27);
        }
    }
// Hover effect στα κουμπιά

    private static void addTouchEffect2Buttons(Scene scene){

        Map<String[], String[]> touchEffect = FxCalcLookAndFeel.touchEffect;

        for(String[] ids : touchEffect.keySet()){
            final String[] effects = touchEffect.get(ids);
            for(int i = 0; i < ids.length; i++){
                final Button button = (Button)scene.lookup(ids[i]);
                button.setStyle("-fx-background-color: #" + effects[0]);
                button.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        button.setStyle("-fx-background-color: #" + effects[1]);
                    }
                });
                button.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        button.setStyle("-fx-background-color: #" + effects[0]);
                    }
                });
            }
        }

        final Map<String, String[]> touchEffect2 = FxCalcLookAndFeel.touchEffect2;
        for(final String id : touchEffect2.keySet()){
            final Button button = (Button)scene.lookup(id);
            attachImage2Button(button, touchEffect2.get(id)[0], 10);
            button.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    attachImage2Button(button, touchEffect2.get(id)[1], 10);
                }
            });
            button.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    attachImage2Button(button, touchEffect2.get(id)[0], 10);
                }
            });
        }
    }

    // hover effect στα labels

    private static void addTouchEffect2Labels(Scene scene){

        for(int i = 1; i <= 4; i++){

            final Rectangle rectangle = (Rectangle)scene.lookup("#rect" + i);
            final Label label = (Label)scene.lookup("#LABEL" + i);
            if(i <= 2){
                rectangle.setStyle("-fx-fill: #ced7d8");
                label.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        rectangle.setStyle("-fx-fill: #E8891D");
                    }
                });
                label.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        rectangle.setStyle("-fx-fill: #ced7d8");
                    }
                });
            }else{
                rectangle.setStyle("-fx-fill: #ced7d8");
                label.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        rectangle.setStyle("-fx-fill: #E8891D");
                    }
                });
                label.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        rectangle.setStyle("-fx-fill: #ced7d8");
                    }
                });
            }
        }
    }

    private static void attachImage2Button(Button button, String imgPath, int width){
        button.setText(null);
        ImageView imageView = new ImageView(new Image(FxCalcApplication.class.getResourceAsStream(imgPath)));
        imageView.setFitWidth(width);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
        button.setGraphic(imageView);
    }


}
