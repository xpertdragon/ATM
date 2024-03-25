import java.util.*;

public class Main {
	
	private List<User> users;
	private Scanner scanner;
	
	public Main(Scanner scanner) {
		this.scanner = scanner;
		this.users = new ArrayList<>();
	}
	
	public void cls() {
		try {
			new ProcessBuilder("clear").inheritIO().start().waitFor();
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
	
	
	
	public boolean isEmailValid(String email) {		
		
		String a = "@";
		boolean my = false;
		/*for (int i = 0;i < email.length();i++) {
			String b = Character.toString(email.charAt(i)); 
			if (b.equals(a)) {
				my = true;
				break;
			}
		}*/
		if((email.contains("@")) && (email.contains(".")) && email.length() >= 5) {
			my = true;
		}
		return my;
	}

	public int  findEmail(String email) {
		int l = -1;
		String mail = email;
		
		for(int i = 0; i < users.size();i++){
			String a = users.get(i).getEmail();

			if(mail.equals(a)){
				l = i;
				
				break;
			}
		}
		return l;
	} 
	
	public boolean findPassword(String password,int index) {
		
		boolean ans = false;
		String a  = this.users.get(index).getPassword();
		if(password.equals(a)){
			ans = true;
			 
		}
		return ans;
	}

	public void sendMoney(int from ,int to ,int amount) {
		
		int balance1 = this.users.get(from).getAmount();
		int balance2 = this.users.get(to).getAmount();
		String fn = this.users.get(to).getFn();
		String ln = this.users.get(to).getLn();
		
		if(balance1 >= amount ) {
			System.out.println("CONFIRM TRANSACTION");	
			System.out.println("You're sending RS: " + amount +fn+" "+ln);
			System.out.println("--------------------------------------------------");
			System.out.println("Enter your Password To continue [Pasword/N]");
			String pasword = this.scanner.nextLine();
			if(pasword.toUpperCase().equals("N")){
				cls();
				cls();
				System.out.println("Transaction Cancelled");
			}else {
				boolean y = findPassword(pasword,from);
				if(y) {
					cls();
					this.users.get(from).setAmount(balance1 - amount);
					this.users.get(to).setAmount(balance2 + amount);
					
												
					System.out.println("RS. " + amount + " has been successfully sent  to " +fn +" "+ln);
					System.out.println("Your New Balance Is RS: " + this.users.get(from).getAmount());
				}else if (!y) {
					cls();
					System.out.println("Invaild Password");
				}
			}		
		} else {
			System.out.println("insufficient balance");		
		} 
	}
	
	public void onStart() {
		
		cls();
		
		System.out.println("Welcome To ATM\n ");
		System.out.println("How may i help you?");
		
		while(true) {
			
			
			
			System.out.println("----------------------------------------");
			System.out.println("\n 1. Register Account");
			System.out.println(" 2. Login");
			System.out.println(" 3. Quit");
	
			System.out.println("\n----------------------------------------");
			System.out.print("Enter Selection: ");
			String choice =this.scanner.nextLine();
		
				cls();
				
			if (choice.equals("1")){			  	///TO REGISTER ACCOUNT					
							
					System.out.println("Enter First Name: ");
					String firstName = this.scanner.nextLine();
			
					System.out.println("Enter Last Name");
					String lastName = this.scanner.nextLine();
				
					System.out.println("Enter Email");
					String email = this.scanner.nextLine();
					boolean k = isEmailValid(email); 
					if(k){
						System.out.println("Enter Password");
						String password = this.scanner.nextLine();
						if(password.length() >= 5) {		
							System.out.println("Enter Intial Amount");
							int totalBalance = Integer.parseInt(this.scanner.nextLine());
							
							cls();
							System.out.println("ACCOUNT CREATED SUCCESSFULY ");

							this.users.add(new User(
								firstName,
								lastName,
								email,
								password,
								totalBalance,new PasswordNotify() {
									public void validAmount() {
										System.out.println("Please Enter A Valid Amount");
									}
									public void insufficientBalance() {
										cls();
										System.out.println("Insufficient Balance");
										///System.out.println("Please Enter a Valid Amount"); 
											///break;
									}	
								}
							));
						} else {
							cls();
							System.out.println("Password Should be greater than or 5 characters long");
						}
					} else {
						cls();
						
						System.out.println("Invalid Email");
					}

			}else if(choice.equals("2")) { 			///TO LOGIN ACCOUNT
				
				System.out.println("Enter Email");
				String email2 = this.scanner.nextLine();
				
				int index = findEmail(email2);
				
				if(index >= 0) {
					System.out.println("Enter Password");   
					String password2 = this.scanner.nextLine();  
					boolean is = findPassword(password2,index);
					
					if(is) {
						
						cls();
						
						System.out.println("WELCOME " + this.users.get(index).getFn() + " " + this.users.get(index).getLn());
						System.out.println("How Can I Asist You!");
						while(true){
							
							System.out.println("-------------------------------------------");
							System.out.println("1: Deposit Amount");
							System.out.println("2: Withdrawal Amount");
							System.out.println("3: Check Balance");
							System.out.println("4: Send Amount");
							System.out.println("5: Logout");
							System.out.println("--------------------------------------------");
							System.out.println("Enter Selection");
							
							String choice2 = this.scanner.nextLine();
							
							if(choice2.equals("1")) {									///for Deposit Amount
								
								cls();
							
								System.out.println("Let's Add Cash \n");
								System.out.println("--------------------------------------------");
								System.out.println("Enter amount To Deposit");
								int deposit = Integer.parseInt(this.scanner.nextLine());			
								
								/*if(deposit <= 0) {
									
									cls();
									System.out.println("Please Enter a Valid Amount");	
									break;
								
								} else if(deposit > 0)*/ ///{
									
									cls();
									boolean h  = this.users.get(index).depositAmount(deposit);
									if(h){
										
										System.out.println("RS. " + deposit + " has been added to your account!");
										System.out.println("Your new balance is RS. " + this.users.get(index).getAmount());
									}
								///}
							} else if(choice2.equals("2")) {								/// for Withdrawal Amount
								
								cls();
								
								System.out.println("Let's Get The Cash Out");
								System.out.println("---------------------------------------------");
								System.out.println("Enter Amount To Withdraw");			
								int withdrawalAmount = Integer.parseInt(this.scanner.nextLine());
								cls();
								boolean with = this.users.get(index).withdrawal(withdrawalAmount);
								
								if(!with) {
								
								
									
									
								
								} else if (with) {
									
									
								
									System.out.println("RS. " + withdrawalAmount + "has been withdrawan from your account!");
									System.out.println("Your new balance is RS. " + this.users.get(index).getAmount());
									
								}
							} else if(choice2.equals("3")) { 						/// Check Balance
								cls();
						
								System.out.println("Your current balance is RS. " + this.users.get(index).getAmount());
								
								
							} else if(choice2.equals("4")) {	    ///Send Amount
								
								cls();
								
								System.out.println("Let's Send Money");
								System.out.println("----------------------------------------------");
								System.out.println("Enter Recipient Email");			///email check
								String email3 = scanner.nextLine();
								if(!(email3.equals(email2))) {
									int id = this.findEmail(email3);
									
									if(id >= 0) { 
									
										cls();
										
										System.out.println("Enter Amount To Send");				///amount check 
										int amount = Integer.parseInt(this.scanner.nextLine());
										cls();
										if(amount > 0) {
											sendMoney(index,id,amount);
										}else  {
										System.out.println("sdftfdgsfh");
										}
										
									} else {
									
										cls();
										System.out.println("No Account Found With Provided Email");
										
									}
								} else {
									cls();
									
									System.out.println("please dont enter your own email ");
									
								}
							
							} else if(choice2.equals("5")) {    ///Logout
								
								cls();
								System.out.println("Logout Success");
								break;
							
							} else {
								cls();
								System.out.println("Invalid Choice ");
							}
						} 
					
					} else  {
						
						cls();
						System.out.println("Invalid Password");
						
					}
				} else if(index == -1) {
					cls();
					System.out.println("Account Doesn't Exist");
					
				}			
			
		
			} else if(choice.equals("3")){			///TO QUIT
					while(true){
						System.out.println("Thanks For Using!");
						break;
					}
				break;		
			} else  {
				while(true){
				System.out.println("Invalid  Choice");
				break;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		new Main(new Scanner(System.in)).onStart();
    }
}
