package obj;

/********************************************************************
 *	RealityUWeb: Occupation.java
 *  3/26/2014
 ********************************************************************/
/**
 * The Class Occupation.
 */

public class Occupation {
    //   ========================== Properties ===========================
	/*********************************
	 * Declarations
	 ********************************/
	private int id;
	private String name; //Use for specific occupation
	private String type;
	
	private String industry;
	private String category; //Use for dropdown industry category
	
	//Annual & Monthly Gross Salary of Occupation
	private double annGrossSal;
	private double monGrossSal;
	
	//Married: Annual, Monthly Taxes & Remaining Monthly Income After Taxes
	private double marAnnualTax;
	private double marMonthlyTax;
	private double marAfterTax; //Monthly
	
	//Single: Annual, Monthly Taxes & Remaining Monthly Income After Taxes
	private double sinAnnualTax ;
	private double sinMonthlyTax;
	private double sinAfterTax; //Monthly
	
	private int gpaCategory; //1-6 for gpa range allowed for occupation
	private double loan; //college loan needed for occupation

	//   ==========================  Constructors  ========================
	/**
	 * Default Constructor
	 */
	public Occupation() {
		this.id = 0;
		this.name = "";
		this.type = "";
	
		this.industry = "";
		this.category = "";
		
		this.annGrossSal = 0.0;	
		this.monGrossSal = 0.0;
		
		this.marAnnualTax = 0.0;
		this.marMonthlyTax = 0.0;	
		this.marAfterTax = 0.0;
		
		this.sinAnnualTax = 0.0;
		this.sinMonthlyTax = 0.0;
		this.sinAfterTax = 0.0;
		
		this.gpaCategory = 0;
		this.loan = 0.0;
	}

	/**
	 * Constructor with all parameters
	 * 
	 * @param id
	 * @param name;
	 * @param type;
	 *
	 * @param industry;
	 * @param category;
	 * 
	 * @param annGrossSal;
	 * @param monGrossSal;
	 * 
	 * @param marAnnualTax;
	 * @param marMonthlyTax;
	 * @param marAfterTax;
	 * 
	 * @param sinAnnualTax ;
	 * @param sinMonthlyTax;
	 * @param sinAfterTax;
	 * 
	 * @param gpa;
	 * @param loan;
	 */
	public Occupation(int id, String name, String type, String industry,
			String category, double annGrossSal, double monGrossSal, double marAnnualTax,
			double marMonthlyTax, double marAfterTax, double sinAnnualTax,
			double sinMonthlyTax, double sinAfterTax, int gpaCategory, double loan) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.industry = industry;
		this.category = category;
		this.annGrossSal = annGrossSal;
		this.monGrossSal = monGrossSal;
		this.marAnnualTax = marAnnualTax;
		this.marMonthlyTax = marMonthlyTax;
		this.marAfterTax = marAfterTax;
		this.sinAnnualTax = sinAnnualTax;
		this.sinMonthlyTax = sinMonthlyTax;
		this.sinAfterTax = sinAfterTax;
		this.gpaCategory = gpaCategory;
		this.loan = loan;
	}

	/**
	 * Constructor with some calculated fields
	 * 
	 * @param id
	 * @param name;
	 * @param type;
	 *
	 * @param industry;
	 * @param category;
	 * 
	 * @param annGrossSal;
	 * 			monGrossSal: calculated value
	 * 
	 * @param marAnnualTax;
	 * 			marMonthlyTax: calculated value
	 * 			marAfterTax: calculated value
	 * 
	 * @param sinAnnualTax ;
	 * 			sinMonthlyTax: calculated value
	 * 			sinAfterTax: calculated value
	 * 
	 * @param gpa;
	 * @param loan;
	 */
	public Occupation(int id, String name, String type, String industry,
			String category, double annGrossSal, double marAnnualTax,
			double sinAnnualTax, int gpaCategory, double loan) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.industry = industry;
		this.category = category;
		this.annGrossSal = annGrossSal;
		//Calculated value, rounded to 2 decimals
		this.monGrossSal = (double) Math.round((this.annGrossSal / 12)*100)/100; 
		this.marAnnualTax = marAnnualTax;
		//Calculated value, rounded to 2 decimals
		this.marMonthlyTax = (double) Math.round((this.marAnnualTax / 12)*100)/100;
		//Calculated value, rounded to 2 decimals
		this.marAfterTax = (double) Math.round((this.monGrossSal - this.marMonthlyTax)*100)/100;
		this.sinAnnualTax = sinAnnualTax;
		//Calculated value, rounded to 2 decimals
		this.sinMonthlyTax = (double) Math.round((this.sinAnnualTax / 12)*100)/100;
		//Calculated value, rounded to 2 decimals
		this.sinAfterTax = (double) Math.round((this.monGrossSal - this.sinMonthlyTax)*100)/100;
		this.gpaCategory = gpaCategory;
		this.loan = loan;
	}	
	
	//   ==========================  Behaviors  ==========================
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}	
    
    /**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the industry
	 */
	public String getIndustry() {
		return industry;
	}

	/**
	 * @param industry the industry to set
	 */
	public void setIndustry(String industry) {
		this.industry = industry;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the annGrossSal
	 */
	public double getAnnGrossSal() {
		return annGrossSal;
	}

	/**
	 * @param annGrossSal the annGrossSal to set
	 */
	public void setAnnGrossSal(double annGrossSal) {
		this.annGrossSal = annGrossSal;
	}

	/**
	 * @return the monGrossSal
	 */
	public double getMonGrossSal() {
		return monGrossSal;
	}

	/**
	 * @param monGrossSal the monGrossSal to set
	 */
	public void setMonGrossSal(double monGrossSal) {
		this.monGrossSal = monGrossSal;
	}

	/**
	 * @return the marAnnualTax
	 */
	public double getMarAnnualTax() {
		return marAnnualTax;
	}

	/**
	 * @param marAnnualTax the marAnnualTax to set
	 */
	public void setMarAnnualTax(double marAnnualTax) {
		this.marAnnualTax = marAnnualTax;
	}

	/**
	 * @return the marMonthlyTax
	 */
	public double getMarMonthlyTax() {
		return marMonthlyTax;
	}

	/**
	 * @param marMonthlyTax the marMonthlyTax to set
	 */
	public void setMarMonthlyTax(double marMonthlyTax) {
		this.marMonthlyTax = marMonthlyTax;
	}

	/**
	 * @return the marAfterTax
	 */
	public double getMarAfterTax() {
		return marAfterTax;
	}

	/**
	 * @param marAfterTax the marAfterTax to set
	 */
	public void setMarAfterTax(double marAfterTax) {
		this.marAfterTax = marAfterTax;
	}

	/**
	 * @return the sinAnnualTax
	 */
	public double getSinAnnualTax() {
		return sinAnnualTax;
	}

	/**
	 * @param sinAnnualTax the sinAnnualTax to set
	 */
	public void setSinAnnualTax(double sinAnnualTax) {
		this.sinAnnualTax = sinAnnualTax;
	}

	/**
	 * @return the sinMonthylyTax
	 */
	public double getSinMonthlyTax() {
		return sinMonthlyTax;
	}

	/**
	 * @param sinMonthylyTax the sinMonthylyTax to set
	 */
	public void setSinMonthlyTax(double sinMonthlyTax) {
		this.sinMonthlyTax = sinMonthlyTax;
	}

	/**
	 * @return the sinAfterTax
	 */
	public double getSinAfterTax() {
		return sinAfterTax;
	}

	/**
	 * @param sinAfterTax the sinAfterTax to set
	 */
	public void setSinAfterTax(double sinAfterTax) {
		this.sinAfterTax = sinAfterTax;
	}

	/**
	 * @return the gpaCategory
	 */
	public int getGpaCategory() {
		return gpaCategory;
	}

	/**
	 * @param gpaCategory the gpaCategory to set
	 */
	public void setGpaCategory(int gpaCategory) {
		this.gpaCategory = gpaCategory;
	}

	/**
	 * @return the loan
	 */
	public double getLoan() {
		return loan;
	}

	/**
	 * @param loan the loan to set
	 */
	public void setLoan(double loan) {
		this.loan = loan;
	}
 
    //   ========================  DISPLAY METHOD  ====================        
	public void display() {
		System.out.println("ID\t\t\t= " + getId());
		System.out.println("Occupation Name\t\t= " + getName());
		System.out.println("Type\t\t\t= " + getType());
		
		System.out.println("Industry\t\t= " + getIndustry());
		System.out.println("Category\t\t= " + getCategory());
		
		System.out.println("Annual Gross Salary\t= " + getAnnGrossSal());		
		System.out.println("Monthly Gross Salary\t= " + getMonGrossSal());
		
		System.out.println("Married Annual Tax\t= " + getMarAnnualTax());
		System.out.println("Married Monthly Tax\t= " + getMarMonthlyTax());		
		System.out.println("Married Income After Tax= " + getMarAfterTax());
		
		System.out.println("Single Annual Tax\t= " + getSinAnnualTax());
		System.out.println("Single Monthly Tax\t= " + getSinMonthlyTax());
		System.out.println("Single Income After Tax\t= " + getSinAfterTax());
		
		System.out.println("GPA Category\t\t= " + getGpaCategory());
		System.out.println("Loan\t\t\t= " +  getLoan());
	} //end display()

    //   ========================  MAIN METHOD  ==================== 
	public static void main(String[] args) {
		//Test Calculated Values Constructor
		Occupation o = new Occupation(0, "Actor", "Entertainment", "Entertainment", "Entertainment", 15205.72, 986.00, 679.00, 3, 1000.00);
		o.display();
	} //end main()
}