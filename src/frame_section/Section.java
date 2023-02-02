package frame_section;

import Jama.Matrix;
import consec.Concrete;
import consec.Steel;


public class Section {
    Matrix m_GS;
    Matrix m_GC;
    Steel m_s;
    Concrete m_c;


    // Конструктор класса

    public Section(String SG, Matrix SD, String CG, Matrix CD) {
        m_GS = SD;
        m_GC = CD;
        m_s = new Steel(SG); // Арматурная сталь
        m_c = new Concrete(CG); //  Бетон
    }

    public Matrix get_GS() {
        return m_GS;
    }

    public Steel get_ms() {
        return m_s;
    }

    public Matrix get_GC() {
        return m_GC;
    }

    public Concrete get_mc() {
        return m_c;
    }
}

