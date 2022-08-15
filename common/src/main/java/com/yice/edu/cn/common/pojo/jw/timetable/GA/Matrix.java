package com.yice.edu.cn.common.pojo.jw.timetable.GA;

/**
 * 
* @ClassName: Matrix  
* @Description: 染色体矩阵  
* @author xuchang  
* @date 2018年12月26日
 */
public class Matrix {

	/**
	 * @param args
	 */
	protected int[][] M;
	protected int column;
	protected int row;

	public Matrix() {
		this.column = 1;
		this.row = 1;
		M = new int[column][row];

		for (int i = 0; i < this.column; i++) {
			for (int j = 0; j < this.row; j++) {
				M[i][j] = 0;
			}
		}
	}

	public Matrix(int column, int row, int value) {
		this.column = column;
		this.row = row;
		M = new int[column][row];

		for (int i = 0; i < this.column; i++) {
			for (int j = 0; j < this.row; j++) {
				this.M[i][j] = value;
			}
		}
	}
	
	
	
	/**
	 * 
	 * @Description: 单坐标访问,自下而上  
	 * @param pos
	 * @return    值矩阵值   
	 * @throws
	 */
	public int getValueBySingle(int x) {
		int c=x%this.column;
		int r=x/this.column;
		
		return this.M[c][r];
		
	}

	public void setM(int column, int row, int value) {
		M[column][row] = value;
	}
	
	public int getColumnNum() {
		return this.column;
	}
	
	public int getRowNum() {
		return this.row;
	}

	
	public void displayMatrix() {
		for (int i = 0; i < this.column; ++i) {
			for (int j = 0; j < this.row; ++j) {
				System.out.print(M[i][j] + "\t");
			}

			System.out.println();
		}

		System.out.println();

	}
	
	public int getCrossValue(int column, int row) {
		
		
		return this.M[column][row];
	}
	
	public static void main(String[] args) {
		Matrix a=new Matrix(2, 3, 0);
		System.out.println(a.M[1][1]);
		
		
	}
	
	
}