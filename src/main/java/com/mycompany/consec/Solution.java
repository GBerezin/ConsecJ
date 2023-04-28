package com.mycompany.consec;

public final class Solution {
    Jama.Matrix m_u; // Деформации

    // Конструктор класса

    Solution(Jama.Matrix D, Jama.Matrix F) {
        m_u = sol(D, F);
    }

    Jama.Matrix sol(Jama.Matrix D, Jama.Matrix F) {
        Jama.Matrix u = null;
        try {
            u = D.solve(F);
        } catch (Exception e) {
            System.err.println("Bad matrix!\n");
        }
        return u;
    }
}
