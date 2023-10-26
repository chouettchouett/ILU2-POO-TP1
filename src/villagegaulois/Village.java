package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

// Etal complet à la création du village.

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum,int nbEtal) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtal);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les lÃ©gendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	// -------------- classinterne
	
	private static class Marche {
		private Etal[] etals;
		
		private Marche(int nbEtal) {
			etals = new Etal[nbEtal];
			for (int i = 0; i < etals.length; i++) {
				etals[i] = new Etal();
			}
		}
		
		//visibilité check.
		private void utiliserEtal(int indiceEtal, Gaulois vendeur,String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		//à améliorer check.
		private int trouverEtalLibre() {
			for(int i=0; i < etals.length; i++) {
				if(etals[i].isEtalOccupe() == false) {
					return i;
					}						
			}
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			int nbEtalProduit = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					nbEtalProduit++;

				}
			}
			Etal[] etalsProduits = new Etal[nbEtalProduit];
			if (nbEtalProduit != 0) {
				for (int i = 0, j = 0; i < etals.length; i++) {
					if (etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
						etalsProduits[j] = etals[i];
						j++;

					}
				}
			}
			return etalsProduits;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for(int i=0; i < etals.length; i++) {
				if(etals[i].getVendeur().equals(gaulois)) {
					return etals[i];  //"L'étal du vendeur" + gaulois + " est l'étal numéro : " + etals[i] + "." +"\n";
				}
			
			}
			return null;
		}
		
		private String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			
			int nbEtalVide = etals.length;
			for(int i=0; i < etals.length; i++) {
				if(etals[i].isEtalOccupe()) {
					chaine.append(etals[i].afficherEtal());
					nbEtalVide --;
				}
			}
			chaine.append("Il reste " + nbEtalVide + " étals non utilisés dans le marché. " + "\n") ;
			return chaine.toString();
		}
			
	}

	// ------------- fin classe interne
	
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		int indiceEtal = marche.trouverEtalLibre() ;
		StringBuilder chaine = new StringBuilder();
		chaine.append( vendeur.getNom() + "cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		if (indiceEtal == -1) {
			chaine.append("Il n'y a plus de place dans ce marche pour le vendeur " + vendeur.getNom()
					+ " il repars donc sans etal.\n");
		} else {
			marche.utiliserEtal(indiceEtal, vendeur, produit, nbProduit);
			chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " a l'etal n° " + indiceEtal + ".\n");
		}
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etalproduit = marche.trouverEtals(produit);
		chaine.append("Les vendeurs qui proposent des " + produit + " sont:\n");
		for (int i = 0; i < etalproduit.length; i++) {
			chaine.append("- " + etalproduit[i].getVendeur().getNom() + "\n");
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		Etal etals = rechercherEtal(vendeur); //si diff null
		StringBuilder chaine = new StringBuilder();
		chaine.append(etals.libererEtal());
		return chaine.toString();

	}
	
	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Le marché du village " + nom + " possède plusieurs étals : ");
		chaine.append(marche.afficherMarche());
		return chaine.toString();
	}
	
}