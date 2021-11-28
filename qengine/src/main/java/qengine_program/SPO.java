/**
 * @author ES-SEBBAR Imane
 * @author EL MAROUNI Majda
 * */

package qengine_program;

import java.util.ArrayList;

public class SPO {
	private static ArrayList<ArrayList<Integer>> SPO = new ArrayList<>();
	private static SPO SPO_ = null; 
	
	private SPO() {
	}
	
	public static SPO getSPOInstance() {
		if(SPO_ == null) {
			SPO_ = new SPO();
		}
		return SPO_;
	}
	
	/** retourne le SPO construit **/
	public static ArrayList<ArrayList<Integer>> getSPO() {
		return SPO;
	}
	
	/** prend en parametres la clé correspondante au subject, predicate et object d'un statement 
	 ** et les ajoute dans une ligne qui elle est ajoutée dans le tableau SPO **/
	public static void addToSPO(Integer subjectKey, Integer predicateKey, Integer objectKey) {
		ArrayList<Integer> line = new ArrayList<Integer>();
		line.add(subjectKey);
		line.add(predicateKey);
		line.add(objectKey);
		SPO.add(line);
	}
	
	/** Convertir SPO en OSP **/
	public static ArrayList<ArrayList<Integer>> toOSP() {
		ArrayList<ArrayList<Integer>> OSP = new ArrayList<>();
		ArrayList<Integer> newline = new ArrayList<>();
		for(ArrayList<Integer> line : SPO){
			newline.add(0, line.get(2)); 
			newline.add(1, line.get(0)); 
			newline.add(2, line.get(1)); 
			OSP.add(newline);
			newline = new ArrayList<>();
		}
		return OSP;
	}
	
	/** Convertir SPO en PSO **/
	public static ArrayList<ArrayList<Integer>> toPSO() {
		ArrayList<ArrayList<Integer>> PSO = new ArrayList<>();
		ArrayList<Integer> newline = new ArrayList<>();
		for(ArrayList<Integer> line : SPO){
			newline.add(0, line.get(1)); 
			newline.add(1, line.get(0)); 
			newline.add(2, line.get(2)); 
			PSO.add(newline);
			newline = new ArrayList<>();
		}
		return PSO;
	}
	
	/** Convertir SPO en SOP **/
	public static ArrayList<ArrayList<Integer>> toSOP() {
		ArrayList<ArrayList<Integer>> SOP = new ArrayList<>();
		ArrayList<Integer> newline = new ArrayList<>();
		for(ArrayList<Integer> line : SPO){
			newline.add(0, line.get(0)); 
			newline.add(1, line.get(2)); 
			newline.add(2, line.get(1)); 
			SOP.add(newline);
			newline = new ArrayList<>();
		}
		return SOP;
	}
	
	/** Convertir SPO en POS **/
	public static  ArrayList<ArrayList<Integer>> toPOS() {
		ArrayList<ArrayList<Integer>> POS = new ArrayList<>();
		ArrayList<Integer> newline = new ArrayList<>();
		for(ArrayList<Integer> line : SPO){
			newline.add(0, line.get(1)); 
			newline.add(1, line.get(2)); 
			newline.add(2, line.get(0)); 
			POS.add(newline);
			newline = new ArrayList<>();
		}
		return POS;
	}
	
	/** Convertir SPO en OPS **/
	public static ArrayList<ArrayList<Integer>> toOPS() {
		ArrayList<ArrayList<Integer>> OPS = new ArrayList<>();
		ArrayList<Integer> newline = new ArrayList<>();
		for(ArrayList<Integer> line : SPO){
			newline.add(0, line.get(2)); 
			newline.add(1, line.get(1)); 
			newline.add(2, line.get(0)); 
			OPS.add(newline);
			newline = new ArrayList<>();
		}
		return OPS;
	}
	
	/** Affichage **/
	public void print() {
		System.out.println("SPO : \n [");
		for(ArrayList<Integer> x : SPO) {
			System.out.println("\t"+ x);
		}
		System.out.println("]");

	}
}
