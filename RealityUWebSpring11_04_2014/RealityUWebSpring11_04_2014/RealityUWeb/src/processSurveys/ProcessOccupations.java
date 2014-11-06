package processSurveys;
/********************************************************************
 *	RealityUWeb: ProcessOccupations.java
 *  4/28/2014
 ********************************************************************/
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dao.OccupationsDAO;
import dao.SurveysDAO;
import obj.Occupation;
import obj.Survey;

/**
 * The Class ProcessOccupations.
 */
public class ProcessOccupations {
	
	/**
	 * Do processing to assign an Occupation to each Survey based on GPA & Preferred Occupation.
	 * Student Loans are obtained via the Occupation table (Loan based on assigned Occupation),
	 * no calculation needed for loans
	 * 
	 * @param the list of Surveys to be processed from a Group
	 * @return the list of Surveys after being processed
	 */
	public List<Survey> doProcess(List<Survey> surveysList) {
		System.out.println("Entering ProcessOccupations.doProcess() method");
		
		//List of eligible occupations
		List<Occupation> eligibleJobsList = new ArrayList<Occupation>();
		
		//GPA Categories
		double one = 1.499; //under 1.5
		double two = 1.5; //1.5 - 1.9 
		double three = 2.0; //2.0 - 2.4 
		double four = 2.5; //2.5 - 2.9 
		double five = 3.0; //3.0 - 3.4 
		double six = 3.5; //3.5 - 4.0 
		
		OccupationsDAO od = new OccupationsDAO();
		List<Occupation> occupsList = new ArrayList<Occupation>();
		int gpaCat = 0;
		int surveyGpaCat = 0;
		SurveysDAO sd = new SurveysDAO();
		

		for (Survey survey : surveysList) {
			//Use preferred job to look up Occupation name (should only be 1 match)			
			occupsList = od.search("name", survey.getPrefJob());
			//Get GPA Category for preferred job
			gpaCat = occupsList.get(0).getGpaCategory();
			System.out.println("Preferred job GPA Category is: "+gpaCat);
			
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

			//Compare student survey gpa category to Occupation gpaCategory
			if (surveyGpaCat >= gpaCat) { // student gpa is high enough to get their preferred job
				//Assign preferred job as assigned "job" in survey obj & dbase 
				survey.setJob(survey.getPrefJob());
				sd.update(survey);
				System.out.println("Assign job same as preferred job.");
				
			}
			// *********************************************************************************
			else { // otherwise student has some problems and he/she may not be
					// able to have his/her dream job!

				// Get the jobs in the same category, send occupation, and gpaCategory
				eligibleJobsList = getJobsInCategory(occupsList.get(0), surveyGpaCat);
				System.out.println("Pick from jobs in Category: "+eligibleJobsList.size());

				// If no jobs, get jobs in same industry
				if (eligibleJobsList.size() < 1) {
					eligibleJobsList = getJobsInIndustry(occupsList.get(0), surveyGpaCat);
					System.out.println("Pick from jobs in Industry: "+eligibleJobsList.size());
				}
				// If no jobs, get jobs in same type
				if (eligibleJobsList.size() < 1) {
					eligibleJobsList = getJobsInType(occupsList.get(0), surveyGpaCat);
					System.out.println("Pick from jobs in Type: "+eligibleJobsList.size());
				}
				// If no jobs, get jobs in same GPA category
				if (eligibleJobsList.size() < 1) {
					eligibleJobsList = getJobsInGPA(surveyGpaCat);
					System.out.println("Pick from jobs in same GPA Category: "+eligibleJobsList.size());
				}
				// If no jobs, get lowest GPA jobs
				if (eligibleJobsList.size() < 1) { // This will get the minimum number of jobs
					eligibleJobsList = getLowestGPAJobs(surveyGpaCat);
					System.out.println("Pick from jobs in lower GPA Category: "+eligibleJobsList.size());
				} //end if to get list of eligible Occupations

				// Pick random job out of eligible jobs
				if (eligibleJobsList.size() > 0) {

					// If there is only one choice, get it. Otherwise, get a random job
					if (eligibleJobsList.size() == 1) {
						//Assign job in survey obj & dbase
						survey.setJob(eligibleJobsList.get(0).getName());
						sd.update(survey);
						System.out.println("Assign job from only one choice: "+eligibleJobsList.get(0).getName());
					} else {
						// Go through the list and pick a random job
						Random rndJob = new Random();
						int randomArrayNumber = rndJob.nextInt(eligibleJobsList.size());
						survey.setJob(eligibleJobsList.get(randomArrayNumber).getName());
						sd.update(survey);
						System.out.println("Assign job randomly: "+eligibleJobsList.get(randomArrayNumber).getName());
					}
				} //end if select random job
				
			} // end outer else loop

		} // end for loop

		System.out.println("Leaving ProcessJobs.doProcess() method");
		System.out.println("-------------------------\n");
		
		return surveysList;
	} // end doProcess() method

	/**
	 * Returns a list of eligible jobs based on Category.
	 * 
	 * @param oc
	 *            the occupation selected by student
	 * @return list of eligible jobs
	 */
	private List<Occupation> getJobsInCategory(Occupation oc, int surveyGpaCat) {

		String category = oc.getCategory();
		OccupationsDAO od = new OccupationsDAO();
		
		List<Occupation> preferredJobsList = checkGPA(od.search("category", category), surveyGpaCat);

		return preferredJobsList;
	}

	/**
	 * Returns a list of eligible jobs based on Industry.
	 * 
	 * @param oc
	 *            the occupation selected by student
	 * @return list of eligible jobs
	 */
	private List<Occupation> getJobsInIndustry(Occupation oc, int surveyGpaCat) {

		String industry = oc.getIndustry();
		OccupationsDAO od = new OccupationsDAO();

		List<Occupation> lstPrefJobs = checkGPA(od.search("industry", industry), surveyGpaCat);

		return lstPrefJobs;
	}

	/**
	 * Returns a list of eligible jobs based on Type.
	 * 
	 * @param oc
	 *            the occupation selected by student
	 * @return list of eligible jobs
	 */
	private List<Occupation> getJobsInType(Occupation oc, int surveyGpaCat) {

		String type = oc.getType();
		OccupationsDAO od = new OccupationsDAO();

		List<Occupation> lstPrefJobs = checkGPA(od.search("type", type), surveyGpaCat);

		return lstPrefJobs;
	}

	/**
	 * Returns a list of eligible jobs based on GPA.
	 * 
	 * @param surveyGpaCat
	 *            the survey gpa category
	 * @return list of eligible jobs
	 */
	private List<Occupation> getJobsInGPA(int surveyGpaCat) {

		OccupationsDAO od = new OccupationsDAO();
		//All jobs in all industries having same gpa category as student's actual gpa category
		List<Occupation> lstPrefJobs = od.search("gpaCategory", ""+surveyGpaCat); //have to convert to String for 'search' method

		return lstPrefJobs;
	}

	/**
	 * Returns a list of lowest GPA available jobs.
	 * 
	 * @param surveyGpaCat
	 *            the survey gpa category
	 * @return list of eligible jobs
	 */
	private List<Occupation> getLowestGPAJobs(int surveyGpaCat) {

		OccupationsDAO od = new OccupationsDAO();
		//Sends list of all Occupations in dbase, and returns all those lower than the student's actual gpa category	
		List<Occupation> lowestGPAJobsList = checkGPA(od.findAllOccupations(), (surveyGpaCat-1));

		return lowestGPAJobsList;
	}

	/**
	 * Checks eligible jobs GPA against the survey GPA.
	 * 
	 * @param occupationsList
	 *            : the list of jobs being checked
	 * @param survey
	 *            the survey being processed
	 * @return list of checked jobs
	 */
	private List<Occupation> checkGPA(List<Occupation> jobsList, int surveyGpaCat) {

		List<Occupation> matchedJobsList = new ArrayList<>();

		for (Occupation job : jobsList) {
			//Will allow jobs at the same or lower gpa category
			if (surveyGpaCat >= job.getGpaCategory()) {
				matchedJobsList.add(job);
			}
		}
		return matchedJobsList;
	}

}