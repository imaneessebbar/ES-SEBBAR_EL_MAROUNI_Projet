/**
 * @author ES-SEBBAR Imane
 * @author EL MAROUNI Majda
 * */

package qengine_program;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.map.HashedMap;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.rio.helpers.AbstractRDFHandler;

/**
 * Le RDFHandler intervient lors du parsing de données et permet d'appliquer un traitement pour chaque élément lu par le parseur.
 * 
 * <p>
 * Ce qui servira surtout dans le programme est la méthode {@link #handleStatement(Statement)} qui va permettre de traiter chaque triple lu.
 * </p>
 * <p>
 * À adapter/réécrire selon vos traitements.
 * </p>
 */
public final class MainRDFHandler extends AbstractRDFHandler {
	
	static int  i = 1;
	private static Dictionnaire dictio = Dictionnaire.getDictioInstance();
	private static SPO spo = SPO.getSPOInstance();

	
	@Override
	public void handleStatement(Statement st) {

		/* Dictionnaire */
		Dictionnaire.addToDictio(st.getSubject(), st.getPredicate(), st.getObject());
			
		/* Indexation */  
			/*SPO*/
		SPO.addToSPO(Dictionnaire.getKey(st.getSubject()), Dictionnaire.getKey(st.getPredicate()), Dictionnaire.getKey(st.getObject()));			
		
		System.out.println("\n" + st.getSubject() + "\t " + st.getPredicate() + "\t " + st.getObject());
	}
	

	
}