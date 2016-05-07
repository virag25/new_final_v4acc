package amigoinn.db_model;

import java.util.ArrayList;
import java.util.List;

import amigoinn.activerecordbase.ActiveRecordBase;
import amigoinn.activerecordbase.ActiveRecordException;
import amigoinn.activerecordbase.CamelNotationHelper;
import amigoinn.example.v4sales.AccountApplication;
import amigoinn.modelmapper.ModelMapper;

/**
 * Created by Virag kuvadia on 24-04-2016.
 */
public class TaskInfo extends ActiveRecordBase {

    @ModelMapper(JsonKey = "category_id")
    public int category_id = 0;
    @ModelMapper(JsonKey = "parent_category_id")
    public int parent_category_id = 0;
    @ModelMapper(JsonKey = "name")
    public String name = "";
    @ModelMapper(JsonKey = "specification")
    public String specification = "";
    @ModelMapper(JsonKey = "image_name")
    public String image_name = "";
    @ModelMapper(JsonKey = "image")
    public String image = "";
    @ModelMapper(JsonKey = "created")
    public String created = "";
    @ModelMapper(JsonKey = "modified")
    public String modified = "";
    public boolean is_deleted = false;
    public boolean is_active = false;

    public static ArrayList<TaskInfo> getAllTask() {
        ArrayList<TaskInfo> m_list = new ArrayList<TaskInfo>();
        try {
            List<TaskInfo> lst = AccountApplication.Connection().findAll(
                    TaskInfo.class);
            if (lst != null && lst.size() > 0) {
                m_list = new ArrayList<TaskInfo>(lst);
            }
        } catch (ActiveRecordException e) {
            e.printStackTrace();
        }
        return m_list;
    }

    public static int getCategoryIdByName(String name) {
        try {
            List<TaskInfo> lst = AccountApplication.Connection().find(
                    TaskInfo.class,
                    CamelNotationHelper.toSQLName("name") + "=?",
                    new String[] { String.valueOf(name) });
            if (lst != null && lst.size() > 0) {
                return lst.get(0).category_id;
            }
        } catch (ActiveRecordException e) {
            e.printStackTrace();
        }
        return 0;
    }



    public static TaskInfo getCategoryInfoById(int category_id) {
        try {
            List<TaskInfo> lst = AccountApplication.Connection().find(
                    TaskInfo.class,
                    CamelNotationHelper.toSQLName("category_id") + "=?",
                    new String[] { String.valueOf(category_id) });
            if (lst != null && lst.size() > 0) {
                return lst.get(0);
            }
        } catch (ActiveRecordException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCatNameById(int category_id) {
        try {
            List<TaskInfo> lst = AccountApplication.Connection().find(
                    TaskInfo.class,
                    CamelNotationHelper.toSQLName("category_id") + "=?",
                    new String[] { String.valueOf(category_id) });
            if (lst != null && lst.size() > 0) {
                return lst.get(0).name;
            }
        } catch (ActiveRecordException e) {
            e.printStackTrace();
        }
        return null;
    }
    

}
