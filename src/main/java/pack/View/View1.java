package pack.View;


import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import pack.Controller.Controller1;
import pack.Model.Model1;
import pack.View.Customs.*;
import pack.View.GraphView.Graph;
import java.util.ArrayList;

import static pack.View.Customs.Custom.p;

public class View1 extends Pane implements iView {

    private CustomTextField[][] fieldListRb1, fieldListRb2;
    private GridPane fieldsPane;
    private CustomRadioButton rb1, rb2;
    private CustomButton btnStart, btnReset;
    private ToggleGroup group = new ToggleGroup();
    private Graph graph = new Graph();
    private String[] signsRb1 = {"X +", "Y ="};
    private String[] signsRb2 = {"X +", "Y +", "Z ="};

    private VBox vbUi;
    private VBox vbPo;

    private VBox vbLeft;
    private VBox vbRight;

    private VBox vbBackground;

    public View1() {
        rb1 = new CustomRadioButton("2 x 2");
        rb2 = new CustomRadioButton("3 x 3");
        rb1.setToggleGroup(group);
        rb2.setToggleGroup(group);
        btnStart = new CustomButton("START\nTHE\nMAGIK");
        btnStart.setDisable(true);
        btnReset = new CustomButton("RESET\nTHE\nMAGIK");

        fieldListRb1 = new CustomTextField[2][3];
        fieldListRb2 = new CustomTextField[3][4];
        fieldsPane = new GridPane();

        vbUi = new VBox();
        vbPo = new VBox();
        vbLeft = new VBox();
        vbRight = new VBox();
        vbBackground = new VBox();

        setVbUi(setHbRadios(this.rb1, this.rb2));
        setVbPo("Systems of linear equations");

        setVbLeft(setLeft(this.vbUi, setGraphPane(graph)));
        setVbRight(setRight(this.vbPo, setHbBottom(this.btnStart, this.btnReset)));

        setView1();
        setActions();
    }

    public void setView1() {
        this.setPrefSize(1050, 750);
        this.setStyle("-fx-background-color: #6F6F77;");    // Blue Grey
        this.getChildren().addAll(this.vbLeft, this.vbRight);
    }

    private void setVbLeft(VBox vbLeft) {
        this.vbLeft = vbLeft;
    }

    private void setVbRight(VBox vbRight) {
        this.vbRight = vbRight;
    }

    private void setVbUi(HBox hbRadios) {
        this.vbUi.setSpacing(5);
        this.vbUi.setPrefSize(500, 160);
        this.vbUi.setStyle("-fx-background-color: #333335");
        this.vbUi.getChildren().add(hbRadios);
    }

    private void setVbPo(String title) {
        this.vbBackground.setPrefSize(500, 580);
        BackgroundImage myBI= new BackgroundImage(new Image(p + "View2.png",520,580,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        this.vbBackground.setBackground(new Background(myBI));

        this.vbPo.setPrefSize(500, 595);
        this.vbPo.setSpacing(15);
        this.vbPo.setAlignment(Pos.TOP_CENTER);
        this.vbPo.setStyle("-fx-background-color: #333335");
        this.vbPo.getChildren().addAll(Custom.setTitle(title), this.vbBackground);
    }

    public void setActions() {
        rb1.setOnAction(event -> {
            this.btnStart.setDisable(false);
            fieldsPane = setFields(fieldListRb1, signsRb1, this.btnStart);
            this.vbUi.getChildren().clear();
            this.vbUi.getChildren().addAll(setHbRadios(rb1, rb2), fieldsPane);
        });

        rb2.setOnAction(event -> {
            this.btnStart.setDisable(false);
            fieldsPane = setFields(fieldListRb2, signsRb2, this.btnStart);
            this.vbUi.getChildren().clear();
            this.vbUi.getChildren().addAll(setHbRadios(rb1, rb2), fieldsPane);
        });
        this.btnStart.setOnAction(event -> {
            handleStart(rb1.isSelected());});
        this.btnReset.setOnAction(event -> { handleReset(); });
    }

    // doesn't set text in text field to 0, also only works for rb1?
    public void handleStart(boolean isRb1Selected) {
        if (isRb1Selected) {
            for (int i = 0; i < Model1.getN(); i++) {
                System.out.println(Model1.getN() + "what thwe fuck");
                for (int j = 0; j < fieldListRb1[0].length; j++) {
                    if (fieldListRb1[i][j].getText().equals("")) {
                        fieldListRb1[i][j].setText("0");
                    }
                    //   System.out.println("YO: " + fieldListRb1[i][j].getText());
                }
            }
        } else {
            for (int i = 0; i < fieldListRb2.length; i++) {
                for (int j = 0; j < fieldListRb2[0].length; j++) {
                    if (fieldListRb2[i][j].getText().equals("")) {
                        fieldListRb2[i][j].setText("0");
                    }
                    //   System.out.println("YO FROM 3x3: " + fieldListRb2[i][j].getText());
                }
            }
        }
        Controller1 controller = new Controller1(this);
        addOutput(controller);
//   controller.humptyDumptyRevival();
    }

    public void handleReset() {
        this.getChildren().clear();
        btnStart.setDisable(false);
        rb1.setSelected(false);
        rb2.setSelected(false);
        this.vbUi.getChildren().remove(fieldsPane);
        this.getChildren().addAll(this.vbLeft, this.vbRight);
    }

    public void addOutput(Controller1 controller1) {
        this.vbPo.getChildren().clear();
        setVbPo("Systems of linear equations");

        VBox vbOutput = new VBox();
        vbOutput.setSpacing(15);
        vbOutput.setPadding(new Insets(15));
        String[] sol = new String[controller1.getArraySize()];
        sol = controller1.getOutput();
        vbOutput.getChildren().add(Custom.setTitle("X = " + sol[0]));
        vbOutput.getChildren().add(Custom.setTitle("Y = " + sol[1]));
        if (sol.length>3) {
            vbOutput.getChildren().add(Custom.setTitle("Z = " + sol[2]));
        }
        vbOutput.getChildren().add(Custom.setTitle("Rank = " + controller1.getRank()));




        this.vbPo.getChildren().add(vbOutput);
    }

    public CustomRadioButton getRb1() {
        return rb1;
    }

    public ArrayList<CustomTextField> getFieldListRb1() {
        ArrayList<CustomTextField> fieldList = new ArrayList<>();
        for (CustomTextField[] tfArray: this.fieldListRb1) {
            for (CustomTextField tf: tfArray) {
                fieldList.add(tf);
            }
        }
        return fieldList;
    }

    public ArrayList<CustomTextField> getFieldListRb2() {
        ArrayList<CustomTextField> fieldList = new ArrayList<>();
        for (CustomTextField[] tfArray: this.fieldListRb2) {
            for (CustomTextField tf: tfArray) {
                fieldList.add(tf);
            }
        }
        return fieldList;
    }
}
