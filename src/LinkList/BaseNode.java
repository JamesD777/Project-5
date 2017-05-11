package LinkList;

public class BaseNode {//base node
    private int seat;
    private int row;
    
    public BaseNode(){row = -1;seat = -1;}
    public BaseNode(int r,int s){row = r;seat = s;}
    
    public int getSeat(){return seat;}
    public int getRow(){return row;}
    public void setRow(int row){this.row = row;}
    public void setSeat(int seat){this.seat = seat;}
}
