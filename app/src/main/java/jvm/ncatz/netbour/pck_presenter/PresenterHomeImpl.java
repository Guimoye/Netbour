package jvm.ncatz.netbour.pck_presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import jvm.ncatz.netbour.pck_interface.presenter.PresenterHome;
import jvm.ncatz.netbour.pck_pojo.PoCommunity;
import jvm.ncatz.netbour.pck_pojo.PoDocument;
import jvm.ncatz.netbour.pck_pojo.PoEntry;
import jvm.ncatz.netbour.pck_pojo.PoIncidence;
import jvm.ncatz.netbour.pck_pojo.PoMeeting;
import jvm.ncatz.netbour.pck_pojo.PoUser;

public class PresenterHomeImpl implements PresenterHome {

    private PresenterHome.Activity activity;

    public PresenterHomeImpl(PresenterHome.Activity activity) {
        this.activity = activity;
    }

    @Override
    public void getCurrentUser() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            PoUser us = snapshot.getValue(PoUser.class);
                            if (!us.isDeleted()) {
                                if (us.getEmail().equals(user.getEmail())) {
                                    activity.getCurrentUserResponseUser(us.getCommunity(), us.getName(), us.getPhoto(), us.getEmail(), us.getCategory());
                                }
                            } else {
                                activity.getCurrentUserResponseClose();
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    activity.getCurrentUserResponseFailure();
                }
            });
        }
    }

    @Override
    public void reInsertCommunity(PoCommunity item) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference().child("communities").child(item.getCode());
        databaseReference.child("deleted").setValue(false);
        activity.reInsertResponse();
    }

    @Override
    public void reInsertDocument(PoDocument item, String actual_code) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference().child("communities").child(actual_code).child("documents").child(String.valueOf(item.getKey()));
        databaseReference.child("deleted").setValue(false);
        activity.reInsertResponse();
    }

    @Override
    public void reInsertEntry(PoEntry item, String actual_code) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference().child("communities").child(actual_code).child("entries").child(String.valueOf(item.getKey()));
        databaseReference.child("deleted").setValue(false);
        activity.reInsertResponse();
    }

    @Override
    public void reInsertIncidence(PoIncidence item, String actual_code) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference().child("communities").child(actual_code).child("incidences").child(String.valueOf(item.getKey()));
        databaseReference.child("deleted").setValue(false);
        activity.reInsertResponse();
    }

    @Override
    public void reInsertMeeting(PoMeeting item, String actual_code) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference().child("communities").child(actual_code).child("meetings").child(String.valueOf(item.getKey()));
        databaseReference.child("deleted").setValue(false);
        activity.reInsertResponse();
    }

    @Override
    public void reInsertUser(PoUser item) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference().child("users").child(String.valueOf(item.getKey()));
        databaseReference.child("deleted").setValue(false);
        activity.reInsertResponse();
    }
}
