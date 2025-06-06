public class SNode {
    private Object data;
    private SNode next;

    public SNode(Object data) {
        this.data = data;
        this.next = null;
    }
    public SNode getNext(){
        return this.next;
    }
    public void setNext(SNode next){
        this.next = next;
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
