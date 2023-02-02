package frame_section;

import Jama.Matrix;

public class Stiffness {

    Matrix m_D;

    // Конструктор класса

    public Stiffness(Matrix G, Matrix E, Matrix v) {
        Matrix D = new Matrix(3, 3, 0.0);
        double D00 = D.get(0, 0);
        double D01 = D.get(0, 1);
        double D02 = D.get(0, 2);
        double D11 = D.get(1, 1);
        double D12 = D.get(1, 2);
        double D22 = D.get(2, 2);

        for (int i = 0; i < v.getRowDimension(); i++) {
            double Zxi = G.get(i, 0);
            double Zyi = G.get(i, 1);
            double Ai = G.get(i, 2);
            double Ei = E.get(i, 0);
            double vi = v.get(i, 0);
            D00 += Ai * Ei * vi;
            D11 += Ai * Zxi * Zxi * Ei * vi;
            D22 += Ai * Zyi * Zyi * Ei * vi;
            D01 += Ai * Zxi * Ei * vi;
            D02 += Ai * Zyi * Ei * vi;
            D12 += Ai * Zxi * Zyi * Ei * vi;
        }

        D.set(0, 0, D00);
        D.set(0, 1, D01);
        D.set(0, 2, D02);
        D.set(1, 0, D01);
        D.set(1, 1, D11);
        D.set(1, 2, D12);
        D.set(2, 0, D02);
        D.set(2, 1, D12);
        D.set(2, 2, D22);
        m_D = D;
    }

    public Matrix getD() {
        return m_D;
    }
}
