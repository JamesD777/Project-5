package LinkList;

public class DoubleLinkNode extends BaseNode{//extends base node and adds next and prev functionality
    private DoubleLinkNode next;
    private DoubleLinkNode prev;

    public DoubleLinkNode(){super(-1,-1);next = null;prev = null;}
    public DoubleLinkNode(int r, int s, DoubleLinkNode n,DoubleLinkNode p){super(r,s);next = n;prev = p;}

    public DoubleLinkNode getNext(){return next;}
    public DoubleLinkNode getPrev(){return prev;}
    public void setNext(DoubleLinkNode next){this.next = next;}
    public void setPrev(DoubleLinkNode prev){this.prev = prev;}
}
