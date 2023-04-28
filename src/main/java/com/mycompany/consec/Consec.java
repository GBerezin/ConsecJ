/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.consec;

/**
 *
 * @author georg
 */

import com.mycompany.frame_section.Loads;
import com.mycompany.frame_section.Section;
import com.mycompany.frame_section.Stiffness;
import javax.swing.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Consec {

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
        String datfile = "data.csv";
        List2 datl = new List2(datfile);
        List<List<String>> list_dat = datl.get_list2();
        int numberiter = Integer.parseInt(list_dat.get(0).get(0)); // Число итераций
        String steelgrade = list_dat.get(0).get(1); // Класс арматуры
        String concretegrade = list_dat.get(0).get(2); // Класс бетона
        // Нагрузки
        String lofile = "loads.csv";
        List2 lol = new List2(lofile);
        List<List<String>> list_lo = lol.get_list2();
        Array2 loa = new Array2(list_lo);
        Jama.Matrix load = new Jama.Matrix(loa.get_array2()).transpose();
        // Арматура
        String stfile = "steel.csv";
        List2 stl = new List2(stfile);
        List<List<String>> list_st = stl.get_list2();
        Array2 sta = new Array2(list_st);
        Jama.Matrix st = new Jama.Matrix(sta.get_array2());
        // Бетон
        String confile = "concrete.csv";
        List2 conl = new List2(confile);
        List<List<String>> list_con = conl.get_list2();
        Array2 cona = new Array2(list_con);
        Jama.Matrix con = new Jama.Matrix(cona.get_array2());
        // Сечение
        Section section = new Section(steelgrade, st, concretegrade, con);
        Jama.Matrix GS = section.get_GS();
        Steel ms = section.get_ms();
        Jama.Matrix Es = new Jama.Matrix(GS.getRowDimension(), 1, ms.m_E);
        Jama.Matrix GC = section.get_GC();
        Concrete mc = section.get_mc();
        Jama.Matrix Ec = new Jama.Matrix(GC.getRowDimension(), 1, mc.m_E);
        Loads loads = new Loads(load);
        Jama.Matrix F = loads.getLoad();
        Jama.Matrix vs = new Jama.Matrix(GS.getRowDimension(), 1, 1.0);
        Stiffness stiffnessS = new Stiffness(GS, Es, vs);
        Jama.Matrix Ds = stiffnessS.getD();
        Jama.Matrix vc = new Jama.Matrix(GC.getRowDimension(), 1, 1.0);
        Stiffness stiffnessC = new Stiffness(GC, Ec, vc);
        Jama.Matrix Dc = stiffnessC.getD();
        Jama.Matrix D = Ds.plus(Dc);
        Solution solution = new Solution(D, F);
        Jama.Matrix u = solution.sol(D, F);
        int i;
        for (i = 0; i < numberiter; i++) {
            Jama.Matrix v_s = calcS(u, GS, ms, Es, vs);
            Stiffness stiffness_S = new Stiffness(GS, Es, v_s);
            Jama.Matrix D_s = stiffness_S.getD();
            Jama.Matrix v_c = calcC(u, GC, mc, Ec, vc);
            Stiffness stiffness_C = new Stiffness(GC, Ec, v_c);
            Jama.Matrix D_c = stiffness_C.getD();
            Jama.Matrix D_ = D_s.plus(D_c);
            Solution solution_ = new Solution(D_, F);
            u = solution_.sol(D_, F);
        }
        System.out.print("Number Iterations: ");
        System.out.println(i);
        System.out.println("Results:");
        System.out.println("Strain Vector:");
        u.print(16, 16);
    }

    static Jama.Matrix calcS(Jama.Matrix u, Jama.Matrix G, Steel m, Jama.Matrix Es, Jama.Matrix v) {
        Jama.Matrix e = new Jama.Matrix(G.getRowDimension(), 1, 1.0);
        Jama.Matrix s = new Jama.Matrix(G.getRowDimension(), 1, 0.0);
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

    static Jama.Matrix calcC(Jama.Matrix u, Jama.Matrix G, Concrete m, Jama.Matrix E, Jama.Matrix v) {
        Jama.Matrix e = new Jama.Matrix(G.getRowDimension(), 1, 1.0);
        Jama.Matrix s = new Jama.Matrix(G.getRowDimension(), 1, 0.0);
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
