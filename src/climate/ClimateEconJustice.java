package climate;

import java.util.ArrayList;

/**
 * This class contains methods which perform various operations on a layered 
 * linked list structure that contains USA communitie's Climate and Economic information.
 * 
 * @author Navya Sharma
 */

public class ClimateEconJustice {

    private StateNode firstState;
    
    /*
    * Constructor
    * 
    * **** DO NOT EDIT *****
    */
    public ClimateEconJustice() {
        firstState = null;
    }

    /*
    * Get method to retrieve instance variable firstState
    * 
    * @return firstState
    * 
    * **** DO NOT EDIT *****
    */ 
    public StateNode getFirstState () {
        // DO NOT EDIT THIS CODE
        return firstState;
    }

    /**
     * Creates 3-layered linked structure consisting of state, county, 
     * and community objects by reading in CSV file provided.
     * 
     * @param inputFile, the file read from the Driver to be used for
     * @return void
     * 
     * **** DO NOT EDIT *****
     */
    public void createLinkedStructure ( String inputFile ) {
        
        // DO NOT EDIT THIS CODE
        StdIn.setFile(inputFile);
        StdIn.readLine();
        
        // Reads the file one line at a time
        while ( StdIn.hasNextLine() ) {
            // Reads a single line from input file
            String line = StdIn.readLine();
            // IMPLEMENT these methods
            addToStateLevel(line);
            addToCountyLevel(line);
            addToCommunityLevel(line);
        }
    }

    /*
    * Adds a state to the first level of the linked structure.
    * Do nothing if the state is already present in the structure.
    * 
    * @param inputLine a line from the input file
    */
    public void addToStateLevel ( String inputLine ) {

        String[] info = inputLine.split(",");

        StateNode state = new StateNode();
        state.setName(info[2]);

        StateNode statesList = firstState;
        StateNode lastNode = null;
        boolean containsState = false;

        while (statesList != null){
            if (statesList.name.equals(state.name)){
                containsState = true;
            }
            lastNode = statesList;
            statesList = statesList.next;
        }

        if (lastNode == null){
            firstState = state;
        }
        else if (containsState == false){
            lastNode.next = state;
        }
    }

    /*
    * Adds a county to a state's list of counties.
    * 
    * Access the state's list of counties' using the down pointer from the State class.
    * Do nothing if the county is already present in the structure.
    * 
    * @param inputFile a line from the input file
    */
    public void addToCountyLevel ( String inputLine ) {

        String[] info = inputLine.split(",");

        StateNode state = new StateNode();
        state.setName(info[2]);
        CountyNode county = new CountyNode();
        county.setName(info[1]);
        
        StateNode statesList = firstState;

        while (statesList != null){
            if (statesList.name.equals(state.name)){

                CountyNode countyList = statesList.down;
                CountyNode lastCountyNode = null;
                boolean containsCounty = false;
        
                while (countyList != null){
                    if (countyList.name.equals(county.name)){
                        containsCounty = true;
                    }
                    lastCountyNode = countyList;
                    countyList = countyList.next;
                }

                if (lastCountyNode == null){
                    statesList.down = county;
                }
                else if (containsCounty == false){
                    lastCountyNode.next = county;
                }
            }
            statesList = statesList.next;
        }
    }

    /*
    * Adds a community to a county's list of communities.
    * 
    * Access the county through its state
    *      - search for the state first, 
    *      - then search for the county.
    * Use the state name and the county name from the inputLine to search.
    * 
    * Access the state's list of counties using the down pointer from the StateNode class.
    * Access the county's list of communities using the down pointer from the CountyNode class.
    * Do nothing if the community is already present in the structure.
    * 
    * @param inputFile a line from the input file
    */
    public void addToCommunityLevel ( String inputLine ) {

        String[] info = inputLine.split(",");

        StateNode state = new StateNode();
        state.setName(info[2]);
        CountyNode county = new CountyNode();
        county.setName(info[1]);
        CommunityNode community = new CommunityNode();
        community.setName(info[0]);

        Data data = new Data();
        data.setPrcntAfricanAmerican(Double.parseDouble(info[3]));
        data.setPrcntNative(Double.parseDouble(info[4]));
        data.setPrcntAsian(Double.parseDouble(info[5]));
        data.setPrcntWhite(Double.parseDouble(info[8]));
        data.setPrcntHispanic(Double.parseDouble(info[9]));
        data.setAdvantageStatus(info[19]);
        data.setPMlevel(Double.parseDouble(info[49]));
        data.setChanceOfFlood(Double.parseDouble(info[37]));
        data.setPercentPovertyLine(Double.parseDouble(info[121]));

        community.setInfo(data);
        
        StateNode statesList = firstState;

        while (statesList != null){
            if (statesList.name.equals(state.name)){

                CountyNode countyList = statesList.down;
        
                while (countyList != null){
                    if (countyList.name.equals(county.name)){
                        CommunityNode communityList = countyList.down;
                        CommunityNode lastCommunityNode = null;
                        boolean containsCommunity = false;

                        while (communityList != null){
                            if (communityList.name.equals(community.name)){
                                containsCommunity = true;
                            }
                            lastCommunityNode = communityList;
                            communityList = communityList.next;
                        }

                        if (lastCommunityNode == null){
                            countyList.down = community;
                        }
                        else if (containsCommunity == false){
                            lastCommunityNode.next = community;
                        }

                    }
                    countyList = countyList.next;
                }
            }
            statesList = statesList.next;
        }
    }

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial group  
     * and are identified as disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial groups
     * @param race the race which will be returned
     * @return the amount of communities that contain the same or higher percentage of the given race
     */
    public int disadvantagedCommunities ( double userPrcntage, String race ) {

        StateNode statesList = firstState;
        int count = 0;

        while (statesList != null){
            CountyNode countyList = statesList.down;
        
            while (countyList != null){
                CommunityNode communityList = countyList.down;

                while (communityList != null){
                    if (communityList.info.getAdvantageStatus().equals("True")){
                        if (race.equalsIgnoreCase("African American")){
                            if (communityList.info.prcntAfricanAmerican*100 >= userPrcntage){
                                count++;
                            }
                        }
                        else if (race.equalsIgnoreCase("Native American")){
                            if (communityList.info.prcntNative*100 >= userPrcntage){
                                count++;
                            }
                        }
                        else if (race.equalsIgnoreCase("Asian American")){
                            if (communityList.info.prcntAsian*100 >= userPrcntage){
                                count++;
                            }
                        }
                        else if (race.equalsIgnoreCase("White American")){
                            if (communityList.info.prcntWhite*100 >= userPrcntage){
                                count++;
                            }
                        }
                        else if (race.equalsIgnoreCase("Hispanic American")){
                            if (communityList.info.prcntHispanic*100 >= userPrcntage){
                                count++;
                            }
                        }
                    }
                    communityList = communityList.next;
                }
                countyList = countyList.next;
            }
            statesList = statesList.next;
        }
    
        
        return count;  
    }

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial group  
     * and are identified as non disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial groups
     * @param race the race which will be returned
     * @return the amount of communities that contain the same or higher percentage of the given race
     */
    public int nonDisadvantagedCommunities ( double userPrcntage, String race ) {

        StateNode statesList = firstState;
        int count = 0;

        while (statesList != null){
            CountyNode countyList = statesList.down;
        
            while (countyList != null){
                CommunityNode communityList = countyList.down;

                while (communityList != null){
                    if (communityList.info.getAdvantageStatus().equals("False")){
                        if (race.equalsIgnoreCase("African American")){
                            if (communityList.info.prcntAfricanAmerican*100 >= userPrcntage){
                                count++;
                            }
                        }
                        else if (race.equalsIgnoreCase("Native American")){
                            if (communityList.info.prcntNative*100 >= userPrcntage){
                                count++;
                            }
                        }
                        else if (race.equalsIgnoreCase("Asian American")){
                            if (communityList.info.prcntAsian*100 >= userPrcntage){
                                count++;
                            }
                        }
                        else if (race.equalsIgnoreCase("White American")){
                            if (communityList.info.prcntWhite*100 >= userPrcntage){
                                count++;
                            }
                        }
                        else if (race.equalsIgnoreCase("Hispanic American")){
                            if (communityList.info.prcntHispanic*100 >= userPrcntage){
                                count++;
                            }
                        }
                    }
                    communityList = communityList.next;
                }
                countyList = countyList.next;
            }
            statesList = statesList.next;
        }
    
        
        return count; 
    }
    
    /** 
     * Returns a list of states that have a PM (particulate matter) level
     * equal to or higher than value inputted by user.
     * 
     * @param PMlevel the level of particulate matter
     * @return the States which have or exceed that level
     */ 
    public ArrayList<StateNode> statesPMLevels ( double PMlevel ) {

        ArrayList<StateNode> statesAL = new ArrayList<StateNode>();

        StateNode statesList = firstState;

        while (statesList != null){
            CountyNode countyList = statesList.down;
        
            while (countyList != null){
                CommunityNode communityList = countyList.down;

                while (communityList != null){
                    if (communityList.info.PMlevel >= PMlevel){
                        if (!statesAL.contains(statesList)){
                            statesAL.add(statesList);
                        }
                    }
                    communityList = communityList.next;
                }
                countyList = countyList.next;
            }
            statesList = statesList.next;
        }
    
        
        return statesAL; 
    }

    /**
     * Given a percentage inputted by user, returns the number of communities 
     * that have a chance equal to or higher than said percentage of
     * experiencing a flood in the next 30 years.
     * 
     * @param userPercntage the percentage of interest/comparison
     * @return the amount of communities at risk of flooding
     */
    public int chanceOfFlood ( double userPercntage ) {

        StateNode statesList = firstState;
        int count = 0;

        while (statesList != null){
            CountyNode countyList = statesList.down;
        
            while (countyList != null){
                CommunityNode communityList = countyList.down;

                while (communityList != null){
                    if (communityList.info.chanceOfFlood >= userPercntage){
                        count++;
                    }
                    communityList = communityList.next;
                }
                countyList = countyList.next;
            }
            statesList = statesList.next;
        }

        return count; 
    }

    /** 
     * Given a state inputted by user, returns the communities with 
     * the 10 lowest incomes within said state.
     * 
     *  @param stateName the State to be analyzed
     *  @return the top 10 lowest income communities in the State, with no particular order
    */
    public ArrayList<CommunityNode> lowestIncomeCommunities ( String stateName ) {

        StateNode statesList = firstState;

        ArrayList<CommunityNode> lowIncCom = new ArrayList<CommunityNode>();

        while (statesList != null){
            if (statesList.name.equals(stateName)){
                CountyNode countyList = statesList.down;
            
                while (countyList != null){
                    CommunityNode communityList = countyList.down;

                    while (communityList != null){
                        if (lowIncCom.size() < 10){
                            lowIncCom.add(communityList);
                        }
                        else if (lowIncCom.size() < 11){
                            CommunityNode lowestNode = lowIncCom.get(0);
                            for (int i = 1; i < lowIncCom.size(); i++){
                                if (lowIncCom.get(i).info.getPercentPovertyLine() < lowestNode.info.getPercentPovertyLine()){
                                    lowestNode = lowIncCom.get(i);
                                }
                            }

                            if (communityList.info.getPercentPovertyLine() > lowestNode.info.getPercentPovertyLine()){
                                lowIncCom.set(lowIncCom.indexOf(lowestNode), communityList);
                            }
                        }
                        communityList = communityList.next;
                    }
                    countyList = countyList.next;
                }
            }
            statesList = statesList.next;
            
            
        }

        return lowIncCom; 
    }
}