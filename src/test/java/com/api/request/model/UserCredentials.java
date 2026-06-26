package com.api.request.model;

public record UserCredentials(
		String username
		, String password
		
	) implements Comparable<String>{ // code block ---- start of the record
	
	
	static int x; //static variables! Class variables!
	
	static {
		x=10;
		System.out.println("Inside the static block");
	}
	
	public UserCredentials() {
		this("iamfd", "password");
		System.out.println("Inside a Normal Constructor");
	}
	
	public static void demo() {
		System.out.println("Inside demo method!");
	}
	
	public void m1() {
		System.out.println("Inside m1 method!");
	}

	@Override
	public int compareTo(String o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
} //end of the record