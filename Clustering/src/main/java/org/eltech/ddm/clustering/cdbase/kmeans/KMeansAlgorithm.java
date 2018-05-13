

package org.eltech.ddm.clustering.cdbase.kmeans;

import org.eltech.ddm.clustering.ClusteringMiningModel;
import org.eltech.ddm.clustering.cdbase.kmeans.steps.*;
import org.eltech.ddm.clustering.cdbase.steps.InitClusterByRandom;
import org.eltech.ddm.clustering.cdbase.steps.InitClusterByVectors;
import org.eltech.ddm.clustering.cdbase.steps.SetCentroidOfCluster;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.ReportType;
import org.eltech.ddm.miningcore.VerificationReport;
import org.eltech.ddm.miningcore.algorithms.*;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;


/**
 * Implementation of a simple K-Means clustering algorithm.
 */
public class KMeansAlgorithm extends MiningAlgorithm
{
  public KMeansAlgorithm(EMiningFunctionSettings miningSettings)
			throws MiningException {
		super(miningSettings);
		// TODO Auto-generated constructor stub
	}

  /**
   * Checks mining algorithm for completeness by calling verify method
   * of superclass. Adiitionally, it checks whether numberOfClusters and
   * maxNumberOfIterations are admissible.
   *
   * @throws IllegalArgumentException if some algorithm attributes are incorrect
   */

  public VerificationReport verify() {
	  //super.verify();
	  String report = "";

	  if(report.length() > 0)
		  return new VerificationReport(ReportType.error, report);
	  else
		  return null;
	  }

  /**
   * Initialization of kmeans algorithm's steps.
   *
   */

	public void initBlocks() throws MiningException {

		blocks = new MiningSequence(miningSettings,
			new MiningLoopElement(miningSettings, ClusteringMiningModel.INDEX_CLUSTERS,
					new MiningLoopElement(miningSettings, EMiningModel.INDEX_ATTRIBUTE_SET,
							//new InitClusterByRandom(miningSettings))),
							new InitClusterByVectors(miningSettings))),
			new WhileChangClustersLoop(miningSettings,
					new MiningLoopElement(miningSettings, ClusteringMiningModel.INDEX_CLUSTERS,
							new InitCluster(miningSettings)),
					new MiningLoopVectors(miningSettings,
							new MiningLoopElement(miningSettings, ClusteringMiningModel.INDEX_CLUSTERS,
									new MiningLoopElement(miningSettings, EMiningModel.INDEX_ATTRIBUTE_SET,
											new AccumulateDistanceVectorCluster(miningSettings)),
									new DistanceVectorCluster(miningSettings),
									new ClosestClusterForVector(miningSettings)),
							new MiningLoopElement(miningSettings, EMiningModel.INDEX_ATTRIBUTE_SET,
									new CalcNewClusterCenter(miningSettings)),
							new IncVectorCountInCluster(miningSettings)),
					new MiningLoopElement(miningSettings, ClusteringMiningModel.INDEX_CLUSTERS,
							new MiningLoopElement(miningSettings, EMiningModel.INDEX_ATTRIBUTE_SET,
									new SetCentroidOfCluster(miningSettings))))
	  	);

	  blocks.addListenerExecute(new BlockExecuteTimingListner());

    }

	@Override
	public EMiningModel createModel(MiningInputStream inputStream) throws MiningException {
		EMiningModel resultModel = new KMeansMiningModel(miningSettings);

		return resultModel;
	}


}
