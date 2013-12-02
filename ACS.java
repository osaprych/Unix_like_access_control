// Olga Saprycheva
// UT EID os3587
// CS ID osaprych

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.*;

class User{
    public String userName;
    public Group group;

    public User(String n, Group g){
        userName = n;
        group = g;
    }
}

class Group{
    public String groupName;
    public ArrayList<User> groupUsers;

    public Group(String n){
        groupName = n;
        groupUsers = new ArrayList<User>();
    }

    public void addUser(User u){
        groupUsers.add(u);
    }
}

class UnixFile{
    public String mode;
    public User owner;

    public UnixFile(String m, User o){
        mode = this.convertMode(m);
        owner = o;
    }

    public String convertMode(String s){
        // convert octal string to binary
        // file method
        // int i = 7755;
        // String s = Integer.toString(i);
        String modeTemp = "";
        for(int j = 0; j < s.length(); j++){
            int x = Character.getNumericValue(s.charAt(j));
            modeTemp = modeTemp + Integer.toBinaryString(x);
        }
        // System.out.println(s);
        // System.out.println(modeTemp);
        return modeTemp;
    }
}
     
class ACS
{
    public static void main(String args[]) throws IOException{
        // check for -r option
        if (!args[0].equals("-r")){
            // create a root user, a member of a group root
            Group rootGroup = new Group("root");
            User rootUser = new User("root", rootGroup);
            rootGroup.addUser(rootUser);

            // read userList
            // read fileList
            File userListFile = new File(args[0]);
            System.out.println("args[0] = " + args[0]);
            File fileListFile = new File(args[1]);
            System.out.println("args[1] = " + args[1]);

            Scanner userList = new Scanner(new FileReader(userListFile));
            String line;
            while(userList.hasNextLine()){
                line = userList.nextLine();
                String[] result = line.split(" ");
                // System.out.println(Arrays.toString(result));
                
            }
                

        }


        else{
            // read userList
            // read fileList
            File userListFile = new File(args[1]);
            System.out.println("args[1] = " + args[1]);
            File fileListFile = new File(args[2]);
            System.out.println("args[2] = " + args[2]);
        }
          

        // while(!"EXIT")
        // prompt the user for input of format   action user file
        // output 0 or 1 depending on whether request was allowed or not.

        // print the current state of the system to state.log 
        // end the program


          // int a;
          // float b;
          // String s;
     
          // Scanner in = new Scanner(System.in);
     
          // System.out.println("Enter a string");
          // s = in.nextLine();
          // System.out.println("You entered string "+s);
     
          // System.out.println("Enter an integer");
          // a = in.nextInt();
          // System.out.println("You entered integer "+a);
     
          // System.out.println("Enter a float");
          // b = in.nextFloat();
          // System.out.println("You entered float "+b);   
       }
    }