package processSurveys;
/********************************************************************
 *	RealityUWeb: ProcessCreditScore.java
 *  5/4/2014
 ********************************************************************/
import java.util.List;

import dao.SurveysDAO;
import obj.Survey;
/**
 * The Class ProcessCreditScore
 */
public class ProcessCreditScore {
	
	/**
	 * Do processing to determine Credit Score based on GPA & Credit Card Use.
	 * 
	 * @param the list of Surveys to be processed from a Group
	 * @return the list of Surveys after being processed
	 */
	public List<Survey> doProcess(List<Survey> surveysList) {
		System.out.println("Entering ProcessCreditScore.doProcess() method");
		
		//Create SurveysDAO object
		SurveysDAO sd = new SurveysDAO();
		
		//GPA Categories
		double one = 1.499; //under 1.5
		double two = 1.5; //1.5 - 1.9 
		double three = 2.0; //2.0 - 2.4 
		double four = 2.5; //2.5 - 2.9 
		double five = 3.0; //3.0 - 3.4 
		double six = 3.5; //3.5 - 4.0 
		
		int surveyGpaCat = 0;
		
		for (Survey survey : surveysList) {
						
			//Assign student survey gpa to a gpaCategory
			if (survey.getGpa() >= six) {
				surveyGpaCat = 6;				
			} else if (survey.getGpa() >= five) {
				surveyGpaCat = 5;
			} else if (survey.getGpa() >= four) {
				surveyGpaCat = 4;
			} else if (survey.getGpa() >= three) {
				surveyGpaCat = 3;
			} else if (survey.getGpa() >= two) {
				surveyGpaCat = 2;
			} else {
				surveyGpaCat = 1;
			} 
			System.out.println("Assigned to GPA Category: "+surveyGpaCat+", Actual GPA: "+survey.getGpa());
			
			switch (surveyGpaCat) {
			case 1:
				if ( survey.getCCards().equals("No") || survey.getCCardUses().equals("Emergencies Only") ) {
					survey.setCreditScore(550);
				}
				else { //Non-Emergency CCards Use
					survey.setCreditScore(500);
				}
				break;
			case 2:
				if ( survey.getCCards().equals("No") || survey.getCCardUses().equals("Emergencies Only") ) {
					survey.setCreditScore(600);
				}
				else { //Non-Emergency CCards Use
					survey.setCreditScore(550);
				}
				break;
			case 3:
				if ( survey.getCCards().equals("No") || survey.getCCardUses().equals("Emergencies Only") ) {
					survey.setCreditScore(625);
				}
				else { //Non-Emergency CCards Use
					survey.setCreditScore(575);
				}
				break;
			case 4:
				if ( survey.getCCards().equals("No") || survey.getCCardUses().equals("Emergencies Only") ) {
					survey.setCreditScore(650);
				}
				else { //Non-Emergency CCards Use
					survey.setCreditScore(600);
				}
				break;
			case 5:
				if ( survey.getCCards().equals("No") || survey.getCCardUses().equals("Emergencies Only") ) {
					survey.setCreditScore(675);
				}
				else { //Non-Emergency CCards Use
					survey.setCreditScore(625);
				}
				break;
			case 6:
				if ( survey.getCCards().equals("No") || survey.getCCardUses().equals("Emergencies Only") ) {
					survey.setCreditScore(700);
				}
				else { //Non-Emergency CCards Use
					survey.setCreditScore(650);
				}
				break;
			} // end switch statement
		
			//Update value in dbase
			sd.update(survey);
			System.out.println("Updated Credit Score in dbase.");
		} // end big for loop
	
	
		System.out.println("Leaving ProcessCreditScore.doProcess() method.");
		System.out.println("-------------------------\n");
		
		return surveysList;
	} // end doProcess() method

}
