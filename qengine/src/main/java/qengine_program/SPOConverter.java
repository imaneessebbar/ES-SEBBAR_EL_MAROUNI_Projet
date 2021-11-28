/**
 * @author ES-SEBBAR Imane
 * @author EL MAROUNI Majda
 * */

package qengine_program;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class SPOConverter {

	public SPOConverter() {
		
	}
	/** Convertir SPO en OSP **/
	public  HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> toOSP(HashMap<Integer,HashMap<Integer,ArrayList<Integer>>> SPO ) {
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
	public  HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> toPSO(HashMap<Integer,HashMap<Integer,ArrayList<Integer>>> SPO ) {
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
							SO.put(spos.getKey(),O); //
						}
						PSO.put(sposp.getKey(), SO);
					}
				}

			}
		}
		return PSO;
	}
	
	/** Convertir SPO en SOP **/
	public  HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> toSOP(HashMap<Integer,HashMap<Integer,ArrayList<Integer>>> SPO ) {
		
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
	public  HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> toPOS(HashMap<Integer,HashMap<Integer,ArrayList<Integer>>> SPO ) {
		
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
	public   HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> toOPS(HashMap<Integer,HashMap<Integer,ArrayList<Integer>>> SPO ) {
		
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
	
}
