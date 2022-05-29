package CompressionHuffman;

public class Node {
	
    private Node leftChild;
    private Node rightChild;
    private double value;
    private String character;

    public Node(double value, String character) {
    	
        this.value = value;
        this.character = character;
        this.leftChild = null;
        this.rightChild = null;
        //un noeud sans fils avec juste des valeurs 
    }

    public Node(Node left, Node right) {
        this.value = left.value + right.value;
        character = left.character + right.character;
        if (left.value < right.value) {
            this.rightChild = right;
            this.leftChild = left;
        } else {
            this.rightChild = left;
            this.leftChild = right;
        //on construit le noeud avec ses deux fils et leur valeur binaire
        }
    }
    
    public String toString() {
    	return this.character + " : " + Double.toString(this.value);
    }
    
    public Node getLeftChild() {
    	return this.leftChild;
    }
    
    public Node getRightChild() {
    	return this.rightChild;
    }
    
    public double getValue() {
    	return this.value;
    }
    
    public String getCharacter() {
    	return this.character;
    }
}