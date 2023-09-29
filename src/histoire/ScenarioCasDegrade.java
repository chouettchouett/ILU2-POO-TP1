package histoire;

import personnages.Chef;
import personnages.Druide;
import personnages.Gaulois;
import villagegaulois.Etal;
import villagegaulois.Village;

public class ScenarioCasDegrade {
	public static void main(String[] args) {
		Etal etal = new Etal();
		Gaulois asterix = new Gaulois("Asterix",10);
//		etal.libererEtal();
		try {
			System.out.println(etal.acheterProduit(2, asterix));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		catch (IllegalStateException e) {
			e.printStackTrace();
		}
		System.out.println("Fin du test");
		}
}
