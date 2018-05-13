package org.eltech.ddm.miningcore.algorithms;

import java.util.ArrayList;
import java.util.List;

import org.eltech.ddm.handlers.ExecutionHandlerFactory;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningErrorCode;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

public class MiningParallel extends MiningBlock {

//    protected final DataProcessingStrategy dataHandlingStrategy;
//
//    protected final MiningModelProcessingStrategy modelHandlingStrategy;

    protected final MiningBlock[] blocks;

    protected MemoryType memoryType;

    protected boolean isDataParallelize = false;

    protected final int handlersNumber;


    /**
     * Constructor for task-parallelization
     * @param settings
     * @param memory - type of memory(shared or distributed)
     * @param blocks - list of parallel mining blocks
     * @throws MiningException
     */
    public MiningParallel(EMiningFunctionSettings settings, MemoryType memory, MiningBlock... blocks) throws MiningException {
        super(settings);
        this.blocks = blocks;
        memoryType = memory;
        handlersNumber = blocks.length;
     }

    /**
     * Constructor for data-parallelization
     * @param settings
     * @param memory - type of memory(shared or distributed)
     * @param block - parallel mining block
     * @throws MiningException
     */
     public MiningParallel(EMiningFunctionSettings settings, MemoryType memory, MiningBlock block) throws MiningException {
        super(settings);
        memoryType = memory;

        handlersNumber = algorithmSettings.getNumberHandlers();
        this.blocks = new MiningBlock[handlersNumber];
        this.blocks[0] = block;
        isDataParallelize = true;
    }

    @Override
    protected EMiningModel execute(MiningInputStream dataSet, EMiningModel model) throws MiningException {

        if(isDataParallelize){
            if(!(blocks[0] instanceof MiningLoop))
                throw new MiningException(MiningErrorCode.PARALLEL_EXECUTION_ERROR);

            MiningLoop block = (MiningLoop)blocks[0];

            int startPos = 0;
            if(block instanceof MiningLoopElement) {
                int[] index = ((MiningLoopElement) block).getIndexSet();
                MiningModelElement elem = model.getElement(index);
                int countElement = elem.size() / handlersNumber;
                int mod = elem.size() % handlersNumber;
                blocks[0] = new MiningLoopElement(functionSettings, index, startPos, countElement + mod, block.getIteration());
                startPos += countElement + mod;
                for (int i = 1; i < handlersNumber; i++) {
                    blocks[i] = new MiningLoopElement(functionSettings, index, startPos, countElement, block.getIteration());
                    startPos += countElement;
                }

            } else if(block instanceof MiningLoopVectors){
                MiningInputStream data = dataSet;
                int countElement = data.getVectorsNumber() / handlersNumber;
                int mod = data.getVectorsNumber() % handlersNumber;
                blocks[0] = new MiningLoopVectors(functionSettings, startPos, countElement + mod, block.getIteration());
                startPos += countElement + mod;
                for (int i = 1; i < handlersNumber; i++) {
                    blocks[i] = new MiningLoopVectors(functionSettings, startPos, countElement, block.getIteration());
                    startPos += countElement;
                }
            }
        }

        List<EMiningModel> models = fork(model);

        if(memoryType == MemoryType.distributed) {
            model.join(models);
            return model;
        } else {
            return models.get(0);
        }
    }

	private List<EMiningModel> fork(EMiningModel model)
			throws MiningException {

        ExecutionHandlerFactory handlerFactory = algorithmSettings.getEnvironment().getExecutionHandlerFactory();

        // init and start all handlers
        List<ExecutionHandler> handlers = new ArrayList<ExecutionHandler>();
        for (int i = 0; i < handlersNumber; i++) {
            ExecutionHandler h = handlerFactory.create();
            handlers.add(h);
            if(memoryType == MemoryType.distributed)
                h.start(blocks[i], (EMiningModel) model.clone());
            else
                h.start(blocks[i], (EMiningModel) model.share());
        }

        // finished all handlers
        ArrayList<EMiningModel> resModels = new ArrayList<EMiningModel>();
        for (ExecutionHandler handler : handlers) {
            resModels.add(handler.getModel());
        }

        return resModels;
    }

    /*
     *   Definition listeners for parallel block
     */
    protected ArrayList<BlockExecuteListener> listenersBeforeSplit = new ArrayList<BlockExecuteListener>();
    protected ArrayList<BlockExecuteListener> listenersAfterSplit = new ArrayList<BlockExecuteListener>();
    protected ArrayList<BlockExecuteListener> listenersBeforeJoin = new ArrayList<BlockExecuteListener>();
    protected ArrayList<BlockExecuteListener> listenersAfterJoin = new ArrayList<BlockExecuteListener>();

    public void addListenerExecute(BlockExecuteListener listener) {
        super.addListenerExecute(listener);
        addListener(listener, listenersBeforeSplit);
        addListener(listener, listenersAfterSplit);
        addListener(listener, listenersBeforeJoin);
        addListener(listener, listenersAfterJoin);
    }

    public void addListenerBeforeSplit(BlockExecuteListener listener) {
        MiningBlock.addListener(listener, listenersBeforeSplit);
    }

    public void notifyBeforeSplit() {
        MiningBlock.notifyListeners(this, listenersBeforeSplit, EventType.BeforeSplit);
    }

    public void removeListenerBeforeSplit(BlockExecuteListener listener) {
        MiningBlock.removeListener(listener, listenersBeforeSplit);
    }

    public void addListenerAfterSplit(BlockExecuteListener listener) {
        MiningBlock.addListener(listener, listenersAfterSplit);
    }

    public void notifyAfterSplit() {
        MiningBlock.notifyListeners(this, listenersAfterSplit, EventType.AfterSplit);
    }

    public void removeListenerAfterSplit(BlockExecuteListener listener) {
        MiningBlock.removeListener(listener, listenersAfterSplit);
    }

    public void addListenerBeforeJoin(BlockExecuteListener listener) {
        MiningBlock.addListener(listener, listenersBeforeJoin);
    }

    public void notifyBeforeJoin() {
        MiningBlock.notifyListeners(this, listenersBeforeJoin, EventType.BeforeJoin);
    }

    public void removeListenerBeforeJoin(BlockExecuteListener listener) {
        MiningBlock.removeListener(listener, listenersBeforeJoin);
    }

    public void addListenerAfterJoin(BlockExecuteListener listener) {
        MiningBlock.addListener(listener, listenersAfterJoin);
    }

    public void notifyAfterJoin() {
        MiningBlock.notifyListeners(this, listenersAfterJoin, EventType.AfterJoin);
    }

    public void removeListenerAfterJoin(BlockExecuteListener listener) {
        MiningBlock.removeListener(listener, listenersAfterJoin);
    }

    public void removeAllListeners() {
        listenersBeforeSplit.clear();
        listenersAfterSplit.clear();
        listenersBeforeJoin.clear();
        listenersAfterJoin.clear();
    }


}
