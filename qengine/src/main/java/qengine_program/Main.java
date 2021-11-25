/**
 * @author ES-SEBBAR Imane
 * @author EL MAROUNI Majda
 * */

package qengine_program;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.query.algebra.Projection;
import org.eclipse.rdf4j.query.algebra.StatementPattern;
import org.eclipse.rdf4j.query.algebra.helpers.AbstractQueryModelVisitor;
import org.eclipse.rdf4j.query.algebra.helpers.StatementPatternCollector;
import org.eclipse.rdf4j.query.parser.ParsedQuery;
import org.eclipse.rdf4j.query.parser.sparql.SPARQLParser;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParser;
import org.eclipse.rdf4j.rio.Rio;

/**
 * Programme simple lisant un fichier de requête et un fichier de données.
 * 
 * <p>
 * Les entrées sont données ici de manière statique,
 * à vous de programmer les entrées par passage d'arguments en ligne de commande comme demandé dans l'énoncé.
 * </p>
 * 
 * <p>
 * Le présent programme se contente de vous montrer la voie pour lire les triples et requêtes
 * depuis les fichiers ; ce sera à vous d'adapter/réécrire le code pour finalement utiliser les requêtes et interroger les données.
 * On ne s'attend pas forcémment à ce que vous gardiez la même structure de code, vous pouvez tout réécrire.
 * </p>
 * 
 * @author Olivier Rodriguez <olivier.rodriguez1@umontpellier.fr>
 */
final class Main {
	static final String baseURI = null;
	
	static HashMap<Integer,Object> dictio_ ;
	
	static SPO spo = SPO.getSPOInstance();
	static Dictionnaire dictio = Dictionnaire.getDictioInstance();
	
	//static ArrayList<ArrayList<Integer>> SPO_; 
	/*static ArrayList<ArrayList<Integer>> OSP; 
	static ArrayList<ArrayList<Integer>> PSO; 
	static ArrayList<ArrayList<Integer>> SOP; 
	static ArrayList<ArrayList<Integer>> POS; 
	static ArrayList<ArrayList<Integer>> OPS; */

	/**
	 * Votre répertoire de travail où vont se trouver les fichiers à lire
	 */
	static final String workingDir = "data/";

	/**
	 * Fichier contenant les requêtes sparql
	 */
	static final String queryFile = workingDir + "sample_query.queryset";

	/**
	 * Fichier contenant des données rdf
	 */
	static final String dataFile = workingDir + "sample_data.nt";

	// ========================================================================

	/**
	 * Méthode utilisée ici lors du parsing de requête sparql pour agir sur l'objet obtenu.
	 */
	public static void processAQuery(ParsedQuery query) {
		List<StatementPattern> patterns = StatementPatternCollector.process(query.getTupleExpr());

		/*System.out.println("first pattern : " + patterns.get(0));

		System.out.println("object of the first pattern : " + patterns.get(0).getObjectVar().getValue());

		System.out.println("variables to project : ");*/
		int i = 0;
		Value subject ;
		Value object ;
		Value predicate ;

		int subject_indx ;
		int object_indx ;
		int predicate_indx ;
		
		//dictio 

		while( i  < patterns.size() ) {
			System.out.println("first pattern : " + patterns.get(i));

			subject = patterns.get(i).getSubjectVar().getValue();
			object = patterns.get(i).getObjectVar().getValue();
			predicate = patterns.get(i).getPredicateVar().getValue();
			
			// valeur dans le dictionnaire
			
			/*if(subject != null && dictio.containsValue(subject) ){
				//subject_indx = dictio.
			}else {
				dictio.put(dictio.size()+1,subject);
			}*/
			
			
			System.out.println("object of the "+i+" pattern : " + patterns.get(i).getObjectVar().getValue());
			System.out.println("subject of the "+i+" pattern : " + patterns.get(i).getPredicateVar().getValue());
			System.out.println("predicate of the "+i+" pattern : " + patterns.get(i).getSubjectVar().getValue());

			System.out.println("variables to project : ");
			
			
			i++;
		}

		// Utilisation d'une classe anonyme
		query.getTupleExpr().visit(new AbstractQueryModelVisitor<RuntimeException>() {

			public void meet(Projection projection) {
				System.out.println(projection.getProjectionElemList().getElements());
			}
		});
	}
	


	/**
	 * Entrée du programme
	 */
	public static void main(String[] args) throws Exception {
		parseData();
		parseQueries();
	}

	// ========================================================================

	/**
	 * Traite chaque requête lue dans {@link #queryFile} avec {@link #processAQuery(ParsedQuery)}.
	 */
	private static void parseQueries() throws FileNotFoundException, IOException {
		/**
		 * Try-with-resources
		 * 
		 * @see <a href="https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html">Try-with-resources</a>
		 */
		/*
		 * On utilise un stream pour lire les lignes une par une, sans avoir à toutes les stocker
		 * entièrement dans une collection.
		 */
		try (Stream<String> lineStream = Files.lines(Paths.get(queryFile))) {
			SPARQLParser sparqlParser = new SPARQLParser();
			Iterator<String> lineIterator = lineStream.iterator();
			StringBuilder queryString = new StringBuilder();

			while (lineIterator.hasNext())
			/*
			 * On stocke plusieurs lignes jusqu'à ce que l'une d'entre elles se termine par un '}'
			 * On considère alors que c'est la fin d'une requête
			 */
			{
				String line = lineIterator.next();
				queryString.append(line);

				if (line.trim().endsWith("}")) {
					ParsedQuery query = sparqlParser.parseQuery(queryString.toString(), baseURI);

					processAQuery(query); // Traitement de la requête, à adapter/réécrire pour votre programme

					queryString.setLength(0); // Reset le buffer de la requête en chaine vide
				}
			}
		}
	}

	/**
	 * Traite chaque triple lu dans {@link #dataFile} avec {@link MainRDFHandler}.
	 */
	private static void parseData() throws FileNotFoundException, IOException {

		try (Reader dataReader = new FileReader(dataFile)) {
			// On va parser des données au format ntriples
			RDFParser rdfParser = Rio.createParser(RDFFormat.NTRIPLES);

			// On utilise notre implémentation de handler
			
			MainRDFHandler handler = new MainRDFHandler();
			rdfParser.setRDFHandler(handler);
			
			// Parsing et traitement de chaque triple par le handler
			rdfParser.parse(dataReader, baseURI);
			
			/********** dictionnaire & Indexation **********/
			/** La création et l'ajout des données dans le dictionnaire se font dans la classe MainRDFHandler*/
			
			//dictio_ = dictio.getDictio(); //dictionnaire
			//SPO_ = spo.getSPO();  // Indexation 
			
			/********** Affichage **********/
			
			dictio.print();
			spo.print();
			
			
			/********** Les autres **********/
			
			/*OSP = spo.toOSP();
			PSO = spo.toPSO();
			SOP = spo.toSOP();
			POS = spo.toPOS();
			OPS = spo.toOPS();
			
			System.out.println("OSP : \n" + OSP + " \n ");
			System.out.println("PSO : \n" + PSO + " \n ");
			System.out.println("SOP : \n" + SOP + " \n ");
			System.out.println("POS : \n" + POS + " \n ");
			System.out.println("OPS : \n" + OPS );*/
		}
	}
}
