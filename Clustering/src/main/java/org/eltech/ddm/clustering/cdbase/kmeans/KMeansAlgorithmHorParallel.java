

package org.eltech.ddm.clustering.cdbase.kmeans;

import org.eltech.ddm.clustering.ClusteringMiningModel;
import org.eltech.ddm.clustering.cdbase.kmeans.steps.*;
import org.eltech.ddm.clustering.cdbase.steps.InitClusterByRandom;
import org.eltech.ddm.clustering.cdbase.steps.InitClusterByVectors;
import org.eltech.ddm.clustering.cdbase.steps.SetCentroidOfCluster;
import org.eltech.ddm.miningcore.MiningException;

import org.eltech.ddm.miningcore.algorithms.*;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;


/**
 * Implementation of a simple K-Means clustering algorithm.
 */
public class KMeansAlgorithmHorParallel extends KMeansAlgorithm
{
  public KMeansAlgorithmHorParallel(
			EMiningFunctionSettings miningSettings) throws MiningException {
		super(miningSettings);
		// TODO Auto-generated constructor stub
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
                    new MiningParallel(miningSettings, MemoryType.shared,
                        new MiningLoopVectors(miningSettings,
                                new MiningLoopElement(miningSettings, ClusteringMiningModel.INDEX_CLUSTERS,
                                                new MiningLoopElement(miningSettings, EMiningModel.INDEX_ATTRIBUTE_SET,
                                                    new AccumulateDistanceVectorCluster(miningSettings)),
                                        new DistanceVectorCluster(miningSettings),
                                        new ClosestClusterForVector(miningSettings)),
                                new MiningParallel(miningSettings, MemoryType.shared,
                                       new MiningLoopElement(miningSettings, EMiningModel.INDEX_ATTRIBUTE_SET,
                                                new CalcNewClusterCenter(miningSettings)),
                                 new IncVectorCountInCluster(miningSettings)))),
                    new MiningLoopElement(miningSettings, ClusteringMiningModel.INDEX_CLUSTERS,
                            new MiningLoopElement(miningSettings, EMiningModel.INDEX_ATTRIBUTE_SET,
                                    new SetCentroidOfCluster(miningSettings))))
    );

    blocks.addListenerExecute(new BlockExecuteTimingListner());

}

}
