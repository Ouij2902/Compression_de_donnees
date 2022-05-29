package CompressionHuffman;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;

public class Huffman {

	private PriorityQueue<Node> nodes = new PriorityQueue<>((o1, o2) -> (o1.getValue() < o2.getValue()) ? -1 : 1);
	//liste de noeud class� par ordre de fr�quence d'apparition des caract�res
    private TreeMap<Character, String> codes = new TreeMap<>();
    //arbre binaire o� tout les �l�ments repr�sentants les caract�res sont des feuilles
    private String textToEncode = "";
    //chaine de caract�re � encoder
    private String encodedText = "";
    //chaine de caract�re en binaire
    private Hashtable<Character, Integer> dico = new Hashtable<Character, Integer>();
    //dictionnaire qui contient le nombre d'apparition de chaque caract�re
    
    
    public Huffman(String textToEncode){
    	this.textToEncode = textToEncode;
    }
    
    public String getTextToEncode() {
    	return this.textToEncode;
    }
    
    public PriorityQueue<Node> getNodes(){
    	return this.nodes;
    }
    
    public TreeMap<Character, String> getCodes(){
    	return this.codes;
    }
    public Hashtable<Character, Integer> getDico(){
    	return this.dico;
    }
            
    public String encodeText() {
    	//retourne l'encodage binaire du texte int�grale textToEncode
        this.encodedText = "";
        for (int i = 0; i < textToEncode.length(); i++) {
            encodedText = encodedText + codes.get(textToEncode.charAt(i));
            //pour toutes les valeurs � coder, on va prendre sa valeur binaire dans l'arbre binare
        }
        return encodedText;
    }

    public void buildTree(PriorityQueue<Node> nodes) {
    	//va prendre tout les noeuds de la priorityqueue et les supprimant en partant des feuilles
    	//pour n'avoir plus qu'un noeud racine avec des fils successifs
        while (nodes.size() > 1) //tant qu'il reste un noeud (noeud racine)
            nodes.add(new Node(nodes.poll(), nodes.poll()));
        	//on ajoute un nouveau noeud avec les fils gauche et droit
    }

    public String frequency(PriorityQueue<Node> nodes){
    	//retourne une chaine de caractere correspondant au nombre d'apparition de chaque caractere
    	Hashtable<Character, Integer> dico2 = new Hashtable<Character, Integer>();
    	//dictionnaire dans lequel on stocke le nombre d'apparition de chaque caract�re
    	String res = "";
        for (int i = 0; i < this.textToEncode.length(); i++) {
        	if (dico2.containsKey(textToEncode.charAt(i))){
        		int value = dico2.get(textToEncode.charAt(i));
        		dico2.put(textToEncode.charAt(i), value + 1);
        		//si le caractere est contenu dans le dictionnaire on incr�mente sa valeur d'apparition 
        	}
        	else {
        		dico2.put(textToEncode.charAt(i),1);
        		//sinon on ajoute la cl� dans le dictionnaire et on initialise sa valeur d'apparition � 1  
        	}
        }
        Set<Character> keys = dico2.keySet();        
        Iterator<Character> itr = keys.iterator();     
        Character key;
        //cl� - valeur dans le dictionnaire
        while (itr.hasNext()) { 
           // on parcours les �l�ments du dictionnaire
           key = (Character) itr.next();
           //on prend la cl� suivante 
           res = res + key + " " + dico2.get(key) + "\n";
           //on ajoute � notre chaine r�sultat le caractere ainsi que sa valeur d'apparition
        }
        return res;
    }
    
    public void createNodes(String file) {
    	//on cr�e les noeuds pour chaque caract�re que l'on ajoute dans la liste de noeuds
    	this.dico.clear();
    	//on supprime les anciennes valeurs du dictionnaire
    	try{
    		InputStream flux=new FileInputStream(file); 
    		InputStreamReader lecture=new InputStreamReader(flux);
    		BufferedReader buff=new BufferedReader(lecture);
    		String ligne;
    		while ((ligne=buff.readLine())!=null){
    			//on lit le fichier o� sont contenu le nombre d'apparition de chaque caractere
    			String value = "";
    			int tailleLigne = ligne.length();
    			if (tailleLigne == 0) {
    				ligne = buff.readLine();
    				tailleLigne = ligne.length();
    				for (int i = 1; i < ligne.length();i++) {
    					value = value + ligne.charAt(i);
    				//si c'est une ligne vide c'est que c'est pour le retour � la ligne, on va donc chercher la 
    				//valeur � la ligne d'apr�s
    				}
    				this.dico.put((char)(10), Integer.parseInt(value));
    				//on ajoute dans le dictionnaire ou la cl� est le caract�re et le valeur le nb d'apparition
    			}else {
    			for(int i = 2; i < tailleLigne ; i++) {
    				value = value + ligne.charAt(i);
    			}
    			this.dico.put(ligne.charAt(0), Integer.parseInt(value));
    			//on fait pareil pour tout les autres caracteres
    			}
    		}
    		buff.close(); 
    		}		
    		catch (Exception e){
    		System.out.println(e.toString());
    		}
    	Set<Character> keys = this.dico.keySet();        
        Iterator<Character> itr = keys.iterator();     
        Character key;
        //cl� - valeur dans le dictionnaire
        while (itr.hasNext()) { 
           // on parcours les �l�ments du dictionnaire
           key = (Character) itr.next();
           //on prend la cl� suivante 
           this.nodes.add(new Node(this.dico.get(key) / (textToEncode.length() * 1.0), key + ""));
           //on ajoute le noeud avec comme valeur nbApparition/nbCaractereTotal et le caractere
        }    
    }
    

    public void encodingText(Node node, String s) {
    	//fonction qui parcours tout les noeuds fils du noeud racine et va construire l'arbre binaire avec
    	//leur caractere et la valeur binaire associ�e
        if (node != null) {
            if (node.getRightChild() != null)
                encodingText(node.getRightChild(), s + "1");
            //on utlise la r�cursivit� pour parcourir les fils 
            //si c'est un fils droit on ajoute un 1

            if (node.getLeftChild() != null)
                encodingText(node.getLeftChild(), s + "0");
            //on utlise la r�cursivit� pour parcourir les fils 
            //si c'est un fils gauche on ajoute un 0

            if (node.getLeftChild() == null && node.getRightChild() == null)
                codes.put(node.getCharacter().charAt(0), s);
            //lorsqu'on arrive sur une feuille on ajoute le noeud dans l'arbre
        }
    }
}
