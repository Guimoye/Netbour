package jvm.ncatz.netbour.pck_pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class PoMeeting implements Parcelable {
    private long createdAt;
    private String date;
    private String description;
    private boolean deleted;

    public PoMeeting() {
        //
    }

    public PoMeeting(long createdAt, String date, String description, boolean deleted) {
        this.createdAt = createdAt;
        this.date = date;
        this.description = description;
        this.deleted = deleted;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    protected PoMeeting(Parcel in) {
        createdAt = in.readLong();
        date = in.readString();
        description = in.readString();
        deleted = in.readByte() != 0;
    }

    public static final Creator<PoMeeting> CREATOR = new Creator<PoMeeting>() {
        @Override
        public PoMeeting createFromParcel(Parcel in) {
            return new PoMeeting(in);
        }

        @Override
        public PoMeeting[] newArray(int size) {
            return new PoMeeting[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(createdAt);
        dest.writeString(date);
        dest.writeString(description);
        dest.writeByte((byte) (deleted ? 1 : 0));
    }
}
