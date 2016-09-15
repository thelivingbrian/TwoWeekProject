package edu.bsu.cs222;

public class UserList {

	private User[] users;
	private int entries;
	
	public UserList(){
		entries = 0;
		users = new User[30];
	}
	
	public void place(String username){
		if (entries==0){
			add(username);
		}else{
			boolean found = false;
			for (int i=0; i<entries; i++){			
				if (username.equals(users[i].getName())){
					increaseAt(i);
					found = true;
				}
			}
			if (!found){
				add(username);
			}
		}
	}
	
	public void add(String username){
		users[entries]=new User(username);
		entries++;
	}
	
	public void increaseAt(int i){
		users[i].add();
	}
	
	public void sort(){
		User placeholder;
		for(int i=0; i<entries-1; i++){
			for(int j=i+1; j<entries; j++){
				if (users[i].getCount() < users[j].getCount()){
					placeholder=users[i];
					users[i]=users[j];
					users[j]=placeholder;
				}
			}
		}
	}
	
	public User userAtIndex(int i){
		return users[i];
	}
	
	public int contributors(){
		return entries;
	}
	
}
