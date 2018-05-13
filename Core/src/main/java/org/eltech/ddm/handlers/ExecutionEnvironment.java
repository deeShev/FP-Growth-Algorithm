package org.eltech.ddm.handlers;

import java.util.Stack;


import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.*;
import org.eltech.ddm.miningcore.algorithms.MiningSequence;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

public abstract class ExecutionEnvironment {

    protected ExecutionSettings settings;

    protected ExecutionHandler mainHandler;

    //protected List<ExecutionHandler> handlers;

    protected ExecutionHandler templateHandler;

    protected int usedHandlers = 1; // 1 - handler is always used for main algorithm

    protected ExecutionHandlerFactory executionHandlerFactory;

    protected MiningAlgorithm algorithm;


    public ExecutionEnvironment(ExecutionSettings settings) throws ParallelExecutionException {
        this.settings = settings;
        initEnvironment();
    }

    public ExecutionEnvironment(ExecutionSettings settings, MiningAlgorithm algorithm) throws MiningException {
        this(settings);
        this.algorithm = algorithm;
//        deployAlgorithm(algorithm);
    }

    protected abstract void initEnvironment() throws ParallelExecutionException;

//    public void deployAlgorithm(MiningAlgorithm algorithm) throws MiningException {
//        ;
//
//        Stack<MiningBlock> blockStack = new Stack<MiningBlock>();
//
//        pushSeq2Stack(algorithm.getStepSequence(), blockStack);
//
//       while (!blockStack.empty()) {
//            MiningBlock block = blockStack.pop();
//
//            if (block instanceof MiningDecision) {
//                MiningDecision decisionBlock = (MiningDecision) block;
//                if (decisionBlock.getTrueBranch() != null) {
//                    pushSeq2Stack(decisionBlock.getTrueBranch(), blockStack);
//                }
//                if (decisionBlock.getFalseBranch() != null) {
//                    pushSeq2Stack(decisionBlock.getFalseBranch(), blockStack);
//                }
//                continue;
//            } else if (block instanceof MiningLoop) {
//                MiningLoop cycleStep = (MiningLoop) block;
//                if (cycleStep.getIteration() != null) {
//                    pushSeq2Stack(cycleStep.getIteration(), blockStack);
//                }
//                continue;
//            } else if (block instanceof MiningParallel) {
//                int n = usedHandlers;
//                usedHandlers = usedHandlers + ((MiningParallel) block).getHandlersNumber();
//                MiningParallel mp = (MiningParallel) block;
//                mp.setHandlerFactory(executionHandlerFactory);
//                if()
//                if (mp.get BIteration() != null) {
//                    blockStack.push(cycleStep.getIteration());
//                }
//
////            		ParallelByDataProxy pStep = new ParallelByDataProxy((DataParallelBlock)block, this);
////            		sequence.getSequence().attributes(i, pStep);
//                continue;
//            }
////            	else if (block instanceof TaskParallelBlock) {
////            		ParallelByTaskProxy pStep = new ParallelByTaskProxy((TaskParallelBlock)block, this);
////            		sequence.getSequence().attributes(i, pStep);
////            		continue;
////            	}
//        }
//        }
//    }

//    private void pushSeq2Stack(MiningSequence sequence, Stack<MiningBlock> stack) {
//        for (MiningBlock block: sequence.getSequence()) {
//            stack.push(block);
//        }
//    }

    public EMiningModel runAlgorithm(MiningInputStream inputStream, EMiningModel model) throws MiningException {
        //ExecutionHandler mainHandler = (ExecutionHandler)templateHandler.clone();
        //mainHandler.setStep(algorithm.getStepSequence());
//		if(mainHandler.getStep() == null)
//			throw new ParallelExecutionException("Did not deploy main sequance of step!");

        mainHandler.start(algorithm.getStepSequence(), model);

        EMiningModel resultModel = mainHandler.getModel();

        return resultModel;
    }


    public ExecutionSettings getSettings() {
        return settings;
    }

    public void setSettings(ExecutionSettings settings) {
        this.settings = settings;
    }

    public ExecutionHandlerFactory getExecutionHandlerFactory() {
        return executionHandlerFactory;
    }

//	public void addHandler(ExecutionHandler handler) {
//		this.handlers.add(handler);
//	}
//
//	public List<ExecutionHandler> getHandlers() {
//		return handlers;
//	}


}
