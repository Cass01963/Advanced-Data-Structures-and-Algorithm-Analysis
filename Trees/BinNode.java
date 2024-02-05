
public class BinNode
{
    private String data;
    private BinNode left;
    private BinNode right;

    private int height;

    private BinNode parent;
    private boolean color;
    public BinNode(){
        data = "";
        left = null;
        right = null;
    }
    public BinNode(String d){
        data = d;
        left = null;
        right = null;
    }
    public void setData(String d){
        this.data = d;
    }
    public String getData(){
        return this.data;
    }
    public void setLeft(BinNode l){
        this.left = l;
    }
    public BinNode getLeft(){
        return this.left;
    }
    public void setRight(BinNode r){
        this.right = r;
    }
    public BinNode getRight(){
        return this.right;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    public BinNode getParent() {
        return parent;
    }

    public void setParent(BinNode parent) {
        this.parent = parent;
    }

    // Getter and Setter for color
    public boolean getColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }
}
