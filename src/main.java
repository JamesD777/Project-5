import LinkList.DoubleLinkNode;
import LinkList.LinkedList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Scanner;

/**
 *
 * @author James Dunlap
 */
public class main {

    public static void main(String[] args)throws FileNotFoundException{
        LinkedList A1Available = new LinkedList();
        LinkedList A1Reserved = new LinkedList();
        Hashtable table = new Hashtable();

        Scanner userdb = new Scanner(new File("userdb.dat"));
        while(userdb.hasNext()){
            account u = new account(userdb.next(), userdb.next());
            table.put(u.getName(), u);
        }
        userdb.close();

        try {
            Scanner A1 = new Scanner(new File("A1.txt"));
            int r = 0;
            String line;
            while(A1.hasNextLine()){
                line = A1.nextLine();//get file line by line
		for(int i = 0; i < line.length();i++){//loop through line
                    if(line.charAt(i) == '#')//add node to the respective linked list
                        A1Available.addNode(r,i);
                    else
                        A1Reserved.addNode(r,i);
                }
                r++;
            }
            A1.close();
        } catch (FileNotFoundException e){System.out.println("File not found" + e);}//catch error if file not found
        
        LinkedList A2Available = new LinkedList();
        LinkedList A2Reserved = new LinkedList();
        try {
            Scanner A2 = new Scanner(new File("A2.txt"));
            int row = 0;
            while(A2.hasNextLine()){
                String line = A2.nextLine();//Get file line by line
		for(int i = 0; i < line.length();i++){//loop through line
                    if(line.charAt(i) == '#')//add node to each respective linked list
                        A2Available.addNode(row,i);
                    else
                        A2Reserved.addNode(row,i);
                }
                row++;
            }
            A2.close();
        } catch (FileNotFoundException e){System.out.println("File not found" + e);}//catch error if file is missing
        
        LinkedList A3Available = new LinkedList();
        LinkedList A3Reserved = new LinkedList();
        try {
            Scanner A3 = new Scanner(new File("A3.txt"));//check for file 3
            int row = 0;
            while(A3.hasNextLine()){
                String line = A3.nextLine();//get file line by line
		for(int i = 0; i < line.length();i++){//loop through each line
                    if(line.charAt(i) == '#')//add each node to it's respective linked list
                        A3Available.addNode(row,i);
                    else
                        A3Reserved.addNode(row,i);
                }
                row++;
            }
            A3.close();
        }catch(FileNotFoundException e){System.out.println("File not found" + e);}//catch error if file not found

        Scanner input = new Scanner(System.in);
        LinkedList[] auditoriums = {A1Available, A2Available, A3Available, A1Reserved, A2Reserved, A3Reserved};
        boolean login = true;
        while(login){
            System.out.println("Enter username:");
            String username = input.next();
            if(table.containsKey(username)){
                int tries = 0;
                while (tries < 3) {
                    System.out.println("Enter password:");
                    String password = input.next();
                    boolean loop;
                    boolean loop2;
                    LinkedList a = null;
                    LinkedList r = null;
                    if (password.equals(((account)table.get(username)).getPassword())) {
                        if (username.equals("admin")){
                            loop = true;
                            while(loop){
                                System.out.println("1) View Auditorium\n" + "2) Print Report\n" + "3) Exit");
                                if(input.hasNextInt()){
                                    switch(input.nextInt()){
                                        case 1:
                                            loop2 = true;
                                            while(loop2){
                                                System.out.println("1. Auditorium 1\n" + "2. Auditorium 2\n" + "3. Auditorium 3");
                                                if(!input.hasNextInt()){
                                                    input.next();
                                                    continue;
                                                }
                                                switch(input.nextInt()){
                                                    case 1:
                                                        a = A1Available;
                                                        r = A1Reserved;
                                                        loop2 = false;
                                                        break;
                                                    case 2:
                                                        a = A2Available;
                                                        r = A2Reserved;
                                                        loop2 = false;
                                                        break;
                                                    case 3:
                                                        a = A3Available;
                                                        r = A3Reserved;
                                                        loop2 = false;
                                                        break;
                                                    default:
                                                        System.out.println("Invalid input");

                                                }
                                            }
                                            display(a, r);//display the auditorium
                                            break;
                                        case 2:
                                            //Print 4 lines displaying columns of the names, seats reserved, seats open, and total ticket sales for each auditorium and the total overall.
                                            System.out.print("\nAuditorium:  Reserved Open Adult Senior Child  Sales");
                                            int size1 = size(A1Reserved, A1Available);
                                            int size2 = size(A2Reserved, A2Available);
                                            int size3 = size(A3Reserved, A3Available);
                                            int r1 = 0;
                                            DoubleLinkNode cur = A1Reserved.getHead();
                                            while(cur != null){//get the reserved 1 count 
                                                r1++;
                                                cur = cur.getNext();
                                            }
                                            int r2 = 0;
                                            cur = A2Reserved.getHead();
                                            while(cur != null){//get the reserved 2 count 
                                                r2++;
                                                cur = cur.getNext();
                                            }
                                            int r3 = 0;
                                            cur = A3Reserved.getHead();
                                            while(cur != null){//get the reserved 3 count 
                                                r3++;
                                                cur = cur.getNext();
                                            }
                                            Scanner userd = new Scanner(new File("userdb.dat"));
                                            int a1 = 0, a2 = 0, a3 = 0, s1 = 0, s2 = 0, s3 = 0, c1 = 0, c2 = 0, c3 = 0;
                                            while(userd.hasNext()){
                                                String name = userd.next();
                                                userd.next();
                                                account u = (account)table.get(name);
                                                for(int i = 0; i < u.orders.size(); i++){
                                                    switch(((order)u.orders.get(i)).auditorium){
                                                        case 1:
                                                            a1 += ((order)u.orders.get(i)).adult;
                                                            s1 += ((order)u.orders.get(i)).senior;
                                                            c1 += ((order)u.orders.get(i)).child;
                                                            break;
                                                        case 2:
                                                            a2 += ((order)u.orders.get(i)).adult;
                                                            s2 += ((order)u.orders.get(i)).senior;
                                                            c2 += ((order)u.orders.get(i)).child;
                                                            break;
                                                        case 3:
                                                            a3 += ((order)u.orders.get(i)).adult;
                                                            s3 += ((order)u.orders.get(i)).senior;
                                                            c3 += ((order)u.orders.get(i)).child;
                                                            break;
                                                        default:
                                                            break;
                                                    }
                                                }
                                            }
                                            userdb.close();
                                            
                                            System.out.print("\nAuditorium 1: " + r1 + "       " + (size1 - r1) + "       " + a1 + "       " + s1 + "       " + c1 + "    $" + ((a1*10)+(c1*5.25)+(s1*7.5))+ "   ");
                                            System.out.print("\nAuditorium 2: " + r2 + "       " + (size2 - r2) + "       " + a2 + "       " + s2 + "       " + c2 + "    $" + ((a2*10)+(c2*5.25)+(s2*7.5))+ "   ");
                                            System.out.print("\nAuditorium 3: " + r3 + "       " + (size3 - r3) + "       " + a3 + "       " + s3 + "       " + c3+ "    $" + ((a3*10)+(c3*5.25)+(s3*7.5))+ "   ");

                                            System.out.print("\nTotal:        " + (r1+r2+r3) + "      " + ((size3 - r3)+(size2 - r2)+(size1 - r1)) + "       " + (a1+a2+a3) + "       " + (s1+s2+s3) + "       " + (c1+c2+c3) + "    $" + (((a1+a2+a3)*10)+((c1+c2+c3)*5.25)+((s1+s2+s3)*7.5)));
                                            break;
                                        case 3:
                                            loop = false;
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                            login = false;
                            break;
                        }
                        else{
                            loop = true;
                            while(loop){//main loop, loop till accountwants to exit
                                PrintWriter p = null;
                                System.out.println("1) Reserve Seats\n" + "2) View orders\n" + "3) Update order\n" + "4) Display Receipt\n" + "5) Log Out");
                                if(input.hasNextInt()){
                                    switch(input.nextInt()){
                                        case 1://if account wants to reserve
                                            loop2 = true;
                                            while(loop2){
                                                System.out.println("Which auditorium would you like to reserve in? 1 or 2 or 3?");
                                                if(!input.hasNextInt()){
                                                    input.next();
                                                    continue;
                                                }
                                                order neworder;
                                                switch(input.nextInt()){//set auditorium to the null lists for easy use along with the printwriter
                                                    case 1:
                                                        neworder = new order();
                                                        neworder.auditorium = 1;
                                                        ((account)table.get(username)).orders.add(neworder);
                                                        a = auditoriums[0];
                                                        r = auditoriums[3];
                                                        loop2 = false;
                                                        p = new PrintWriter("A1.txt");
                                                        reserve1(a, r, neworder, input, ((account)table.get(username)));
                                                        break;
                                                    case 2:
                                                        neworder = new order();
                                                        neworder.auditorium = 1;
                                                        ((account)table.get(username)).orders.add(neworder);
                                                        a = auditoriums[1];
                                                        r = auditoriums[4];
                                                        loop2 = false;
                                                        p = new PrintWriter("A2.txt");
                                                        reserve1(a, r, neworder, input, ((account)table.get(username)));
                                                        break;
                                                    case 3:
                                                        neworder = new order();
                                                        neworder.auditorium = 1;
                                                        ((account)table.get(username)).orders.add(neworder);
                                                        a = auditoriums[2];
                                                        r = auditoriums[5];
                                                        loop2 = false;
                                                        p = new PrintWriter("A3.txt");
                                                        reserve1(a,r,neworder, input, ((account)table.get(username)));
                                                        break;
                                                    default:
                                                        System.out.println("Invalid input");
                                                        break;
                                                }
                                            }
                                            r.print(p, a, r);//recursive print and update the files
                                            p.close();//close printwriter
                                            break;

                                        case 2:
                                        view((account)table.get(username));
                                        break;
                                    case 3:
                                        view((account)table.get(username));
                                        update((account)table.get(username), input, auditoriums);
                                        break;
                                    case 4:
                                        displayReceipt((account)table.get(username));
                                        break;
                                    case 5:
                                        loop = false;
                                        break;
                                    default:
                                        System.out.println("Invalid input");
                                        break;
                                    }
                                }
                                else{
                                    input.next();
                                }
                            }
                        }
                    }
                    tries++;
                }
            }
        }
    }

    public static int size(LinkedList a, LinkedList b){//calculate the size of the auditorium
        int row, seat;
        
        if(a.getTail().getSeat() > b.getTail().getSeat())
            seat = a.getTail().getSeat();
        else
            seat = b.getTail().getSeat();
        if(a.getTail().getRow() > b.getTail().getRow())
            row = a.getTail().getRow();
        else
            row = b.getTail().getRow();
        return (row+1) * (seat+1);
    }
    
    public static void display(LinkedList a, LinkedList r){//display the auditorium to the account in console
        System.out.print("  ");
        int s;
        if(r.getTail() == null){//if it was a full file of available, only use the available tail
            s = a.getTail().getSeat()+1;
        }
        else if(a.getTail().getSeat() > r.getTail().getSeat())
            s = a.getTail().getSeat()+1;
        else
            s = r.getTail().getSeat()+1;

        for(int i=1; i<=s; i++)//print out top numbers
                System.out.print(i % 10 + " ");
        System.out.print('\n' + "1 ");
        DoubleLinkNode cura = a.getHead();
        DoubleLinkNode curr = r.getHead();
        
        int row = 0;
        int count = 0;
        while(cura != null && curr != null){//while both currs arent at the tail, loop through the two linked lists and print the data
            if(cura.getSeat() == count && cura.getRow() == row){
                System.out.print("# ");
                cura = cura.getNext();
            }
            else{
                System.out.print(". ");
                curr = curr.getNext();
            }
            count++;//increment count each time a . or # is printed
            if(count == s){//at the end of each line, print a new line and the row number
                count -= s;
                row++;
                System.out.print("\n" + (row+1) + " ");}
        }
        while(cura != null){//deals when there is a row of available at the end
            if(count == s){
                count -= s;
                row++;
                System.out.print("\n" + (row+1) + " ");}
            if(cura.getSeat() == count && cura.getRow() == row){
                System.out.print("# ");
                cura = cura.getNext();
            }
            count++;
        }
        while(curr != null){//deals when there is a row of reserved at the end
            if(count == s){
                count -= s;
                row++;
                System.out.print("\n" + (row+1) + " ");}
            if(curr.getSeat() == count && curr.getRow() == row){
                System.out.print(". ");
                curr = curr.getNext();
            }
            count++;
        }
        if(cura == null && curr != null)//gets the last one
            System.out.print('.' + "\n\n");
        if(cura != null && curr == null)
            System.out.print('#' +"\n\n");
    }

    
    public static int[] findBest(int amountofSeats, LinkedList r, LinkedList a){//calculates the best seat available and returns the row and seat in an array
        int mSeat;
        int mRow;//calculate middle row and middle seat
        if(a.getTail().getSeat() > r.getTail().getSeat())
            mSeat = a.getTail().getSeat() / 2;
        else
            mSeat = r.getTail().getSeat() / 2;
        if(a.getTail().getRow() > r.getTail().getRow())
            mRow = a.getTail().getRow() / 2;
        else
            mRow = r.getTail().getRow() / 2;
        
        int bR = 9999999;//default best seat values for if there are no seats in the auditorium to fit the criteria
        int bS = 9999999;
        DoubleLinkNode cur = a.getHead();
        while(cur.getNext() != null){//loop through auditorium and find best row and seat using distance formula
            if(checkA(cur.getRow(),cur.getSeat(),amountofSeats,a)){
                if((Math.sqrt(Math.pow(Math.abs((double)bR - (double)mRow),2) + Math.pow(Math.abs((double)bS - (double)mSeat),2))) > (Math.sqrt(Math.pow(Math.abs((double)cur.getRow() - (double)mRow),2) + Math.pow(Math.abs((double)(cur.getSeat()+(amountofSeats/2)) - (double)mSeat),2)))){
                    bR = cur.getRow();
                    bS = cur.getSeat();
                }
                else if((Math.sqrt(Math.pow(Math.abs((double)bR - (double)mRow),2) + Math.pow(Math.abs((double)bS - (double)mSeat),2))) == (Math.sqrt(Math.pow(Math.abs((double)cur.getRow() - (double)mRow),2) + Math.pow(Math.abs((double)(cur.getSeat()+(amountofSeats/2)) - (double)mSeat),2)))){
                    if(cur.getRow() == mRow){    //if the distance is equal, find which is on a row closer to the middle
                        bR = cur.getRow();
                        bS = cur.getSeat();
                    }
                    else if(Math.abs(mRow - cur.getRow()) < Math.abs(mRow - bR)){
                        bR = cur.getRow();
                        bS = cur.getSeat();
                    }
                }
            }
            cur = cur.getNext();
        }
        return new int[] {bR, bS};
    }

    public static void reserve1(LinkedList a, LinkedList r, order o, Scanner input, account u){
        display(a,r);
        int row = 0;
        int seat = 0;
        int n = 0;
        boolean loop2 = true;
        while(loop2){
            System.out.println("How many adult tickets do you want?");
            if(!input.hasNextInt()){
                input.next();
                continue;
            }
            n = input.nextInt();
            if(n >= 0)
                loop2 = false;
        }
        o.adult = n;
        loop2 = true;
        while(loop2){
            System.out.println("How many senior tickets do you want?");
            if(!input.hasNextInt()){
                input.next();
                continue;
            }
            n = input.nextInt();
            if(n >= 0)
                loop2 = false;
        }
        o.senior = n;
        loop2 = true;
        while(loop2){
            System.out.println("How many child tickets do you want?");
            if(!input.hasNextInt()){
                input.next();
                continue;
            }
            n = input.nextInt();
            if(n >= 0)
                loop2 = false;
        }
        o.child = n;

        o.row = new int[o.child + o.adult + o.senior];
        o.seat = new int[o.child + o.adult + o.senior];
        o.type = new int[o.child + o.adult + o.senior];

        for(int i = 0; i < o.adult; i++){
            loop2 = true;
            while(loop2){
                System.out.println("Enter row number you would like: ");
                if(!input.hasNextInt()){
                    input.next();
                    continue;
                }
                row = input.nextInt() - 1;
                if(r.getTail() == null){
                    if(row >= 0 && row <= a.getTail().getRow())
                        loop2 = false;
                }
                else if(row >= 0 && (row <= r.getTail().getRow() || row <= a.getTail().getRow()))
                    loop2 = false;
            }
            o.row[i] = row;
            loop2 = true;
            while(loop2){
                System.out.println("Which seat do you want?");
                if(!input.hasNextInt()){
                    input.next();
                    continue;
                }
                seat = input.nextInt() - 1;
                if(r.getTail() == null){
                    if(seat >= 0 && seat <= a.getTail().getSeat())
                        loop2 = false;
                }
                else if(seat >= 0 &&( seat <= r.getTail().getSeat() || seat <= a.getTail().getSeat()))//loop till seat is valid
                    loop2 = false;
            }
            o.seat[i] = seat;
            o.type[i] = 1;
        }
        for (int i = 0; i < o.senior; i++){
            loop2 = true;
            while(loop2){
                System.out.println("Enter row number you would like: ");
                if(!input.hasNextInt()){
                    input.next();
                    continue;
                }
                row = input.nextInt() - 1;
                if(r.getTail() == null){
                    if(row >= 0 && row <= a.getTail().getRow())
                        loop2 = false;
                }
                else if(row >= 0 && (row <= r.getTail().getRow() || row <= a.getTail().getRow()))
                    loop2 = false;
            }
            o.row[i + o.adult] = row;
            loop2 = true;
            while(loop2){
                System.out.println("Which seat do you want?");
                if(!input.hasNextInt()){
                    input.next();
                    continue;
                }
                seat = input.nextInt() - 1;
                if(r.getTail() == null){
                    if(seat >= 0 && seat <= a.getTail().getSeat())
                        loop2 = false;
                }
                else if(seat >= 0 &&( seat <= r.getTail().getSeat() || seat <= a.getTail().getSeat()))//loop till seat is valid
                    loop2 = false;
            }
            o.seat[i + o.adult] = seat;
            o.type[i + o.adult] = 2;
        }

        for(int i = 0; i < o.child; i++){
            loop2 = true;
            while(loop2){
                System.out.println("Enter row number you would like: ");
                if(!input.hasNextInt()){
                    input.next();
                    continue;
                }
                row = input.nextInt() - 1;
                if(r.getTail() == null){
                    if(row >= 0 && row <= a.getTail().getRow())
                        loop2 = false;
                }
                else if(row >= 0 && (row <= r.getTail().getRow() || row <= a.getTail().getRow()))
                    loop2 = false;
            }
            o.row[i + o.adult + o.senior] = row;
            loop2 = true;
            while(loop2){
                System.out.println("Which seat do you want?");
                if(!input.hasNextInt()){
                    input.next();
                    continue;
                }
                seat = input.nextInt() - 1;
                if(r.getTail() == null){
                    if(seat >= 0 && seat <= a.getTail().getSeat())
                        loop2 = false;
                }
                else if(seat >= 0 &&( seat <= r.getTail().getSeat() || seat <= a.getTail().getSeat()))//loop till seat is valid
                    loop2 = false;
            }
            o.seat[i + o.adult + o.senior] = seat;
            o.type[i + o.adult + o.senior] = 3;
        }

        loop2 = true;
        if(a.getTail() == null){
            System.out.println("No Seats Available");
            u.orders.remove(o);
        }
        else{
            for(int i = 0; i < o.row.length; i++){
                if(!checkA(o.row[i],o.seat[i],1,a)){
                    loop2 = false;
                    break;
                }
            }
        }
        if(loop2){//check if seats asked for are available
            for(int i = 0; i < o.row.length; i++){
                reserve(o.row[i],o.seat[i],1,r,a);//reserve asked for seats
                System.out.println("\nSeats Reserved!\n");
            }
        }
        else{//if seats are full, find best open ones
            int[] best = findBest(o.seat.length, r, a);//get best seat option
            int bR = best[0];
            int bS = best[1];
            if(bR == 9999999 || bS == 9999999){//if no options are available
                System.out.println("Seats cannot be found");
                u.orders.remove(o);
            }
            else{
                loop2 = true;
                while(loop2){
                    System.out.println("Would you like row: " + (bR + 1) + " seat: " + (bS + 1) + " ?  Y/N?");//loop till valid answer
                    char y = input.next().charAt(0);
                    if(y == 'Y' || y == 'y'){//if they want the seat, reserve
                        for(int i = 0; i < o.seat.length; i++){
                            o.row[i] = bR;
                            o.seat[i] = bS + i;
                        }
                        reserve(bR,bS,o.seat.length,r,a);
                        System.out.println("Seats reserved");
                        loop2 = false;
                    }
                    else if(y == 'N' || y == 'n'){//if they dont want the seat, dont reserve
                        System.out.println("Seats not reserved");
                        u.orders.remove(o);
                        loop2 = false;
                    }
                }
            }
        }
    }
    public static void reserve(int r, int s, int n, LinkedList R, LinkedList A){//reserve seats using addnode and deletenode
        for(int i = 0; i < n; i++){
            R.addNode(r, s + i);
            A.deleteNode(r, s + i);
        }
    }
    
    public static boolean checkA(int r, int s, int n, LinkedList A){//check if seats are available to reserve
        DoubleLinkNode cur = A.getHead();
        
        while(cur != null){//loop through available linked list
            if(cur.getRow() == r && cur.getSeat() == s){//if at the matching seat and row
                for(int i = 0;i < n;i++){
                    if(cur == null || cur.getRow() != r || cur.getSeat() != s + i)//check the correct number of seats after to verify availability
                        return false;
                    cur = cur.getNext();
                }
                return true;
            }
            cur = cur.getNext();
        }
        return false;
    }
    public static void displayReceipt(account u){
        for(int i = 0;i < u.orders.size();i++){
            order o = ((order)u.orders.get(i));
            System.out.print("Order: " + (i+1) + "     " + "Auditorium: " + o.auditorium);
            System.out.println("Child tickets: " + o.child + "\t$" +(o.child*5.25));
            System.out.println("\nAdult tickets: " + o.adult + "\t$" +(o.adult*10));
            System.out.println("Senior tickets: " + o.senior + "\t$" +(o.senior*7.5));
            System.out.print("Seats: ");
            for(int k = 0; k < o.row.length; k++)
                System.out.print( "Row: " + (o.row[k]+1) + " , Seat: " + (o.seat[k]+1));
            System.out.println("\nTotal number of orders: " + u.orders.size());
            System.out.println("Total amount spent: $" + (o.child*5.25 + o.adult*10 + o.senior*7.5));
        }
    }
    public static void view(account u){
        for(int i = 0;i < u.orders.size();i++){
            order o = ((order)u.orders.get(i));
            System.out.print("order: " + (i+1) + "     " + "Auditorium: " + o.auditorium);
            System.out.println("\nAdult tickets: " + o.adult);
            System.out.println("Senior tickets: " + o.senior);
            System.out.println("Senior tickets: " + o.child);
            System.out.print("Seats: ");
            for(int j = 0; j < o.row.length; j++)
                System.out.print( "(" + (o.row[j]+1) + "," + (o.seat[j]+1) + ")");
            System.out.println();
        }
    }   
    public static void update(account u, Scanner input, LinkedList[] auditoriums){
        if(!u.orders.isEmpty()){    
            System.out.println("Which order do you want?: ");
            int o = input.nextInt() - 1;
            LinkedList a = auditoriums[((order)u.orders.get(o)).auditorium - 1];
            LinkedList r = auditoriums[((order)u.orders.get(o)).auditorium + 2];
            boolean loop = true;
            while(loop){
                System.out.println("1) Add tickets" + "\n2) Delete tickets" + "\n3) Cancel order" + "\n4) Exit");
                switch(input.nextInt()){
                    case 1:
                        reserve1(a, r, ((order)u.orders.get(o)), input, u);
                        break;
                    case 2:
                        if(((order)u.orders.get(o)).row.length != 0){
                            for(int i = 0; i < ((order)u.orders.get(o)).row.length; i++)
                                System.out.print( (i+1) + ": " + ((order)u.orders.get(o)).type[i] + "(" + (((order)u.orders.get(o)).row[i]+1) + "," + (((order)u.orders.get(o)).seat[i]+1) + ")  ");
                            System.out.println("Which of these seats do you want to remove? Insert the number assigned to the seat.");
                            int removeSeat = input.nextInt() - 1;
                            if(removeSeat < ((order)u.orders.get(o)).row.length){
                                reserve(((order)u.orders.get(o)).row[removeSeat], ((order)u.orders.get(o)).seat[removeSeat], 1, a, r);

                                switch(((order)u.orders.get(o)).type[removeSeat]){
                                    case 1:
                                        ((order)u.orders.get(o)).adult--;
                                        break;
                                    case 2:
                                        ((order)u.orders.get(o)).senior--;
                                        break;
                                    case 3:
                                        ((order)u.orders.get(o)).child--;
                                        break;
                                    default:
                                        break;
                                }

                                if(((order)u.orders.get(o)).row.length == 1)
                                    u.orders.remove(o);
                                else{
                                    int[] n = new int[((order)u.orders.get(o)).seat.length - 1];
                                    for(int i = 0; i < ((order)u.orders.get(o)).seat.length; i++)
                                        if(i != removeSeat)
                                            n[i] = ((order)u.orders.get(o)).seat[i];
                                    ((order)u.orders.get(o)).seat = n;

                                    n = new int[((order)u.orders.get(o)).row.length - 1];
                                    for(int i = 0; i < ((order)u.orders.get(o)).row.length; i++)
                                        if(i != removeSeat)
                                            n[i] = ((order)u.orders.get(o)).row[i];
                                    ((order)u.orders.get(o)).row = n;

                                    n = new int[((order)u.orders.get(o)).type.length - 1];
                                    for(int i = 0; i < ((order)u.orders.get(o)).type.length; i++)
                                        if(i != removeSeat)
                                            n[i] = ((order)u.orders.get(o)).type[i];
                                    ((order)u.orders.get(o)).type = n;
                                }
                            }
                        }
                        else
                            System.out.println("No Seats to remove! Add some seats!");
                        break;
                    case 3:
                        for(int i = 0; i < ((order)u.orders.get(i)).row.length;i++)
                            reserve(((order)u.orders.get(o)).row[i], ((order)u.orders.get(o)).seat[i], 1, a, r);
                        u.orders.remove(o);
                        break;
                    case 4:
                        loop = false;
                        break;
                    default:
                        System.out.println("Invalid input");         
                }
            }   
        }
        else
            System.out.println("\nYou have no orders!\n");
    }
}

class order {
    int[] row;
    int[] seat;
    int[] type;
    
    int auditorium = 0;
    int child = 0;
    int adult = 0;
    int senior = 0;
}