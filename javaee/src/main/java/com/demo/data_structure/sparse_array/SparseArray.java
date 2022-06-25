package com.demo.data_structure.sparse_array;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author shenguangyang
 * @date 2022-02-27 8:17
 */
public class SparseArray {
    @Test
    public void create() throws IOException {
        // 创建一个原始的二维数11*11
        // 0表示没有旗子，1表示黑子，2表示蓝子
        int[][] chessArr1 = new int[11][11];
        chessArr1[1][2] = 1;
        chessArr1[2][3] = 2;

        // 输出原始数组
        System.out.println("原始数组: ");
        int targetRow = 0;
        int targetCol = 0;
        for (int[] row : chessArr1) {
            targetRow = 0;
            for (int data : row) {
                targetRow++;
                System.out.printf("%d\t", data);
            }
            targetCol++;
            System.out.println();
        }

        System.out.println();
        int sum = 0;
        for (int i = 0; i < targetRow; i++) {
            for (int j = 0; j < targetCol; j++) {
                if (chessArr1[i][j] != 0) {
                    sum++;
                }
            }
        }
        System.out.printf("sum = %d", sum);
        System.out.println();

        // 将得到的值放入稀疏数组
        int sparseArr[][] = new int[sum + 1][3];//sum是一共有多少个棋
        //给稀数组赋值
        //这是稀疏数组的第一行，也就是存放标准二维数组（棋盘）信息的地方
        sparseArr[0][0] = targetRow;//有多少行
        sparseArr[0][1] = targetCol;//有多少列
        sparseArr[0][2] = sum;//必须拿到sum才能创建数组（sum在棋盘上有几个棋子）

        int count = 0;
        for (int i = 0; i < targetRow; i++) {
            for (int j = 0; j < targetCol; j++) {
                if (chessArr1[i][j] != 0) {
                    count++;
                    sparseArr[count][0] = i;
                    sparseArr[count][1] = j;
                    sparseArr[count][2] = chessArr1[i][j];
                }
            }
        }

        System.out.print("\n稀疏数组: \n");
        for (int[] row : sparseArr) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }

        // 将稀疏数组保存到磁盘中
        saveSparseArr(sparseArr);

        // 从磁盘中加载稀疏数组
        int[][] recoverSparseArr = getSparseArr();

        System.out.println("还原后的稀疏数组为：\n");
        for (int[] ints : recoverSparseArr) {
            System.out.printf("%d\t%d\t%d\n", ints[0], ints[1], ints[2]);
        }

        //恢复原始二维数组
        int[][] chessArr3 = new int[recoverSparseArr[0][0]][recoverSparseArr[0][1]];
        for (int i = 1; i < recoverSparseArr.length; i++) {
            chessArr3[recoverSparseArr[i][0]][recoverSparseArr[i][1]] = recoverSparseArr[i][2];
        }

        System.out.println("恢复原始二维数组: \n");
        for (int[] row : chessArr3) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }
    }

    private void saveSparseArr(int[][] sparseArr) throws IOException {
        File file = new File("sparse-arr.txt");
        if (file.exists()) {
            file.delete();
        }
        if (!file.createNewFile()) {
            throw new RuntimeException("createNewFile fail");
        }

        try (FileOutputStream fos = new FileOutputStream(file);
             OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8)) {
            for (int[] row : sparseArr) {
                osw.write(row[0] + "," + row[1] + "," + row[2] + ",");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int[][] getSparseArr() throws IOException {
        StringBuilder sb = new StringBuilder();
        try (FileInputStream fis = new FileInputStream("sparse-arr.txt");
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);) {
            while(isr.ready()) {
                sb.append((char)isr.read());
            }
            String[] split = sb.toString().split(",");
            int[][] sparseArr = new int[split.length/3][3];
            sparseArr[0][0] = Integer.parseInt(split[0]);
            sparseArr[0][1] = Integer.parseInt(split[1]);
            sparseArr[0][2] = Integer.parseInt(split[2]);

            int count = 0;
            for (int i = 3; i < split.length; i += 3) {
                count++;
                sparseArr[count][0] = Integer.parseInt(split[i]);
                sparseArr[count][1] = Integer.parseInt(split[i+1]);
                sparseArr[count][2] = Integer.parseInt(split[i+2]);
            }
            return sparseArr;
        } catch (Exception e) {
            throw e;
        }
    }
}
