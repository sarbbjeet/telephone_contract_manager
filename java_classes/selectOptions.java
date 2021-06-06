package java_classes;
import java.io.InputStreamReader;
import java.util.Scanner;

public class selectOptions extends ReadWriteTextFile implements ReadCallback{
    private ReadCallback callback;  //callback 
   
    //constructor  
  public selectOptions(){
    super();
    this.callback = (ReadCallback)this;

  }

private void printMainMenu(){
System.out.println("*************************************");
System.out.println("*****Please select below options*****");
System.out.println("*************************************");
System.out.println("1. Enter new Contract");
System.out.println("2. Display summary of the Contracts");
System.out.println("3. Display summary of Contracts for Selected Month");
System.out.println("4. Find the display Contract");
System.out.println("5. Modify existing Contract");
System.out.println("0. exit");
}

//handle user command line 
private void navigateToNextOptions(byte userInput){
switch(userInput){
    case 1:
      UserInputHandler user = new UserInputHandler();
      user.contractHolderName(); //enter contact holder name
      user.contractRefNum(); //enter ref num e.g. PT123N 
      user.selectPackage();   //enter package 1 2 or 3 
      user.selectDataPlan();  //enter data bundle 1 2 3 and 4 
      user.selectInternationalCall(); //international call select Y or N
      user.selectContractPeriod();   //contract length 1 12 18 or 24 months
      new contractSummary().printContractSummary(user.newContractArr());
      System.out.println(user.contractTextForm());
      new ReadWriteTextFile().appendDataToFile(StaticVariables.CONTRACT_FILE_PATH, user.contractTextForm());
      System.out.println("****Successfully added new contract****");
      this.optionSelectedByUser();

    break;
    case 2:
      byte fileSelectTag = this.fileSelectionMenu();
      byte mTag =0 ;  //no month selection 
      if(fileSelectTag ==1)
        this.readFile(StaticVariables.CONTRACT_FILE_PATH,this.callback,mTag); //month filter 0

      else if(fileSelectTag == 2)
        this.readFile(StaticVariables.ARCHIVE_FILE_PATH,this.callback,mTag); //month filter 0
      else
         //back to main screen
        this.optionSelectedByUser();
    break;
    case 3:
       byte fSelectTag = this.fileSelectionMenu(); 
       byte monthTag = this.monthSelectionMenu();  
       if(fSelectTag ==1){
           this.readFile(StaticVariables.CONTRACT_FILE_PATH,this.callback,monthTag);
          
       }
       else if(fSelectTag == 2){
           this.readFile(StaticVariables.ARCHIVE_FILE_PATH,this.callback,monthTag);
       }
       else
         //back to main screen
         this.optionSelectedByUser();
        break;
    case 4:
    System.out.println("******sorry this option is not active *****");
    this.optionSelectedByUser();
    break;
    case 5:
     new ContractSearchModification().searchMenu();
    break;
    default:
    System.out.println("**********************************"); 
    System.out.println("**************EXIT****************"); 
    System.out.println("**********************************"); 
}
}

//primary method run to ask user for input  
public void optionSelectedByUser(){
    byte input; 
    Scanner scanner = new Scanner(new InputStreamReader(System.in)); 
    boolean inputvalidation= false;
    while(!inputvalidation){
    printMainMenu();
    input = scanner.nextByte();
    if(input>=0 && input<=5){
    inputvalidation=true;
    navigateToNextOptions(input);

    }
    else
        System.out.println("******wrong entry*******");
    
    }
}

/*  options to select text file to read for accessing  summary */
private byte fileSelectionMenu(){  
//select 1 text file
 boolean inputV=false;
 byte fileSelectTag=0; //indentify which file is selected(contracts.txt or Archive.txt) by user.  
 while(!inputV){
     System.out.println("****Select option to select a .txt file for summary****");
     System.out.println("1. Summary of Contract.txt file");
     System.out.println("2. Summary of Achieve.txt file");
     System.out.println("0. Go back to main screen");
     Scanner scanner = new Scanner(new InputStreamReader(System.in)); 
     byte input = scanner.nextByte();
     if(input==1 && this.isfileAvailable(StaticVariables.CONTRACT_FILE_PATH)){
        inputV =true; //break loop
        fileSelectTag=1; //contracts file is selected
         
     }
     else if(input==2 && this.isfileAvailable(StaticVariables.ARCHIVE_FILE_PATH)){
         inputV =true; //break loop
         fileSelectTag=2; //Archive file is selected
     }
     else if(input==0){
         fileSelectTag=0;
         inputV = true;
     }
     else
     System.out.println("*****Invalid entry or file is not found*****"); 
    
    }
 return fileSelectTag;
}

/* menu to select month from list of 12 month */
private byte monthSelectionMenu(){
    boolean wLoop =false;
    byte monthTag=0;
    while(!wLoop){
        System.out.println("*****Select below digit for month selection ******\n");
        for (int m=0; m<StaticVariables.monthsNameArr.length;m++)
            System.out.printf("%3s. %4s\n", m+1 , StaticVariables.monthsNameArr[m]);
  
        monthTag = new Scanner(System.in).nextByte();
        if(monthTag>=1 && monthTag<=12)
           wLoop=true; //break loop
        else
          System.out.println("****wrong digit is selected*****");
    }
    return monthTag;
}

//callback function (run when data text file reading proccess is finished)
@Override
public void readFileCallback(String str, byte monthTag) {
    new contractSummary().allContractsSummary(str, monthTag);
} 

}