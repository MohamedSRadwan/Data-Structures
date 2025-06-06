public class DNode {
    private Object data;
    private DNode next;
    private DNode prev;

    // Constructor
    public DNode(Object data){
        this.data = data;
        this.next = null;
    }

    public DNode getNext(){
        return this.next;
    }
    public void setNext(DNode next){
        this.next = next;
    }
    public DNode getPrev(){
        return this.prev;
    }
    public void setPrev(DNode prev){
        this.prev = prev;
    }
    public Object getData(){
        return this.data;
    }
    public void setData(Object data){
        this.data = data;
    }
    public boolean hasNext(){
        return this.next != null;
    }
}
