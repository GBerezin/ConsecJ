package com.mycompany.consec;

import java.util.HashMap;

public class Concrete extends Material { // Класс бетон

    double m_ec1; // Относительная деформация сжатия
    double m_e1; // Относительная деформация растяжения
    double m_Rc1; // Расчетное напряжение сжатию
    double m_R1; // Расчетное напряжение растяжению

    // Конструктор базового класса
    public Concrete(String grade) {
        super(grade);
        // Инициализация собственных полей
        HashMap<String, double[]> co = new HashMap<>();
        co.put("B15", new double[]{-0.0035, -0.002, 0.0001, 0.00015, -8.5, 0.75, 24000});
        co.put("B20", new double[]{-0.0035, -0.002, 0.0001, 0.00015, -11.5, 0.9, 27500});
        co.put("B25", new double[]{-0.0035, -0.002, 0.0001, 0.00015, -14.5, 1.05, 30000});
        co.put("B30", new double[]{-0.0035, -0.002, 0.0001, 0.00015, -17, 1.15, 32500});
        co.put("B35", new double[]{-0.0035, -0.002, 0.0001, 0.00015, -19.5, 1.3, 34500});
        co.put("B40", new double[]{-0.0035, -0.002, 0.0001, 0.00015, -22, 1.4, 36000});
        co.put("B45", new double[]{-0.0035, -0.002, 0.0001, 0.00015, -25, 1.5, 37000});
        co.put("B50", new double[]{-0.0035, -0.002, 0.0001, 0.00015, -27.5, 1.6, 38000});
        co.put("B55", new double[]{-0.0035, -0.002, 0.0001, 0.00015, -30, 1.7, 39000});
        co.put("B60", new double[]{-0.0035, -0.002, 0.0001, 0.00015, -33, 1.8, 39500});
        co.put("B70", new double[]{-0.0033, -0.002, 0.0001, 0.00015, -37, 1.9, 41000});
        co.put("B80", new double[]{-0.0031, -0.002, 0.0001, 0.00015, -41, 2.1, 42000});
        co.put("B90", new double[]{-0.003, -0.002, 0.0001, 0.00015, -44, 2.15, 42500});
        co.put("B100", new double[]{-0.0028, -0.002, 0.0001, 0.00015, -47.5, 2.2, 43000});

        double[] c = co.get(grade);
        m_grade = grade;
        m_ec2 = c[0];
        m_ec0 = c[1];
        m_e0 = c[2];
        m_e2 = c[3];
        m_Rc = c[4];
        m_R = c[5];
        m_E = c[6];
        m_Rc1 = 0.6 * m_Rc;
        m_R1 = 0.6 * m_R;
        m_ec1 = m_Rc1 / m_E;
        m_e1 = m_R1 / m_E;
        System.out.print("Concrete ");
        System.out.println(grade);
    }
}
