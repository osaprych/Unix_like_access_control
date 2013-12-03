// Olga Saprycheva
// UT EID os3587
// CS ID osaprych

import java.util.*;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.Scanner;
// import java.util.HashMap;
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

    // public void addUser(User u){
    //     groupUsers.add(u);
    // }
}

class UnixFile{
    public String fileName;
    public String mode;
    public User owner;

    public UnixFile(String n, User o, String m){
        fileName = n;
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
     
class ACS{
    static HashMap<String, Group> groups = new HashMap<String, Group>();
    static HashMap<String, User> users = new HashMap<String, User>();
    static HashMap<String, UnixFile> files = new HashMap<String, UnixFile>();

    public static void readUserList(File userListFile) throws IOException{
        Scanner userList = new Scanner(new FileReader(userListFile));
        String line;
        int counter = 0;
        while(userList.hasNextLine()){
            line = userList.nextLine();
            String[] result = line.split(" ");
            // System.out.println(Arrays.toString(result));
            if(result.length == 2){
                if(!groups.containsKey(result[1])){
                    Group g = new Group(result[1]);
                    groups.put(result[1], g);
                    // groups.add(g);
                }
                users.put(result[0], new User(result[0], groups.get(result[1])));
                // strUsers.put(result[0], counter);
            }

            counter++;
        }
    }

    public static void readFileList(File fileListFile) throws IOException{
        Scanner fileList = new Scanner(new FileReader(fileListFile));
        String line;
        // int counter = 0;
        while(fileList.hasNextLine()){
            line = fileList.nextLine();
            String[] result = line.split(" ");
            System.out.println(Arrays.toString(result));

            UnixFile f = new UnixFile(result[0], users.get(result[1]), result[2]);
            files.put(result[0], f);
        }
    }

    public static void printHashMap(HashMap m){
        Iterator iterator = m.keySet().iterator();  
   
        while (iterator.hasNext()) {  
            String key = iterator.next().toString();  
            String value = m.get(key).toString();  
   
            System.out.println(key + " " + value);  
        }
    }

    public static void main(String args[]) throws IOException{
        // ArrayList<String> strGroups = new ArrayList<String>();
        // ArrayList<Group> groups = new ArrayList<Group>();
        // ArrayList<User> users = new ArrayList<User>();

        // check for -r option
        if (!args[0].equals("-r")){
            // create a root user, a member of a group root
            Group rootGroup = new Group("root");
            User rootUser = new User("root", rootGroup);
            groups.put("root", rootGroup);
            users.put("root", rootUser);
            // rootGroup.addUser(rootUser);

            // read userList
            File userListFile = new File(args[0]);
            System.out.println("args[0] = " + args[0]);
            readUserList(userListFile);

            printHashMap(users);
            printHashMap(groups);
            
            // read fileList
            File fileListFile = new File(args[1]);
            System.out.println("args[1] = " + args[1]);
            readFileList(fileListFile);
            printHashMap(files);            
        }

        else{
            // read userList
            File userListFile = new File(args[1]);
            System.out.println("args[1] = " + args[1]);
            readUserList(userListFile);

            printHashMap(users);
            printHashMap(groups);

            // read fileList
            File fileListFile = new File(args[2]);
            System.out.println("args[2] = " + args[2]);
            readFileList(fileListFile);
            printHashMap(files);
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