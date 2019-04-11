package de.tubs.skeditor.keymaera;

import java.util.Arrays;
import java.util.Vector;

public class SymbolicVector {
	
	public static SymbolicVector NullVector(int size) {
		String[] str = new String[size];
		Arrays.fill(str, "0");
		return new SymbolicVector(str);
	}
	
	protected Vector<Expression> data = new Vector<Expression>();
	
	public SymbolicVector() {
	}
	
	public SymbolicVector(String... exprs) {
		for(String e : exprs) {
			data.addElement(new Expression(e));
		}
	}
	
	public SymbolicVector(Expression... exprs) {
		for(Expression e : exprs) {
			data.addElement(e);
		}
	}
	
	public void addExpression(Expression e) {
		data.add(e);
	}
	
	public SymbolicVector add(SymbolicVector sv) {
		if(length() != sv.length())
			throw new IllegalArgumentException("Wrong dimensions!");
		
		SymbolicVector res = new SymbolicVector();
		
		for(int i = 0; i < length(); ++i) {
			res.addExpression(data.get(i).add(sv.get(i)));
		}
		
		return res;
	}
	
	public Expression multiply(SymbolicVector sv) {
		if(length() != sv.length())
			throw new IllegalArgumentException("Wrong dimensions!");
		
		Expression sum = data.get(0).multiply(sv.get(0));
		for(int i = 1; i < length(); ++i) {
			sum = sum.add(data.get(i).multiply(sv.get(i)));
		}
		
		return sum;
	}
	
	public SymbolicVector multiply(Expression e) {		
		if(e.toString().trim().equals("0"))
			return SymbolicVector.NullVector(data.size());
		
		SymbolicVector res = new SymbolicVector();
		
		for(int i = 0; i < length(); ++i) {
			res.addExpression(data.get(i).multiply(e));
			//System.out.println(data.get(i).multiply(e));
		}
		
		return res;
	}
	
	public int length() {
		return data.size();
	}
	
	public Expression get(int idx) {
		if(idx < 0 || idx >= data.size())
			throw new IllegalArgumentException("Index out of bounds!");
		return data.get(idx);
	}
	
	public void set(int idx, Expression e) {
		if(idx < 0 || idx >= data.size())
			throw new IllegalArgumentException("Index out of bounds!");
		data.set(idx, e);
	}
	
	public String toString() {
		String res = "[";
		for(Expression e : data)
			res+=e.toString()+", ";
		res=res.substring(0, res.length()-2);
		return res + "]";
	}
	
	public static void main(String[] args) {
		SymbolicVector v1 = new SymbolicVector(new Expression("a"),
											  new Expression("0"),
											  new Expression("c"),
											  new Expression("0"));
		SymbolicVector v2 = new SymbolicVector(new Expression("y"),
				  new Expression("0"),
				  new Expression("x"),
				  new Expression("5+4"));
		
		System.out.println(v1.multiply(v2));
		System.out.println(v1.add(v2));
		System.out.println(v1.multiply(new Expression("(x+y)")));
	}
}
