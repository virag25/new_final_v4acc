package amigoinn.models;

/**
 * Created by maulik1293 on 5/4/2016.
 */
public class TaskDetails
{
    public String Task_Id;
    public String Task_Status;
    public String Task_UploadStatus;

    public String getTask_Id() {
        return Task_Id;
    }

    public void setTask_Id(String task_Id) {
        Task_Id = task_Id;
    }

    public String getTask_Status() {
        return Task_Status;
    }

    public void setTask_Status(String task_Status) {
        Task_Status = task_Status;
    }

    public String getTask_UploadStatus() {
        return Task_UploadStatus;
    }

    public void setTask_UploadStatus(String task_UploadStatus) {
        Task_UploadStatus = task_UploadStatus;
    }

    public String getTask_AsigneeId() {
        return Task_AsigneeId;
    }

    public void setTask_AsigneeId(String task_AsigneeId) {
        Task_AsigneeId = task_AsigneeId;
    }

    public String getTask_AsigneerId() {
        return Task_AsigneerId;
    }

    public void setTask_AsigneerId(String task_AsigneerId) {
        Task_AsigneerId = task_AsigneerId;
    }

    public String Task_AsigneeId;
    public String Task_AsigneerId;

}
