package java_classes;
import java.util.Scanner;

public class contractSummary{


public contractSummary(){
}


//print single contract summary option1 
public boolean printContractSummary(String[] dataArry){
    boolean printStatus =false; 
    try{
    int ex =0; //extra spaces value 
    for(int r=0;r<13;r++){  //row
        if((r==0) || (r==12)){
            System.out.print('+');
            for(int c=0;c<50;c++){
                System.out.print('-');
            }
            System.out.println('+');
        }
        else if(r==2){
            String s = String.format("|%10s %s","Customer:",dataArry[6]);  //name of customer 
            System.out.print(s);
            ex =52-s.length(); //extra spaces
            s = String.format("%" + ex + "s" ,"|");
            System.out.println(s);
     
        }
        else if(r==4){
           String[] tagsArr = {"Ref:",dataArry[5], "Date:" , dataArry[0]}; //ref num and date 
            System.out.println(tagsToString(tagsArr));
        }
        else if(r==5){
            String[] tagsArr = {"Package:",dataArry[1], "Data:" , dataArry[2]}; //package and data plan 
            System.out.println(tagsToString(tagsArr));

        }
        else if(r==6){
            String[] tagsArr = {"Period:",dataArry[3], "Type:" , dataArry[8]}; //type period and type of business 
            System.out.println(tagsToString(tagsArr));
        }
        else if(r==8){
            String[] tagsArr = {"Discount:",dataArry[7], "Intl. Calls:" , dataArry[4]}; //discount and international call boolean 
            System.out.println(tagsToString(tagsArr));

        }
        else if(r==10){
            String s = String.format("|%34s %s","Discounted Monthly Charge:", dataArry[9]);// total monthly discount
            System.out.print(s);
            ex =52-s.length(); //extra spaces
            s = String.format("%" + ex + "s" ,"|");
            System.out.println(s);
            // continue; 

        }
        else {  //last row to end the table 
            System.out.print('|');
            for(int c=0;c<50;c++){
                System.out.print(' ');
            }
            System.out.println('|');
        }
    }
    printStatus= true; 
    }
    catch(Exception e){
        printStatus = false;

    }
    return printStatus; 
}

//method to print summary of all contracts option 2 

public void allContractsSummary(String textFileData, byte monthTag){
    GetSummaryModel getSummary =new GetSummaryModel(textFileData,monthTag);
    System.out.println("****Summary of Contracts****");
    System.out.printf("Total Number of contracts: %s",getSummary.getNumberOfContracts());
    System.out.println();
    System.out.printf("Contracts with High and Unlimited Data Bundles: %s",
            getSummary.getNumberOfHighDataBundles()+ getSummary.getNumberOfUnlimitedDataBundles());
    System.out.println();
    System.out.printf("Average charge for large packages: % .2f",
                getSummary.getAverageOfLargePackage());
    System.out.println("\n");
    // Below month count display
    String f1="";
    String f2="";
    for(int m=0; m<StaticVariables.monthsNameArr.length; m++){
        f1 += String.format("%5s", StaticVariables.monthsNameArr[m]);
        f2 += String.format("%5s", getSummary.getmonthlyContractsNumber()[m]);
    }
    if(monthTag ==0){  //only print if month is not selected
        System.out.println("Number of contracts per Month:\n");
        System.out.println(f1); //print month name
        System.out.println(f2);  //total count of contracts per month
    }
    //move main loop to selectOptions class 
    System.out.println("\n*****Press enter to go back to main screen*****");
    new Scanner(System.in).nextLine();
    new selectOptions().optionSelectedByUser();
}



//support method helps to return string output with defined formating  
private String tagsToString(String[] rowString){
    int ex=0;
    String str; 
    str = String.format("|%10s %s",rowString[0], rowString[1]);
    ex =35-str.length(); //extra spaces
    str= str.concat(String.format("%" + ex + "s %s",rowString[2], rowString[3] ));
    ex = 52 - (str.length());
    str=str.concat(String.format("%" + ex + "s" ,"|"));
 return str; 
}
}