package consec;

public class SigmaS {
    double S;
    Steel m_s;

    // Конструктор класса

    SigmaS(Steel ms) {
        m_s = ms;
    }

    public double sigmas(double e) {
        S = 0.0;
        double e_2 = m_s.m_ec2;
        double e_0 = m_s.m_ec0;
        double e0 = m_s.m_e0;
        double e2 = m_s.m_e2;
        double Rc = m_s.m_Rc;
        double Rt = m_s.m_R;
        double E = m_s.m_E;
        if (e <= e_0 && e >= e_2) {
            S = Rc;
        } else if (e >= e_0 && e < 0.0) {
            S = E * e;
        } else if (e > 0.0 && e <= e0) {
            S = E * e;
        } else if (e >= e0 && e <= e2) {
            S = Rt;
        } else {
            S = 0.0;
        }
        return S;
    }
}