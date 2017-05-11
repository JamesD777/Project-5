//James Dunlap   Jcd160230
package LinkList;

import java.io.PrintWriter;

public class LinkedList {
    private DoubleLinkNode head;
    private DoubleLinkNode tail;
    
    public LinkedList(){head = null;tail = null;}
    public LinkedList(DoubleLinkNode n){head = n;tail = n;}
    
    public DoubleLinkNode getHead(){return head;}
    public DoubleLinkNode getTail(){return tail;}
    public void setHead(DoubleLinkNode head){this.head = head;}
    public void setTail(DoubleLinkNode tail){this.tail = tail;}
    
    public void addNode(int r, int s){
        //edge case for head
        if(head == null){
            head = new DoubleLinkNode(r,s,tail,null);
            tail = head;
            return;
        }
        //edge case for tail
        if(tail.getRow() < r || (tail.getRow() == r && tail.getSeat() < s)){
            tail.setNext(new DoubleLinkNode(r,s,null,tail));
            tail = tail.getNext();
            return;
        }
        DoubleLinkNode c = head;
        //looks for node that is after new node
        while(c.getNext() != null){
            if(c.getRow() >= r){
                if(c.getSeat() > s || c.getRow() > r){
                    //adds new node right before node that is bigger than it
                    DoubleLinkNode newN = new DoubleLinkNode(r,s,c,c.getPrev());
                    c.getPrev().setNext(newN);
                    c.setPrev(newN);
                    return;
                }
            }
            c = c.getNext();
        }
        //used when first making list from file
        tail.setNext(new DoubleLinkNode(r,s,null,tail));
        tail = tail.getNext();
    }
    
    public void deleteNode(int r, int s){
        DoubleLinkNode c = head;
        //Loops though list util it finds a node with matching row and seat
        while(c != null){
            if(c.getRow() == r){
                if(c.getSeat() == s){
                    //edge case for deleting head
                    if(c == head){
                        head = head.getNext();
                        return;
                    }
                    //edge case for deleteing tail
                    if(c == tail){
                        tail = tail.getPrev();
                        tail.setNext(null);
                        return;
                    }
                    //manges pointers to "go around" deleted node and thus delete it
                    c.getPrev().setNext(c.getNext());
                    c.getNext().setPrev(c.getPrev());
                    return;
                }
            }
            c = c.getNext();
        }
    }
    
    public void print(PrintWriter out, LinkedList a, LinkedList r)//recursive helper function
    {
        int s;
        if(a.getTail().getSeat() > r.getTail().getSeat())
            s = a.tail.getSeat()+1;
        else
            s = r.tail.getSeat()+1;
        print(out, a.getHead(), head, 0, 0, s);
    }
    public void print(PrintWriter out, DoubleLinkNode cura, DoubleLinkNode curr, int row, int count, int size)//recursive print
    {
        if(count == size && (curr!=null && cura != null)){//print new line if at the end of a row
                count -= size;
                row++;
                out.println();
            }
        if(cura != null && curr != null){//if both nodes aren't at the tails, add the next . or #
            if(cura.getSeat() == count && cura.getRow() == row){
                out.print("#");
                count++;
                print(out, cura.getNext(), curr, row, count, size);
            }
            else{
                out.print(".");
                count++;
                print(out, cura, curr.getNext(), row, count, size);
            }
        }
        else if(cura != null){//if row of available is at the end, add #'s
            if(cura.getSeat() == count && cura.getRow() == row){
                out.print("#");
                count++;
                print(out, cura.getNext(), curr, row, count, size);
            }
        }
        else if(curr != null){//if row of reserved at the end, add .'s
            if(curr.getSeat() == count && curr.getRow() == row){
                out.print(".");
                count++;
                print(out, cura, curr.getNext(), row, count, size);
            }
        }
    }
}
