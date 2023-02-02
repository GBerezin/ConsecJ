package consec;

import java.util.HashMap;

public class Steel extends Material { // Класс арматурная сталь

    // Конструктор базового класса
    public Steel(String grade) {
        super(grade);
        // Инициализация собственных полей
        HashMap<String, double[]> st = new HashMap<>();
        st.put("A240", new double[]{-0.025, -0.00105, 0.00105, 0.025, -210, 210, 200000});
        st.put("A400", new double[]{-0.025, -0.00175, 0.00175, 0.025, -350, 350, 200000});
        st.put("A500", new double[]{-0.025, -0.002, 0.002175, 0.025, -400, 435, 200000});
        double[] s = st.get(grade);
        m_grade = grade;
        m_ec2 = s[0];
        m_ec0 = s[1];
        m_e0 = s[2];
        m_e2 = s[3];
        m_Rc = s[4];
        m_R = s[5];
        m_E = s[6];
        System.out.print("Steel ");
        System.out.println(grade);
    }
}
