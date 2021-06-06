
/*filter received data from text file and find out required 
summary parameters */
package java_classes;
public class GetSummaryModel {
  private String fileStringData="";
  private byte monthSelectionTag=0; //default no month selection
  private int NumberOfContracts;
  private int NumberOfHighDataBundles;
  private int NumberOfUnlimitedDataBundles;
  private float AverageOfLargePackage;
  private int[] monthlyContractsNumber;  


//constructor                      
public GetSummaryModel(String fileStringData, byte monthTag){
  this.fileStringData=fileStringData;
  this.monthSelectionTag=monthTag;
  try {
    this.filterSummaryParameters();  //default method to filter data    
  } catch (Exception e) {
      System.out.println("Error to filter ...");
      new selectOptions().optionSelectedByUser();
  }
}

//getter functions  //
public int getNumberOfContracts(){
 
return this.NumberOfContracts;
}

public int getNumberOfHighDataBundles(){
    return NumberOfHighDataBundles; 
}
public int getNumberOfUnlimitedDataBundles(){
    return NumberOfUnlimitedDataBundles; 
}

public float getAverageOfLargePackage(){
    return AverageOfLargePackage; 
}

public int[] getmonthlyContractsNumber(){
    return monthlyContractsNumber; 
} 


/* function to perform all type of filtering such as find number of  contract, 
number of data bundles and many more
*/

private void filterSummaryParameters(){
  
    String[] lines = this.fileStringData.split("\r\n|\r|\n"); //search for next line
    int countHighDataBundles=0;  //count high data budles
    int countUnlimitedDataBundles=0;  //count number of unlimitied data bundles
    int countLargePackage=0;  //count number of large package in the data 
    float totalPrice=0;
    int[] _monthlyCountContract= new int[12]; 
    for(int i=0;i<lines.length;i++){
       String[] col =lines[i].split("\\s+");//split by any non word char
       //System.out.println(col);
       
        //// count monthly contracts
       String monthName=col[0].split("-")[1];   //split date and get month 
       for(int m =0 ; m<StaticVariables.monthsNameArr.length; m++){
           if(StaticVariables.monthsNameArr[m].equals(monthName))
             _monthlyCountContract[m]++;
       }
       //bundle, average price and large package 
      if(this.monthSelectionTag !=0){
          if(col[2].equals("3") && StaticVariables.monthsNameArr[this.monthSelectionTag-1].equals(monthName))
            countHighDataBundles++; 
          else if(col[2].equals("4") && StaticVariables.monthsNameArr[this.monthSelectionTag-1].equals(monthName))
            countUnlimitedDataBundles++;
          if(col[1].equals("3") && StaticVariables.monthsNameArr[this.monthSelectionTag-1].equals(monthName)){
            totalPrice = totalPrice + Float.parseFloat(col[9]);
            countLargePackage ++; 
          }
          this.NumberOfContracts = _monthlyCountContract[this.monthSelectionTag-1];
        }
        else{
            if(col[2].equals("3"))
            countHighDataBundles++; 
          else if(col[2].equals("4"))
            countUnlimitedDataBundles++;
          if(col[1].equals("3")){
            totalPrice = totalPrice + Float.parseFloat(col[9]);
            countLargePackage ++; 
          }
          this.NumberOfContracts = lines.length;
        } 
  
    }
    //set all parameters
    this.NumberOfHighDataBundles=countHighDataBundles;
    this.NumberOfUnlimitedDataBundles = countUnlimitedDataBundles;
    this.AverageOfLargePackage = totalPrice/countLargePackage;
    this.monthlyContractsNumber = _monthlyCountContract;
   }
   
}

