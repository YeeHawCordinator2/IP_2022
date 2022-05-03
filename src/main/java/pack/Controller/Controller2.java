package pack.Controller;

import pack.Model.Model2for2x2;
import pack.Model.Model2for3x3;
import pack.View.Customs.CustomTextField;
import pack.View.View2;

import java.util.ArrayList;

public class Controller2 {

    private final View2 view;
    private ArrayList<CustomTextField> fieldList;
    private final ArrayList<Double> matrixCoefficients;
    private final boolean is2by2;

    private Model2for2x2 model2for2x2;
    private Model2for3x3 model2for3x3;

    public Controller2(View2 view) {
        this.view = view;
        is2by2 = view.getRb1().isSelected();
        matrixCoefficients = new ArrayList<>();
        fieldList = new ArrayList<>();
        transform();
    }

    private void transform() {
        if (is2by2) {
            fieldList = view.getFieldListRb1();
            copyArray();
            model2for2x2 = new Model2for2x2(matrixCoefficients);
        } else {
            fieldList = view.getFieldListRb2();
            copyArray();
            model2for3x3 = new Model2for3x3(matrixCoefficients);
        }
    }

    private void copyArray() {
        for (CustomTextField tf : fieldList) {
            matrixCoefficients.add(Double.parseDouble(tf.getText()));
        }
    }

    public double[] getEigenValues() {
        if (is2by2) {
            return model2for2x2.getEigenValues();
        } else {
            return model2for3x3.getEigenValues();
        }
    }

    public ArrayList<Double>[] getEigenVectors() {
        if (is2by2) {
            return model2for2x2.getEigenVectors();
        } else {
            return model2for3x3.getEigenVectors();
        }
    }


}
