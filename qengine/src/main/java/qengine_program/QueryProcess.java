/**
 * @author ES-SEBBAR Imane
 * @author EL MAROUNI Majda
 * */


package qengine_program;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.query.algebra.StatementPattern;

public class QueryProcess {
	private Value subject ;
	private Value object ;
	private Value predicate ;

	private int object_indx ;
	private int predicate_indx ;
	
	static HashMap<Integer, Object> dictio = Dictionnaire.getDictio();
	static HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> spo = IndexationSPO.getSPO();
	static HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> pos ;
	String resultString= "";

	public void  getResult(List<StatementPattern> patterns) {
		int i = 0;
		SPOConverter converter = new SPOConverter();
		HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> resultPOS = new HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>();


		while( i  < patterns.size() ) {
			
			HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> newresultPOS = new HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>();
			HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> newresultSPO = new HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>();
			HashMap<Integer, ArrayList<Integer>> resOS = new HashMap<Integer, ArrayList<Integer>>();
			ArrayList<Integer> resS = new ArrayList<Integer>();

			
			System.out.println("N° "+(i+1)+" pattern : " + patterns.get(i));

			System.out.println("\tObject of the "+i+" pattern : " + patterns.get(i).getObjectVar().getValue());
			System.out.println("\tSubject of the "+i+" pattern : " + patterns.get(i).getSubjectVar().getValue());
			System.out.println("\tPredicate of the "+i+" pattern : " + patterns.get(i).getPredicateVar().getValue());
			
			
			subject = patterns.get(i).getSubjectVar().getValue();
			object = patterns.get(i).getObjectVar().getValue();
			predicate = patterns.get(i).getPredicateVar().getValue();
			
			
			
			if(subject == null && predicate != null && object != null) {
				predicate_indx = getIndexFromDictionnary(predicate);
				object_indx = getIndexFromDictionnary(object);

				if(checkInDictionary(predicate_indx,object_indx)) {				//check if all the elements exist in the database (in the dictionary)
					
					if(i == 0 && resultPOS.isEmpty()) {	 						// get the POS indexation
						if(pos == null) 
							pos = IndexationSPO.toPOS();
						
						resS = pos.get(predicate_indx).get(object_indx);		//resS : les subjects resultant de la requete
						resOS.put(object_indx, resS);							//resOS : < object , [subjects]>
						newresultSPO = IndexationSPO.newData(spo,resS);	// newresultSPO : newData retourne un <subject <predicat , < [object]>> pour chaque subject de resS 
						if(newresultSPO==null) break;                     // tout les donn
						newresultPOS = converter.toPOS(newresultSPO);			// convertion en POS pour continuer la requete avec des données reduites
						resultPOS = newresultPOS;

					}else {
						if(i != 0 && !resultPOS.isEmpty()&& resultPOS.get(predicate_indx)!=null) {				// utiliser resultPOS pour les patterns pas premiers (utiliser les resultats des patterns precedents)

							resS = resultPOS.get(predicate_indx).get(object_indx);
							resOS.put(object_indx, resS);
							newresultSPO = IndexationSPO.newData(converter.toOSP(resultPOS),resS);
							newresultPOS = converter.toPOS(newresultSPO);
							resultPOS = newresultPOS;
							
						}else { 
							if(i != 0 && resultPOS == null) { 
								System.out.println(" [No Solution Found]"); // pas de solution pour le pattern
								break;
							}
						}
					}
					
				}else {
					// missing data
					System.out.println(" [Missing Data] ");
				}
			}
			
			if(resS != null && !resS.isEmpty() ) {
				ArrayList<String> solution =  new ArrayList<String>();
				for(int r : resS) {
					solution.add(dictio.get(r).toString());
				}
				System.out.println("[Pattern "+(i+1)+" Result : "+solution+"] \n \n"); // resultat de chaque pattern

				if(i == patterns.size()-1) {
					System.out.println("[Final Query Result : "+solution+"] \n \n");// resultat final de la requette
					resultString = ""+solution;

				}
			}else {
					System.out.println(" [No Solution Found for this Request] \n \n");
					resultString = "";
					if(i!= patterns.size() - 1) {
						break; //si avant la fin de tous les patterns on a plus de données a verifier alors pas de solution : pas besoin de contunier on passe a la requete d'apres
					}
				}

			newresultPOS = null;
			
			i++;
		}
		//return resultPOS;
	}
	
	public int getIndexFromDictionnary(Value value) {
		if(value == null) {
			return -1;
		}else {
			if(dictio.containsValue(value) ){
				return Dictionnaire.getKey(value);
			}else {
				//pas de resultat 
				return 0;
			}
		}
		
	}
	
	public boolean checkInDictionary(int valueIndex1, int valueIndex2) {
		if(valueIndex1 != 0 && valueIndex2 != 0) {
			return true;
		}else {
			return false;
		}
	}
	public String toString() {
		return resultString;	
	}
}
