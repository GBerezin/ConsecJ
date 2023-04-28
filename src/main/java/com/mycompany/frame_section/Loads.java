package com.mycompany.frame_section;

public class Loads {
    Jama.Matrix m_load; // Матрица нагрузок

    // Конструктор класса

    public Loads(Jama.Matrix load) {
        m_load = load;
        System.out.println("Loads:");
        load.print(6,6);
    }


    public Jama.Matrix getLoad() {
        return m_load;
    }
}
