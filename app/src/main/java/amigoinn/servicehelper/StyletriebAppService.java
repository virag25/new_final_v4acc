package amigoinn.servicehelper;



import amigoinn.models.MyPojoRoute;
import amigoinn.models.MyPojoRouteDetails;
import amigoinn.models.MyPojotaskDetails;
import amigoinn.models.MyPojotaskList;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by LENOVO on 25-06-2015.
 */
public interface StyletriebAppService
{


    //    @FormUrlEncoded
//    @POST("/schooluserReg.php")
//    public void schoolRegistration(@FieldMap Map<String, String> map, Callback<Registration> callback);
//    @FormUrlEncoded
//    @POST("/individualuserReg.php")
//    public void individualRegistration(@FieldMap Map<String, String> map, Callback<Registration> callback);
//
//    @FormUrlEncoded
//    @POST("/userlogin.php")
//    public void login(@FieldMap Map<String, String> map, Callback<LoginResponse> callback);
//
//    @FormUrlEncoded
//    @POST("/booklist.php")
//    public void registrationBookList(@FieldMap Map<String, String> map, Callback<BookList> callback);
//
//    @FormUrlEncoded
//    @POST("/booklistwithlogin.php")
//    public void lessionList(@FieldMap Map<String, String> map, Callback<LessionList> callback);
//
//    @FormUrlEncoded
//    @POST("/forgotpassword.php")
//    public void forgotPassword(@FieldMap Map<String, String> map, Callback<ForgotPassword> callback);
//
//    @FormUrlEncoded
//    @POST("/lessonlist.php")
//    public void indexList(@FieldMap Map<String, String> map, Callback<IndexList> callback);
//
//    @FormUrlEncoded
//    @POST("/country.php")
//    public void countryList(@FieldMap Map<String, String> map, Callback<CountryList> callback);
//    @FormUrlEncoded
//    @POST("/index.php/api/homepage")
//    public void home_products(@FieldMap Map<String, String> map, Callback<myPojo> callback);
//    @FormUrlEncoded
//    @POST("/index.php/api/lssections")
//    public void filter_section(@FieldMap Map<String, String> map, Callback<MypojoFilter> callback);
//
//    @FormUrlEncoded
//    @POST("/index.php/api/lscolors")
//    public void filter_Colors(@FieldMap Map<String, String> map, Callback<MyPojoFilterColors> callback);
//

    @GET("/tasks.php")
    public void getTasksList(@Query("assigneeid") String assigneeid, Callback<MyPojotaskList> callback);

    @GET("/subtasks.php")
    public void getTasksDetails(@Query("taskid") String assigneeid, Callback<MyPojotaskDetails[]> callback);

    @GET("/root.php")
    public void getRootInfo(@Query("userid") String userid, Callback<MyPojoRoute> callback);

    @GET("/rootdetails.php")
    public void getRootDetails(@Query("rootid") String rootid, Callback<MyPojoRouteDetails> callback);

//    @GET("/updatetask.php")
//    public void updateTasks(@Query("status") String status,@Query("taskid") String taskid, Callback<MyPojotaskDetails[]> callback);
//
//    @GET("/subtasks.php")
//    public void getTasksDetails(@Query("taskid") String assigneeid, Callback<MyPojotaskDetails[]> callback);

//
//    @GET("/p3.2.2.php")
//    public void Documents(@Query("student_id") String parent_id, Callback<MyPojoDocuments> callback);
//
//    @GET("/p3.2.1.php")
//    public void GetAudio(@Query("student_id") String parent_id, Callback<MyPojoDocuments> callback);
//
//    @GET("/p3.2.3.php")
//    public void GetVideo(@Query("student_id") String parent_id, Callback<MyPojoDocuments> callback);
//
//    @GET("/p4.1.1.php")
//    public void GetVideoCo(@Query("student_id") String parent_id, Callback<MyPojoDocuments> callback);
//
//    @GET("/p2.1.2.1.php")
//    public void Attendance_Graph(@Query("student_id") String parent_id, Callback<MyPojoAttendance[]> callback);
//
//    @GET("/p7.1.3.php")
//    public void getComplaintDetails(@Query("mobile") String parent_id, Callback<viewcomplaintpojo> callback);
//    //future exams for getting exams
//    @GET("/p2.4.1.php")
//    public void Exams_List(@Query("student_id") String parent_id, Callback<MyPojoExamsList[]> callback);
//
//    @GET("/mcq_subject.php")
//    public void SubjectMcq(@Query("section_id") String parent_id, Callback<MyPojoMock> callback);
//
//    @GET("/mcq_examlist.php")
//    public void ExamsMcq(@Query("subject_id") String parent_id, Callback<MyPojoExamMcq> callback);
//
//    @GET("/mcqs.php")
//    public void McqQuestions(@Query("examdetailid") String parent_id, Callback<MyPojoMcqQuestions> callback);
//
//    @GET("/student_list.php")
//    public void getStudentList(@Query("student_id") String parent_id, Callback<MyPojoExamsList[]> callback);
//
//    @GET("/p6.1.1.php")
//    public void Fee_Overview(@Query("admissionid") String parent_id, @Query("year") String year, @Query("section_id") String section_id, Callback<PojoFee[]> callback);
//
//    @GET("/pvi.php")
//    public void Get_Image(@Query("student_id") String parent_id, Callback<MyPojoAttendanceDetails> callback);
//
////    @FormUrlEncoded
//    @GET("/p2.4.2.php")//getting exam details for exam
//    public void Get_Exams(@Query("student_id") String parent_id, @Query("exam_id") String exam_id, Callback<PojoSubjects[]> callback);
//
//    @GET("/p6.1.2.php")
//    public void Get_Fees(@Query("student_id") String parent_id, @Query("invoice_id") String exam_id, Callback<PojoFeesDetails> callback);
}
