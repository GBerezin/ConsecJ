package consec;

import Jama.Matrix;

public class Solution {
    Matrix m_u; // Деформации

    // Конструктор класса

    Solution(Matrix D, Matrix F) {
        m_u = sol(D, F);
    }

    Matrix sol(Matrix D, Matrix F) {
        Matrix u = null;
        try {
            u = D.solve(F);
        } catch (Exception e) {
            System.err.println("Bad matrix!\n");
        }
        return u;
    }
}
