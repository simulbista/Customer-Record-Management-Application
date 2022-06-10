package cma_package;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Student Name: Kaiyan Chen, Simul Bista, Jaydenn(Ching-Ting) Chang
 * Student ID: N01489178, N01489966, N01511476
 * Section: ITC-5201-RIA
 */

/*************************************************************************************************
 *  ITC-5201-RIA – Assignment 2                                                                                                                                *

 *  I declare that this assignment is my own work in accordance with Humber Academic Policy. *

 *  No part of this assignment has been copied manually or electronically from any other source *

 *  (including web sites) or distributed to other students/social media.                                                       *

 *  Name: Kaiyan Chen Student ID: N01489178 Date: 6/10/2022 *
 *  Name: Simul Bista Student ID: N01489966 Date: 6/10/2022 *
 *  Name: Jaydenn(Ching-Ting) Chang Student ID: N01511476 Date: 6/10/2022 *

 * *************************************************************************************************/




public class WriteAndReadFiles {
	//path location
	private static String filePath = "src/cma_package/cma.dat";
	static File myFile = new File(filePath);


	//method to add new customer to the binary file
	public static boolean addNewCus(String id, String name, String phone, String email, String poscode) {
//		flag to check if data with the user input id exists
		boolean repeated = false;
		//creating string array	
		String[] stringArray = new String[5];
		//creating an array list that stores the string array
		ArrayList<String[]> myArrayList = new ArrayList<String[]>();
		

		try (DataOutputStream output = new DataOutputStream(new FileOutputStream(filePath,true));
			 DataInputStream input = new DataInputStream(new FileInputStream(filePath));){
			
				//read the binary file
				
				int byteRead;
				char result;
				String s="";
				while((byteRead = input.read())!=-1) {
					result = (char) byteRead;
					s +=result;			
				}
				String[] line = s.split("~"); 
				for(int i=0;i<line.length;i++) {
					stringArray = line[i].split(",");
					if(id.equals(stringArray[0])) {
						repeated = true;
					}
				}
				//end of read
				
				//if repeated flag is true, it means data with same id exists, so do nothing but if repeated is false, then add/append data
				if(!repeated) {
//					System.out.println("Write");
					output.writeBytes(id + ',');
					output.writeBytes(name + ',');
					output.writeBytes(phone + ',');
					output.writeBytes(email + ',');
					output.writeBytes(poscode + '~');
				}
		}catch (EOFException e) {
			e.printStackTrace();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}	
		
		return repeated;
        
    }

	//method to update customer record based on the id
    public static boolean updateCus(String id, String name, String phone, String email, String poscode) {
    	//flag to check if the record was updated
    	boolean updated = false;
    	//creating string array	
    	String[] stringArray = new String[5];
    	//creating an array list that stores the string array
    	ArrayList<String[]> myArrayList = new ArrayList<String[]>();
    			
    		//update only if the file exists
    			if(myFile.exists()) {
    			
	    			try (
	    					DataInputStream input = new DataInputStream(new FileInputStream(filePath));
	    					DataOutputStream outputAppend = new DataOutputStream(new FileOutputStream(filePath,true));){
	    				
	    					//read the binary file
	    					int byteRead;
	    					char result;
	    					String s="";
	    					while((byteRead = input.read())!=-1) {
	    						
	    						result = (char) byteRead;
	    						s +=result;				
	    					}
	    					
	    					String[] line = s.split("~"); 
	    					for(int i=0;i<line.length;i++) {
	    						stringArray = line[i].split(",");
	    						if(id.equals(stringArray[0])) {
	    							stringArray[0] = id;
	    							stringArray[1] = name;
	    							stringArray[2] = phone;
	    							stringArray[3] = email;
	    							stringArray[4] = poscode;
	    							
	    							updated = true;
	    						}
//	    						storing all the data in an arraylist of string arrays
	    						myArrayList.add(stringArray); 
//	    						System.out.println(Arrays.toString(stringArray));
	    					}
	    					// end of read

	    					
	    					//if a record is updated, then 
	    						//1)store all the value(with the updated record) in an arraylist of strings
	    						//2)clearing the content of the binary file (making it empty)
	    						//3)storing the updated content from the arraylist of strings to the empty binary file 
	    						if(updated) {
		    						//deleting all the contents of the binary file 
	    							//(i.e. add blank value without append to the same file)
	    							try (DataOutputStream outputAdd = new DataOutputStream(new FileOutputStream(filePath))) {
//										outputAdd.writeBytes("");
										for(String[] arr : myArrayList) {
			    							for(int i=0;i<arr.length;i++) {				
			    								outputAppend.writeBytes(arr[i]);
			    								//if its the last field i.e. poscode , write ~ after it else write a comma
			    								if(i==arr.length-1) {
			    									outputAppend.writeBytes("~");
			    								}else {
			    									outputAppend.writeBytes(",");
			    								}
			    							}
			    						}

									}catch(Exception e) {
										e.printStackTrace();
									}
		    						
	    						}		
	    					
	    					//end of read
	    			} catch (EOFException e) {
	    				e.printStackTrace();
	    			}catch (FileNotFoundException e) {
	    				e.printStackTrace();
	    			} catch (IOException e) {
	    				e.printStackTrace();
	    			}
    			}
    			return updated;
    }

    //method to return searched customer info
    public static String[] searchCusId(String id) {
    	String[] stringArray = new String[5];
    	String[] searchStringArray = new String[5];
    	//flag to check if the result searched was found
    	boolean found = false;
    	
    	//setting the array of strings to null by default (only updated it if the record has been found otherwise return null)
    	stringArray = null;
    	
    	//search only if the file exists
		if(myFile.exists()) {
	    	try (
					DataInputStream input = new DataInputStream(new FileInputStream(filePath));){
				
					//read the binary file
					int byteRead;
					char result;
					String s="";
					while((byteRead = input.read())!=-1) {
						
						result = (char) byteRead;
						s +=result;				
					}
					
					String[] line = s.split("~"); 
					for(int i=0;i<line.length;i++) {
						searchStringArray = line[i].split(",");
	
						//store the record found in an array of string
						if(id.equals(searchStringArray[0])) {
							stringArray[0] = searchStringArray[0];
							stringArray[1] = searchStringArray[1];
							stringArray[2] = searchStringArray[2];
							stringArray[3] = searchStringArray[3];
							stringArray[4] = searchStringArray[4];
							found = true;
							break;
						}//return null if no data with similar id exists (don't need to update when there is no data to be updated)	
					}
					//end of read
			} catch (EOFException e) {
				e.printStackTrace();
			}catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return stringArray;
    }

    //method to return all the existing customer records
    public static ArrayList<String[]> displayAll() {
    	
    	//creating string array	
		String[] stringArray = new String[5];
		//creating an array list that stores the string array
		ArrayList<String[]> myArrayList = new ArrayList<String[]>();
	
		//return result only if the file exists
    	if(myFile.exists()) {
						
			try (
					DataInputStream input = new DataInputStream(new FileInputStream(filePath));){
				
					//read the binary file and return the values in an arraylist of string[]
					int byteRead;
					char result;
					String s="";
					while((byteRead = input.read())!=-1) {
						
						result = (char) byteRead;
						s +=result;				
					}
					
					String[] line = s.split("~"); 
					for(int i=0;i<line.length;i++) {
						stringArray = line[i].split(",");
						myArrayList.add(stringArray);
					}
					//end of read
			} catch (EOFException e) {
				e.printStackTrace();
			}catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}	

    	}
    	//if file doesn't exist
    	else {
    		myArrayList = null;
    	}
    	//		myArrayList = sort();
		return myArrayList;
    }

}

	// method to sort the record before displaying it
//    private static ArrayList<String[]> sort() {
//    	String[] stringArray = new String[5];
//    	ArrayList<String[]> mysortedArrayList = new ArrayList<String[]>();
//    	ArrayList<String[]> myArrayList = new ArrayList<String[]>();
//    	String prevId="";
//    	String prevLine="";
//    	String tempId="";
//    	String tempLine="";
//    	String[] sortedline = null;
//    	
//    	try (
//				DataInputStream input = new DataInputStream(new FileInputStream(filePath));){
//			
//				//read the binary file and return the values in an arraylist of string[]
//				int byteRead;
//				char result;
//				String s="";
//				while((byteRead = input.read())!=-1) {
//					
//					result = (char) byteRead;
//					s +=result;				
//				}
//				
//				
//				String[] line = s.split("~"); 
//				
//				for(int i=0;i<line.length;i++) {
//					stringArray = line[i].split(",");
//					if(i==0) {
//						prevId = stringArray[i];
//						prevLine = line[i];
//						continue;
//					}
////					System.out.println("String0" + stringArray[0]);
////					System.out.println("Previd" + prevId);
//						if(stringArray[0].compareTo(prevId) < 0) {
//							tempLine = prevLine;
//							line[i-1] = line[i];
//							line[i] = tempLine;
//							
//							tempId = prevId;
////							System.out.println("iteration" + i + "temp=" + tempLine + "line[" + (i-1) + "=" + line[i-1]
////									+ "line[" + i +"]=" + line[i]);
//						}
//						else {
//							prevLine = line[i];
//							prevId = stringArray[0];
//						}
//					}
//				mysortedArrayList.add(line);
//					
//				//end of read
//		} catch (EOFException e) {
//			e.printStackTrace();
//		}catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}	
//    	
//    	
//    	return mysortedArrayList;
//    }
//
//}
