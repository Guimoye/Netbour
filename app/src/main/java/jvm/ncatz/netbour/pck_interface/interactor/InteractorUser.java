package jvm.ncatz.netbour.pck_interface.interactor;

import java.util.List;

import jvm.ncatz.netbour.pck_pojo.PoUser;

public interface InteractorUser {

    void instanceFirebase(String code);

    void attachFirebase();

    void dettachFirebase();

    void editUser(PoUser user);

    void deleteUser(PoUser item);

    interface Listener {

        void returnList(List<PoUser> list);

        void returnListEmpty();

        void editedUser();

        void deletedUser(PoUser item);
    }
}
