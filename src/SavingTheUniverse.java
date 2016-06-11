import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Vector;

public class SavingTheUniverse {
	public static void main(String[] args) {
		
		SavingTheUniverse stu = new SavingTheUniverse();
		
		stu.readInputAndSolve();
	}
	
	private void readInputAndSolve() {
	    Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
	    
	    int numberOfCases = scanner.nextInt();  // Scanner has functions to read ints, longs, strings, chars, etc.
	    
	    // Read in each case's data and solve the case.
	    for (int caseNumber = 1; caseNumber <= numberOfCases; caseNumber++) {
	    	
	    	// Next several rows of data are the number of search engines followed by the search engine names.
	    	// Let's collect the names of the search engines into a searchEngineList.
	    	int numberOfSearchEngines = scanner.nextInt();
	    	scanner.nextLine();
	    	//
	    	Vector<String> searchEngineList = new Vector<String>();
	    	for (int searchEngineNumber = 1; searchEngineNumber <= numberOfSearchEngines; searchEngineNumber++) {
	    		String searchEngineName = scanner.nextLine();
	    		searchEngineList.add(searchEngineName);
	    	}
	    	
	    	// Next up in the input is the number of incoming queries followed by the queries themselves.
	    	// The queries are just search engine names. Let's stash the queries in a queryList.
	    	// Choosing to use Vector because of its indexOf(Object o, int index) method.
	    	int numberOfQueries = scanner.nextInt();
	    	scanner.nextLine();
	    	//
	    	Vector<String> queryList = new Vector<String>();
	    	for (int queryNumber = 1; queryNumber <= numberOfQueries; queryNumber++) {
	    		String searchEngineName = scanner.nextLine();
	    		queryList.add(searchEngineName);
	    	}
	    	
	    	int numberOfSwitches = solveThisCase(searchEngineList, queryList); 
	  
	    	System.out.println(String.format("Case #%1$d: %2$d", caseNumber, numberOfSwitches));
	    	
	    }

	    scanner.close();
	}
	
	private int solveThisCase(Vector<String> searchEngineList, Vector<String> queryList) {
		
		String currentQuery = "", currentSearchEngine = "";
		int index = 0, switchCount = 0, queryListLength = queryList.size();
		
		while (index < queryListLength) {
			
			currentQuery = queryList.get(index);
			
			// Is it time to pick another search engine?
			if (currentSearchEngine.isEmpty() || currentSearchEngine.equals(currentQuery)) {
				
				int maxDistanceFound = Integer.MIN_VALUE;
				String searchEnginePicked = "";
				
				// If currentSearchEngine is null, we're just getting started and need to pick our
				// first search engine. This pick doesn't count against the switchCount.
				// If not doing first pick, then increment switchCount by 1.
				switchCount = currentSearchEngine.isEmpty() ? 0 : switchCount + 1;
				
				// Find a new search engine.
				for (String possibleSearchEngine : searchEngineList) {
					
					if(!possibleSearchEngine.equals(currentSearchEngine)) {
						
						// If the possibleSearchEngine doesn't appear in queryList, then distance will be set to -1 
						int distance = queryList.indexOf(possibleSearchEngine, index);
						distance = distance == -1 ? Integer.MAX_VALUE : distance;
						
						// If distance is greater than any seen so far, set maxDistanceFound and searchEnginePicked.
						if(distance > maxDistanceFound) {
							maxDistanceFound = distance;
							searchEnginePicked = possibleSearchEngine;
						}
					}
				}
				
				// Set currentSearchEngine to searchEnginePicked.
				currentSearchEngine = searchEnginePicked;
				// Increase index to the position of searchEnginePicked. No reason to scan anything between
				// index and the position of the searchEnginePicked as we know we won't be switching.
				index =  maxDistanceFound == Integer.MAX_VALUE ? maxDistanceFound : index + maxDistanceFound;
			}
		}
		
		return switchCount;
	}
	
	
}
