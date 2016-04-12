/*
Michael Walz | 700609721
Tyler Gaskey | 700603897
Alyssa Ringhausen | 700578441
Marissa Blanton | 700600430

Write a Java program to implement LSH as given in section 3.4.3 of the textbook

This is a secondary test file used for figuring out basic MinHashing
*/
import java.util.*;
import java.io.*;

public class MinHash<T> {

	private int hash[];
	private int numHash;

	/**
	 *
	 */
	public MinHash(int numHash){
		this.numHash = numHash;
        hash = new int[numHash];

        Random r = new Random(11);
        for (int i = 0; i < numHash; i++){
            int a = (int)r.nextInt();
            int b = (int)r.nextInt();
            int c = (int)r.nextInt();
            int x = hash(a*b*c, a, b, c);
            hash[i] = x;
        }
    }

	// similarity computation and beggining of methods
    public double similarity(Set<T> set1, Set<T> set2){

        int numSets = 2; // setting total number of sets to compare
        Map<T, boolean[]> bitMap = buildBitMap(set1, set2); // building bitmap based on both sets

        int[][] minHashValues = initializeHashBuckets(numSets, numHash);

        computeMinHashForSet(set1, 0, minHashValues, bitMap);
        computeMinHashForSet(set2, 1, minHashValues, bitMap);

        return computeSimilarityFromSignatures(minHashValues, numHash);
    }

	/**
	 *
	 */
	private static int[][] initializeHashBuckets(int numSets, int numHashFunctions) {
		int[][] minHashValues = new int[numSets][numHashFunctions];

        for (int i = 0; i < numSets; i++) {
        	for (int j = 0; j < numHashFunctions; j++) {
        		minHashValues[i][j] = Integer.MAX_VALUE;
            }
        }
        return minHashValues;
    }

	/**
	 *
	 * @param minHashValues
	 * @param numHashFunctions
	 * @return
	 */
	private static double computeSimilarityFromSignatures(int[][] minHashValues, int numHashFunctions) {
		int identicalMinHashes = 0;
        for (int i = 0; i < numHashFunctions; i++){
            if (minHashValues[0][i] == minHashValues[1][i]) {
                identicalMinHashes++;
            }
        }
        return (1.0 * identicalMinHashes) / numHashFunctions;
    }

	/**
	 *
	 * @param x
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	private static int hash(int x, int a, int b, int c) {
        int hashValue = (int)((a * (x >> 4) + b * x + c) & 131071);
        return Math.abs(hashValue);
    }


    private void computeMinHashForSet(Set<T> set, int setIndex, int[][] minHashValues, Map<T, boolean[]> bitArray){

    	int index=0;

    	for(T element : bitArray.keySet()) { // for every element in the bit array
    		for (int i = 0; i < numHash; i++){ // for every hash
    			if(set.contains(element)) { // if the set contains the element
    				int hindex = hash[index]; // get the hash
    				if (hindex < minHashValues[setIndex][index]) {
    					// if current hash is smaller than the existing hash in the slot then replace with the smaller hash value
    					minHashValues[setIndex][i] = hindex;
    				}
    			}
    		}
    		index++;
    	}
    }

	/**
	 *
	 * @param set1
	 * @param set2
	 * @return
	 */
	 // Building the bitMap before similarities can be done
	public Map<T,boolean[]> buildBitMap(Set<T> set1, Set<T> set2){

		Map<T,boolean[]> bitArray = new HashMap<T,boolean[]>();

		for(T t : set1){
			bitArray.put(t, new boolean[]{true,false});
		}
		for(T t : set2){
			if(bitArray.containsKey(t)){
				// item is not present in set1
				bitArray.put(t, new boolean[]{true,true});
			}else if(!bitArray.containsKey(t)){
				// item is not present in set1
				bitArray.put(t, new boolean[]{false,true});
			}
		}
		return bitArray;
	}


	public static void main(String[] args) throws Exception{
		// Create Token
		String test = "";
		String test2 = "";

		// first scanner
		Scanner in = new Scanner(new File("foo.txt"));
		while(in.hasNextLine()){
			test += in.nextLine();
		}

		// first scanner
		Scanner in2 = new Scanner(new File("foo2.txt"));
				while(in2.hasNextLine()){
					test2 += in2.nextLine();
		}

		// declaring hashsets
		HashSet<String> shingles = new HashSet<String>();
		HashSet<String> shingles2 = new HashSet<String>();
		int shingleLength = 5;

		// splitting each string
		String[] split = test.split("\\s+");
		String[] split2 = test2.split("\\s+");

		// First HashSet
		String temp = "";
		for(int i = 0; i < split.length - shingleLength + 1; i++){
			for(int j = i; j < shingleLength + i; j++){
				temp += split[j] + " ";
			}
			shingles.add(temp);

			temp = "";
		}
		System.out.println("Read Doc 1");
		// Second Hashset
		String temp2 = "";
		for(int i = 0; i < split2.length - shingleLength + 1; i++){
					for(int j = i; j < shingleLength + i; j++){
						temp2 += split2[j] + " ";
					}
					shingles2.add(temp2);

					temp2 = "";
		}
		System.out.println("Read Doc 2");

/*
		Set<String> set1 = new HashSet<String>();
		set1.add("12");
		set1.add("1231321");
		set1.add("5435353534");
		set1.add("65462424");
		set1.add("65363634");
		set1.add("134141412");

		Set<String> set2 = new HashSet<String>();
		set2.add("12");
		set2.add("1231321");
		set2.add("5234525423");
		set2.add("1431413534");
		set2.add("13414245523");
*/

		System.out.println("Read Doc ");
		MinHash<String> minHash = new MinHash<String>(shingles.size()+shingles2.size());
		System.out.println(minHash.similarity(shingles, shingles2));
	}
}