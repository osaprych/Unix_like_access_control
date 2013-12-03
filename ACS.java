// Olga Saprycheva
// UT EID os3587
// CS ID osaprych

import java.util.*;
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
            if(x == 0){
                modeTemp = modeTemp + "000";
            }
            // else if(x == 1){
            //     modeTemp = modeTemp + "001";
            // }
            else
                modeTemp = modeTemp + Integer.toBinaryString(x);
        }
        // System.out.println(s);
        // System.out.println(modeTemp);
        return modeTemp;
    }

    public void resetMode(String m){
        mode = this.convertMode(m);
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

    public static int actionRead(String[] action){
        if(action.length == 3 && users.containsKey(action[1]) && files.containsKey(action[2])){
            // user is owner of the file
            if(users.get(action[1]).equals(files.get(action[2]).owner.userName)){
                if(files.get(action[2]).mode.charAt(3) == '1'){
                    return 1;
                }
                else{
                    return 0;
                }
            }
            // file belongs to user's group
            else if(users.get(action[1]).group.groupName.equals(files.get(action[2]).owner.group.groupName)){
                if(files.get(action[2]).mode.charAt(6) == '1'){
                    return 1;
                }
                else{
                    return 0;
                }
            }
            // other
            else if(!users.get(action[1]).group.groupName.equals(files.get(action[2]).owner.group.groupName)){
                // System.out.println("mode = " + files.get(action[2]).mode);
                if(files.get(action[2]).mode.charAt(9) == '1'){
                    return 1;
                }
                else{
                    return 0;
                }
            }
        }
        else{
            System.out.println("Invalid input!");
        }
        return 0;
    }

    public static int actionWrite(String[] action){
        if(action.length == 3 && users.containsKey(action[1]) && files.containsKey(action[2])){
            // user is owner of the file
            if(users.get(action[1]).equals(files.get(action[2]).owner.userName)){
                if(files.get(action[2]).mode.charAt(4) == '1'){
                    return 1;
                }
                else{
                    return 0;
                }
            }
            // file belongs to user's group
            else if(users.get(action[1]).group.groupName.equals(files.get(action[2]).owner.group.groupName)){
                if(files.get(action[2]).mode.charAt(4) == '1'){
                    return 1;
                }
                else{
                    return 0;
                }
            }
            // other
            else if(!users.get(action[1]).group.groupName.equals(files.get(action[2]).owner.group.groupName)){
                // System.out.println("mode = " + files.get(action[2]).mode);
                if(files.get(action[2]).mode.charAt(10) == '1'){
                    return 1;
                }
                else{
                    return 0;
                }
            }
        }
        else{
            System.out.println("Invalid input!");
        }
        return 0;
    }

    public static int actionExecute(String[] action){
        if(action.length == 3 && users.containsKey(action[1]) && files.containsKey(action[2])){
            // user is owner of the file
            if(users.get(action[1]).equals(files.get(action[2]).owner.userName)){
                if(files.get(action[2]).mode.charAt(5) == '1'){
                    return 1;
                }
                else{
                    return 0;
                }
            }
            // file belongs to user's group
            else if(users.get(action[1]).group.groupName.equals(files.get(action[2]).owner.group.groupName)){
                if(files.get(action[2]).mode.charAt(8) == '1'){
                    return 1;
                }
                else{
                    return 0;
                }
            }
            // other
            else if(!users.get(action[1]).group.groupName.equals(files.get(action[2]).owner.group.groupName)){
                // System.out.println("mode = " + files.get(action[2]).mode);
                if(files.get(action[2]).mode.charAt(11) == '1'){
                    return 1;
                }
                else{
                    return 0;
                }
            }
        }
        else{
            System.out.println("Invalid input!");
        }
        return 0;
    }

    public static int actionChmod(String[] action){
        if(action.length == 4){
            // if file owner or root user
            if( (files.get(action[2]).owner == users.get(action[1])) || (users.get(action[1]).userName.equals("root")) ){
                files.get(action[2]).resetMode(action[3]);
                return 1;
            }
        }
        else{
            System.out.println("Invalid input!");
        }
        return 0;
    }

    public static void printResult(int result, String[] action){
        if((action.length == 3 && users.containsKey(action[1]) && files.containsKey(action[2])) ||
            (action.length == 4 && users.containsKey(action[1]) && files.containsKey(action[2]) && action[0].equals("chmod"))){
            System.out.print(action[0] + " ");
            // file owner user
            if(files.get(action[2]).mode.charAt(0) == '1')
                System.out.print(files.get(action[2]).owner.userName + " ");
            // running user
            else
                System.out.print(action[1] + " ");

            // file owner's group
            if(files.get(action[2]).mode.charAt(1) == '1')
                System.out.print(files.get(action[2]).owner.group.groupName + " " + result + "\n");
            // running user's group
            else
                System.out.print(users.get(action[1]).group.groupName + " " + result + "\n");
        }
    }

    public static void main(String args[]) throws IOException{

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
        
        String input = "";

        // while(!"EXIT")
        while(true){
            Scanner in = new Scanner(System.in);
            System.out.println("\nEnter action");
            input = in.nextLine();
            String[] arrayInput = input.split(" ");
            int result = 0;
            if(arrayInput[0].toLowerCase().equals("read")){
                result = actionRead(arrayInput);
                printResult(result, arrayInput);
            }
            else if(arrayInput[0].toLowerCase().equals("write")){
                result = actionWrite(arrayInput);
                printResult(result, arrayInput);
            }
            else if(arrayInput[0].toLowerCase().equals("execute")){
                result = actionExecute(arrayInput);
                printResult(result, arrayInput);
            }
            else if(arrayInput[0].toLowerCase().equals("chmod")){
                result = actionChmod(arrayInput);
                printResult(result, arrayInput);
            }
            else if(arrayInput[0].toLowerCase().equals("exit")){
                break;
            }
            else{
                System.out.println("Invalid input!");
            }
        }

        // while(!"EXIT")
        // prompt the user for input of format   action user file
        // output 0 or 1 depending on whether request was allowed or not.

        // print the current state of the system to state.log 
        // end the program

       }
    }