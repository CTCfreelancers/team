package processSurveys;
import servlet.NewGroupServlet;

/********************************************************************
 *	RealityUWeb: ProcessMarried.java
 *  5/5/2014
 ********************************************************************/
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import obj.Group;
import obj.Survey;
import dao.SurveysDAO;

/**
 * The Class ProcessMarried
 */
public class ProcessMarried {
	
    private HttpSession session;
	
	private SurveysDAO sd = new SurveysDAO();

	private List<Survey> listOfMales = new ArrayList<>();

	private List<Survey> listOfFemales = new ArrayList<>();
	
	private List<Survey> listOfSingleMales = new ArrayList<>();
	
	private List<Survey> listOfSingleFemales = new ArrayList<>();

	private List<Survey> listOfMarriedMales = new ArrayList<>();

	private List<Survey> listOfMarriedFemales = new ArrayList<>();

	private List<Survey> listOfDivorcedMales = new ArrayList<>();

	private List<Survey> listOfDivorcedFemales = new ArrayList<>();



	Double percent = (Double)session.getAttribute("choise");
	private double marriedRequirementRatio = percent; // 40%

	private double divorcedRequirementRatio = .35; // 35%

	/**
	 * Do processing to assign Marriages/Spouses.
	 * 
	 * @param the list of Surveys to be processed from a Group
	 * @return the list of Surveys after being processed
	 */
	public List<Survey> doProcess(List<Survey> surveysList) {
		System.out.println("Entering ProcessMarried.doProcess() method.");
		// populate the various survey lists from main survey list

		//Can use any survey in surveysList to get Group ID (all grp id's are same)
		int grpID = surveysList.get(0).getId();
		clearLists();
		
		for (Survey survey : surveysList) {

			// Populate gender lists
			if (survey.getGender().equals("Male")) {
				listOfMales.add(survey);
				survey.setSpouse(0); // clear the list for processing
				sd.update(survey);
			}
			else {// Female
				listOfFemales.add(survey);
				survey.setSpouse(0); // clear the list for processing
				sd.update(survey);
			}

			// Populate married lists
			if (survey.getMarried().equals("Yes")) {
				if (survey.getGender().equals("Male")) // Married Male
					listOfMarriedMales.add(survey);
				else // Married Female
					listOfMarriedFemales.add(survey);
			} // end if married code block
			
			// Populate single lists
			if (survey.getMarried().equals("No")) {
				if (survey.getGender().equals("Male")) // Single Male
					listOfSingleMales.add(survey);
				else  // Single Female
					listOfSingleFemales.add(survey);
			} // end if single code block

		} // end for loop

		//Determine number of Male/Female Married Needed to match marriedRequirementRatio
		double totalMarriedNeeded = marriedRequirementRatio * surveysList.size();
		int numEachNeeded = (int) Math.round(totalMarriedNeeded/2.0); // need 50/50 male/female
		System.out.println("TotalMarriedNeeded: " + totalMarriedNeeded + ", NumEachNeeded: " + numEachNeeded +
				", # Male: " + listOfMarriedMales.size() + ", # Female: " + listOfMarriedFemales.size());
		
		// Set Married & Some Divorced if needed to meet married ratio
		setMarriedMales(numEachNeeded);
		setMarriedFemales(numEachNeeded);

		// Set spouses
		setSpouses();
	
		//Determine number of Male/Female Divorced Needed to match divorcedRequirementRatio
		double totalDivorcedNeeded = divorcedRequirementRatio * surveysList.size();
		int numEachNeededDiv = (int) Math.round(totalDivorcedNeeded/2.0); // need 50/50 male/female
		System.out.println("TotalDivorcedNeeded: " + totalDivorcedNeeded + ", NumEachNeeded: " + numEachNeededDiv +
				", # Male: " + listOfDivorcedMales.size() + ", # Female: " + listOfDivorcedFemales.size());
		
		// Set Divorced, Make sure we have 35% of the group divorced
		setDivorcedMales(numEachNeededDiv);
		setDivorcedFemales(numEachNeededDiv);

		System.out.println("Leaving ProcessMarried.doProcess() method.");
		System.out.println("-------------------------\n");
		
		//After all processing get revised list of surveys for this group
		surveysList = sd.search("groupID", ""+grpID);
		return surveysList;
	} // end doProcess() method

	/**
	 * Loop through males and make some married or divorced until get correct number.
	 */
	public void setMarriedMales(int numEachNeeded) {

		Random random = new Random();
			
		while (listOfMarriedMales.size() < numEachNeeded) {
			//Break out of loop if not enough in list
			if (listOfSingleMales.size() <= 0) { break; }
			
			Survey survey = listOfSingleMales.get(random.nextInt(listOfSingleMales.size()));
			//Change from Single to Married
			survey.setMarried("Yes");
			sd.update(survey);
			listOfSingleMales.remove(survey);
			listOfMarriedMales.add(survey);

		} // end while
		
		while (listOfMarriedMales.size() > numEachNeeded) {
			//Break out of loop if not enough in list
			if (listOfMarriedMales.size() <= 0) { break; }
			
			Survey survey = listOfMarriedMales.get(random.nextInt(listOfMarriedMales.size()));
			//Change from Married to Divorced
			survey.setMarried("Divorced");
			sd.update(survey);
			listOfMarriedMales.remove(survey);
			listOfDivorcedMales.add(survey);				
		} // end while
		
		System.out.println("Num Married Males Needed: "+numEachNeeded+", Actual Num: "+listOfMarriedMales.size());
	} // end of setMarriedMales method

	/**
	 * Loop through females and make some married or divorced until get correct number.
	 */
	public void setMarriedFemales(int numEachNeeded) {
		
		Random random = new Random();
			
		while (listOfMarriedFemales.size() < numEachNeeded) {
			//Break out of loop if not enough in list
			if (listOfSingleFemales.size() <= 0) { break; }
			
			Survey survey = listOfSingleFemales.get(random.nextInt(listOfSingleFemales.size()));
			//Change from Single to Married
			survey.setMarried("Yes");
			sd.update(survey);
			listOfSingleFemales.remove(survey);
			listOfMarriedFemales.add(survey);

		} // end while
		
		while (listOfMarriedFemales.size() > numEachNeeded) {
			//Break out of loop if not enough in list
			if (listOfMarriedFemales.size() <= 0) { break; }
			
			Survey survey = listOfMarriedFemales.get(random.nextInt(listOfMarriedFemales.size()));
			//Change from Married to Divorced
			survey.setMarried("Divorced");
			sd.update(survey);
			listOfMarriedFemales.remove(survey);
			listOfDivorcedFemales.add(survey);				
		} // end while
		
		System.out.println("Num Married Females Needed: "+numEachNeeded+", Actual Num: "+listOfMarriedFemales.size());
	} // end setMarried Females method

	// ************************************************
	// ************************************************
	
	/**
	 * Sets the spouses for the group.
	 */
	public void setSpouses() {
		// TODO: Try to match similar income (Still to be done)
		
		Survey marriedMale = null;
		Survey marriedFemale = null;

			for (int i = 0; i < listOfMarriedMales.size(); i++) {
				
				marriedMale = listOfMarriedMales.get(i);
				
				if ( listOfMarriedFemales.size() > i ) {
					
					// Set spouse from parallel List. This is like randomly placing spouses together
					// since Surveys will be filled out by students in random order. (Could shuffle
					// the order of surveys or randomly generate if want to mix it up more.)
					marriedFemale = listOfMarriedFemales.get(i);
					//Set Married Spouse value as Survey ID #
					marriedMale.setSpouse(marriedFemale.getId());
					sd.update(marriedMale);
					marriedFemale.setSpouse(marriedMale.getId());
					sd.update(marriedFemale);

				} else { break; } // no point in looping through the rest if no more eligible females altho # should be =
			} // end for Loop
			
			// Set whatever spouseless marriages that are left over to
			// marital status of single. There shouldn't be any, but do as a safety.
			for (int i = 0; i < listOfMarriedMales.size(); i++ ) {
				//Male/Female Spouses should've been = number, but as safety, handle any Married not assigned a Spouse
				if (listOfMarriedMales.get(i).getSpouse() == 0 || (Integer)listOfMarriedMales.get(i).getSpouse() == null) {
					listOfMarriedMales.get(i).setMarried("No");
					sd.update(listOfMarriedMales.get(i));
					listOfMarriedMales.remove(listOfMarriedMales.get(i));
					listOfSingleMales.add(listOfMarriedMales.get(i));
				} //end if
			} // end male outer loop
			
			for (int i = 0; i < listOfMarriedFemales.size(); i++ ) {
				//Male/Female Spouses should've been = number, but as safety, handle any Married not assigned a Spouse
				if (listOfMarriedFemales.get(i).getSpouse() == 0 || (Integer)listOfMarriedFemales.get(i).getSpouse() == null) {
					listOfMarriedFemales.get(i).setMarried("No");
					sd.update(listOfMarriedFemales.get(i));
					listOfMarriedFemales.remove(listOfMarriedMales.get(i));
					listOfSingleFemales.add(listOfMarriedFemales.get(i));
				} //end if
			} // end female outer loop			
			
	} // end setSpouses() method

	/**
	 * Loop through males and make some divorced.
	 */
	public void setDivorcedMales(int numEachNeededDiv) {

		Random rndDivMale = new Random();
			while (listOfDivorcedMales.size() < numEachNeededDiv) {
				//Break out of loop if not enough in list
				if (listOfSingleMales.size() <= 0) { break; }
				
				Survey survey = listOfSingleMales.get(rndDivMale.nextInt(listOfSingleMales.size()));
				survey.setMarried("Divorced");
				sd.update(survey);
				listOfSingleMales.remove(survey);
				listOfDivorcedMales.add(survey);
			} // end while loop

			while (listOfDivorcedMales.size() > numEachNeededDiv) {
				//Break out of loop if not enough in list
				if (listOfDivorcedMales.size() <= 0) { break; }
				
				Survey survey = listOfDivorcedMales.get(rndDivMale.nextInt(listOfDivorcedMales.size()));
				if (survey.getMarried().equals("Divorced")) {
					survey.setMarried("No");
					sd.update(survey);
					listOfDivorcedMales.remove(survey);
					listOfSingleMales.add(survey);
				} // end if
			} // end while

	} // end setDivorcedMales() method

	/**
	 * Loop through females and make some divorced.
	 */
	public void setDivorcedFemales(int numEachNeededDiv) {

		Random rndDivFemale = new Random();
		while (listOfDivorcedFemales.size() < numEachNeededDiv) {
			//Break out of loop if not enough in list
			if (listOfSingleFemales.size() <= 0) { break; }
			
			Survey survey = listOfSingleFemales.get(rndDivFemale.nextInt(listOfSingleFemales.size()));
			survey.setMarried("Divorced");
			sd.update(survey);
			listOfSingleFemales.remove(survey);
			listOfDivorcedFemales.add(survey);
		} // end while loop

		while (listOfDivorcedFemales.size() > numEachNeededDiv) {
			//Break out of loop if not enough in list
			if (listOfDivorcedFemales.size() <= 0) { break; }
			
			Survey survey = listOfDivorcedFemales.get(rndDivFemale.nextInt(listOfDivorcedFemales.size()));
			if (survey.getMarried().equals("Divorced")) {
				survey.setMarried("No");
				sd.update(survey);
				listOfDivorcedFemales.remove(survey);
				listOfSingleFemales.add(survey);
			} // end if
		} // end while
	} // end setDivorcedFemales() method
	
	private void clearLists() {
		listOfMales.clear();
		listOfFemales.clear();
		listOfSingleMales.clear();
		listOfSingleFemales.clear();
		listOfMarriedMales.clear();
		listOfMarriedFemales.clear();
		listOfDivorcedMales.clear();
		listOfDivorcedFemales.clear();
	}
	
	
//  ========================  MAIN METHOD  ==================== 
	public static void main(String[] args) {
		List<Survey> lstSurvey = new ArrayList<Survey>();
       //Create SurveysDAO & Survey Objs and Validate Login
       SurveysDAO sd = new SurveysDAO();
       lstSurvey = sd.search("groupID", "1");
       lstSurvey = new ProcessMarried().doProcess(lstSurvey);
 
		

	} //end main()	

} //end class
