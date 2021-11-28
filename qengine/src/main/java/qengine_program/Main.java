/**
 * @author ES-SEBBAR Imane
 * @author EL MAROUNI Majda
 * */

package qengine_program;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.eclipse.rdf4j.query.algebra.StatementPattern;
import org.eclipse.rdf4j.query.algebra.helpers.StatementPatternCollector;
import org.eclipse.rdf4j.query.parser.ParsedQuery;
import org.eclipse.rdf4j.query.parser.sparql.SPARQLParser;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParser;
import org.eclipse.rdf4j.rio.Rio;

/**
 * Programme simple lisant un fichier de requ�te et un fichier de donn�es.
 * 
 * <p>
 * Les entr�es sont donn�es ici de mani�re statique,
 * � vous de programmer les entr�es par passage d'arguments en ligne de commande comme demand� dans l'�nonc�.
 * </p>
 * 
 * <p>
 * Le pr�sent programme se contente de vous montrer la voie pour lire les triples et requ�tes
 * depuis les fichiers ; ce sera � vous d'adapter/r��crire le code pour finalement utiliser les requ�tes et interroger les donn�es.
 * On ne s'attend pas forc�mment � ce que vous gardiez la m�me structure de code, vous pouvez tout r��crire.
 * </p>
 * 
 * @author Olivier Rodriguez <olivier.rodriguez1@umontpellier.fr>
 */
final class Main {
	static final String baseURI = null;
	
	static int j; // compteur requetes juste pour l'ecriture

	static IndexationSPO spo = IndexationSPO.getSPOInstance();

	static Dictionnaire dictio = Dictionnaire.getDictioInstance();
	
	//static HashMap<Integer,Object> dictio_ ;
	//static ArrayList<ArrayList<Integer>> SPO_; 
	
	 /*static HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> OSP; 
	 static HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> PSO; 
	 static HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>POS; 
	 static HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> OPS;
	 static HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> SOP;*/


	/**
	 * Votre r�pertoire de travail o� vont se trouver les fichiers � lire
	 */
	static final String workingDir = "data/";

	/**
	 * Fichier contenant les requ�tes sparql
	 */
	static  String queryFile = workingDir + "STAR_ALL_workload.queryset";

	/**
	 * Fichier contenant des donn�es rdf
	 */
	static  String dataFile = workingDir + "100K.nt";

	// ========================================================================

	/**
	 * M�thode utilis�e ici lors du parsing de requ�te sparql pour agir sur l'objet obtenu.
	 */
	public static void processAQuery(ParsedQuery query) {
		List<StatementPattern> patterns = StatementPatternCollector.process(query.getTupleExpr());

		QueryProcess qp = new QueryProcess();  
		 qp.getResult(patterns);  // l� ou se passe le processus
		 writes_into_file(qp.toString());
	}
	

	/**
	 * Entr�e du programme
	 */
	public static void main(String[] args) throws Exception {
		
		j = 1; // initialisation du compteur � 1
		
		/*final Options options = configParameters();
        final CommandLineParser parser = new DefaultParser();
        final CommandLine line = parser.parse(options, args);

        boolean helpMode = line.hasOption("help"); // args.length == 0
        if (helpMode) {
            final HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("RDFEngine", options, true);
            System.exit(0);
        }
        if(options.getOption("queries").getValue()!=null) {
        	        queryFile = options.getOption("queries").getValue();
        }if( options.getOption("data").getValue()!=null) {
            dataFile = options.getOption("data").getValue();
        }*/

		parseData();
		parseQueries();
	}

	// ========================================================================

	/**
	 * Traite chaque requ�te lue dans {@link #queryFile} avec {@link #processAQuery(ParsedQuery)}.
	 */
	private static void parseQueries() throws FileNotFoundException, IOException {
		/**
		 * Try-with-resources
		 * 
		 * @see <a href="https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html">Try-with-resources</a>
		 */
		/*
		 * On utilise un stream pour lire les lignes une par une, sans avoir � toutes les stocker
		 * enti�rement dans une collection.
		 */
		try (Stream<String> lineStream = Files.lines(Paths.get(queryFile))) {
			SPARQLParser sparqlParser = new SPARQLParser();
			Iterator<String> lineIterator = lineStream.iterator();
			StringBuilder queryString = new StringBuilder();

			while (lineIterator.hasNext())
			/*
			 * On stocke plusieurs lignes jusqu'� ce que l'une d'entre elles se termine par un '}'
			 * On consid�re alors que c'est la fin d'une requ�te
			 */
			{
				String line = lineIterator.next();
				queryString.append(line);

				if (line.trim().endsWith("}")) {
					ParsedQuery query = sparqlParser.parseQuery(queryString.toString(), baseURI);

					processAQuery(query); // Traitement de la requ�te, � adapter/r��crire pour votre programme
					
					j++; // incrementation du compteur des requetes
					
					queryString.setLength(0); // Reset le buffer de la requ�te en chaine vide
				}
			}
		}
	}

	/**
	 * Traite chaque triple lu dans {@link #dataFile} avec {@link MainRDFHandler}.
	 */
	private static void parseData() throws FileNotFoundException, IOException {

		try (Reader dataReader = new FileReader(dataFile)) {
			// On va parser des donn�es au format ntriples
			RDFParser rdfParser = Rio.createParser(RDFFormat.NTRIPLES);

			// On utilise notre impl�mentation de handler
			
			MainRDFHandler handler = new MainRDFHandler();
			rdfParser.setRDFHandler(handler);
			
			// Parsing et traitement de chaque triple par le handler
			rdfParser.parse(dataReader, baseURI);
			
			/********** dictionnaire & Indexation **********/
			/** La cr�ation et l'ajout des donn�es dans le dictionnaire se font dans la classe MainRDFHandler*/
			
			//dictio_ = dictio.getDictio(); //dictionnaire
			//SPO_ = spo.getSPO();  // Indexation 
			
			/********** Affichage **********/
			
			dictio.print();
			spo.print();

			
			/********** Les autres **********/
			/*SOP = SPO_bis.toSOP();
			OSP = SPO_bis.toOSP();
			PSO = SPO_bis.toPSO();
			POS = SPO_bis.toPOS();
			OPS = SPO_bis.toOPS();
			
			System.out.println("SOP : \n" );spo.print(SOP);
			System.out.println("OSP : \n" );spo.print(OSP);
			System.out.println("PSO : \n" );spo.print(PSO);
			System.out.println("POS : \n" );spo.print(POS);
			System.out.println("OPS : \n" );spo.print(OPS);*/
					

		}
	}
	
	public static void writes_into_file(String result){
        try {
            BufferedWriter fWriter = new BufferedWriter(new FileWriter("outputs\\results.txt",true));
            BufferedWriter bw = new BufferedWriter(fWriter);
            bw.write("Result query  "+ j+" : ");
            bw.newLine();
            bw.write(result);// ici tu mets ce que tu veux ecrire dans ton fichier
            bw.newLine();
            bw.close();
        }catch (IOException e) {
            System.out.print(e.getMessage());
        }
	}
	private static Options configParameters() {

        final Option helpFileOption = Option.builder("h").longOpt("help").desc("affiche le menu d'aide").build();

        final Option queriesOption = Option.builder("queries").longOpt("queries").hasArg(true)
                .argName("/chemin/vers/dossier/requetes").desc("Le chemin vers les queries").required(false).build();

        final Option dataOption = Option.builder("data").longOpt("data").hasArg(true)
                .argName("/chemin/vers/fichier/donnees").desc("Le chemin vers les donnees").required(false).build();

        final Option outputOption = Option.builder("output").longOpt("output").hasArg(true)
                .argName("/chemin/vers/dossier/sortie").desc("Le chemin vers le dossier de sortie").required(false)
                .build();

        final Options options = new Options();
        options.addOption(queriesOption);
        options.addOption(dataOption);
        options.addOption(outputOption);
        options.addOption(helpFileOption);

        return options;
    }
	
	
}
