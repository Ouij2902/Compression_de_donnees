package CompressionHuffman;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;


public class ReadFileToCompress {
	
	private String fileName;
	
	public ReadFileToCompress(String fileName) {
		this.fileName = fileName;
	}
	public String readFile() throws IOException {
		//méthode qui lit le contenu d'un fichier et le retourne dans une chaine de caractere
		String text = "";
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String line;
		while ((line = br.readLine()) != null) {
		   text = text + line + "\n";
		}
		br.close();
		return text;
	}
	public String readCharacterFile() throws IOException {
		//méthode qui lit un fichier de caractere encodé par paquets de 8 bits et retourne une chaine de caractere
		//qui correspond à la conversion en code binaire
		String text = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(this.fileName),"ISO-8859-1"));
		String line;
		while ((line = br.readLine()) != null) {
			for(int j=0; j<line.length();j++) {				
				int test2 = (int) line.charAt(j);
				String string2 = Integer.toBinaryString(test2);
				//on convertit le caractere en int 
				while(string2.length() != 8) {
					string2 = "0" + string2;
				//on ajoute des 0 au début de cet entier pour obtenir une valeur sur 8 bits
				}
				text = text + string2;
			}
			
		}
		br.close();
		return text;
		
	}
	
	public Hashtable<Character, String> getDicoCodage() {
		//méthode qui retourne un dictionnaire avec les valeurs binaires des caractères, à partir d'un fichier où sont 
		//contenu ces valeurs pour chaque caractere
		Hashtable<Character, String> dico = new Hashtable<Character, String>();
    	try{
    		InputStream flux=new FileInputStream(this.fileName); 
    		InputStreamReader lecture=new InputStreamReader(flux);
    		BufferedReader buff=new BufferedReader(lecture);
    		String ligne;
    		while ((ligne=buff.readLine())!=null){
    			String value = "";
    			int tailleLigne = ligne.length();
    			if (tailleLigne == 0) {
    				ligne = buff.readLine();
    				tailleLigne = ligne.length();
    				for (int i = 1; i < ligne.length();i++) {
    					value = value + ligne.charAt(i);
    				}
    				dico.put((char)(10), value);
    				//si c'est une ligne vide c'est que c'est pour le retour à la ligne, on va donc chercher la 
    				//valeur à la ligne d'après
    			}else {
    			for(int i = 2; i < tailleLigne ; i++) {
    				value = value + ligne.charAt(i);
    			}
    			dico.put(ligne.charAt(0), value);
    			//on ajoute les valeurs dans le dictionnaire
    			}
    		}
    		buff.close(); 
    		}		
    		catch (Exception e){
    			System.out.println(e.toString());
    		}
    	return dico;
	}
}
