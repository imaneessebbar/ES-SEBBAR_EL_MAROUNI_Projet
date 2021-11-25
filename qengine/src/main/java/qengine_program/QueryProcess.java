package qengine_program;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.query.algebra.StatementPattern;

public class QueryProcess {
	Value subject ;
	Value object ;
	Value predicate ;

	int subject_indx ;
	int object_indx ;
	int predicate_indx ;
	
	//Dictionnaire dictio_ = Dictionnaire.getDictioInstance();
	HashMap<Integer, Object> dictio = Dictionnaire.getDictio();
	ArrayList<ArrayList<Integer>> spo = SPO.getSPO();
	
	public void getQuery(List<StatementPattern> patterns) {
		int i = 0;
		
		
		//dictio 

		while( i  < patterns.size() ) {
			
			System.out.println("first pattern : " + patterns.get(i));

			subject = patterns.get(i).getSubjectVar().getValue();
			object = patterns.get(i).getObjectVar().getValue();
			predicate = patterns.get(i).getPredicateVar().getValue();
			
			// valeur dans le dictionnaire
			/*subject_indx = getIndex(subject);
			testdictio(subject_indx);*/
			predicate_indx = getIndex(predicate);
			testdictio(predicate_indx);
			object_indx = getIndex(object);
			testdictio(object_indx);
			//spo.findAll(new ArrayList().);
			///////////
			System.out.println("object of the "+i+" pattern : " + patterns.get(i).getObjectVar().getValue());
			System.out.println("subject of the "+i+" pattern : " + patterns.get(i).getPredicateVar().getValue());
			System.out.println("predicate of the "+i+" pattern : " + patterns.get(i).getSubjectVar().getValue());

			System.out.println("variables to project : ");
			
			
			i++;
		}
	}
	
	public int getIndex(Value value) {
		if(value == null) {
			return -1;
		}else {
			if(dictio.containsValue(value) ){
				return Dictionnaire.getKey(value);
			}else {
				//pas de resultat 
				return 0;
				//System.out.println("[ Pas de données correspondant à la requête ]");
			}
		}
		
	}
	public void testdictio(int valueIndex) {
		if(valueIndex == -1 ){
			//
		}else {
			if(valueIndex == 0 ) {
				//pas de resultat 
				System.out.println("[ Pas de données correspondant à la requête ]");
		
			}else{
				
				System.out.println();
			}
		}
	}
}
