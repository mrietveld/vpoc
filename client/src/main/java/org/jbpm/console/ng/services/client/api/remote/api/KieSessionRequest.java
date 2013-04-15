package org.jbpm.console.ng.services.client.api.remote.api;

import java.util.Collection;
import java.util.Map;

import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.runtime.process.WorkItemManager;

public interface KieSessionRequest {

    /**
     * Start a new process instance. The process (definition) that should be
     * used is referenced by the given process id.
     * 
     * @param processId
     *            The id of the process that should be started
     * @return the <code>ProcessInstance</code> that represents the instance of
     *         the process that was started
     */
    ProcessInstance startProcess(String processId);

    /**
     * Start a new process instance. The process (definition) that should be
     * used is referenced by the given process id. Parameters can be passed to
     * the process instance (as name-value pairs), and these will be set as
     * variables of the process instance.
     * 
     * @param processId
     *            the id of the process that should be started
     * @param parameters
     *            the process variables that should be set when starting the
     *            process instance
     * @return the <code>ProcessInstance</code> that represents the instance of
     *         the process that was started
     */
    ProcessInstance startProcess(String processId, Map<String, Object> parameters);

    /**
     * Signals the engine that an event has occurred. The type parameter defines
     * which type of event and the event parameter can contain additional
     * information related to the event. All process instances that are
     * listening to this type of (external) event will be notified. For
     * performance reasons, this type of event signaling should only be used if
     * one process instance should be able to notify other process instances.
     * For internal event within one process instance, use the signalEvent
     * method that also include the processInstanceId of the process instance in
     * question.
     * 
     * @param type
     *            the type of event
     * @param event
     *            the data associated with this event
     */
    void signalEvent(String type, Object event);

    /**
     * Signals the process instance that an event has occurred. The type
     * parameter defines which type of event and the event parameter can contain
     * additional information related to the event. All node instances inside
     * the given process instance that are listening to this type of (internal)
     * event will be notified. Note that the event will only be processed inside
     * the given process instance. All other process instances waiting for this
     * type of event will not be notified.
     * 
     * @param type
     *            the type of event
     * @param event
     *            the data associated with this event
     * @param processInstanceId
     *            the id of the process instance that should be signaled
     */
    void signalEvent(String type, Object event, long processInstanceId);

    /**
     * Returns a collection of currently active process instances. Note that
     * only process instances that are currently loaded and active inside the
     * engine will be returned. When using persistence, it is likely not all
     * running process instances will be loaded as their state will be stored
     * persistently. It is recommended not to use this method to collect
     * information about the state of your process instances but to use a
     * history log for that purpose.
     * 
     * @return a collection of process instances currently active in the session
     */
    Collection<ProcessInstance> getProcessInstanceStatus();

    /**
     * Returns the process instance with the given id. Note that only active
     * process instances will be returned. If a process instance has been
     * completed already, this method will return <code>null</code>.
     * 
     * @param id
     *            the id of the process instance
     * @return the process instance with the given id or <code>null</code> if it
     *         cannot be found
     */
    ProcessInstance getProcessInstanceStatus(long processInstanceId);

    /**
     * Returns the process instance with the given id. Note that only active
     * process instances will be returned. If a process instance has been
     * completed already, this method will return <code>null</code>.
     * 
     * @param id
     *            the id of the process instance
     * @param readonly
     *            if this is true and when using persistence, this process
     *            instance will not be tracked and updated by the engine
     * @return the process instance with the given id or <code>null</code> if it
     *         cannot be found
     */
    ProcessInstance getProcessInstance(long processInstanceId, boolean readonly);

    /**
     * Aborts the process instance with the given id. If the process instance
     * has been completed (or aborted), or the process instance cannot be found,
     * this method will throw an <code>IllegalArgumentException</code>.
     * 
     * @param id
     *            the id of the process instance
     */
    void abortProcessInstance(long processInstanceId);

    /**
     * Returns the <code>WorkItemManager</code> related to this session. This
     * can be used to register new <code>WorkItemHandler</code>s or to complete
     * (or abort) <code>WorkItem</code>s.
     * 
     * @return the <code>WorkItemManager</code> related to this session
     */
    WorkItemManagerRequest getWorkItemManager();

}
