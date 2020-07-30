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

@DynamoDBTable(tableName = "parentteacher-mobilehub-2099914171-QueryTable")

public class QueryTableDO {
    private String _mobileNumber;
    private String _uniqueId;
    private String _answer;
    private String _question;

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
    @DynamoDBAttribute(attributeName = "Answer")
    public String getAnswer() {
        return _answer;
    }

    public void setAnswer(final String _answer) {
        this._answer = _answer;
    }
    @DynamoDBAttribute(attributeName = "Question")
    public String getQuestion() {
        return _question;
    }

    public void setQuestion(final String _question) {
        this._question = _question;
    }

}
