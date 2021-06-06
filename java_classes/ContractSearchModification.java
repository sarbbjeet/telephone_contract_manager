package java_classes;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class ContractSearchModification extends UserInputHandler implements ReadCallback{
    private String searchChar=""; 
    private ReadCallback callback;
    private String allContracts="";
    private String refNum="";    
    private String selectedContract=""; 
    public ContractSearchModification(){
     super(); 
     this.callback= (ReadCallback)this;
    }

public void searchMenu(){
    boolean loop=false;
  while(!loop){
    System.out.println("******Please enter 2-char to search for a contract******");
    System.out.print("Type name or ref num 2 char : ");
    searchChar= new Scanner(System.in).nextLine();
    searchChar = searchChar.toLowerCase();  //convert to lower case
    if(searchChar.length()==2){
        new ReadWriteTextFile().readFile(StaticVariables.CONTRACT_FILE_PATH, this.callback, (byte)0); 
        loop=true ; //break loop/ 
    }   
    else{
        System.out.println("input error ");
        continue;
    }
}
}

@Override
public void readFileCallback(String allContracts, byte tag) {
    this.allContracts = allContracts;
    String[] lines= allContracts.split("\n");
    String matchedContracts="";
    byte foundContracts =0;   
    for (int i=0;i<lines.length;i++){
        String[] col=lines[i].split("\\s+");
        if(((col[5].toLowerCase()).indexOf(searchChar)!=-1) || ((col[6].toLowerCase()).indexOf(searchChar)!=-1)){
            matchedContracts += lines[i] + "\n";
            foundContracts ++;
            System.out.printf("%2s: %s\n", foundContracts, lines[i]); 
        }
    }
    if(matchedContracts !=""){
        this.selectOneContract(matchedContracts, foundContracts);
    }
    else{
         System.out.println("no-contract found");
         this.searchMenu();
    }
  
}

private void selectOneContract(String matchedContracts, byte foundContracts){
//user input
try{
    System.out.println("*******select one contract ********");
    byte selectOne=  new Scanner(System.in).nextByte();
    if(selectOne>=1 && selectOne<=foundContracts){
        String contractDate = matchedContracts.split("\n")[selectOne-1].split("\\s+")[0];
        String contractStartDay = contractDate.split("-")[0];
        String contractStartMonth= contractDate.split("-")[1];
        String contractStartYear =  contractDate.split("-")[2];
        Month[] m ={Month.JANUARY,Month.FEBRUARY,Month.MARCH, Month.APRIL, Month.MAY, Month.JUNE, Month.JULY
                    , Month.AUGUST, Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER};  
        
        long noOfDaysBetween=0;           
        for(int mm =0; mm < StaticVariables.monthsNameArr.length; mm++){
          if(StaticVariables.monthsNameArr[mm].equals(contractStartMonth)){
             LocalDate dateBefore = LocalDate.of(Integer.parseInt(contractStartYear), m[mm] ,Integer.parseInt(contractStartDay) );
             LocalDate dateAfter = LocalDate.now();
             noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);
                  
          }
        }
        String contractPeriod=matchedContracts.split("\n")[selectOne-1].split("\\s+")[3]; 
        if(noOfDaysBetween>=((Integer.parseInt(contractPeriod))*30)/2){
            this.selectedContract = matchedContracts.split("\n")[selectOne-1];
            //System.out.println(this.selectedContract);
            this.refNum = this.selectedContract.split("\\s+")[5];
            this.setDefaultParametersValue(this.selectedContract); //set initial parameters  
            this.editSelectedContract();
                
        }
        else{
            System.out.println("*****Not Allowed to modify because contract is not older than 50% ********");
            System.out.println("1: go back  search menu");
            System.out.println("2: go back to main menu");
            try{
                String in = new Scanner(System.in).nextLine(); 
                if(in.equals("1")){
                    this.searchMenu();
                }
                else if(in.equals("2")){
                    new selectOptions().optionSelectedByUser(); //main menu // / 
                }

            }
            catch(Exception e ){
                System.out.println("wrong input ..");
                this.searchMenu();

            }
        
        }

    }else{
        System.out.println("Wrong Input");
        this.searchMenu();
    }

}
catch(Exception e){
    System.out.println("Wrong Input");
    this.searchMenu();
}
}


private void editSelectedContract(){
    //// display edit menu  //// 
this.printEditMenu();

try{
byte eSelect = new Scanner(System.in).nextByte();
switch(eSelect){
    case 1:
    System.out.print("Enter package (ex: 1 2 or 3) : ");
    byte tPackageId =new Scanner(System.in).nextByte();
    if(!this.setPackage(tPackageId))
      System.out.println("Wrong Entry");
    this.editSelectedContract();    
    break;
    case 2:
    System.out.print("Enter data bundle(ex: 1 2 3 or 4) : ");
    byte tDatabundle =new Scanner(System.in).nextByte();
    if(!this.setDataBundle(tDatabundle))
      System.out.println("Wrong Entry");
    this.editSelectedContract();     
    break;

    case 3:
    System.out.print("Enter contract period (ex: 1 12 or 24) : ");
    byte tContractPeriod =new Scanner(System.in).nextByte();
    if(!this.setContractLength(tContractPeriod))
      System.out.println("Wrong Entry");
    this.editSelectedContract();    
    break;

    case 4:
    System.out.print("International Call(ex: Y or N) : ");
    String tInc =new Scanner(System.in).nextLine();
    if(!this.setInternationalCall(tInc))
      System.out.println("Wrong Entry");
    this.editSelectedContract();    
    break;

    case 5:
    System.out.print("Enter contract holder name (ex: rohan singh) : ");
    String tName =new Scanner(System.in).nextLine();
    if(!this.setContractHolderName(tName))
      System.out.println("Wrong Entry");
    this.editSelectedContract();    
    break;

    case 6:
    //Display contract

    // String[] contract =new String[11];
    // contract[0]=this.getContractStartDate();
    // contract[1]=String.format("%s", this.getpackage());
    // contract[2]=String.format("%s", this.getDataBundle());
    // contract[3]=String.format("%s", this.getContractLength());
    // contract[4]=this.getInternationalCall();
    // contract[5]=this.getContractRefNum();
    // contract[6]=this.getCName();
    // contract[7]= String.format("%s",this.getContractDiscount()+"%");  
    // contract[8]= String.format("%s",this.getTypeOfBussiness());
    // contract[9]= String.format("%.2f", this.getDiscountedPrice()); 
    new contractSummary().printContractSummary(this.newContractArr());
    this.editSelectedContract();

    break;

    case 7:
    //save updated contract to contracts.txt file
    String tContract="";
    String[] l=this.allContracts.split("\n");
     for(int i=0;i<l.length;i++){
        if(this.refNum.equals(l[i].split("\\s+")[5]))

          tContract += String.format("%8s %6s %6s %8s %8s %6s %8s %8s %8s %.2f\n",this.getContractStartDate(), this.getpackage(), this.getDataBundle(),
                        this.getContractLength(), this.getInternationalCall(), this.getContractRefNum(), this.getCName().split(" ")[0], this.getCName().split(" ")[1], 
                        this.getTypeOfBussiness(), this.getDiscountedPrice()); 
    
         else
           tContract += l[i] + "\n"; 
        
     }
     this.allContracts=tContract;
    
     boolean isSuccess=new ReadWriteTextFile().writeDataToFile(StaticVariables.CONTRACT_FILE_PATH, this.allContracts);
     System.out.println(isSuccess ? "****successfully updated******" : "Error to update contract");
   this.editSelectedContract();

    break;
    case 8:
    new selectOptions().optionSelectedByUser(); //main menu


    default:
    break;
} 
}
catch(Exception e){
System.out.println("Sorry wrong input");
this.editSelectedContract();
}
}

private void printEditMenu(){
    System.out.println("*****select below option to modify contract data*****");
    System.out.println("1: edit package");
    System.out.println("2: edit data bundle");
    System.out.println("3: edit contract period");
    System.out.println("4: select international call");
    System.out.println("5: edit Name of contract holder");
    System.out.println("6: display selected contract");
    System.out.println("7: Save updated contact data to .txt file");
    System.out.println("8: GO back to main menu");
}
}


