package com.mycompany.frame_section;

import com.mycompany.consec.Concrete;
import com.mycompany.consec.Steel;


public class Section {
    Jama.Matrix m_GS;
    Jama.Matrix m_GC;
    Steel m_s;
    Concrete m_c;


    // Конструктор класса

    public Section(String SG, Jama.Matrix SD, String CG, Jama.Matrix CD) {
        m_GS = SD;
        m_GC = CD;
        m_s = new Steel(SG); // Арматурная сталь
        m_c = new Concrete(CG); //  Бетон
    }

    public Jama.Matrix get_GS() {
        return m_GS;
    }

    public Steel get_ms() {
        return m_s;
    }

    public Jama.Matrix get_GC() {
        return m_GC;
    }

    public Concrete get_mc() {
        return m_c;
    }
}

