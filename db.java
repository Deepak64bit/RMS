/**Assumption and input method
1. Menu driven program is implemented
2. Commands to be given in mysql terminal are given in README file
3. Give proper username and password in DAO class (username,pwd) as well as database name while hosting locally.
4. Case 1 corresponds to order given by new customer and inputs should be given like 
comma separated numbers(token number in Menu), If more than one quantity required for the
item then the item number should be given multiple times (as much as required quantity) following comma format.   
5. Case 2 stands for Updating order of existing customer where in the first line 
the table number should be provided followed by a line taking order as per assumption 4.
6. Case 3 stands for including new items to the restaurant menu. 
Item name should be given first followed by a space and then give price
 for that item and then a space and finally approximate time to prepare that item.
7. Case 4 displays all items in the menu.
8. Case 5 is for deleting existing item in the menu. Provide the actual name of the item in the menu in newline.
9. Case 6 gives the list of table and their corresponding availability status.
10.Case 7 sets the number of waiters in the restaurant.  Provide number of waiters required in new line.
11.Case 8 is set for chief chef to allocate waiter at a particular table after seeing new orders 
being placed. Provide the table at which waiter is required in new line.
12.Case 9 prints out the current orders present in the chief chefs list.
13. Case 10  provides the option for the  owner to login to the system. Provide username 
in first line and then provide password in the next line.   
14. Case 11  provides the option for the  current owner to register new owner to the system or provide
new username and password.Provide username in first line and then provide password in the next line.    
15. Case 12 helps in adding new employee to the list of employees. Provide the name(firstname) 
address age salary and phone number in one line separated by  a space between them. To store multiple 
value in address make use of comma.
16. Case 13 provides the scope of deleting an employee based on empID provide their empID in new line.
17. Case 14 helps updating an employee name. Provide empID and updated name in one line separated by a space. 
18. Case 15 sets the number of tables available in the restaurant. Provide the number in new line.
19. Case 16 provides the option of leaving the restaurant for the customer. 
Input the table number from which the customer is leaving in the new line.
20. Case 17 displays all the order details placed in the restaurant to owner.
21. Case 18 displays all the order placed in the restaurant till that time to the owner.
22. Case 19 displays the login details of username and password
23. Case 20 displays status of waiters present. Whether free or assigned to customer (table number). 
24. Case 21 displays the details of all the employees present in the employees list as provided by owner. 
25. The input to begin with is expected to start filling in case 15, 7 and 3 since owner is expected
to set the table number and chief chef to provide the waiters required on average as well as provide
the menu details at the start of the software for convenience. 
****/

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class db {
	public static void main(String[] args) throws Exception {
		Customer one;
		Owner own=new Owner();
		Chiefchef chef = new Chiefchef();
		Scanner input = new Scanner(System.in);
		String val[];
		DAO obj = new DAO();
		Statement st;
		ResultSet rs;
		int x=Integer.parseInt(input.nextLine());
		while(x!=0) {
		switch(x) {
		case 1:
			String str= input.nextLine();
			String s[]=str.replace(" ","").split(",");
			int[] n = new int[s.length];
			for(int i=0;i<s.length;i++)
				n[i]=Integer.parseInt(s[i]);
			one= new Customer();
			one.allocateTable();
			one.placeOrder(n);
			break;
		case 2: 
			//update order
			System.out.println("table");
			int tablenum =Integer.parseInt(input.nextLine());
			System.out.println("new orders");
			String ord[]=input.nextLine().replace(" ","").split(",");
			int[] y = new int[ord.length];
			for(int g=0;g<ord.length;g++)
				y[g]=Integer.parseInt(ord[g]);
			one= new Customer();
			one.updateOrder(tablenum,y);
			break;
		case 3:
			//add menu
			val= input.nextLine().split(" ");
			Menu.expandMenu(val[0], Integer.parseInt(val[1]),Integer.parseInt(val[2]));
			break;
		case 4: 
			obj.connect();
			st = obj.con.createStatement();
			rs = st.executeQuery("SELECT * FROM MENU");
			System.out.println("Menu no.\tItemName ItemPrice itemTime");
			while(rs.next())
				{
				String nam = String.format("%15s",rs.getString(2));
				String IP= String.format("%7d",rs.getInt(3));
				String tym = String.format("%8d",rs.getInt(4));
				System.out.println(" "+rs.getInt(1)+"\t"+nam+"\t"+IP+""+tym);
				}
			st.close();
			obj.con.close();
			//Menu items
			break;
		case 5:
			Menu.deleteItemFromMenu(input.nextLine());
			//delete item
			break;
		case 6:
			//table status
			obj.connect();
			st = obj.con.createStatement();
			rs = st.executeQuery("SELECT * FROM TABLES");
			System.out.println("Tableno. Status");
			Owner.Table.clear();
			while(rs.next())
				Owner.Table.add(rs.getInt(2));
			st.close();
			obj.con.close();
			own.tableStatus();
			break;
		case 7:
			int j=Integer.parseInt(input.nextLine());
			chef.noOfWaiters(j);
			//set waiters number
			break;
		case 8:
			//allocate waiter at particular table taken from chief chef order list
			int tableNo=Integer.parseInt(input.nextLine());
			System.out.println("Waiter at tableNo: "+tableNo+" is "+ chef.allocateWaiter(tableNo));
			break;
		case 9:
			//chiefcheforderlist
			obj.connect();
			st = obj.con.createStatement();
			rs = st.executeQuery("SELECT * FROM CHIEFORDERLIST");
			System.out.println(" Item  Table");
			while(rs.next())
			{
				String Im= String.format("%3d",rs.getInt(1));
				String tb=String.format("%7d",rs.getInt(2));;
				System.out.println(""+Im+""+tb);
			}
			st.close();
			obj.con.close();
			break;
		case 10:
			//Login
			 String nam = input.nextLine();
			 String pswd = input.nextLine();
			if (!own.userVerification(nam, pswd))
				System.out.println("Error logging in");
			
			break;
		case 11:
			//register
			String name = input.nextLine();
			String paswd = input.nextLine();
			own.setter(name, paswd);	
			break;
		case 12:
			//add employee
			System.out.println("name, Address, age, salary, num");
			String det[]= input.nextLine().split(" ");
			own.addEmployee(det[0], det[1], Integer.parseInt(det[2]), Integer.parseInt(det[3]), Long.parseLong(det[4]));
			break;
		case 13:
			//remove employee
			own.removeEmployee(Integer.parseInt(input.nextLine()));
			break;
		case 14:
			//update employee name
			System.out.println("id, name");
			String up[]= input.nextLine().split(" ");
			own.updateEmployee(Integer.parseInt(up[0]),up[1]);
			break;
		case 15:
			//set table no
			int u=Integer.parseInt(input.nextLine());
			obj.connect();
			String query = "DELETE FROM TABLES";
			st = obj.con.createStatement();
			st.execute(query);
			for(int i=0;i<u;i++)
			{//own.Table.add(-1);
			obj.connect();
			query = "INSERT INTO TABLES values(?,?)";
			PreparedStatement Set;
			Set = obj.con.prepareStatement(query);
			Set.setInt(1,i);
			Set.setInt(2, -1);
			Set.executeUpdate();
			Set.close();
			obj.con.close();
			}
			break;	
		case 16:
			//customer leave
			int table=Integer.parseInt(input.nextLine());
			one= new Customer();
			one.CustomerLeft(table);
			break;
		case 17:
			//ownerorderlist
			obj.connect();
			st = obj.con.createStatement();
			rs = st.executeQuery("SELECT * FROM OWNERORDERLIST");
			System.out.println(" Price  Table\t  Date\t     Time\tType ");
			while(rs.next())
			{
				String Pr= String.format("%4d",rs.getInt(1));
				String tb=String.format("%7d",rs.getInt(2));;
				String Da= String.format("%24s",rs.getString(3));
				String Ty=String.format("%9s",rs.getString(4));;
				System.out.println(""+Pr+""+tb+""+Da+""+Ty);
			}
			st.close();
			obj.con.close();
			break;
		case 18:
			//orderlist
			obj.connect();
			st = obj.con.createStatement();
			rs = st.executeQuery("SELECT * FROM ORDERSLIST");
			System.out.println(" Table  Item\t  Date\t     Time\tType ");
			while(rs.next())
			{
				String Pr= String.format("%4d",rs.getInt(1));
				String tb=String.format("%7d",rs.getInt(2));;
				String Da= String.format("%24s",rs.getString(3));
				String Ty=String.format("%9s",rs.getString(4));;
				System.out.println(""+Pr+""+tb+""+Da+""+Ty);
			}
			st.close();
			obj.con.close();
			break;
		case 19:
			//logindetails
			obj.connect();
			st = obj.con.createStatement();
			rs = st.executeQuery("SELECT * FROM LOGIN");
			System.out.println(" Username\tPassword ");
			while(rs.next())
			{
				String Pr= String.format("%8s",rs.getString(1));
				String tb=String.format("%16s",rs.getString(2));;
				System.out.println(""+Pr+""+tb);
			}
			st.close();
			obj.con.close();
			break;
		case 20:
			//waiters
			obj.connect();
			st = obj.con.createStatement();
			rs = st.executeQuery("SELECT * FROM WAITERS");
			System.out.println("Waiter\t Tablenum ");
			while(rs.next())
			{
				String Pr= String.format("%2d",rs.getInt(1)+1);
				String tb =String.format("%16s","Unassigned");
				if (rs.getInt(2)!=-1)
					tb=String.format("%11d",rs.getInt(2));;
				System.out.println(""+Pr+""+tb);
			}
			st.close();
			obj.con.close();
			break;
		case 21:
			//Employees
			obj.connect();
			st = obj.con.createStatement();
			rs = st.executeQuery("SELECT * FROM EMPLOYEE");
			System.out.println("EmpID\tFirstName   age\t salary   Phone\t\t\tAddress ");
			while(rs.next())
			{
				String Ei= String.format("%4d",rs.getInt(1));
				String Fn =String.format("%12s",rs.getString(2));
				String ag= String.format("%6d",rs.getInt(4));
				String sa= String.format("%8d",rs.getInt(5));
				String Pn= String.format("%12d",rs.getLong(6));
				System.out.println(""+Ei+""+Fn+""+ag+""+sa+""+Pn+"\t"+rs.getString(3));
			}
			st.close();
			obj.con.close();
			break;
		
		}
		x=Integer.parseInt(input.nextLine());
		}
	input.close();	
	}
}

class DAO{
	 Connection con=null;
	 PreparedStatement Set;
	public void connect() {
		try {
			String URL="jdbc:mysql://localhost:3306/RESTAURANT";
			String username="deepak";
			String pwd="Oosproj#1";
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(URL,username,pwd);
		}
		catch(Exception e) {
		}
	}
}

class Login{
	 protected String username;
	 protected String password;
	 public void setter(String name, String pwd) {
		 try {
				DAO obj = new DAO();
				obj.connect();
				String query = "INSERT INTO LOGIN values(?,?)";
				PreparedStatement Set;
				Set = obj.con.prepareStatement(query);
				Set.setString(1,name);
				Set.setString(2,pwd);
				Set.executeUpdate();
				Set.close();
				obj.con.close();
			}
		catch(Exception e)
			{
				System.out.print("Register error");
			}	
	}
	 public boolean userVerification(String name, String pwd) {
		 try {
				DAO obj = new DAO();
				obj.connect();
				Statement st = obj.con.createStatement();
				String query = "SELECT * FROM LOGIN;";
				ResultSet rs= st.executeQuery(query);
				rs.next();
				this.username=rs.getString(1);
				this.password=rs.getString(2);
				obj.con.close();
		 	  }
			catch(Exception e)
			{
				System.out.print(name+pwd);
			}
			if(this.username.equals(name) && this.password.equals(pwd))
				return true;
			return false;
		}
}

class Item{
	int price;
	int time;
	String name;
}

class Menu{
	static HashMap<Integer, Item> itemList = new HashMap<Integer, Item>();
	static int index=1;
	public static void expandMenu(String item, int price, int time) throws Exception {
			//System.out.println(item);	
			Item items = new Item();
			items.name = item;
			items.price = price;
			items.time = time;
			DAO obj = new DAO();
			obj.connect();
			String query = "SELECT MAX(token) FROM MENU";
			Statement st = obj.con.createStatement();
			ResultSet rs= st.executeQuery(query);
			rs.next();
			Menu.index=rs.getInt(1)+1;
			query = "INSERT INTO MENU values(?,?,?,?)";
			PreparedStatement Set;
			Set = obj.con.prepareStatement(query);
			Set.setInt(1,index);
			Set.setString(2,item);
			Set.setInt(3,price);
			Set.setInt(4,time);
			Set.executeUpdate();
			obj.con.close();
			Set.close();
			index++;
			//Menu.itemList.put(index++,items);
		}
	public static void deleteItemFromMenu(String item){
		DAO obj= new DAO();
		try {
			obj.connect();
			PreparedStatement Set;
			String query = "SELECT token FROM MENU WHERE name = '"+item+"'";
			Statement st = obj.con.createStatement();
			
			ResultSet rs= st.executeQuery(query);
			rs.next();
			int k=rs.getInt(1);
			query = "SELECT MAX(token) FROM MENU";
			st = obj.con.createStatement();
			rs= st.executeQuery(query);
			rs.next();
			index=rs.getInt(1);
			for(;k<index;k++)
			{
				query = "UPDATE MENU SET token = ? WHERE token = ?";
				Set = obj.con.prepareStatement(query);
				Set.setInt(1,k);
				Set.setInt(2,k+1);
				Set.executeUpdate();
			}
			query = "DELETE FROM MENU WHERE name = ?";
			Set = obj.con.prepareStatement(query);
			Set.setString(1,item);
			Set.executeUpdate();
			Set.close();
			obj.con.close();
		}
		catch(Exception e){
			System.out.print("Menu deletion error"+e);
		}			
	}
}
class Order extends Menu{
	ArrayList<Integer> order = new ArrayList<Integer>(); 
	int tableNo;
	int timeEstimated;
	int amount;
	public void entry(int[] items) {
		for (int i=0;i<items.length;i++)
			order.add(items[i]);
	if(items.length!=0)
	{
		timeCalculation();
		amountCalculation();
	}
	
	}
	public void timeCalculation(){
		timeEstimated=0;
		int timeForItem;
		try {
			for(int i=0;i<order.size();i++)
				{
				DAO obj = new DAO();
				obj.connect();
				String query = "SELECT TIME FROM MENU WHERE TOKEN="+order.get(i);
				Statement st = obj.con.createStatement();
				ResultSet rs= st.executeQuery(query);
				rs.next();
				timeForItem=rs.getInt(1);
				obj.con.close();
				if(timeEstimated< timeForItem)
					timeEstimated=timeForItem;
				}
			} 
		catch(Exception e){
			System.out.println("Time estimation error");
		}
		timeEstimated+=5;
	}
	public void amountCalculation() {
		amount=0;
		try {
			for(int i=0;i<order.size();i++)
				{
				DAO obj = new DAO();
				obj.connect();
				String query = "SELECT PRICE FROM MENU WHERE TOKEN="+order.get(i);
				Statement st = obj.con.createStatement();
				ResultSet rs= st.executeQuery(query);
				rs.next();
				amount+=rs.getInt(1);
				obj.con.close();
				}
			}
		catch(Exception e) {
			System.out.println("Price estimation error");
		}
	}
}
class Employee{
	private int empID;
	private String name;
	private String address;
	private int age;
	private int salary;
	private long phoneNumber;
	static int id=100;
	public Employee(String name, String Address, int age, int salary,long num){
		this.empID=id++;
		this.name=name;
		this.address= Address;
		this.age=age;
		this.salary= salary;
		this.phoneNumber=num;
	}
	public int getID() {
		return this.empID;
	}
	public String getName() {
		return this.name;
	}
	public String getAddress() {
		return this.address;
	}
	public int getAge() {
		return this.age;
	}
	public int getSalary() {
		return this.salary;
	}
	public long getPhoneNum() {
		return this.phoneNumber;
	}
}
class Owner extends Login{
   static String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")); 
	static ArrayList<Integer> Table = new ArrayList<Integer>(25); 
	ArrayList<Employee> Employees = new ArrayList<Employee>();
	static ArrayList<Order> orderList = new ArrayList<Order>();
	public void checkTransaction(){
		int i=0;
		for(i=0;i<orderList.size();i++)
				System.out.println(orderList.get(i).tableNo+" "+orderList.get(i).order+" "+orderList.get(i).amount);
	}
	public void tableStatus() {
		int i=0;
		for(i=0;i<Table.size();i++)
			if (Table.get(i)==-1)
				System.out.println(" "+i+" : \tAvailable");
			else
				System.out.println(" "+i+" : \tUnavailable");
	}
	public void addEmployee(String name, String Address, int age, int salary,long num) {		
		DAO obj = new DAO();
		obj.connect();
		int i=-1;
		try {
			String query = "SELECT MAX(ID) FROM EMPLOYEE";
			Statement st = obj.con.createStatement();
			ResultSet rs= st.executeQuery(query);
			rs.next();
			i=rs.getInt(1);
			if(i==0)
				i=100;
			query = "INSERT INTO EMPLOYEE values(?,?,?,?,?,?)";
			PreparedStatement Set;
			Set = obj.con.prepareStatement(query);
			Set.setInt(1,i+1);
			Set.setString(2,name);
			Set.setString(3,Address);
			Set.setInt(4,age);
			Set.setInt(5,salary);
			Set.setLong(6,num);
			Set.executeUpdate();
			obj.con.close();
			Set.close();
			}
		catch(Exception e)
		{
			System.out.print("Employee addition error");
		}
	}
	public int searchEmployee(int id){
		Employees.clear();
		//Employees.add(new Employee(name, Address,age, salary, num));
		for (int i=0;i<Employees.size();i++)
			if (Employees.get(i).getID()==id)
				return i;
		return -1;
	}
	public void updateEmployee(int id,String name) { 
		DAO obj = new DAO();
		try {
				obj.connect();
				PreparedStatement Set;
				String query = "UPDATE EMPLOYEE SET name = ? WHERE ID = "+id;
				Set = obj.con.prepareStatement(query);
				Set.setString(1,name);
				Set.executeUpdate();
				Set.close();
				obj.con.close();
			}
		catch (Exception e)
		{
			System.out.println("Employee updation error"+e);
		}
	}
	public void removeEmployee(int id) { 
		DAO obj = new DAO();
		try 
		{
			obj.connect();
			PreparedStatement Set;
			String query = "DELETE FROM EMPLOYEE WHERE ID = "+id;
			Set = obj.con.prepareStatement(query);
			Set.executeUpdate();
			Set.close();
			obj.con.close();
		}	
		catch (Exception e)
		{
			System.out.println("Delete employee error");
		}
	}
}

class Customer{
	int tableNo;
	Order items = new Order();
	public void placeOrder(int[] oderno ) {
		items.entry(oderno);		
		//Chiefchef.orderList.add(items);
		DAO obj = new DAO();
		try {
			for(int i=0;i<items.order.size();i++)
				{
					obj.connect();
					String query = "INSERT INTO CHIEFORDERLIST values(?,?)";
					PreparedStatement Set;
					Set = obj.con.prepareStatement(query);
					Set.setInt(1,items.order.get(i));
					Set.setInt(2,items.tableNo);
					Set.executeUpdate();
					obj.con.close();
					Set.close();
				}
			}
		catch(Exception e)
		{
			System.out.println("Chiefchef orderlist addition error");
		}
		//Owner.orderList.add(items);
		try {
			Owner.date=LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
			obj.connect();
			PreparedStatement Set;
			for(int i=0;i<items.order.size();i++)
				{
					String query = "INSERT INTO ORDERSLIST values(?,?,?,?)";
					Set = obj.con.prepareStatement(query);
					Set.setInt(1,items.tableNo);
					Set.setInt(2,items.order.get(i));
					Set.setString(3,Owner.date);
					Set.setString(4,"new");
					Set.executeUpdate();
					Set.close();
				}
			String query = "INSERT INTO OWNERORDERLIST values(?,?,?,?)";
			Set = obj.con.prepareStatement(query);
			Set.setInt(1,items.amount);
			Set.setInt(2,items.tableNo);
			Set.setString(3,Owner.date);
			Set.setString(4,"new");
			Set.executeUpdate();
			Set.close();
			obj.con.close();
			}
		catch(Exception e)
		{
			System.out.println("Owner orderlist addition error"+ e);
		}
	}
	public void updateOrder(int table,int[] oderno ) {
		//items.order.clear();   
		items.entry(oderno);
		items.tableNo=table;
		DAO obj = new DAO();
		try {
			for(int i=0;i<items.order.size();i++)
				{
					obj.connect();
					String query = "INSERT INTO CHIEFORDERLIST values(?,?)";
					PreparedStatement Set;
					Set = obj.con.prepareStatement(query);
					Set.setInt(1,items.order.get(i));
					Set.setInt(2,items.tableNo);
					Set.executeUpdate();
					obj.con.close();
					Set.close();
				}
			}
		catch(Exception e)
		{
			System.out.println("Chiefchef orderlist updation error");
		}
		//Chiefchef.orderList.get(i).tableNo
		//Chiefchef.orderList.add(items);
		try {
			Owner.date=LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
			obj.connect();
			PreparedStatement Set;
			for(int i=0;i<items.order.size();i++)
				{
					String query = "INSERT INTO ORDERSLIST values(?,?,?,?)";
					Set = obj.con.prepareStatement(query);
					Set.setInt(1,items.tableNo);
					Set.setInt(2,items.order.get(i));
					Set.setString(3,Owner.date);
					Set.setString(4,"updated");
					Set.executeUpdate();
					Set.close();
				}
			String query = "INSERT INTO OWNERORDERLIST values(?,?,?,?)";
			Set = obj.con.prepareStatement(query);
			Set.setInt(1,items.amount);
			Set.setInt(2,items.tableNo);
			Set.setString(3,Owner.date);
			Set.setString(4,"updated");
			Set.executeUpdate();
			Set.close();
			obj.con.close();
			}
		catch(Exception e)
		{
			System.out.println("Owner orderlist addition error"+ e);
		}
		//Owner.orderList.add(items);
	}
	public void allocateTable() {
		int i=0;
		DAO obj = new DAO();
		try {
			obj.connect();
			String query = "SELECT MAX(number) FROM TABLES";
			Statement st = obj.con.createStatement();
			ResultSet rs= st.executeQuery(query);
			rs.next();
			if(Owner.Table.size()==0) 
				for(i=0;i<rs.getInt(1)+1;i++)
					Owner.Table.add(0);
			//System.out.println(Owner.Table.size());	
				obj.connect();
				query = "SELECT * FROM TABLES";
				st = obj.con.createStatement();
				rs= st.executeQuery(query);
				while(rs.next())
					Owner.Table.set(rs.getInt(1),rs.getInt(2));
				obj.con.close();
			}
		catch (Exception e)
		{
			System.out.println("Customer table allocation error");
		}
		i=0;
		while(Owner.Table.get(i)!=-1 && i<Owner.Table.size())
			i++;
		tableNo=i;
		Owner.Table.set(i, 1);
		try {
			obj.connect();
			PreparedStatement Set;
			String query = "UPDATE TABLES SET STATUS = 1 WHERE NUMBER = "+tableNo;
			Set = obj.con.prepareStatement(query);
			Set.executeUpdate();
			Set.close();
			obj.con.close();
			}
	catch (Exception e)
	{
		System.out.println("Customer table allocation 2error");
	}
		items.tableNo=tableNo;////////
	}
	public void CustomerLeft(int table) {
		DAO obj = new DAO();
		//while(Chiefchef.orderList.get(i).tableNo!=table) //remove from chief chef order list
			//i++;
		try {
			obj.connect();
			PreparedStatement Set;
			String query = "DELETE FROM CHIEFORDERLIST WHERE tablenum = "+table;
			Set = obj.con.prepareStatement(query);
			Set.executeUpdate();
			Set.close();
			obj.con.close();
			}
		catch (Exception e)
		{
			System.out.println("Delete chiefordertable error");
		}
		//Chiefchef.orderList.remove(i);
		try {
			obj.connect();
			PreparedStatement Set;
			String query = "UPDATE TABLES SET STATUS = -1 WHERE number = "+table;
			Set = obj.con.prepareStatement(query);
			Set.executeUpdate();
			query = "UPDATE WAITERS SET STATUS = -1 WHERE STATUS = "+table;
			Set = obj.con.prepareStatement(query);
			Set.executeUpdate();
			Set.close();
			obj.con.close();
			}
		catch (Exception e)
		{
			System.out.println("Owner table deallocation error");
		}
		//Owner.Table.set(table,-1);
		//i=0;
		//while(Chiefchef.waiterNo.get(i)!=table)
			//i++;
		//Chiefchef.waiterNo.set(i,-1);
	}	
}

class Chiefchef{
	static ArrayList<Order> orderList = new ArrayList<Order>();
	static ArrayList<Integer> waiterNo = new ArrayList<Integer>();
	public void noOfWaiters(int n) throws Exception {
		DAO obj = new DAO();
		obj.connect();
		String query = "DELETE FROM WAITERS";
		Statement st = obj.con.createStatement();
		st.execute(query);
		for(int i=0;i<n;i++)
		{
			obj.connect();
			PreparedStatement Set;
			Set = obj.con.prepareStatement(query);
			query = "INSERT INTO WAITERS values(?,?)";
			Set = obj.con.prepareStatement(query);
			Set.setInt(1,i);
			Set.setInt(2,-1);
			Set.executeUpdate();
			Set.close();
			obj.con.close();	
		}
	}
	public int allocateWaiter(int tableNo){
		int i=0;
		DAO obj = new DAO();
		try{
			obj.connect();
			String query = "SELECT MAX(waiter) FROM WAITERS";
			Statement st = obj.con.createStatement();
			ResultSet rs = st.executeQuery(query);
			rs.next();
			if(Chiefchef.waiterNo.size()==0) 
				for(i=0;i<rs.getInt(1)+1;i++)
					{
					Chiefchef.waiterNo.add(0);
					}
			query = "SELECT * FROM WAITERS";
			st = obj.con.createStatement();
			rs= st.executeQuery(query);
			while(rs.next())
				Chiefchef.waiterNo.set(rs.getInt(1),rs.getInt(2));
			obj.con.close();
		}
		catch (Exception e)
		{
			System.out.println("wait1 allocation error");
		}
		i=0;
		while(Chiefchef.waiterNo.get(i)>-1&& i<Chiefchef.waiterNo.size())
			i++;
		Chiefchef.waiterNo.set(i,tableNo);
		try{
			obj.connect();
			String query = "UPDATE WAITERS SET STATUS = ? WHERE waiter = ?";
			PreparedStatement Set = obj.con.prepareStatement(query);
			Set.setInt(1,tableNo);
			Set.setInt(2,i);
			Set.executeUpdate();
			Set.close();
			obj.con.close();
		}
		catch(Exception e)
		{
			System.out.println("waiter table allocation error");
		}
		return i+1;
	}
}// waiter at index i  will get table no. as value
