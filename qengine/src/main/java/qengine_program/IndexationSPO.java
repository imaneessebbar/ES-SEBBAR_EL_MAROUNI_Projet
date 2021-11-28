/**
 * @author ES-SEBBAR Imane
 * @author EL MAROUNI Majda
 * */

package qengine_program;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class IndexationSPO {
	private static HashMap<Integer,HashMap<Integer,ArrayList<Integer>>> SPO = new HashMap<Integer,HashMap<Integer,ArrayList<Integer>>>();
	private static IndexationSPO SPO_ = null; 
	
	private IndexationSPO() {
	}
	
	public static IndexationSPO getSPOInstance() {
		if(SPO_ == null) {
			SPO_ = new IndexationSPO();
		}
		return SPO_;
	}
	
	/** retourne le SPO construit **/
	public static HashMap<Integer,HashMap<Integer,ArrayList<Integer>>> getSPO() {
		return SPO;
	}

	public static void addToSPO(int subjectKey, int predicateKey, int objectKey) {
		if(!SPO.containsKey(subjectKey)) {    // new subject
			ArrayList<Integer> objectArray = new ArrayList<Integer>();
			objectArray.add(objectKey);
			HashMap<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
			map.put(predicateKey, objectArray);
			SPO.put(subjectKey, map);
		}else { // subject aleady existing in the spo
			if(!SPO.get(subjectKey).containsKey(predicateKey)) { // predicate doesnt exist
				ArrayList<Integer> sA = new ArrayList<Integer>();
				sA.add(objectKey);
				SPO.get(subjectKey).put(predicateKey, sA);
			}else {
				ArrayList<Integer> objectArray = new ArrayList<Integer>();
				objectArray = SPO.get(subjectKey).get(predicateKey);
				objectArray.add(objectKey);
				SPO.get(subjectKey).put(predicateKey , objectArray);
			}
		}
	}
	/** filtre les donnees par subject : retourne les donnes en SPO si contient un subject de subjects **/
	
	public static HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> newData(HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>olddata,ArrayList<Integer> subjects) {
			HashMap<Integer, ArrayList<Integer>> newpo = new HashMap<Integer, ArrayList<Integer>>();
			HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> newdata = new HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>();
			Set<Entry<Integer, ArrayList<Integer>>> set;
			if(subjects!=null) {
				for(int s : subjects) {
					newpo = new HashMap<Integer, ArrayList<Integer>>();
					set = filterBySubject(olddata,s);
					if(set!=null) {
						for(Entry<Integer, ArrayList<Integer>> po : set ) {
							newpo.put(po.getKey(),po.getValue());
							newdata.put(s, newpo);     
						}
					}
				}
			}
			return newdata;  	// SPO  : < subject , <predicate , [objects]>> 


	}
	
	public static Set<Entry<Integer, ArrayList<Integer>>> filterBySubject(HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>olddata, Integer subject) {
		if(olddata.get(subject)!=null)
			return olddata.get(subject).entrySet();
		else
			return null;
	} 
	
	
	 /** Retourne la cle correspondante à une valeur donnee **/
	public static Integer getKey( Object value)
    {
        for (Entry<Integer, HashMap<Integer, ArrayList<Integer>>> entry: SPO.entrySet())
        {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

	/** Convertir SPO en OSP **/
	public static HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> toOSP() {
		HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> OSP = new HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>();
		HashMap<Integer, ArrayList<Integer>> SP = new HashMap<Integer, ArrayList<Integer>>();
		ArrayList<Integer> P = new ArrayList<Integer>();

	
		for (Entry<Integer, HashMap<Integer, ArrayList<Integer>>> spos : SPO.entrySet()) {
			
			for(Entry<Integer, ArrayList<Integer>> sposp : spos.getValue().entrySet()) {

				for(int spospo : sposp.getValue()) {
					
					if(!OSP.containsKey(spospo)) {
						P = new ArrayList<Integer>();
						SP = new HashMap<Integer, ArrayList<Integer>>();
						P = new ArrayList<Integer>();
						P.add(sposp.getKey());
						SP.put(spos.getKey(), P);
						OSP.put(spospo, SP);
						
					}else {
						if(!OSP.get(spospo).containsKey(spos.getKey())){
							P = new ArrayList<Integer>();
							SP = new HashMap<Integer, ArrayList<Integer>>();
							P.add(sposp.getKey());
							SP.put(spos.getKey(),P);
						}else {
							P = new ArrayList<Integer>();
							SP = new HashMap<Integer, ArrayList<Integer>>();
							SP = OSP.get(spospo);
							P = SP.get(spos.getKey());
							P.add(sposp.getKey());
							SP.put(spos.getKey(),P);
						}
						OSP.put(spospo, SP);
					}
				}

			}
		}
		return OSP;
	}
	
	/** Convertir SPO en PSO **/
	public static HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> toPSO() {
		HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> PSO = new HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>();
		HashMap<Integer, ArrayList<Integer>> SO = new HashMap<Integer, ArrayList<Integer>>();
		ArrayList<Integer> O = new ArrayList<Integer>();
	
		for (Entry<Integer, HashMap<Integer, ArrayList<Integer>>> spos : SPO.entrySet()) {
			
			for(Entry<Integer, ArrayList<Integer>> sposp : spos.getValue().entrySet()) {

				for(int spospo : sposp.getValue()) {
					
					if(!PSO.containsKey(sposp.getKey())) {
						O = new ArrayList<Integer>();
						SO = new HashMap<Integer, ArrayList<Integer>>();
						O = new ArrayList<Integer>();
						O.add(spospo);
						SO.put(spos.getKey(), O);
						PSO.put(sposp.getKey(), SO);
						
					}else {
						if(!PSO.get(sposp.getKey()).containsKey(spospo)){
							O = new ArrayList<Integer>();
							SO = new HashMap<Integer, ArrayList<Integer>>();
							SO = PSO.get(sposp.getKey());
							O.add(spospo);
							SO.put(spos.getKey(),O);
						}else {
							O = new ArrayList<Integer>();
							SO = new HashMap<Integer, ArrayList<Integer>>();
							SO = PSO.get(sposp.getKey());
							O = SO.get(spos.getKey());
							O.add(spospo);//
							SO.put(spos.getKey(),O); 
						}
						PSO.put(sposp.getKey(), SO);
					}
				}

			}
		}
		return PSO;
	}
	
	/** Convertir SPO en SOP **/
	public static HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> toSOP() {
		
		HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> SOP = new HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>();
		HashMap<Integer, ArrayList<Integer>> OP = new HashMap<Integer, ArrayList<Integer>>();
		ArrayList<Integer> P = new ArrayList<Integer>();
	
		for (Entry<Integer, HashMap<Integer, ArrayList<Integer>>> spos : SPO.entrySet()) {
			
			for(Entry<Integer, ArrayList<Integer>> sposp : spos.getValue().entrySet()) {

				for(int spospo : sposp.getValue()) {
					
					if(!SOP.containsKey(spos.getKey())) {
						P = new ArrayList<Integer>();
						OP = new HashMap<Integer, ArrayList<Integer>>();
						P.add(sposp.getKey());
						OP.put(spospo, P);
						SOP.put(spos.getKey(), OP);
						
					}else {
						if(!SOP.get(spos.getKey()).containsKey(sposp.getKey())){
							P = new ArrayList<Integer>();
							OP = new HashMap<Integer, ArrayList<Integer>>();
							OP = SOP.get(spos.getKey());
							P.add(sposp.getKey());
							OP.put(spospo,P);
						}else {
							P = new ArrayList<Integer>();
							OP = new HashMap<Integer, ArrayList<Integer>>();
							OP = SOP.get(spos.getKey());
							P = OP.get(spospo);
							P.add(sposp.getKey());//
							OP.put(spospo,P); //
						}
						SOP.put(spos.getKey(), OP);
					}
				}

			}
		}
		return SOP;
	}
	
	/** Convertir SPO en POS **/
	public static  HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> toPOS() {
		
		HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> POS = new HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>();
		HashMap<Integer, ArrayList<Integer>> OS = new HashMap<Integer, ArrayList<Integer>>();
		ArrayList<Integer> S = new ArrayList<Integer>();
	
		for (Entry<Integer, HashMap<Integer, ArrayList<Integer>>> spos : SPO.entrySet()) {
			
			for(Entry<Integer, ArrayList<Integer>> sposp : spos.getValue().entrySet()) {

				for(int spospo : sposp.getValue()) {
					
					if(!POS.containsKey(sposp.getKey())) {
						S = new ArrayList<Integer>();
						OS = new HashMap<Integer, ArrayList<Integer>>();
						S = new ArrayList<Integer>();
						S.add(spos.getKey());
						OS.put(spospo, S);
						POS.put(sposp.getKey(), OS);
						
					}else {
						if(!POS.get(sposp.getKey()).containsKey(spospo)){
							S = new ArrayList<Integer>();
							OS = new HashMap<Integer, ArrayList<Integer>>();
							OS = POS.get(sposp.getKey());
							S.add(spos.getKey());
							OS.put(spospo,S);
						}else {
							S = new ArrayList<Integer>();
							OS = new HashMap<Integer, ArrayList<Integer>>();
							OS = POS.get(sposp.getKey());
							S = OS.get(spospo);
							S.add(spos.getKey());//
							OS.put(spospo,S); //
						}
						POS.put(sposp.getKey(), OS);
					}
				}

			}
		}
		return POS;
	}
	
	/** Convertir SPO en OPS **/
	public static  HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> toOPS() {
		
		HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> OPS = new HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>();
		HashMap<Integer, ArrayList<Integer>> PS = new HashMap<Integer, ArrayList<Integer>>();
		ArrayList<Integer> S = new ArrayList<Integer>();
	
		for (Entry<Integer, HashMap<Integer, ArrayList<Integer>>> spos : SPO.entrySet()) {
			
			for(Entry<Integer, ArrayList<Integer>> sposp : spos.getValue().entrySet()) {

				for(int spospo : sposp.getValue()) {
					
					if(!OPS.containsKey(spospo)) {
						S = new ArrayList<Integer>();
						PS = new HashMap<Integer, ArrayList<Integer>>();
						S = new ArrayList<Integer>();
						S.add(spos.getKey());
						PS.put(sposp.getKey(), S);
						OPS.put(spospo, PS);
						
					}else {
						if(!OPS.get(spospo).containsKey(sposp.getKey())){
							S = new ArrayList<Integer>();
							PS = new HashMap<Integer, ArrayList<Integer>>();
							PS = OPS.get(spospo);
							S.add(spos.getKey());
							PS.put(sposp.getKey(),S);
						}else {
							S = new ArrayList<Integer>();
							PS = new HashMap<Integer, ArrayList<Integer>>();
							PS = OPS.get(spospo);
							S = PS.get(sposp.getKey());
							S.add(spos.getKey());//
							PS.put(sposp.getKey(),S); //
						}
						OPS.put(spospo, PS);
					}
				}

			}
		}
		return OPS;
	}
	
	/** Affichage **/
	public void print(HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>SPO) {
		System.out.println(" [");
		for (Entry<Integer, HashMap<Integer, ArrayList<Integer>>> spoo : SPO.entrySet()) {
			System.out.print(""+ spoo.getKey()+"  < "); // subject
			for(Entry<Integer, ArrayList<Integer>> x : spoo.getValue().entrySet()) {
				System.out.print("( "+ x.getKey()+"  < ");
				for(int y : x.getValue()) {
					System.out.print(y+", ");
				}
				System.out.print(" > ), ");

			}
			System.out.println("  >  ");
		}
		System.out.println("]");

	}
	public void print() {
		System.out.println("SPO : \n [");
		for (Entry<Integer, HashMap<Integer, ArrayList<Integer>>> spoo : SPO.entrySet()) {
			System.out.print(""+ spoo.getKey()+"  < "); 
			for(Entry<Integer, ArrayList<Integer>> x : spoo.getValue().entrySet()) {
				System.out.print("( "+ x.getKey()+"  < ");
				for(int y : x.getValue()) {
					System.out.print(y+" , ");
				}
				System.out.print(" > ), ");

			}
			System.out.println("  >  ");
		}
		System.out.println("]");

		
	}
	
}



