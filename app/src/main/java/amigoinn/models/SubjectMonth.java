package amigoinn.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maulik Patel on 10/14/2015.
 */
public class SubjectMonth
{



        @SerializedName("subject")
        @Expose
        private List<String> subject = new ArrayList<String>();
        @SerializedName("month")
        @Expose
        private List<String> month = new ArrayList<String>();
        @SerializedName("subject_id")
        @Expose
        private List<String> subject_id = new ArrayList<String>();

        public List<String> getSubject_id() {
                return subject_id;
        }

        /**
         *
         * @param //subject_id
         * The subject
         */
        public void setSubject_d(List<String> subject_id) {
                this.subject_id = subject_id;
        }

        /**
         *
         * @return
         * The subject
         */
        public List<String> getSubject() {
            return subject;
        }

        /**
         *
         * @param subject
         * The subject
         */
        public void setSubject(List<String> subject) {
            this.subject = subject;
        }

        /**
         *
         * @return
         * The month
         */
        public List<String> getMonth() {
            return month;
        }

        /**
         *
         * @param month
         * The month
         */
        public void setMonth(List<String> month) {
            this.month = month;
        }



}
