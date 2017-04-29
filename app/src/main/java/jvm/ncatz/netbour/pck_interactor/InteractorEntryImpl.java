package jvm.ncatz.netbour.pck_interactor;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import jvm.ncatz.netbour.pck_interface.interactor.InteractorEntry;
import jvm.ncatz.netbour.pck_pojo.PoEntry;

public class InteractorEntryImpl implements InteractorEntry {
    private InteractorEntry.Listener listener;

    private DatabaseReference databaseReference;
    private Query query;
    private ValueEventListener eventListener;

    private String communityCode;

    public InteractorEntryImpl(InteractorEntry.Listener listener) {
        this.listener = listener;
    }

    @Override
    public void instanceFirebase(String code, final int category) {
        communityCode = code;
        query = FirebaseDatabase.getInstance().getReference().child("communities").child(code).child("entries").orderByKey();
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<PoEntry> list = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        PoEntry entry = snapshot.getValue(PoEntry.class);
                        if (!entry.isDeleted()) {
                            if (entry.getCategory() == category) {
                                list.add(entry);
                            }
                        }
                    }
                    if (list.size() > 0) {
                        listener.returnList(list);
                    } else {
                        listener.returnListEmpty();
                    }
                } else {
                    listener.returnListEmpty();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.returnListEmpty();
            }
        };
    }

    @Override
    public void attachFirebase() {
        query.addValueEventListener(eventListener);
    }

    @Override
    public void dettachFirebase() {
        if (eventListener != null) {
            query.removeEventListener(eventListener);
        }
    }

    @Override
    public void addEntry(PoEntry entry, String code) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("communities").child(code).child("entries").child(String.valueOf(entry.getKey()));
        databaseReference.setValue(entry);
        listener.addedEntry();
    }

    @Override
    public void editEntry(PoEntry entry, String code) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("communities").child(code).child("entries").child(String.valueOf(entry.getKey()));
        databaseReference.child("content").setValue(entry.getContent());
        databaseReference.child("title").setValue(entry.getTitle());
        listener.editedEntry();
    }

    @Override
    public void deleteEntry(PoEntry item) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("communities").child(communityCode).child("entries").child(String.valueOf(item.getKey()));
        databaseReference.child("deleted").setValue(true);
        listener.deletedEntry(item);
    }
}
