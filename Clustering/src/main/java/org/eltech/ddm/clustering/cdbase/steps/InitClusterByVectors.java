package org.eltech.ddm.clustering.cdbase.steps;

import org.eltech.ddm.clustering.ClusteringMiningModel;
import org.eltech.ddm.clustering.cdbase.Coordinate;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.eltech.ddm.miningcore.miningmodel.LogicalAttributeElement;

public class InitClusterByVectors extends MiningBlock {

	public InitClusterByVectors(EMiningFunctionSettings settings)
			throws MiningException {
		super(settings);
	}

	@Override
	protected EMiningModel execute(MiningInputStream data, EMiningModel model) throws MiningException {
		int iAttr = model.getCurrentAttributeIndex();
		int iCluster = ((ClusteringMiningModel)model).getCurrentClusterIndex();

		Coordinate c = ((ClusteringMiningModel)model).getClusterCenterCoordinate(iCluster, iAttr);
		int nClusters = ((ClusteringMiningModel)model).getClusterSet().size();

		int iVector = data.getVectorsNumber() / nClusters * iCluster;

		MiningVector vector =  data.getVector(iVector);
		c.setValue(vector.getValue(iAttr));

		return model;
	}
}
