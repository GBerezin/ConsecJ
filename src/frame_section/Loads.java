package frame_section;

import Jama.Matrix;

public class Loads {
    Matrix m_load; // Матрица нагрузок

    // Конструктор класса

    public Loads(Matrix load) {
        m_load = load;
        System.out.println("Loads:");
        load.print(6,6);
    }


    public Matrix getLoad() {
        return m_load;
    }
}
