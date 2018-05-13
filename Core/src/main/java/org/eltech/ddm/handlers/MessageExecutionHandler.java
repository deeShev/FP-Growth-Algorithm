package org.eltech.ddm.handlers;

import org.eltech.ddm.miningcore.algorithms.ExecutionHandler;

public abstract class MessageExecutionHandler extends ExecutionHandler {

	public MessageExecutionHandler(ExecutionSettings settings) {
		super(settings);
		// TODO Auto-generated constructor stub
	}

	/**
	 * ���������� ������ �� ���������-����������
	 *
	 * @return ��������-����������
	 */
	public abstract Object getReceiver();

	/**
	 * Send message for receiver
	 * @param message
	 */
	public abstract void send(Object message);

	/**
	 * Receive message
	 * @param message
	 */
	public abstract Object receive();
}
