package consec;

import java.util.List;

public class Array2 {

    double[][] m_array2;

    // Конструктор класса

    public Array2(List<List<String>> records) {
        m_array2 = array2(records);
    }

    public double[][] array2(List<List<String>> records) {
        double[][] darr2 = new double[records.size()][records.get(0).size()];
        int i = 0;
        for (List<String> record : records) {
            int j = 0;
            for (String s : record) {
                double ds = Double.parseDouble(s);
                darr2[i][j] = ds;
                j++;
            }
            i++;
        }
        return darr2;
    }

    public double[][] get_array2() {
        return m_array2;
    }
}
