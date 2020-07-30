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

@DynamoDBTable(tableName = "parentteacher-mobilehub-2099914171-Events")

public class EventsDO {
    private String _uniqueId;
    private String _eventDetails;
    private String _imageUrl;

    @DynamoDBHashKey(attributeName = "UniqueId")
    @DynamoDBAttribute(attributeName = "UniqueId")
    public String getUniqueId() {
        return _uniqueId;
    }

    public void setUniqueId(final String _uniqueId) {
        this._uniqueId = _uniqueId;
    }
    @DynamoDBAttribute(attributeName = "EventDetails")
    public String getEventDetails() {
        return _eventDetails;
    }

    public void setEventDetails(final String _eventDetails) {
        this._eventDetails = _eventDetails;
    }
    @DynamoDBAttribute(attributeName = "ImageUrl")
    public String getImageUrl() {
        return _imageUrl;
    }

    public void setImageUrl(final String _imageUrl) {
        this._imageUrl = _imageUrl;
    }

}
