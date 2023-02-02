package consec;

import Jama.Matrix;
import frame_section.Loads;
import frame_section.Section;
import frame_section.Stiffness;

import javax.swing.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

class Consec {

    public static void main(String[] args) throws IOException {
        String[] analysisArray = {"Frame_section", "Slab_member"};
        String selectedValue = (String) JOptionPane.showInputDialog
                (null, "Select analysis type from the list below:", "Analysis",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        analysisArray,
                        analysisArray[0]);
        if (Objects.equals(selectedValue, "Frame_section")) {
            frame_sec();
        } else {
            JOptionPane.showMessageDialog(null, "In progress...");
        }
    }

    public static void frame_sec() throws IOException {
        String datfile = "./src/resources/data.csv";
        List2 datl = new List2(datfile);
        List<List<String>> list_dat = datl.get_list2();
        int numberiter = Integer.parseInt(list_dat.get(0).get(0)); // Число итераций
        String steelgrade = list_dat.get(0).get(1); // Класс арматуры
        String concretegrade = list_dat.get(0).get(2); // Класс бетона
        // Нагрузки
        String lofile = "./src/resources/loads.csv";
        List2 lol = new List2(lofile);
        List<List<String>> list_lo = lol.get_list2();
        Array2 loa = new Array2(list_lo);
        Matrix load = new Matrix(loa.get_array2()).transpose();
        // Арматура
        String stfile = "./src/resources/steel.csv";
        List2 stl = new List2(stfile);
        List<List<String>> list_st = stl.get_list2();
        Array2 sta = new Array2(list_st);
        Matrix st = new Matrix(sta.get_array2());
        // Бетон
        String confile = "./src/resources/concrete.csv";
        List2 conl = new List2(confile);
        List<List<String>> list_con = conl.get_list2();
        Array2 cona = new Array2(list_con);
        Matrix con = new Matrix(cona.get_array2());
        // Сечение
        Section section = new Section(steelgrade, st, concretegrade, con);
        Matrix GS = section.get_GS();
        Steel ms = section.get_ms();
        Matrix Es = new Matrix(GS.getRowDimension(), 1, ms.m_E);
        Matrix GC = section.get_GC();
        Concrete mc = section.get_mc();
        Matrix Ec = new Matrix(GC.getRowDimension(), 1, mc.m_E);
        Loads loads = new Loads(load);
        Matrix F = loads.getLoad();
        Matrix vs = new Matrix(GS.getRowDimension(), 1, 1.0);
        Stiffness stiffnessS = new Stiffness(GS, Es, vs);
        Matrix Ds = stiffnessS.getD();
        Matrix vc = new Matrix(GC.getRowDimension(), 1, 1.0);
        Stiffness stiffnessC = new Stiffness(GC, Ec, vc);
        Matrix Dc = stiffnessC.getD();
        Matrix D = Ds.plus(Dc);
        Solution solution = new Solution(D, F);
        Matrix u = solution.sol(D, F);
        int i;
        for (i = 0; i < numberiter; i++) {
            Matrix v_s = calcS(u, GS, ms, Es, vs);
            Stiffness stiffness_S = new Stiffness(GS, Es, v_s);
            Matrix D_s = stiffness_S.getD();
            Matrix v_c = calcC(u, GC, mc, Ec, vc);
            Stiffness stiffness_C = new Stiffness(GC, Ec, v_c);
            Matrix D_c = stiffness_C.getD();
            Matrix D_ = D_s.plus(D_c);
            Solution solution_ = new Solution(D_, F);
            u = solution_.sol(D_, F);
        }
        System.out.print("Number Iterations: ");
        System.out.println(i);
        System.out.println("Results:");
        System.out.println("Strain Vector:");
        u.print(16, 16);
    }

    static Matrix calcS(Matrix u, Matrix G, Steel m, Matrix Es, Matrix v) {
        Matrix e = new Matrix(G.getRowDimension(), 1, 1.0);
        Matrix s = new Matrix(G.getRowDimension(), 1, 0.0);
        SigmaS sigma = new SigmaS(m);
        for (int i = 0; i < G.getRowDimension(); i++) {
            double ep_ = u.get(0, 0) + u.get(1, 0) * G.get(i, 0) + u.get(2, 0) * G.get(i, 1);
            e.set(i, 0, ep_);
            double s_ = sigma.sigmas(ep_);
            s.set(i, 0, s_);
            if (ep_ == 0.0) {
                v.set(i, 0, 1.0);
            } else {
                double v_s = s_ / Es.get(i, 0) / ep_;
                v.set(i, 0, v_s);
            }
        }
        return v;
    }

    static Matrix calcC(Matrix u, Matrix G, Concrete m, Matrix E, Matrix v) {
        Matrix e = new Matrix(G.getRowDimension(), 1, 1.0);
        Matrix s = new Matrix(G.getRowDimension(), 1, 0.0);
        SigmaC sigma = new SigmaC(m);
        for (int i = 0; i < G.getRowDimension(); i++) {
            double ep_ = u.get(0, 0) + u.get(1, 0) * G.get(i, 0) + u.get(2, 0) * G.get(i, 1);
            e.set(i, 0, ep_);
            double s_c = sigma.sigmac(ep_);
            s.set(i, 0, s_c);
            if (ep_ == 0.0) {
                v.set(i, 0, 1.0);
            } else {
                double v_c = s_c / E.get(i, 0) / ep_;
                v.set(i, 0, v_c);
            }
        }
        return v;
    }
}
