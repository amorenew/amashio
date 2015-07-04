package co.mazeed.smsproject.controller.communication;


public interface DataRequestor {
	void onStart(Task task);

	void onFinish(Task task);

	void handleClick();
}
