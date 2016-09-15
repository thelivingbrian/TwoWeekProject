package edu.bsu.cs222;

public class User {

	private String name;
	private int numOfRevs = 0;
	
	public User(String name){
		this.name=name;
		add();		
	}
	
	public void add(){
		numOfRevs++;
	}
	
	public String getName(){
		return name;
	}
	
	public int getCount(){
		return numOfRevs;
	}
	
}
