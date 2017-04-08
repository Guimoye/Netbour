package jvm.ncatz.netbour.pck_fragment.list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import jvm.ncatz.netbour.R;
import jvm.ncatz.netbour.pck_adapter.AdpIncidence;
import jvm.ncatz.netbour.pck_interface.FrgBack;
import jvm.ncatz.netbour.pck_interface.presenter.PresenterIncidence;
import jvm.ncatz.netbour.pck_pojo.PoIncidence;
import jvm.ncatz.netbour.pck_presenter.PresenterIncidenceImpl;

public class FrgIncidence extends Fragment implements PresenterIncidence.View {
    private ListIncidence callback;
    private FrgBack callbackBack;

    private PresenterIncidenceImpl presenterIncidence;
    private AdpIncidence adpIncidence;

    @BindView(R.id.fragListIncidence_list)
    ListView incidenceList;
    @BindView(R.id.fragListIncidence_empty)
    TextView incidenceEmpty;

    @OnItemClick(R.id.fragListIncidence_list)
    public void itemClick(int position) {
        //
    }

    public interface ListIncidence {
        //
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        List<PoIncidence> list = new ArrayList<>();
        adpIncidence = new AdpIncidence(getActivity(), list);
        presenterIncidence = new PresenterIncidenceImpl(this);

        presenterIncidence.instanceFirebase();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_incidence, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        incidenceList.setAdapter(adpIncidence);
        incidenceList.setDivider(null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (ListIncidence) context;
        callbackBack = (FrgBack) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
        callbackBack = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        callbackBack.backFromForm();
        presenterIncidence.attachFirebase();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenterIncidence.dettachFirebase();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void returnList(List<PoIncidence> list) {
        incidenceList.setVisibility(View.VISIBLE);
        incidenceEmpty.setVisibility(View.GONE);
        updateList(list);
    }

    @Override
    public void returnListEmpty() {
        incidenceList.setVisibility(View.GONE);
        incidenceEmpty.setVisibility(View.VISIBLE);
        List<PoIncidence> list = new ArrayList<>();
        updateList(list);
    }

    private void updateList(List<PoIncidence> list) {
        adpIncidence.clear();
        adpIncidence.addAll(list);
    }
}
