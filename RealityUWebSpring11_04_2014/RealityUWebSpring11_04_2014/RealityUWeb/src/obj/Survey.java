package obj;
/********************************************************************
 *	RealityUWeb: Survey.java
 *  3/19/2014
 ********************************************************************/
/**
 * The Class Survey.
 */
public class Survey {
	 //   ========================== Properties ===========================
		/*********************************
		 * Declarations
		 ********************************/
		private int id;
		private String fName;
		private String lName;
		
		private String dob; //DATETIME
		private double gpa;
		private String gender;
		
		private int groupID;
		private String education;
		private String prefJob;
		
		private String job;
		private String married ;
		private int spouse ;
		
		private String children;
		private int numChild;
		private String cCards;
		
		private String cCardUses;	
		private String groceries;
		private String clothing;
		
		private String home;
		private String vehicle;       
		private double childSupport;
		
		private double creditScore;
		private String save;
		private String entertainment;
		
		
	//   ==========================  Constructors  ========================
			/**
			 * Default Constructor
			 */
		
		public Survey() {
			this.id = 0;
			this.fName = "";
			this.lName = "";
			this.dob = "";
			this.gpa = 0.0;
			this.gender = "";
			
			this.groupID = 0;
			this.education = "";
			this.prefJob = "";
			this.job = "";
			this.married = "";
			this.spouse = 0;
			this.children = "";
			this.numChild = 0;
			this.cCards = "";
			this.cCardUses = "";
			this.groceries = "";
			this.clothing = "";
			this.home = "";
			this.vehicle = "";
			this.childSupport = 0.0;
			this.creditScore = 0.0;
			this.save = "";
			this.entertainment = "";
		}
		
		/**
		 * @param id
		 * @param fName
		 * @param lName		
		 * @param dob
		 * @param gpa
		 * @param gender		
		 * @param groupID
		 * @param education
		 * @param prefJob		
		 * @param job
		 * @param married 
		 * @param spouse 		
		 * @param children
		 * @param numChild
		 * @param cCards		
		 * @param cCardUses
		 * @param groceries
		 * @param clothing		
		 * @param home
		 * @param vehicle      
		 * @param childSupport
		 * @param creditScore
		 * @param save
		 * @param entertainment
		 */
		public Survey(int id, String fName, String lName, String dob, double gpa,
				String gender, int groupID, String education, String prefJob, String job,
				String married, int spouse, String children, int numChild,
				String cCards, String cCardUses, String groceries, String clothing,
				String home, String vehicle, double childSupport,
				double creditScore, String save, String entertainment) {
			super();
			this.id = id;
			this.fName = fName;
			this.lName = lName;
			
			this.dob = dob;
			this.gpa = gpa;
			this.gender = gender;
			
			this.groupID = groupID;
			this.education = education;
			this.prefJob = prefJob;
			
			this.job = job;
			this.married = married;
			this.spouse = spouse;
			
			this.children = children;
			this.numChild = numChild;
			this.cCards = cCards;
			
			this.cCardUses = cCardUses;
			this.groceries = groceries;
			this.clothing = clothing;
			
			this.home = home;
			this.vehicle = vehicle;
			this.childSupport = childSupport;
			
			this.creditScore = creditScore;
			this.save = save;
			this.entertainment = entertainment;
		}

	//   ==========================  Behaviors  ==========================
		/**
		 * @return the id
		 */
			public int getId() {
				return id;
			}


			public void setId(int id) {
				this.id = id;
			}
			
			public int setID(int id){
				return id;
			}
			
			public int getID(){
				return id;
			}


			public String getFname() {
				return fName;
			}


			public void setFname(String fName) {
				this.fName = fName;
			}


			public String getLname() {
				return lName;
			}


			public void setLname(String lName) {
				this.lName = lName;
			}


			public String getDob() {
				return dob;
			}


			public void setDob(String dob) {
				this.dob = dob;
			}


			public double getGpa() {
				return gpa;
			}


			public void setGpa(double gpa) {
				this.gpa = gpa;
			}


			public String getGender() {
				return gender;
			}


			public void setGender(String gender) {
				this.gender = gender;
			}


			public int getGroupID() {
				return groupID;
			}


			public void setGroupID(int groupID) {
				this.groupID = groupID;
			}


			public String getEducation() {
				return education;
			}


			public void setEducation(String education) {
				this.education = education;
			}


			public String getPrefJob() {
				return prefJob;
			}


			public void setPrefJob(String prefJob) {
				this.prefJob = prefJob;
			}


			public String getJob() {
				return job;
			}


			public void setJob(String job) {
				this.job = job;
			}


			public String getMarried() {
				return married;
			}


			public void setMarried(String married) {
				this.married = married;
			}


			public int getSpouse() {
				return spouse;
			}


			public void setSpouse(int spouse) {
				this.spouse = spouse;
			}


			public String getChildren() {
				return children;
			}


			public void setChildren(String children) {
				this.children = children;
			}


			public int getNumChild() {
				return numChild;
			}


			public void setNumChild(int numChild) {
				this.numChild = numChild;
			}


			public String getCCards() {
				return cCards;
			}


			public void setCCards(String cCards) {
				this.cCards = cCards;
			}


			public String getCCardUses() {
				return cCardUses;
			}


			public void setCCardUses(String cCardUses) {
				this.cCardUses = cCardUses;
			}


			public String getGroceries() {
				return groceries;
			}


			public void setGroceries(String groceries) {
				this.groceries = groceries;
			}


			public String getClothing() {
				return clothing;
			}


			public void setClothing(String clothing) {
				this.clothing = clothing;
			}


			public String getHome() {
				return home;
			}


			public void setHome(String home) {
				this.home = home;
			}


			public String getVehicle() {
				return vehicle;
			}


			public void setVehicle(String vehicle) {
				this.vehicle = vehicle;
			}


			public double getChildSupport() {
				return childSupport;
			}


			public void setChildSupport(double childSupport) {
				this.childSupport = childSupport;
			}


			public double getCreditScore() {
				return creditScore;
			}


			public void setCreditScore(double creditScore) {
				this.creditScore = creditScore;
			}


			public String getSave() {
				return save;
			}


			public void setSave(String save) {
				this.save = save;
			}


			public String getEntertainment() {
				return entertainment;
			}


			public void setEntertainment(String entertainment) {
				this.entertainment = entertainment;
			}
			
			 //   ==========================  validateLogin() Method ==================
	        public boolean validateLogin(String dob, String lname, String fname) {
	            boolean found = false;
	            if (dob.equals(getDob()) && lname.equals(getLname()) && fname.equals(getFname())) {
	                found = true;
	            }
	            return found;
	        } //end validateLogin
	        
	        //   ========================  DISPLAY METHOD  ====================        
	    	public void display() {
	    		System.out.println("ID\t\t= " + getId());
	    		System.out.println("First Name\t= " + getFname());
	    		System.out.println("Last Name\t= " + getLname());
	    		
	    		System.out.println("Dob\t\t= " + getDob());
	    		System.out.println("Gpa\t= " + getGpa());
	    		System.out.println("Gender\t= " + getGender());
	    		
	    		System.out.println("GroupId\t= " + getGroupID());
	    		System.out.println("Education\t= " +  getEducation());
	    		System.out.println("Preferred Job\t\t= " + getPrefJob());
	    		
	    		System.out.println("Job\t= " + getJob());
	    		System.out.println("Married\t= " + getMarried());
	    		System.out.println("Spouse\t\t= " +  getSpouse());
	    		
	    		System.out.println("Children\t= " +  getChildren());	
	    		System.out.println("Number Of Children\t\t= " + getNumChild());
	    		System.out.println("Credit Cards\t= " +  getCCards());
	    		
	    		System.out.println("Credit Card Uses\t= " + getCCardUses() );
	    		System.out.println("Groceries\t= " + getGroceries());
	    		System.out.println("Clothing\t= " + getClothing());
	    		
	    		System.out.println("Home\t\t= " + getHome());
	    		System.out.println("Vehicle\t= " + getVehicle());
	    		System.out.println("Child Support\t= " + getChildSupport());
	    		
	    		System.out.println("Credit Score\t= " + getCreditScore());
	    		System.out.println("Savings\t= " + getSave());		
	    		System.out.println("Entertainment\t\t= " + getEntertainment());
	    		
	    	} //end display()
		
	    	 //   ========================  MAIN METHOD  ==================== 
	    	public static void main(String[] args) {
	    		
	    	}
	
}

