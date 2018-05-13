package org.eltech.ddm.clustering.cdbase.kmeans;

import org.eltech.ddm.clustering.AggregationFunction;
import org.eltech.ddm.clustering.ClusteringMiningModel;
import org.eltech.ddm.clustering.cdbase.CDBaseModelTest;
import org.eltech.ddm.handlers.actors.ActorsClusterExecutionEnvironment;
import org.eltech.ddm.handlers.actors.ActorsClusterExecutionSettings;
import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm;
import org.eltech.ddm.miningcore.miningtask.EMiningBuildTask;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;


public class KMeansVectorsCycleActorslAlgorithmClusteringTest extends CDBaseModelTest {

    private final int NUMBER_HANDLERS = 8;

    protected KMeansAlgorithmSettings miningAlgorithmSettings;
    protected MiningAlgorithm algorithm;

    @Before
    public void setUp() throws Exception {
        // Create mining algorithm settings
        miningAlgorithmSettings = new KMeansAlgorithmSettings();
        miningAlgorithmSettings.setAlgorithm("KMeans");
        miningAlgorithmSettings.setMaxNumberOfIterations(50);
        miningAlgorithmSettings.setEps(0.5);
    }


    /**
     * Cluster nodes must be launched before the test starts.
     * Example ServiceMain.exe 127.0.0.1 2551 8 4 127.0.0.1:2551
     */
    @Test
    public void test4ActorsIris() {

        try {
            setInputData4Iris();
            setMiningSettings4Iris(miningAlgorithmSettings);

            // Assign settings:
            miningSettings.setMaxNumberOfClusters(3);
            miningSettings.setAggregationFunction(AggregationFunction.euclidian);
            miningSettings.verify();

            ActorsClusterExecutionSettings executionSettings = new ActorsClusterExecutionSettings();
            executionSettings.setTotalActors(NUMBER_HANDLERS);
            executionSettings.setActorsPerNode(NUMBER_HANDLERS);
            executionSettings.setHost("127.0.0.1");
            executionSettings.setPort(2551);
            executionSettings.setSeedNode("127.0.0.1:2551");

            ActorsClusterExecutionEnvironment environment = new ActorsClusterExecutionEnvironment(executionSettings);

            MiningAlgorithm algorithm = new KMeanVectorsCycleParallelAlgorithm(miningSettings);

            EMiningBuildTask buildTask = new EMiningBuildTask();
            buildTask.setInputStream(inputData);
            buildTask.setMiningAlgorithm(algorithm);
            buildTask.setMiningSettings(miningSettings);
            buildTask.setExecutionEnvironment(environment);
            model = (ClusteringMiningModel) buildTask.execute();

            verifyModel4Iris(model);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        }


}
