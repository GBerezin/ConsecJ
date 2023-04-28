package com.mycompany.consec;

public class SigmaC {
    double S;
    Concrete m_c;

    // Конструктор класса

    SigmaC(Concrete mc) {
        m_c = mc;
    }

    double C;

    public double sigmac(double e) {
        C = 0.0;
        double e_2 = m_c.m_ec2;
        double e_0 = m_c.m_ec0;
        double e_1 = m_c.m_ec1;
        double e1 = m_c.m_e1;
        double e0 = m_c.m_e0;
        double e2 = m_c.m_e2;
        double Rc = m_c.m_Rc;
        double R_1 = m_c.m_Rc1;
        double R1 = m_c.m_R1;
        double Rt = m_c.m_R;
        double E = m_c.m_E;
        if (e <= e_0 && e >= e_2) {
            S = Rc;
        } else if (e > e_0 && e < e_1) {
            S = ((1 - R_1 / Rc) * (e - e_1) / (e_0 - e_1) + R_1 / Rc) * Rc;
        } else if (e >= e_1 && e < 0.0) {
            S = E * e;
        } else if (e > 0.0 && e <= e1 && Rt != 0.0) {
            S = E * e;
        } else if (e > e1 && e < e0 && Rt != 0.0) {
            S = ((1 - R1 / Rt) * (e - e1) / (e0 - e1) + R1 / Rt) * Rt;
        } else if (e >= e0 && e <= e2 && Rt != 0.0) {
            S = Rt;
        } else {
            S = 0.0;
        }
        return S;
    }
}