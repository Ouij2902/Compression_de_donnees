package CompressionHuffman;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class Main {

	public static void main(String[] args) throws IOException {
    	                
        Scanner sc = new Scanner(System.in);
        //lire les valeurs dq'entrée dans la console //
        boolean menu = true; 

        while (menu){

			System.out.println("Veuillez selectionner une option :");
			System.out.println("1 - Coder un fichier avec Huffman statique");
			System.out.println("2 - Coder un fichier avec Huffman semi-adaptatif");
			System.out.println("3 - Decoder un fichier .txt");
			System.out.println("4 - Quitter le menu");
			System.out.print("Votre choix : ");
			
			int choix = sc.nextInt();
			sc.nextLine();		
			
			switch (choix) {
			case 1 :			
				System.out.println("Vous avez choisit de coder un fichier .txt en Huffman statique.");
				System.out.print("Nom du fichier .txt dans le répertoire du projet : ");
				String nameFichierTexte1 = sc.nextLine();
				System.out.print("Nom du fichier .txt contenant les fréquences pour le codage : ");
				String nameFichierFrequence1 = sc.nextLine();
				ReadFileToCompress file = new ReadFileToCompress(nameFichierTexte1);
		    	Huffman huffman1 = new Huffman(file.readFile());
		    	FileWriter fileCodage1 = new FileWriter("FichierCodageParCaractere.txt", false);
		    	FileWriter fileCode1 = new FileWriter("FichierCodageBinaire.txt",false);
		    	FileWriter fileCompression1 = new FileWriter("FichierCompresse.txt",false);
		    	if (huffman1.getTextToEncode().length() != 0) {
		    		huffman1.createNodes(nameFichierFrequence1);
		            huffman1.buildTree(huffman1.getNodes());
		            huffman1.encodingText(huffman1.getNodes().peek(), "");
		            huffman1.getCodes().forEach((k,v) -> {
						try {
							fileCodage1.write(k + " " + v + "\n");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
		        }
		        else {
		        	System.out.println("Error : please enter string");
		        }
		    	System.out.println("...conception du code binaire associé au texte en cours...");
		    	String code1 = huffman1.encodeText();
		        fileCode1.write(code1);
		        fileCode1.close();
		        fileCodage1.close();
		        int i1 = 0;
		        String myChar1 = "";
		        while(i1 < code1.length()) {
		        	myChar1 = myChar1 + code1.charAt(i1);
		        	if(myChar1.length()==8) {
		        		int seqInt = Integer.valueOf(myChar1,2);
		        		char e = (char)seqInt;
		        		fileCompression1.append(e);
		        		myChar1 = "";
		        	}
		        	i1 = i1 + 1;	        	
		        }
		        while(myChar1.length()!=8) {
		        	myChar1 = myChar1 + "0";
		        }
		        int seqInt = Integer.valueOf(myChar1,2);
	    		char e = (char)seqInt;
	    		fileCompression1.append(e);
		        fileCompression1.close();
		        System.out.println("Conception et compression terminées.");
				break;
			case 2 : 
				System.out.println("Vous avez choisit de coder un fichier .txt en Huffman semi-adaptatif.");
				System.out.print("Nom du fichier .txt dans le répertoire du projet : ");
				String nameFichierTexte2 = sc.nextLine();
				ReadFileToCompress file2 = new ReadFileToCompress(nameFichierTexte2);
		    	Huffman huffman2 = new Huffman(file2.readFile());
				FileWriter fileCodage2 = new FileWriter("FichierCodageParCaractere.txt", false);  
		    	FileWriter fileFrequency2 = new FileWriter("Frequence_SemiAdaptatif.txt", false);
		    	FileWriter fileCode2 = new FileWriter("FichierCodageBinaire.txt",false);
		    	PrintWriter fileCompression2 = new PrintWriter("FichierCompresse.txt","ISO-8859-1");
		        if (huffman2.getTextToEncode().length() != 0) {
		        	fileFrequency2.write(huffman2.frequency(huffman2.getNodes()));
		            fileFrequency2.close();
		            huffman2.createNodes("Frequence_SemiAdaptatif.txt");
		            huffman2.buildTree(huffman2.getNodes());
		            huffman2.encodingText(huffman2.getNodes().peek(), "");
		            huffman2.getCodes().forEach((k,v) -> {
						try {
							fileCodage2.write(k + " " + v + "\n");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					});
		        }
		        else {
		        	System.out.println("Error : please enter string");
		        }
		        System.out.println("...conception du code binaire associé au texte en cours...");
		        String code2 = huffman2.encodeText();    //encodeText est la méthode la plus longue dans l'execution
		        fileCode2.write(code2);
		        fileCode2.close();
		        fileCodage2.close();
		        int i = 0;
		        String myChar = "";
		        while(i < code2.length()) {
		        	myChar = myChar + code2.charAt(i);
		        	if(myChar.length()==8) {
		        		int seqInt1 = Integer.valueOf(myChar,2);
		        		char char1 = (char)seqInt1;
		        		fileCompression2.append(char1);
		        		myChar = "";
		        	}
		        	i = i + 1;	        	
		        }
		        while(myChar.length()!=8) {
		        	myChar = myChar + "0";
		        }
		        int seqInt1 = Integer.valueOf(myChar,2);
	    		char char1 = (char)seqInt1;
	    		fileCompression2.append(char1);
		        fileCompression2.close();
		        System.out.println("Conception et compression terminées.");	
				break;
				
				
			case 3:
				
				System.out.println("Vous avez choisit de décoder un fichier .txt en Huffman.");
				System.out.print("Nom du fichier .txt dans le répertoire du projet : ");
				String nameFichierTexte3 = sc.nextLine();
				FileWriter fileDecodage = new FileWriter("DecodageBinaire.txt", false);
		        FileWriter fileDecoder = new FileWriter("FichierDecode.txt");
		        ReadFileToCompress decompress = new ReadFileToCompress(nameFichierTexte3);
		        ReadFileToCompress valeurCodage = new ReadFileToCompress("FichierCodageParCaractere.txt");
		        Hashtable<Character, String> dicoCodage = valeurCodage.getDicoCodage();
		        String texteBinaire = decompress.readCharacterFile();
		        fileDecodage.write(texteBinaire);        
		        fileDecodage.close();
		        System.out.println("...décompression et décodage en cours...");
		        String mot = "";
		        String result = "";
		        for(int j = 0; j < texteBinaire.length();j++) {
		        	mot = mot + texteBinaire.charAt(j);
		    		Set<Character> keys = dicoCodage.keySet();        
		            Iterator<Character> itr = keys.iterator();     
		            Character key;
		            //affichage des pairs clé-valeur
		            while (itr.hasNext()) {
		            	
		            
		               // obtenir la clé
		                key = (Character) itr.next();
		               /*public V get(Object key): retourne la valeur correspondante
		                * à la clé, sinon null si le map ne contient aucune valeur
		                * correspondante
		                */
		                //System.out.println(dicoCodage.get(key)==mot);
		                if (dicoCodage.get(key).equals(mot)) {
		            	    result = result + key;
		            	    mot = "";
		                }
		            }        		
		        	
		        }
		        fileDecoder.write(result);
		        fileDecoder.close();  
		        System.out.println("Décodage et décompression terminés.");
		        break;
			
			case 4:
				
				
				if (menu = true) {
					sc.close();
				}
				System.out.println("End of the program.");
				menu = false;
				break;
				
			default :
				System.out.println("veuillez choisir entre 1 et 4");
				break;
			}
        }		
    }
}
