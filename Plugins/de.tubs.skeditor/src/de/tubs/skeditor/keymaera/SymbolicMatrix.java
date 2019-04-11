package de.tubs.skeditor.keymaera;

import java.util.Vector;

/**
 * 
 * @author User
 *
 */
public class SymbolicMatrix {
	protected Vector<SymbolicVector> data = new Vector<SymbolicVector>();
	
	public SymbolicMatrix() {
	}
	
	public SymbolicMatrix(int rows, int cols) { //set dimensions
		for(int i = 0; i < rows; ++i) {
			SymbolicVector sv = new SymbolicVector();
			for(int j = 0; j < cols; ++j) {
				sv.addExpression(new Expression("0"));
			}
			data.addElement(sv);
		}
	}
	
	public SymbolicMatrix(SymbolicVector... rows) {
		for(SymbolicVector sv : rows) {
			data.addElement(sv);
		}
	}
	
	public void addRow(SymbolicVector sv) {
		data.add(sv);
	}
	
	public int cols() {
		if(data.size() == 0)
			return 0;
		
		return data.firstElement().length();
	}
	
	public int rows() {
		return data.size();
	}
	
//	public Vector<SymbolicVector> getRows() {
//		return data;
//	}
	
	public Expression get(int row, int col) {
		if(row < 0 || row >= rows())
			throw new IllegalArgumentException("Row out of bounds!");
		if(col < 0 || col >= cols())
			throw new IllegalArgumentException("Column out of bounds!");
		return data.get(row).get(col);
	}
	
	public void set(int row, int col, Expression e) {
		if(row < 0 || row >= rows())
			throw new IllegalArgumentException("Row out of bounds!");
		if(col < 0 || col >= cols())
			throw new IllegalArgumentException("Column out of bounds!");
		data.get(row).set(col, e);
	}
	
	public SymbolicVector getRow(int n) {
		if(n < 0 || n >= rows())
			throw new IllegalArgumentException("Row out of bounds!");
		
		return data.get(n);
	}
	
	public SymbolicVector getColumn(int n) {
		if(n < 0 || n >= cols())
			throw new IllegalArgumentException("Column out of bounds!");
		
		SymbolicVector res = new SymbolicVector();
		
		for(int i = 0; i<rows(); ++i) {
			res.addExpression(data.get(i).get(n));
		}
		return res;
	}
	/*
	 * 
	 */
	SymbolicMatrix multiply(SymbolicMatrix sm) {
		if(cols() != sm.rows())
			throw new IllegalArgumentException("Wrong dimensions for multiplication! [this.cols, sm.rows]  = ["+ cols()+ ", " + sm.rows()+ "]");
		
		SymbolicMatrix res = new SymbolicMatrix(rows(), sm.cols());
		
		for(int row = 0; row < rows(); ++row) {
			for(int col = 0; col < sm.cols(); ++col) {
				res.set(row, col, data.get(row).multiply(sm.getColumn(col)).simplify());
			}
		}
		
		return res;
	}
	
	public SymbolicMatrix transpose() {
		SymbolicMatrix sm = new SymbolicMatrix();
		
		for(int i = 0; i < cols(); ++i)
			sm.addRow(this.getColumn(i));
		
		return sm;
	}
	
	
	public String toString() {
		String res = "[";
		for(SymbolicVector v : data)
			res+=v.toString()+", ";
		res=res.substring(0, res.length()-2);
		return res + "]";
	}

	public SymbolicMatrix addMatrix(SymbolicMatrix sm) {
		if(cols() != sm.cols() || rows() != sm.rows())
			throw new IllegalArgumentException("Wrong dimensions for addition!");
		
		SymbolicMatrix res = new SymbolicMatrix();
		for(int i = 0; i < sm.rows(); ++i) {
			res.addRow(getRow(i).add(sm.getRow(i)));
		}
		return res;
	}

	public SymbolicMatrix multiply(Expression e) {
		// TODO Auto-generated method stub
		SymbolicMatrix res = new SymbolicMatrix();
		for(int i = 0; i < rows(); ++i) {
			res.addRow(getRow(i).multiply(e));
		}
		return res;
	}
}
