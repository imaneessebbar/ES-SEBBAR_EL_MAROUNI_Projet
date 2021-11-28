/**
 * @author ES-SEBBAR Imane
 * @author EL MAROUNI Majda
 * */

package qengine_program;

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
	@Override
	public void handleStatement(Statement st) {

		/* Dictionnaire */
		Dictionnaire.addToDictio(st.getSubject(), st.getPredicate(), st.getObject());
			
		/* Indexation */  
			/*SPO*/
		/** Nous avons changé la structure de SPO par rapport au premier rendu */
		IndexationSPO.addToSPO(Dictionnaire.getKey(st.getSubject()), Dictionnaire.getKey(st.getPredicate()), Dictionnaire.getKey(st.getObject()));		

		//System.out.println("\n" + st.getSubject() + "\t " + st.getPredicate() + "\t " + st.getObject());
	}
	

	
}