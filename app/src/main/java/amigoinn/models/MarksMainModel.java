package amigoinn.models;

/**
 * Created by Maulik Patel on 10/12/2015.
 */
public class MarksMainModel
{
    public String mark_obtained;

    public String getMark_obtained() {
        return mark_obtained;
    }

    public void setMark_obtained(String mark_obtained) {
        this.mark_obtained = mark_obtained;
    }

    public String getMark_total() {
        return mark_total;
    }

    public void setMark_total(String mark_total) {
        this.mark_total = mark_total;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getExam_name() {
        return exam_name;
    }

    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String mark_total,subject_name,exam_name,date,comment,rank;
}
