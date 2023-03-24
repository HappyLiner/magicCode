import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    static int n = 9;
    static int high = 10;
    static int[][][] a = new int[n][n][high];

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < high; k++) {
                    a[i][j][k] = k;
                }
            }
        }

        try (Scanner sc = new Scanner(new File("testos.in"))) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int val = sc.nextInt();
                    a[i][j][0] = val;
                    if (val != 0) {
                        deleteVariantsAfterComputeValue(i, j);
                    }
                }
            }
        }

        int sum = 0;
        while (sum != 405) {
            sum = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (a[i][j][0] == 0) {
                        computeBecauseOfLastValueInColumn(i, j);
                    }
                    if (a[i][j][0] == 0) {
                        computeBecauseValueCanBeOnlyHere(i, j);
                    }
                    sum += a[i][j][0];
                }
            }
        }

        try (PrintWriter fw = new PrintWriter("testos.out")) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    fw.print(a[i][j][0] + " ");
                }
                fw.println();
            }
        }
    }

    private static void deleteVariantsAfterComputeValue(int i, int j) {
        int val = a[i][j][0];
        for (int k = 0; k < n; k++) {
            if (k != i) {
                a[k][j][val] = 0;
            }
            if (k != j) {
                a[i][k][val] = 0;
            }
        }
        for (int k = 1; k < high; k++) {
            if (k != val) {
                a[i][j][k] = 0;
            }
        }
        for (int i1 = i / 3 * 3; i1 < i / 3 * 3 + 3; i1++) {
            for (int j1 = j / 3 * 3; j1 < j / 3 * 3 + 3; j1++) {
                if (a[i1][j1][0] != val) {
                    a[i1][j1][val] = 0;
                }
            }
        }
    }

    private static void computeBecauseOfLastValueInColumn(int i, int j) {
        int columnSum = 0;
        int count = 0;
        for (int k = 1; k < high; k++) {
            if (a[i][j][k] != 0) {
                columnSum += a[i][j][k];
                count++;
            }
        }
        if (count == 1) {
            a[i][j][0] = columnSum;
            deleteVariantsAfterComputeValue(i, j);
        }
    }

    private static void computeBecauseValueCanBeOnlyHere(int index, int jindex) {
        for (int k = 1; k < high; k ++) {
            if (a[index][jindex][k] == 0) {
                continue;
            }
            int gorCount = 0;
            int vertCount = 0;
            for (int s = 0; s < n; s++) {
                if (a[index][s][k] != 0) {
                    gorCount++;
                }
                if (a[s][jindex][k] != 0) {
                    vertCount++;
                }
            }
            int cubeCount = 0;
            for (int i = index / 3 * 3; i < index / 3 * 3 + 3; i++) {
                for (int j = jindex / 3 * 3; j < jindex / 3 * 3 + 3; j++) {
                    if (a[i][j][k] != 0) {
                        cubeCount++;
                    }
                }
            }
            if (gorCount == 1 || vertCount == 1 || cubeCount == 1) {
                a[index][jindex][0] = k;
                deleteVariantsAfterComputeValue(index, jindex);
                return;
            }
        }
    }
}