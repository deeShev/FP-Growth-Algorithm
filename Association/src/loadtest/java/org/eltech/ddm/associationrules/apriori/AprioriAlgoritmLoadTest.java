package org.eltech.ddm.associationrules.apriori;

import org.eltech.ddm.miningcore.MiningException;
import org.junit.After;
import org.junit.Test;

//@Ignore
public class AprioriAlgoritmLoadTest extends AprioriLoadlTest {
	
	@Test
	public void testT_200() throws MiningException {
		System.out.println("----- AprioriAlgoritm T_200 -------");
		
		setSettings("T_200");

	}
	
	@Test
	public void testI_2000() throws MiningException {
		System.out.println("----- AprioriAlgoritm T_2000 -------");
		
		setSettings("T_2000");
 
	}
	
	@Test
	public void testT_20000() throws MiningException {
		System.out.println("----- AprioriAlgoritm T_20000 -------");
		
		setSettings("T_20000");
	}

	@Test
	public void testI_5() throws MiningException {
		System.out.println("----- AprioriAlgoritm I_5-------");
		
		setSettings("I_5");
	}
	
	@Test
	public void testI_10() throws MiningException {
		System.out.println("----- AprioriAlgoritm I_10-------");
		
		setSettings("I_10");
	}
	
	@Test
	public void testI_15() throws MiningException {
		System.out.println("----- AprioriAlgoritm I_15-------");
		
		setSettings("I_15");
	}
	
	@Test
	public void testI_10_20() throws MiningException {
		System.out.println("----- AprioriAlgoritm I_10_20-------");
		
		setSettings("I_10_20");
        miningSettings.setMinConfidence(0.7);
        miningSettings.setMinSupport(0.7);
	}
	
	@Test
	public void testI_10_30() throws MiningException {
		System.out.println("----- AprioriAlgoritm I_10_30-------");
		
		setSettings("I_10_30");
        miningSettings.setMinConfidence(0.7);
        miningSettings.setMinSupport(0.7);
	}
	
	@Test
	public void testI_10_50() throws MiningException {
		System.out.println("----- AprioriAlgoritm I_10_50-------");
		
		setSettings("I_10_50");
        miningSettings.setMinConfidence(0.7);
        miningSettings.setMinSupport(0.7);
	}
	
	@After
	public void tearDown() {
		try {
			AprioriAlgorithm algorithm = new AprioriAlgorithm(miningSettings);
			System.out.println("Start algorithm");
			miningModel = (AprioriMiningModel) algorithm.buildModel(inputData);

			System.out.println("Finish algorithm. Calculation time: " + algorithm.getTimeSpentToBuildModel());
			
    	} catch (MiningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		verifyModel();
	}

}
