/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.liftoff.interview;

/**
 *
 * @author aime1
 */
public class CitiesByPopulation {

    /*
     2. Cities by population
                
     NYC 10
     SF 0.9
     London 6
    
     [...]
     Write a function which randomly returns a city with probability proportional to that city's population.
     P(return 'NYC') = 10 / 16.9
    
     */
    /*
    
    array[10][10.9][16.9]
    array[NYC][SF][London]
    
    */
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // cities[0] has population, accumulatedPopulations[0].
        // cities[i] has population, accumulatedPopulations[i+1] - accumulatedPopulations[i]; 
        // 
        float[] accumulatedPopulations = {10.0f, 10.9f, 16.9f, 20.0f, 100.0f};
        String[] cities = {"NYC", "SF", "London", "MKE", "XYZ"};
        
        // NYC 10
        // SF 0.9
        // London 6
        // MKE 3.1
        // XYZ 80
        
        int numTrial = 100000000;// 100M
        int[] distributions = new int[cities.length];
        for(int i = 0; i < numTrial; i++){// for testing
            String city = getCityByPopulation(cities, accumulatedPopulations);
            //System.out.println(city);
            for(int j = 0; j < cities.length; j++){
                if(cities[j].equals(city)){
                    distributions[j]++;
                    continue;
                }
            }
        }
        for(int i = 0; i < cities.length; i++){
            System.out.println(cities[i]+" has "+100.0*distributions[i]/numTrial+"%");
        }
    }
    
    public static String getCityByPopulation(String[] cities, float[] accumulatedPopulations){
        float totalNumberOfPopulation = accumulatedPopulations[accumulatedPopulations.length-1];
        float randomAccumulatedPopulation = (float)Math.random()*totalNumberOfPopulation;
        //andomAccumulatedPopulation = 11.123
        //System.out.println("randomAccumulatedPopulation: "+randomAccumulatedPopulation);
        return cities[getCityIndex(randomAccumulatedPopulation, accumulatedPopulations)];
    }
    
    public static int getCityIndex(float randomAccumulatedPopulation, float[] accumulatedPopulations){
        int middleIndex = accumulatedPopulations.length / 2;
        //System.out.println("middleIndex: "+middleIndex);
        int rtnIndex = 0;
        if(randomAccumulatedPopulation > accumulatedPopulations[rtnIndex]){
            //
            // going to do binary search
            //
            if(randomAccumulatedPopulation <= accumulatedPopulations[middleIndex]){
                rtnIndex = getCityIndex_helper(randomAccumulatedPopulation, accumulatedPopulations, 0, middleIndex);
            }
            else{
                rtnIndex = getCityIndex_helper(randomAccumulatedPopulation, accumulatedPopulations, middleIndex, accumulatedPopulations.length-1);
            }
        }
        //System.out.println("rtnIndex: "+middleIndex);
        return rtnIndex;
    }
    private static int getCityIndex_helper(float randomAccumulatedPopulation, float[] accumulatedPopulations, int startIndex, int endIndex){        
        int rtnIndex = endIndex;
        if(endIndex - startIndex > 1){
            int middleIndex = startIndex + (endIndex - startIndex)/2;
            if(randomAccumulatedPopulation <= accumulatedPopulations[middleIndex]){
                rtnIndex = getCityIndex_helper(randomAccumulatedPopulation, accumulatedPopulations, startIndex, middleIndex);
            }
            else{
                rtnIndex = getCityIndex_helper(randomAccumulatedPopulation, accumulatedPopulations, middleIndex, endIndex);
            }
        }
        return rtnIndex;
    }
}
