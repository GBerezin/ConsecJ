package com.mycompany.consec;

public class Material {  // Класс материала
    String m_grade; // Имя материала
    double m_ec2; // Относительная деформация сжатия
    double m_ec0; // Относительная деформация сжатия
    double m_e0; // Относительная деформация растяжения
    double m_e2; // Относительная деформация растяжения
    double m_Rc; // Расчетное напряжение сжатию
    double m_R; // Расчетное напряжение растяжению
    double m_E;  // Модуль упругости

    // Конструктор класса
    Material(String grade) {
        m_grade = grade;
    }
}


