package darshita.com.parentteacherinteraction.aws;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "parentteacher-mobilehub-2099914171-TotalDetailsSubjects")

public class TotalDetailsSubjectsDO {
    private String _mobileNumber;
    private String _uniqueId;
    private String _attendance;
    private String _name;
    private String _percentage;
    private String _rollno;
    private List<String> _subjects;

    @DynamoDBHashKey(attributeName = "MobileNumber")
    @DynamoDBAttribute(attributeName = "MobileNumber")
    public String getMobileNumber() {
        return _mobileNumber;
    }

    public void setMobileNumber(final String _mobileNumber) {
        this._mobileNumber = _mobileNumber;
    }
    @DynamoDBRangeKey(attributeName = "UniqueId")
    @DynamoDBAttribute(attributeName = "UniqueId")
    public String getUniqueId() {
        return _uniqueId;
    }

    public void setUniqueId(final String _uniqueId) {
        this._uniqueId = _uniqueId;
    }
    @DynamoDBAttribute(attributeName = "Attendance")
    public String getAttendance() {
        return _attendance;
    }

    public void setAttendance(final String _attendance) {
        this._attendance = _attendance;
    }
    @DynamoDBAttribute(attributeName = "Name")
    public String getName() {
        return _name;
    }

    public void setName(final String _name) {
        this._name = _name;
    }
    @DynamoDBAttribute(attributeName = "Percentage")
    public String getPercentage() {
        return _percentage;
    }

    public void setPercentage(final String _percentage) {
        this._percentage = _percentage;
    }
    @DynamoDBAttribute(attributeName = "Rollno")
    public String getRollno() {
        return _rollno;
    }

    public void setRollno(final String _rollno) {
        this._rollno = _rollno;
    }
    @DynamoDBAttribute(attributeName = "Subjects")
    public List<String> getSubjects() {
        return _subjects;
    }

    public void setSubjects(final List<String> _subjects) {
        this._subjects = _subjects;
    }

}
