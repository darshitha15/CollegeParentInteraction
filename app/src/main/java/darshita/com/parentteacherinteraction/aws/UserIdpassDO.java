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

@DynamoDBTable(tableName = "parentteacher-mobilehub-2099914171-UserIdpass")

public class UserIdpassDO {
    private String _mobileNumber;
    private String _category;
    private String _gcmid;
    private String _password;

    @DynamoDBHashKey(attributeName = "MobileNumber")
    @DynamoDBAttribute(attributeName = "MobileNumber")
    public String getMobileNumber() {
        return _mobileNumber;
    }

    public void setMobileNumber(final String _mobileNumber) {
        this._mobileNumber = _mobileNumber;
    }
    @DynamoDBAttribute(attributeName = "Category")
    public String getCategory() {
        return _category;
    }

    public void setCategory(final String _category) {
        this._category = _category;
    }
    @DynamoDBAttribute(attributeName = "Gcmid")
    public String getGcmid() {
        return _gcmid;
    }

    public void setGcmid(final String _gcmid) {
        this._gcmid = _gcmid;
    }
    @DynamoDBAttribute(attributeName = "Password")
    public String getPassword() {
        return _password;
    }

    public void setPassword(final String _password) {
        this._password = _password;
    }

}
