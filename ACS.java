// Olga Saprycheva
// UT EID os3587
// CS ID osaprych

/*
Please note that since I'm using HashMap to store files, 
the order in whitch the files are written to state.log file might be different from the order they were in fileList.
*/

import java.util.*;
import java.io.*;

class User{
    public String userName;
    public Group group;
    public boolean root;

    public User(String n, Group g){
        this(n, g, false);
    }

    public User(String n, Group g, boolean r){
        userName = n;
        group = g;
        root = r;
    }

    public boolean isRoot(){
        return root;
    }
}

class Group{
    public String groupName;
    public ArrayList<User> groupUsers;

    public Group(String n){
        groupName = n;
        groupUsers = new ArrayList<User>();
    }
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
        String modeTemp = "";
        for(int j = 0; j < s.length(); j++){
            int x = Character.getNumericValue(s.charAt(j));
            if(x == 0){
                modeTemp = modeTemp + "000";
            }
            else if(x == 1){
                modeTemp = modeTemp + "001";
            }
            else if(x == 2){
                modeTemp = modeTemp + "010";
            }
            else if(x == 3){
                modeTemp = modeTemp + "011";
            }
            else
                modeTemp = modeTemp + Integer.toBinaryString(x);
        }
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
    static LinkedHashMap<String, UnixFile> files = new LinkedHashMap<String, UnixFile>();

    public static void readUserList(File userListFile) throws IOException{
        Scanner userList = new Scanner(new FileReader(userListFile));
        String line;
        while(userList.hasNextLine()){
            line = userList.nextLine();
            String[] result = line.split(" ");
            if(result.length == 2){
                if(!groups.containsKey(result[1])){
                    Group g = new Group(result[1]);
                    groups.put(result[1], g);
                }
                users.put(result[0], new User(result[0], groups.get(result[1])));
            }
        }
    }

    public static void readFileList(File fileListFile) throws IOException{
        Scanner fileList = new Scanner(new FileReader(fileListFile));
        String line;
        while(fileList.hasNextLine()){
            line = fileList.nextLine();
            String[] result = line.split(" ");
            System.out.println(Arrays.toString(result));

            UnixFile f = new UnixFile(result[0], users.get(result[1]), result[2]);
            files.put(result[0], f);
        }
    }

    public static void printHashMap(HashMap m){
        Iterator i = m.keySet().iterator();  
   
        while (i.hasNext()) {  
            String key = i.next().toString();  
            String value = m.get(key).toString();  
   
            System.out.println(key + " " + value);  
        }
    }

    public static int actionRead(String[] action){
        if(action.length == 3 && users.containsKey(action[1]) && files.containsKey(action[2])){
            // user is owner of the file or root user
            if( users.get(action[1]).equals(files.get(action[2]).owner.userName) || 
                    users.get(action[1]).isRoot() ){
                if(files.get(action[2]).mode.charAt(3) == '1' || users.get(action[1]).isRoot()){
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
            // user is owner of the file or root user
            if(users.get(action[1]).equals(files.get(action[2]).owner.userName) || users.get(action[1]).isRoot() ){
                if(files.get(action[2]).mode.charAt(4) == '1' || users.get(action[1]).isRoot()){
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
            // user is owner of the file or root
            if( users.get(action[1]).equals(files.get(action[2]).owner.userName) || users.get(action[1]).isRoot() ){
                if(files.get(action[2]).mode.charAt(5) == '1' || users.get(action[1]).isRoot()){
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
        if(action.length == 4 && action[3].length() == 4){
            // if file owner or root user
            if( (files.get(action[2]).owner == users.get(action[1])) || users.get(action[1]).isRoot() ){
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

    public static void writeLog() throws IOException{
        File logFile = new File("state.log");
        BufferedWriter bw = new BufferedWriter(new FileWriter(logFile));
        Iterator i = files.keySet().iterator();  
   
        while (i.hasNext()) {  
            String key = i.next().toString();

            // MODE

            // owner
            if(files.get(key).mode.charAt(3) == '1')
                bw.write("r");
            else
                bw.write("-");

            if(files.get(key).mode.charAt(4) == '1')
                bw.write("w");
            else
                bw.write("-");

            if(files.get(key).mode.charAt(0) == '1' && files.get(key).mode.charAt(5) == '0')
                bw.write("S");
            else if(files.get(key).mode.charAt(0) == '1' && files.get(key).mode.charAt(5) == '1')
                bw.write("s");
            else if(files.get(key).mode.charAt(0) == '0' && files.get(key).mode.charAt(5) == '1')
                bw.write("x");
            else
                bw.write("-");

            // group
            if(files.get(key).mode.charAt(6) == '1')
                bw.write("r");
            else
                bw.write("-");

            if(files.get(key).mode.charAt(7) == '1')
                bw.write("w");
            else
                bw.write("-");

            if(files.get(key).mode.charAt(1) == '1' && files.get(key).mode.charAt(8) == '0')
                bw.write("S");
            else if(files.get(key).mode.charAt(1) == '1' && files.get(key).mode.charAt(8) == '1')
                bw.write("s");
            else if(files.get(key).mode.charAt(1) == '0' && files.get(key).mode.charAt(8) == '1')
                bw.write("x");
            else
                bw.write("-");

            // other
            if(files.get(key).mode.charAt(9) == '1')
                bw.write("r");
            else
                bw.write("-");

            if(files.get(key).mode.charAt(10) == '1')
                bw.write("w");
            else
                bw.write("-");

            if(files.get(key).mode.charAt(2) == '1' && files.get(key).mode.charAt(11) == '0')
                bw.write("T");
            else if(files.get(key).mode.charAt(2) == '1' && files.get(key).mode.charAt(11) == '1')
                bw.write("t");
            else if(files.get(key).mode.charAt(2) == '0' && files.get(key).mode.charAt(11) == '1')
                bw.write("x");
            else
                bw.write("-");

            bw.write(" " + files.get(key).owner.userName.toLowerCase() + " " + files.get(key).owner.group.groupName.toLowerCase()
             + " " + key.toLowerCase() + "\n");
        }

        bw.close();
    }

    public static void main(String args[]) throws IOException{

        // check for -r option
        if (!args[0].equals("-r")){
            // create a root user, a member of a group root
            Group rootGroup = new Group("root");
            User rootUser = new User("root", rootGroup, true);
            groups.put("root", rootGroup);
            users.put("root", rootUser);

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
                writeLog();
                break;
            }
            else{
                System.out.println("Invalid input!");
            }
        }
    }
}